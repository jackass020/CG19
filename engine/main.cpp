#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

float alpha;
float beta;
float radius = 5;

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

	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(xaxis,yaxis,zaxis, 
		      0.0,0.0,-1.0,
			  0.0f,1.0f,0.0f);

// put drawing instructions here
	glutWireTeapot(0.5f);

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

	alpha = 0;
	beta = 3;

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

