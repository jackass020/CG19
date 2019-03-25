#ifndef XMLCheckResult
#define XMLCheckResult(a_eResult) if (a_eResult != XML_SUCCESS) { printf("Error: %i\n", a_eResult); return a_eResult; }
#endif

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

#include <stdlib.h>


#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define USE_MATH_DEFINES
#include <cmath>

#include <fstream>
#include <vector>
#include <string>
#include <set>
#include <iostream>;
#include "tinyxml2.h"

using namespace tinyxml2;
using namespace std;

typedef std::string Path;

typedef struct pathInfo{
    Path path;
    float traX=0,traY=0,traZ=0;
    float angle,rotX=0,rotY=0,rotZ=0;
    float scaleX=1,scaleY=1,scaleZ=1;
} Paths;
typedef struct coordinate {
	float x;
	float y;
	float z;
}Vertex;

typedef std::vector<Vertex> Triangle;

static bool operator<(const coordinate &a1, const coordinate &a2) {
    return true;
}
typedef struct groupData{
    float traX, traY,traZ;
    float angle,rotX, rotY, rotZ;
    float scaleX, scaleY, scaleZ;

}Group;

typedef std::vector<Triangle> Model;

typedef struct modelData{
    Model model;
    float traX=0, traY=0,traZ=0;
    float angle,rotX=0, rotY=0, rotZ=0;
    float scaleX=1, scaleY=1, scaleZ=1;
}ModelData;

static bool operator<(const modelData &a1, const modelData &a2) {
    return true;
}

typedef std::set<ModelData> Models;
bool axes = false;
bool clicked = false;
bool lines = false;
float alpha = 0.0;
float beta = 0.0;
float dx = 0.0,dy = 0.0,dz = -1;
float dxP = 1.0,dzP = 0.0;
float px = 0.0,py = 0.0,pz = 20.0;
float k = 1;

Paths paths [1024];
Group groups[1024];
int paths_size = 0;
Models modelz;

int readModels(XMLElement* elem, int nr, int i) {
	const char* attr;
	for (XMLElement* aux = elem; aux != nullptr; aux = aux->NextSiblingElement()) {
		string nome_elem = aux->Value();
		if (nome_elem == "model") {
			attr = aux->Attribute("file");//retira o tipo
			if (attr != nullptr) {
				paths[i].path = attr;//adicionar o path do ficheiro
				paths[i].traX = groups[nr].traX; //Adicionar no array paths os valores armazenados
				paths[i].traY = groups[nr].traY; //Adicionar no array paths os valores armazenados
				paths[i].traZ = groups[nr].traZ; //Adicionar no array paths os valores armazenados
				paths[i].angle = groups[nr].angle; //Adicionar no array paths os valores armazenados
				paths[i].rotX = groups[nr].rotX; //Adicionar no array paths os valores armazenados
				paths[i].rotY = groups[nr].rotY; //Adicionar no array paths os valores armazenados
				paths[i].rotZ = groups[nr].rotZ; //Adicionar no array paths os valores armazenados
				paths[i].scaleX = groups[nr].scaleX; //Adicionar no array paths os valores armazenados
				paths[i].scaleY = groups[nr].scaleY; //Adicionar no array paths os valores armazenados
				paths[i].scaleZ = groups[nr].scaleZ; //Adicionar no array paths os valores armazenados
				i++;

			}
		}
	}
	return i;
}



int groupAux(XMLElement* elem, int nr,int i){
    const char* attr;//Vai guardar o atributo que queremos
    if (nr==0){//Caso seja o primeiro "group" a ser analisado, este tem de ser inicializado, não sendo possível por a scale=0;
        groups[nr].traX=groups[nr].traY=groups[nr].traZ=0;
        groups[nr].rotX=groups[nr].rotY=groups[nr].rotZ=groups[nr].angle=0;
        groups[nr].scaleX=groups[nr].scaleY=groups[nr].scaleZ=1;
    }
    else{//Caso seja um sub-"group" dentro do "group", o sub-"group" vai ter de herdar os valores do "group".
        groups[nr].traX=groups[nr-1].traX;
        groups[nr].traY=groups[nr-1].traY;
        groups[nr].traZ=groups[nr-1].traZ;
        groups[nr].rotX=groups[nr-1].rotX;
        groups[nr].rotY=groups[nr-1].rotY;
        groups[nr].rotZ=groups[nr-1].rotZ;
        groups[nr].scaleX=groups[nr-1].scaleX;
        groups[nr].scaleY=groups[nr-1].scaleY;
        groups[nr].scaleZ=groups[nr-1].scaleZ;
    }

    for(XMLElement* aux = elem;aux!= nullptr;aux = aux->NextSiblingElement()){//itera todos os model
        string nome_elem = aux->Value();
        if(nome_elem=="translate"){//Caso seja translate, vai se armazenar os valores
            attr= aux-> Attribute("X");//é retirado o atributo de X
           	if(attr!=nullptr) if(strlen(attr)!=0) groups[nr].traX += strtof(attr,nullptr);//string to float
			else groups[nr].traX+=0;
			attr= aux-> Attribute("Y");//é retirado o atributo de Y
            if(attr!=nullptr) if(strlen(attr)!=0) groups[nr].traY += strtof(attr,nullptr);//string to float
			else groups[nr].traY += 0;
			attr= aux-> Attribute("Z");//é retirado o atributo de Z
            if(attr!=nullptr) if(strlen(attr)!=0) groups[nr].traZ += strtof(attr,nullptr);//string to float
			else groups[nr].traZ += 0;
        }
        if(nome_elem=="rotate"){//Caso seja rotate, vai se armazenar os valores
            attr= aux-> Attribute("angle");//é retirado o atributo de X
            if (attr!=nullptr) if(strlen(attr)!=0) groups[nr].angle += strtof(attr,nullptr);//string to float
			else groups[nr].angle = 0;
			attr= aux-> Attribute("X");//é retirado o atributo de X
            if (attr!=nullptr) if(strlen(attr)!=0) groups[nr].rotX += strtof(attr,nullptr);//string to float
			else groups[nr].rotX = 0;
			attr= aux-> Attribute("Y");//é retirado o atributo de Y
            if(attr!=nullptr) if(strlen(attr)!=0) groups[nr].rotY += strtof(attr,nullptr);//string to float
			else groups[nr].rotY = 0;
			attr= aux-> Attribute("Z");//é retirado o atributo de Z
            if(attr!=nullptr) if(strlen(attr)!=0) groups[nr].rotZ += strtof(attr,nullptr);//string to float
			else groups[nr].rotZ = 0;
		}
        if(nome_elem=="scale"){//Caso seja scale, vai se armazenar os valores
	            attr= aux-> Attribute("X");//é retirado o atributo de X
				if (attr != nullptr) {
					if (strlen(attr) != 0) groups[nr].scaleX *= strtof(attr, nullptr);//string to float
				}
				else groups[nr].scaleX *= 1;
				attr= aux-> Attribute("Y");//é retirado o atributo de Y
				if (attr != nullptr) {
					if (strlen(attr) != 0) groups[nr].scaleY *= strtof(attr, nullptr);//string to float
				}
				else groups[nr].scaleY *= 1;
				attr = aux->Attribute("Z");//é retirado o atributo de Z
				if (attr != nullptr) {
					if (strlen(attr) != 0) groups[nr].scaleZ *= strtof(attr, nullptr);//string to float 
				}
			    else groups[nr].scaleZ *= 1;

        }

		if (nome_elem == "models") {
			i = readModels(aux->FirstChildElement() ,nr, i);
		}
		if (nome_elem == "group") {//Caso seja "group" este iŕa ser um sub-group
			i += groupAux(aux->FirstChildElement(), nr + 1,i);
		
		}

    }
	return i;
}

void readXML(){
    XMLDocument doc ;
    int i = 0;
	int j = 0;
    doc.LoadFile("../../Files/Config.xml");// carrega o ficheiro XML
    XMLElement* root = doc.FirstChildElement();// Aponta para o elemento raiz= "scene"
    for(XMLElement* elem = root->FirstChildElement();elem != nullptr; elem = elem->NextSiblingElement()) {//precorre os model
        string nome_elem = elem->Value();
        if(nome_elem == "group") {
			i= groupAux(elem->FirstChildElement(),0,i);//chama a função auxiliar para poder armazenar a informação de cada grupo
			
		}

        else{
            paths_size=0;
            return;
        }
    }
    paths_size = i;
}


void loadXML() {
	float x, y, z;
	int i = 0,j=0;
	string c1, c2, c3;
	ModelData model;
	for (i=0;i<paths_size;i++) {

	    Path p = paths[i].path;

		model.traX = paths[i].traX;
        model.traY = paths[i].traY;
        model.traZ = paths[i].traZ;
        model.angle = paths[i].angle;
        model.rotX = paths[i].rotX;
        model.rotY = paths[i].rotY;
        model.rotZ = paths[i].rotZ;
        model.scaleX = paths[i].scaleX;

        model.scaleY = paths[i].scaleY;
        model.scaleZ = paths[i].scaleZ;
		ifstream file (p);//leitura do fichero
        if (file.is_open()) {//Caso o ficheiro abra
			Triangle t;
			while (getline(file, c1) && getline(file, c2) && getline(file, c3)) {// obter as 3 primeiras linha sendo estas as coordenadas(x,y,z)
				x = strtof(c1.c_str(),nullptr);//obter um apontador para o array c1 através do uso c_str
				y = strtof(c2.c_str(),nullptr);//obter um apontador para o array c2 através do uso c_str
				z = strtof(c3.c_str(),nullptr);//obter um apontador para o array c3 através do uso c_str
				Vertex v = {x,y,z};//criação do pum vértice
				t.push_back(v);//adiciona ao vetor t
				j++;
				if (j % 3 == 0) {//tendo 3 vértices, cria-se um triângulo
					model.model.push_back(t);//adiciona ao vetor model
					t.clear();
				}
			}
			file.close();
		}
		modelz.insert(model);
		model.model.clear();
	}
}

void drawTheFiles(){
    int i=0;
	for (ModelData m : modelz) {
        Model model = m.model;
        glPushMatrix();//Coloca a Matriz atual na stack
        glTranslatef(m.traX, m.traY,m.traZ);//Sendo que as funções são postas em "stack", vão ser executadas inversamente à ordem que são chamadas.
        glRotatef(m.angle, m.rotX, m.rotY, m.rotZ);//Logo Rotate->translate->scale. Rotate aplica o m.angle a um dos vértice, sendo o que está a 1.
		glScalef(m.scaleX, m.scaleY, m.scaleZ);//Neste caso é necessário executar a rotate primeiro devido ao facto da rotação deve ser feita no ponto(0,0,0).
		glBegin(GL_TRIANGLES);
        for (Triangle t : model) {
            if (i % 2 == 0)
                glColor3f(0.69,0.93, 0.93);
            else glColor3f(0.93, 0.51, 0.93);
            glVertex3f(t.at(0).x, t.at(0).y, t.at(0).z);
            glVertex3f(t.at(1).x, t.at(1).y, t.at(1).z);
            glVertex3f(t.at(2).x, t.at(2).y, t.at(2).z);
            i++;
        }
        glEnd();
        glPopMatrix();//Pop da matriz antiga sem transformações
    }
}

void drawAxes() {
	glBegin(GL_LINES);
	// Eixo X
	glColor3f(1, 0, 0); //Vermelho
	glVertex3f(0, 0, 0);
	glVertex3f(100, 0, 0);

	// Eixo Y
	glColor3f(1, 1, 0);//Amarelo
	glVertex3f(0, 0, 0);
	glVertex3f(0, 100, 0);

	// Eixo Z
	glColor3f(0.75, 1, 0);//Lima
	glVertex3f(0, 0, 0);
	glVertex3f(0, 0, 100);
	glEnd();
}



void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if(h == 0) h = 1;

	// compute window's aspect ratio
	float ratio = w * 1.0 / h;

	// Set the projection matrix as current
	glMatrixMode(GL_PROJECTION);
	// Load Identity Matrix
	glLoadIdentity();

	// Set the viewport to be the entire window
    glViewport(0, 0, w, h);

	// Set perspective
	gluPerspective(45.0f ,ratio, 1.0f ,1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}

void mouseMove(int x, int y) {
	if (clicked) {
		float w = glutGet(GLUT_WINDOW_WIDTH);
		float h = glutGet(GLUT_WINDOW_HEIGHT);

		float cx = x - (w / 2);
		float cy = -(y - (h / 2));

		float aStep = cx / w;
		float bStep = cy / h;

		alpha = aStep * (2*M_PI);
		beta = bStep * (M_PI);

		if (beta >= (M_PI / 2)) beta = (M_PI / 2) - 0.001;
		else if (beta <= (M_PI / 2)) beta = -(M_PI / 2) + 0.001;

		dx = sin(alpha);
		dy = sin(beta);
		dz = -cos(alpha);
		dxP = sin(alpha + (M_PI / 2));
		dzP = -cos(alpha + (M_PI / 2));

		glutPostRedisplay();
		
	}
}


void mouseClick(int button, int state, int x, int y) {
	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) {
		clicked = true;
	}
	else if (button == GLUT_LEFT_BUTTON && state == GLUT_UP) clicked = false;
}


void renderScene() {


	//glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(px,py,pz,
		      px + dx,py+dy,pz+dz,
			  0.0f,1.0f,0.0f);

	if (axes) drawAxes();

	if (lines) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	else glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

// put drawing instructions here
	drawTheFiles();


	// End of frame
	glutSwapBuffers();
}


void process_keys(unsigned char key, int x, int y) {

    switch (key) {
	    case'a':
		    px -= dxP * k;
	    	pz -= dzP * k;
    		glutPostRedisplay();
    		break;
    	case'd':
    		px += dxP * k;
	    	pz += dzP * k;
	    	glutPostRedisplay();
	    	break;
        case'w':
            py += dxP * k;
            glutPostRedisplay();
            break;
        case's':
            py -= dxP * k;
            glutPostRedisplay();
            break;
	    case '+':
	    	px += dx * k;
	    	py += dy * k;
    		pz += dz * k;
    		glutPostRedisplay();
	    	break;
	    case '-':
	    	px -= dx * k;
    		py -= dy * k;
    		pz -= dz * k;
    		glutPostRedisplay();
    		break;
    	case 'f':
		    axes = !axes;
		    break;
	    case 'l':
	    	lines = !lines;
	    	break;
	}
	glutPostRedisplay();
}

int main(int argc, char **argv) {

  readXML();
  loadXML();

// put GLUT init here
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(1600, 800);
	glutCreateWindow("Engine");


// put callback registration here
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);

	glutKeyboardFunc(process_keys);
	glutMotionFunc(mouseMove);
	glutPassiveMotionFunc(mouseMove);
	glutMouseFunc(mouseClick);

// OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);


// enter GLUT's main loop
	glutMainLoop();

	return 1;
}
