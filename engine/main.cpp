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
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#include <IL/il.h>
#include <cmath>
#include <fstream>
#include <vector>
#include <string>
#include <set>
#include <iostream>
#include "tinyxml2.h"
#include "CatmullRom.h"

using namespace tinyxml2;
using namespace std;

typedef std::string Path;


typedef struct coordinate {
	float x;
	float y;
	float z;
}Vertex;

typedef std::vector<Vertex> ControlP;
typedef std::vector<float> Texture;
typedef std::vector<float> Normals;

static bool operator<(const coordinate &a1, const coordinate &a2) {
    return true;
}

typedef struct rgb {
    float r;
    float g;
    float b;
}Col;

typedef struct light {
    bool amb= false;
    bool differ = false;
    bool spect = false;
    bool emiss = false;
    Col ambient;
    Col diffuse;
    Col specular;
    Col emissive;
}Lights;

typedef struct type {
    char* light_type;
    bool joint_camera;
    float posx;
    float posy;
    float posz;//pox, posy, poz can either be a direction or a position, depending on the light_type
    Vertex spotDir;
    Lights lights;
    float cutoff;
    float exponent;
    float directional; //0.0(directional) or 1.0(positional)
} LightType;

vector<LightType> light_type;


typedef struct pathInfo{
    Path path;
    ControlP contp;
    int nrcontp;
    bool curved;
    float traX=0,traY=0,traZ=0;
    float rotX=0,rotY=0,rotZ=0;
    float scaleX=1,scaleY=1,scaleZ=1;
    float trans_time=0;
    float rot_time=0;
    char* text;
    bool has_texture = false;
    Lights light;
} Paths;

int textures[20];
int curText=0;

typedef struct groupData{
    float traX, traY,traZ;
    float rotX, rotY, rotZ;
    float scaleX, scaleY, scaleZ;

}Group;

typedef std::vector<float> Model;

typedef struct modelData{
    Model model;
    float traX=0, traY=0,traZ=0;
    float rotX=0, rotY=0, rotZ=0;
    float scaleX=1, scaleY=1, scaleZ=1;
    ControlP contp;
    int nrcontp;
    bool curved;
    float trans_time=0;
    float rot_time=0;
    char* text;
    bool has_texture = false;
    Lights light;
    Texture texture;
    Normals normals;
    int tex;
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
int number_of_groups;

Paths paths [1024];

Group groups[1024];
int paths_size = 0;
Models modelz;
GLuint *buffers;
float gloY[3] = {0,1,0};

void readModels(XMLElement* elem, int nr) {
	const char* attr;

	for (XMLElement* aux = elem; aux != nullptr; aux = aux->NextSiblingElement()) {
        Lights l;
        Col amb;
        Col diff;
        Col spec;
        Col emis;
		string nome_elem = aux->Value();

		if (nome_elem == "model") {
			attr = aux->Attribute("file");//retira o tipo
			if (attr != nullptr) {
				paths[paths_size].path = attr;//adicionar o path do ficheiro
				paths[paths_size].traX = groups[nr].traX; //Adicionar no array paths os valores armazenados
				paths[paths_size].traY = groups[nr].traY; //Adicionar no array paths os valores armazenados
				paths[paths_size].traZ = groups[nr].traZ; //Adicionar no array paths os valores armazenados
				paths[paths_size].rotX = groups[nr].rotX; //Adicionar no array paths os valores armazenados
				paths[paths_size].rotY = groups[nr].rotY; //Adicionar no array paths os valores armazenados
				paths[paths_size].rotZ = groups[nr].rotZ; //Adicionar no array paths os valores armazenados
				paths[paths_size].scaleX = groups[nr].scaleX; //Adicionar no array paths os valores armazenados
				paths[paths_size].scaleY = groups[nr].scaleY; //Adicionar no array paths os valores armazenados
				paths[paths_size].scaleZ = groups[nr].scaleZ; //Adicionar no array paths os valores armazenados

			}

		}
		attr= aux->Attribute("texture");
		if(attr!=nullptr){
		    paths[paths_size].text= strdup(attr);
		    paths[paths_size].has_texture = true;
		}
		else{
		    paths[paths_size].text="";
		}

        attr = aux->Attribute("diffR");
        if(attr!=nullptr) {
            diff.r = strtof(attr,nullptr);
            l.differ = true;
        }

        attr = aux->Attribute("diffG");
        if(attr!=nullptr) {
            diff.g = strtof(attr,nullptr);
            l.differ=true;
        }

        attr = aux->Attribute("diffB");
        if(attr!=nullptr) {
            diff.b = strtof(attr,nullptr);
            l.differ=true;
        }


        if(l.differ)
            l.diffuse = diff;



        attr = aux->Attribute("ambR");
        if(attr!=nullptr) {
            amb.r = strtof(attr, nullptr);
            l.amb = true;
        }
        attr = aux->Attribute("ambG");
        if(attr!=nullptr) {
            amb.g = strtof(attr, nullptr);
            l.amb = true;
        }
        attr = aux->Attribute("ambB");
        if(attr!=nullptr) {
            amb.b = strtof(attr,nullptr);
            l.amb=true;
        }
        if(l.amb)
            l.ambient = amb;

        attr = aux->Attribute("specR");
        if(attr!=nullptr) {
            spec.r = strtof(attr, nullptr);
            l.spect = true;
        }
        attr = aux->Attribute("specG");
        if(attr!=nullptr) {
            spec.g = strtof(attr, nullptr);
            l.spect = true;
        }
        attr = aux->Attribute("specB");
        if(attr!=nullptr) {
            spec.b = strtof(attr,nullptr);
            l.spect=true;
        }

        if(l.spect) l.specular = spec;


        attr = aux->Attribute("emissR");
        if(attr!=nullptr) {
            emis.r = strtof(attr, nullptr);
            l.emiss = true;
        }
        attr = aux->Attribute("emissG");
        if(attr!=nullptr) {
            emis.g = strtof(attr, nullptr);
            l.emiss = true;
        }
        attr = aux->Attribute("emissB");
        if(attr!=nullptr) {
            emis.b = strtof(attr,nullptr);
            l.emiss=true;
        }

        if(l.emiss) l.emissive = emis;

        paths[paths_size].light = l;
		paths_size++;

	}
    number_of_groups++;

}



int readTexture(string s) {
    unsigned int t,tw,th;
    unsigned char *texData;
    unsigned int texID;

    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
    ilGenImages(1,&t);
    ilBindImage(t);
    ilLoadImage((ILstring)s.c_str());
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    glGenTextures(1,&texID);

    glBindTexture(GL_TEXTURE_2D,texID);
    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_WRAP_S,		GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_WRAP_T,		GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_MAG_FILTER,   	GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
    glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);

    return texID;
}
void readPoints (XMLElement* elem) {
    const char *attr;

    for (XMLElement *aux = elem; aux != nullptr; aux = aux->NextSiblingElement()) {
        string nome_elem = aux->Value();
        if (nome_elem == "point") {
            Vertex v;
            attr = aux->Attribute("X");
            if (attr != nullptr) v.x = strtof(attr, nullptr);
            attr = aux->Attribute("Y");
            if (attr != nullptr) v.y = strtof(attr, nullptr);
            attr = aux->Attribute("Z");
            if (attr != nullptr) v.z = strtof(attr, nullptr);
            paths[number_of_groups].contp.push_back(v);
            paths[number_of_groups].nrcontp++;

        }
    }
}

void colorL(int color,LightType lt) {

    switch(color) {
        case 0:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT0,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT0,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT0,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT0,GL_AMBIENT,emiss);
            }
            break;
        case 1:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT1,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT1,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT1,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT1,GL_AMBIENT,emiss);
            }
            break;
        case 2:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT2,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT2,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT2,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT2,GL_AMBIENT,emiss);
            }
            break;
        case 3:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT3,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT3,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT3,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT3,GL_AMBIENT,emiss);
            }
            break;
        case 4:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT4,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT4,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT4,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT4,GL_AMBIENT,emiss);
            }
            break;
        case 5:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT5,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT5,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT5,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT5,GL_AMBIENT,emiss);
            }
            break;
        case 6:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT6,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT6,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT6,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT6,GL_AMBIENT,emiss);
            }
            break;
        case 7:
            if(lt.lights.amb) {
                GLfloat amb[4] = {lt.lights.ambient.r, lt.lights.ambient.g, lt.lights.ambient.b, 1.0};
                glLightfv(GL_LIGHT7,GL_AMBIENT,amb);
            }
            if(lt.lights.differ) {
                GLfloat differ[4] = {lt.lights.diffuse.r, lt.lights.diffuse.g, lt.lights.diffuse.b, 1.0};
                glLightfv(GL_LIGHT7,GL_AMBIENT,differ);
            }
            if(lt.lights.spect) {
                GLfloat spect[4] = {lt.lights.specular.r, lt.lights.specular.g, lt.lights.specular.b, 1.0};
                glLightfv(GL_LIGHT7,GL_AMBIENT,spect);
            }
            if(lt.lights.emiss) {
                GLfloat emiss[4] = {lt.lights.emissive.r, lt.lights.emissive.g, lt.lights.emissive.b, 1.0};
                glLightfv(GL_LIGHT7,GL_AMBIENT,emiss);
            }
            break;
    }
}

void posL(bool before) {
    for(int j=0 ; j<light_type.size();j++) {
        LightType lt = light_type[j];
        if((j==0) && ((before && lt.joint_camera) || (!before && !lt.joint_camera)) ) {
            colorL(0,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT0,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT0,GL_POSITION,pos);
                glLightfv(GL_LIGHT0,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT0,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT0,GL_SPOT_EXPONENT,lt.exponent);
            }



        } else if((j==1) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {
            colorL(1,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT1,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT1,GL_POSITION,pos);
                glLightfv(GL_LIGHT1,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT1,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT1,GL_SPOT_EXPONENT,lt.exponent);
            }


        } else if((j==2) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {
            colorL(2,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT2,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT2,GL_POSITION,pos);
                glLightfv(GL_LIGHT2,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT2,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT2,GL_SPOT_EXPONENT,lt.exponent);
            }


        } else if((j==3) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {
            colorL(3,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT3,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT3,GL_POSITION,pos);
                glLightfv(GL_LIGHT3,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT3,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT3,GL_SPOT_EXPONENT,lt.exponent);
            }


        } else if((j==4) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {
            colorL(4,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT4,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT4,GL_POSITION,pos);
                glLightfv(GL_LIGHT4,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT4,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT4,GL_SPOT_EXPONENT,lt.exponent);
            }


        } else if((j==5) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {
            colorL(5,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT5,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT5,GL_POSITION,pos);
                glLightfv(GL_LIGHT5,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT5,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT5,GL_SPOT_EXPONENT,lt.exponent);
            }


        } else if((j==6) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {

            colorL(6,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT6,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT6,GL_POSITION,pos);
                glLightfv(GL_LIGHT6,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT6,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT6,GL_SPOT_EXPONENT,lt.exponent);
            }


        } else if((j==7) && ((before && lt.joint_camera) || (!before && !lt.joint_camera))) {

            colorL(7,lt);
            if(lt.light_type=="POINT" || lt.light_type=="DIRECTIONAL") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz,lt.directional};
                glLightfv(GL_LIGHT7,GL_POSITION,pos);
            } else if(lt.light_type=="SPOT") {
                GLfloat pos[4] = {lt.posx,lt.posy,lt.posz};
                GLfloat spotDir[3] = {lt.spotDir.x,lt.spotDir.y,lt.spotDir.z};
                glLightfv(GL_LIGHT7,GL_POSITION,pos);
                glLightfv(GL_LIGHT7,GL_SPOT_DIRECTION,spotDir);
                glLightf(GL_LIGHT7,GL_SPOT_CUTOFF,lt.cutoff);
                glLightf(GL_LIGHT7,GL_SPOT_EXPONENT,lt.exponent);
            }
            break;
        }
    }
}

void lightAux(XMLElement * elem){
    const char * attr;

    for(XMLElement * aux = elem; aux != nullptr;aux =aux->NextSiblingElement()){
        Lights l;
        Col amb;
        Col diff;
        Col spec;
        Col emis;
        string nome_elem= aux->Value();
        if(nome_elem =="light"){
            LightType lt;
            attr= aux->Attribute("jointCamera");
            if(attr!= nullptr){
                if(attr == "true"){
                    lt.joint_camera=true;
                }
                else{lt.joint_camera=false;}
            }
            else {lt.joint_camera=false;}
            attr=aux->Attribute("type");
            if(attr != nullptr)
                lt.light_type=strdup(attr);
            if(lt.light_type=="DIRECTIONAL") {
                attr = aux->Attribute("dirX");
                if(attr != nullptr)
                    lt.posx= strtof(attr,nullptr);
                attr = aux->Attribute("dirY");
                if(attr!=nullptr)
                    lt.posy = strtof(attr,nullptr);
                attr = aux->Attribute("dirZ");
                if(attr!=nullptr)
                    lt.posz = strtof(attr,nullptr);
                lt.directional = 0.0;
            } else lt.directional = 1.0;
            attr = aux->Attribute("posX");
            if(attr != nullptr)
                lt.posx = strtof(attr,nullptr);
            attr = aux->Attribute("posY");
            if(attr != nullptr)
                lt.posy = strtof(attr,nullptr);
            attr = aux->Attribute("posZ");
            if(attr != nullptr)
                lt.posz = strtof(attr,nullptr);
            if(lt.light_type=="SPOT") {
                attr = aux->Attribute("cutoff");
                if (attr != nullptr) {
                    float cutoff = strtof(attr, nullptr);
                    if ((cutoff >= 0.0 && cutoff <= 90.0) || cutoff == 180.0)
                        lt.cutoff = cutoff;
                    else lt.cutoff = 90.0;
                }

                Vertex v;
                attr = aux->Attribute("spotDirX");
                if(attr!=nullptr)
                    v.x = strtof(attr,nullptr);
                attr = aux->Attribute("spotDirY");
                if(attr!=nullptr)
                    v.y = strtof(attr,nullptr);
                attr = aux->Attribute("spotDirZ");
                if(attr!=nullptr)
                    v.z = strtof(attr,nullptr);

                lt.spotDir = v;

                attr = aux->Attribute("exponent");
                if(attr!=nullptr) {
                    float exponent = strtof(attr, nullptr);
                    if (exponent >= 0.0 && exponent <= 128.0)
                        lt.exponent = exponent;
                    else lt.exponent = 0.0;
                }
            }

            attr = aux->Attribute("diffR");
            if(attr!=nullptr) {
                diff.r = strtof(attr,nullptr);
                l.differ = true;
            }

            attr = aux->Attribute("diffG");
            if(attr!=nullptr) {
                diff.g = strtof(attr,nullptr);
                l.differ=true;
            }

            attr = aux->Attribute("diffB");
            if(attr!=nullptr) {
                diff.b = strtof(attr,nullptr);
                l.differ=true;
            }


            if(l.differ)
                l.diffuse = diff;



            attr = aux->Attribute("ambR");
            if(attr!=nullptr) {
                amb.r = strtof(attr, nullptr);
                l.amb = true;
            }
            attr = aux->Attribute("ambG");
            if(attr!=nullptr) {
                amb.g = strtof(attr, nullptr);
                l.amb = true;
            }
            attr = aux->Attribute("ambB");
            if(attr!=nullptr) {
                amb.b = strtof(attr,nullptr);
                l.amb=true;
            }
            if(l.amb)
                l.ambient = amb;

            attr = aux->Attribute("specR");
            if(attr!=nullptr) {
                spec.r = strtof(attr, nullptr);
                l.spect = true;
            }
            attr = aux->Attribute("specG");
            if(attr!=nullptr) {
                spec.g = strtof(attr, nullptr);
                l.spect = true;
            }
            attr = aux->Attribute("specB");
            if(attr!=nullptr) {
                spec.b = strtof(attr,nullptr);
                l.spect=true;
            }

            if(l.spect) l.specular = spec;


            attr = aux->Attribute("emissR");
            if(attr!=nullptr) {
                emis.r = strtof(attr, nullptr);
                l.emiss = true;
            }
            attr = aux->Attribute("emissG");
            if(attr!=nullptr) {
                emis.g = strtof(attr, nullptr);
                l.emiss = true;
            }
            attr = aux->Attribute("emissB");
            if(attr!=nullptr) {
                emis.b = strtof(attr,nullptr);
                l.emiss=true;
            }

            if(l.emiss) l.emissive = emis;
            lt.lights = l;

            light_type.push_back(lt);
        }
    }

}

void groupAux(XMLElement* elem, int nr){
    const char* attr;//Vai guardar o atributo que queremos
    bool trans,rot,scale;

    trans=rot=scale=false;
    if (nr==0){//Caso seja o primeiro "group" a ser analisado, este tem de ser inicializado, não sendo possível por a scale=0;
        groups[nr].traX=groups[nr].traY=groups[nr].traZ=0;
        groups[nr].rotX=groups[nr].rotY=groups[nr].rotZ=0;
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

        if (nome_elem == "group") {//Caso seja "group" este iŕa ser um sub-group
             groupAux(aux->FirstChildElement(), nr + 1);

        }
        if(nome_elem == "lights") {
            lightAux(aux->FirstChildElement());
        }
        if(nome_elem=="translate" && !trans) {//Caso seja translate, vai se armazenar os valores
            trans=true;
            attr = aux->Attribute("time");
            if (attr != nullptr && strlen(attr) != 0) {
                readPoints(aux->FirstChildElement());
                paths[number_of_groups].trans_time = strtof(attr, nullptr);
                paths[number_of_groups].curved = true;
            }
            else{
                attr = aux->Attribute("X");//é retirado o atributo de X
                if (attr != nullptr && strlen(attr) != 0) groups[nr].traX += (strtof(attr, nullptr)*groups[nr].scaleX);//string to float

                attr = aux->Attribute("Y");//é retirado o atributo de Y
                if (attr != nullptr && strlen(attr) != 0) groups[nr].traY += (strtof(attr, nullptr)*groups[nr].scaleY);//string to float

                attr = aux->Attribute("Z");//é retirado o atributo de Z
                if (attr != nullptr && strlen(attr) != 0) groups[nr].traZ += (strtof(attr, nullptr)*groups[nr].scaleZ);//string to float
            }
        }
        if(nome_elem=="rotate"&& !rot){//Caso seja rotate, vai se armazenar os valores
            rot= true;
            attr= aux-> Attribute("time");//é retirado o atributo do time
            if (attr!=nullptr && strlen(attr)!=0) paths[number_of_groups].rot_time = strtof(attr,nullptr);//string to floa
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
        if(nome_elem=="scale"&&!scale){//Caso seja scale, vai se armazenar os valores
            scale=true;
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
			readModels(aux->FirstChildElement() ,nr);
		}
    }

}


void readXML(){
    XMLDocument doc ;
    int i = 0;
    doc.LoadFile("../../Files/Config.xml");// carrega o ficheiro XML
    XMLElement* root = doc.FirstChildElement();// Aponta para o elemento raiz= "scene"
    for(XMLElement* elem = root->FirstChildElement();elem != nullptr; elem = elem->NextSiblingElement()) {//precorre os model
        string nome_elem = elem->Value();
        if(nome_elem == "group") {
			groupAux(elem->FirstChildElement(),0);//chama a função auxiliar para poder armazenar a informação de cada grupo
		}
        else if (nome_elem=="lights") {
            lightAux(elem->FirstChildElement());//chama a função auxiliar para armazenar os atributos
        }
        else{
            paths_size=0;
            return;
        }
    }

}

void preparaBuffers() {
    int i = 0;
    for(ModelData m : modelz) {
        glBindBuffer(GL_ARRAY_BUFFER,buffers[i*3]);//Após a crição do buffer, precisamos de ligar o buffer com o devido id antes de este ser usado.
        float *vertexB = &m.model[0];
        glBufferData(GL_ARRAY_BUFFER,sizeof(float) * m.model.size(),vertexB,GL_STATIC_DRAW);// Sendo o buffer inicializado, é possivel copiar os dados para o buffer-
        glBindBuffer(GL_ARRAY_BUFFER,buffers[i*3+1]);
        float *normalsB = &m.normals[0];
        glBufferData(GL_ARRAY_BUFFER,sizeof(float) * m.normals.size(),normalsB,GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER,buffers[i*3+2]);//possivel ERRO!
        float *textures = &m.texture[0];
        glBufferData(GL_ARRAY_BUFFER,sizeof(float) * m.texture.size(),textures,GL_STATIC_DRAW);
        i++;
    }
}

void loadXML() {
	float x, y, z;
	int i = 0,j=0;
	string c1, c2, c3;
	ModelData model;
	for (i=0;i<paths_size;i++) {

	    Path p = paths[i].path;
	    cout<< paths[i].path;
        Path points=p+".3d";
        Path text = p+".t";
        Path normals = p+".n";
		model.traX = paths[i].traX;
        model.traY = paths[i].traY;
        model.traZ = paths[i].traZ;
        model.rotX = paths[i].rotX;
        model.rotY = paths[i].rotY;
        model.rotZ = paths[i].rotZ;
        model.scaleX = paths[i].scaleX;
        model.scaleY = paths[i].scaleY;
        model.scaleZ = paths[i].scaleZ;
        model.curved = paths[i].curved;
        model.trans_time=paths[i].trans_time;
        model.rot_time=paths[i].rot_time;
        model.contp=paths[i].contp;
        model.nrcontp=paths[i].nrcontp;
        model.light=paths[i].light;
        model.text=paths[i].text;
        model.has_texture = paths[i].has_texture;
        if(model.has_texture && strlen(model.text)!=0){
            model.tex=curText;
            curText++;
        }
		ifstream file (p);//leitura do fichero
        if (file.is_open()) {//Caso o ficheiro abra
			while (getline(file, c1) && getline(file, c2) && getline(file, c3)) {// obter as 3 primeiras linha sendo estas as coordenadas(x,y,z)
				x = strtof(c1.c_str(),nullptr);//obter um apontador para o array c1 através do uso c_str
				y = strtof(c2.c_str(),nullptr);//obter um apontador para o array c2 através do uso c_str
				z = strtof(c3.c_str(),nullptr);//obter um apontador para o array c3 através do uso c_str
				model.model.push_back(x);
                model.model.push_back(y);
                model.model.push_back(z);

			}
			file.close();
		}
        ifstream filetext(text.c_str());
        if (filetext.is_open()) {
            while (getline(filetext, c1) && getline(filetext, c2)) {
                x = strtof(c1.c_str(), nullptr);
                y = strtof(c2.c_str(), nullptr);
                model.texture.push_back(x);
                model.texture.push_back(y);
            }
            filetext.close();
        }
        ifstream filetext2(normals.c_str());
        if(filetext2.is_open()) {
            while(getline(filetext2,c1) && getline(filetext2,c2) && getline(filetext2,c3)) {
                x = strtof(c1.c_str(),nullptr);
                y = strtof(c2.c_str(),nullptr);
                z = strtof(c3.c_str(),nullptr);
                model.normals.push_back(x);
                model.normals.push_back(y);
                model.normals.push_back(z);
            }
        }

		modelz.insert(model);
		model.model.clear();
	}

}

void drawTheFiles(float t) {

    float r[3];
    float de[3];
    float p[3];
    float z[3];
    float mad[16];
    int j=0;

    for (ModelData m : modelz) {

        glPushMatrix();
        float controlPoints[m.nrcontp][3];
        for (int i = 0; i < m.nrcontp; i++) {
            controlPoints[i][0] = m.contp[i].x;
            controlPoints[i][1] = m.contp[i].y;
            controlPoints[i][2] = m.contp[i].z;
        }
        if (m.curved) {
            renderCatmullRomCurve(controlPoints, m.nrcontp);
            getGlobalCatmullRomPoint(glutGet(GLUT_ELAPSED_TIME) / (1000 * m.trans_time), p, r, controlPoints,
                                     m.nrcontp);
            *de = *r;
            glTranslatef(p[0], p[1], p[2]);
            normalize_vector(de);
            cross_product(de, gloY, z);
            normalize_vector(z);
            cross_product(z, de, gloY);
            normalize_vector(gloY);
            build_Rotation_Matrix(de, gloY, z, mad);
            glMultMatrixf(mad);
        }

        glTranslatef(m.traX, m.traY, m.traZ);

        Model model = m.model;
        if (!m.curved)
            glTranslatef(m.traX, m.traY, m.traZ);
        glScalef(m.scaleX, m.scaleY, m.scaleZ);
        if (m.rot_time == 0) {
            glRotatef(m.rotX, 1, 0, 0);
            glRotatef(m.rotY, 0, 1, 0);
            glRotatef(m.rotZ, 0, 0, 1);
        } else {
            float alpha = (glutGet(GLUT_ELAPSED_TIME) * 4) / 36;
            glRotatef(m.rotX * alpha, 1, 0, 0);
            glRotatef(m.rotY * alpha, 0, 1, 0);
            glRotatef(m.rotZ * alpha, 0, 0, 1);
        }
        if(m.light.differ) {
            float a[4] = {m.light.diffuse.r,m.light.diffuse.g,m.light.diffuse.b,1.0};
            glMaterialfv(GL_FRONT,GL_DIFFUSE,a);
        } else if(m.light.amb) {
            float a[4] = {m.light.ambient.r,m.light.ambient.g,m.light.ambient.b,1.0};
            glMaterialfv(GL_FRONT,GL_AMBIENT,a);
        } else if(m.light.spect) {
            float a[4] = {m.light.specular.r,m.light.specular.g,m.light.specular.b,1.0};
            glMaterialfv(GL_FRONT,GL_SPECULAR,a);
        } else if(m.light.emiss) {
            float a[4] = {m.light.emissive.r,m.light.emissive.g,m.light.emissive.b,1.0};
            glMaterialfv(GL_FRONT,GL_EMISSION,a);
        }
        if(m.has_texture) {
            if (strlen(m.text) != 0) glBindTexture(GL_TEXTURE_2D, textures[m.tex]);
            glBindBuffer(GL_ARRAY_BUFFER, buffers[j]);
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, buffers[j + 1]);
            glNormalPointer(GL_FLOAT, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, buffers[j + 2]);
            glTexCoordPointer(2, GL_FLOAT, 0, 0);
            glDrawArrays(GL_TRIANGLES, 0, model.size()/3);
        } else {
            glBindBuffer(GL_ARRAY_BUFFER, buffers[j]);
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, buffers[j + 1]);
            glNormalPointer(GL_FLOAT, 0, 0);
            glDrawArrays(GL_TRIANGLES, 0, model.size()/3);
        }
        j+=3;
        glPopMatrix();

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
		float cy = (y - (h / 2));

		float aStep = cx / w;
		float bStep = cy / h;

		alpha = aStep * (2*M_PI);
		beta = bStep * (2*M_PI);

		if (beta >= (M_PI / 2)) beta = (M_PI / 2) - 0.0001;
		else if (beta <= (M_PI / 2)) beta = -(M_PI / 2) + 0.0001;

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

    static float t=0;
	//glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
    posL(true); // true means positioning the lights before positioning the camera
	gluLookAt(px,py,pz,
		      px + dx,py+dy,pz+dz,
			  0.0f,1.0f,0.0f);

    posL(false); // false means positioning the lights after positioning the camera

	if (axes) drawAxes();

	if (lines) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	else glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

// put drawing instructions here
	drawTheFiles(t);


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
        case 'u':
            dx += 0.5f;
            break;
        case 'j':
            dx -= 0.5f;
            break;
        case 'i':
            dy += 0.5f;
            break;
        case 'k':
            dy -= 0.5f;
            break;

	}
	glutPostRedisplay();
}
void enableLighting() {
    long n_lights = light_type.size();
    for(int i =0; i< n_lights;i++) {
        if (i == 0)
            glEnable(GL_LIGHT0);
        else if (i == 1)
            glEnable(GL_LIGHT1);
        else if (i == 2)
            glEnable(GL_LIGHT2);
        else if (i == 3)
            glEnable(GL_LIGHT3);
        else if (i == 4)
            glEnable(GL_LIGHT4);
        else if (i == 5)
            glEnable(GL_LIGHT5);
        else if (i == 6)
            glEnable(GL_LIGHT6);
        else if (i == 7) {
            glEnable(GL_LIGHT7);
            break;
        }

    }

    glEnable(GL_LIGHTING);
}

void initGL(){

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_TEXTURE_2D);
    enableLighting();
    for(ModelData m: modelz){
        if(m.has_texture)
            textures[m.tex]=readTexture(m.text);
    }
}

int main(int argc, char **argv) {

  readXML();
  loadXML();

  buffers = new GLuint[paths_size*3];

// put GLUT init here
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(1600, 800);
	glutCreateWindow("Engine");

    glewInit();
    glEnableClientState(GL_VERTEX_ARRAY);// activate vertex position array
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);
    glGenBuffers(paths_size*3,buffers);//the first one is the number of buffer objects to create, and the second parameter is the address of a GLuint variable or array to store a single ID or multiple IDs
    preparaBuffers();
// put callback registration here
	glutDisplayFunc(renderScene);
    glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);

	glutKeyboardFunc(process_keys);
	glutMotionFunc(mouseMove);
	glutPassiveMotionFunc(mouseMove);
	glutMouseFunc(mouseClick);

	initGL();

// OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);


// enter GLUT's main loop
	glutMainLoop();

	return 1;
}
