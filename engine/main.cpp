#ifndef XMLCheckResult
#define XMLCheckResult(a_eResult) if (a_eResult != XML_SUCCESS) { printf("Error: %i\n", a_eResult); return a_eResult; }
#endif


#include <stdlib.h>


#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif


#include <fstream>
#include <vector>
#include <string>
#include <set>
#include <iostream>
#include <cmath>
#include "tinyxml2.h"

using namespace tinyxml2;
using namespace std;

typedef struct coordinate {
	float x;
	float y;
	float z;
}Vertex;

typedef std::vector<Vertex> Triangle;

static bool operator<(const coordinate &a1, const coordinate &a2) {
    return true;
}

typedef std::vector<Triangle> Model;

typedef std::set<Model> Models;

float alpha;
float beta;
float radius = 10;

string paths [15];
int paths_size = 0;
Models modelz;

void readXML(){
    XMLDocument doc;
    int i = 0;
    doc.LoadFile("C:/Users/ricar/OneDrive/Documentos/CG/engine/Config.xml");
    XMLElement* root = doc.FirstChildElement();
    const char* type;
    for(XMLElement* elem = root->FirstChildElement();elem != NULL; elem = elem->NextSiblingElement()) {
        string nome_elem = elem->Value();
        if(nome_elem == "model") {
            type = elem->Attribute("file");
            if(type != NULL) paths[i++] = type;
        }
    }
    paths_size = i;
}


void loadXML() {
	float x, y, z;
	int i = 0,j=0;
	string c1, c2, c3;
	Model model;
	for (i=0;i<paths_size;i++) {
        string p = paths[i];
		ifstream file(p);
		if (file.is_open()) {
			Triangle t;
			while (getline(file, c1) && getline(file, c2) && getline(file, c3)) {
				x = strtof(c1.c_str(),0);
				y = strtof(c2.c_str(),0);
				z = strtof(c3.c_str(),0);
				//printf("%f\n", x);
				//printf("%f\n", y);
				//printf("%f\n", z);
				Vertex v = {x,y,z};
				t.push_back(v);
				j++;
				if (j % 3 == 0) {
					model.push_back(t);
					t.clear();
				}
			}
			file.close();
		}
		modelz.insert(model);
		model.clear();
	}
}

void drawTheFiles(){
    int i=0;
	for (Model m : modelz) {
		glBegin(GL_TRIANGLES);
		for (Triangle t : m) {
            if(i%2 == 0)
                glColor3f(0,0,255);
            else glColor3f(51,0,0);
			glVertex3f(t.at(0).x, t.at(0).y, t.at(0).z);
			glVertex3f(t.at(1).x, t.at(1).y, t.at(1).z);
			glVertex3f(t.at(2).x, t.at(2).y, t.at(2).z);
            i++;
		}
		glEnd();
	}
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



void renderScene(void) {

	float xaxis = radius * cos(beta) * sin(alpha);
	float yaxis = radius * sin(beta);
	float zaxis = radius * cos(beta) * cos(alpha);

	//glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(xaxis,yaxis,zaxis,
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);

// put drawing instructions here
	drawTheFiles();


	// End of frame
	glutSwapBuffers();
}


void process_keys(unsigned char key, int x, int y) {
	switch (key) {
	case'a':
		alpha += 0.1;
		break;
	case'd':
		alpha -= 0.1;
		break;
	case 'w':
		beta -= 0.1;
		if (beta < -1.5) beta = 1.5;
		break;
	case 's':
		beta += 0.1;
		if (beta > 1.5) beta = 1.5;
		break;
	case 'q':
		radius -= 0.1;
		break;
	case 'e':
		radius += 0.1;
		break;
	}
	glutPostRedisplay();
}

int main(int argc, char **argv) {


  readXML();
  loadXML();

	alpha = 0;
	beta = 5;

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


// OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);


// enter GLUT's main loop
	glutMainLoop();

	return 1;
}
