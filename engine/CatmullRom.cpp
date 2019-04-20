#include <stdlib.h>
#include <math.h>
#include "CatmullRom.h"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif


void build_Rotation_Matrix(float *x, float *y, float *z, float *m) {

    m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
    m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
    m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
    m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}

void cross_product(float *a, float *b, float *res) {

    res[0] = a[1]*b[2] - a[2]*b[1];
    res[1] = a[2]*b[0] - a[0]*b[2];
    res[2] = a[0]*b[1] - a[1]*b[0];
}


void normalize_vector(float *a) {

    float l = sqrt(a[0]*a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0]/l;
    a[1] = a[1]/l;
    a[2] = a[2]/l;
}


float length(float *v) {

    float res = sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
    return res;

}

void mult_Matrix_Vector(float *m, float *v, float *res) {

    for (int j = 0; j < 4; ++j) {
        res[j] = 0;
        for (int k = 0; k < 4; ++k) {
            res[j] += v[k] * m[j * 4 + k];
        }
    }

}


void getCatmullRomPoint(float t, float *p0, float *p1, float *p2, float *p3, float *pos, float *deriv) {

    // catmull-rom matrix
    float m[4][4] = {   {-0.5f,  1.5f, -1.5f,  0.5f},
                         { 1.0f, -2.5f,  2.0f, -0.5f},
                         {-0.5f,  0.0f,  0.5f,  0.0f},
                         { 0.0f,  1.0f,  0.0f,  0.0f}};

    // Compute A = M * P
    float Ax[4];
    float Ay[4];
    float Az[4];
    float px[4] = {p0[0],p1[0],p2[0],p3[0]};
    float py[4] = {p0[1],p1[1],p2[1],p3[1]};
    float pz[4] = {p0[2],p1[2],p2[2],p3[2]};
    float t_line[4] = {t*t*t,t*t,t,1};
    float derivate_t_line[4] = {3*t*t,2*t,1,0};
    mult_Matrix_Vector(*m,px,Ax);
    mult_Matrix_Vector(*m,py,Ay);
    mult_Matrix_Vector(*m,pz,Az);

    // Compute pos = T * A
    pos[0] = (Ax[0] * t_line[0]) + (Ax[1] * t_line[1])  + (Ax[2] * t_line[2]) + (Ax[3] * t_line[3]);
    pos[1] = (Ay[0] * t_line[0]) + (Ay[1] * t_line[1])  + (Ay[2] * t_line[2]) + (Ay[3] * t_line[3]);
    pos[2] = (Az[0] * t_line[0]) + (Az[1] * t_line[1])  + (Az[2] * t_line[2]) + (Az[3] * t_line[3]);

    // compute deriv = T' * A
    deriv[0] = (Ax[0] * derivate_t_line[0]) + (Ax[1] * derivate_t_line[1])  + (Ax[2] * derivate_t_line[2]);
    deriv[1] = (Ay[0] * derivate_t_line[0]) + (Ay[1] * derivate_t_line[1])  + (Ay[2] * derivate_t_line[2]);
    deriv[2] = (Az[0] * derivate_t_line[0]) + (Az[1] * derivate_t_line[1])  + (Az[2] * derivate_t_line[2]);
}

void getGlobalCatmullRomPoint(float gt, float *pos, float *deriv, float controlPoints[][3], int number_points) {

    float t = gt * number_points; // real global t
    int index = floor(t);  // segment
    t -= index; // where within the segment

    // indices store the points
    int indexes_array[number_points-1];
    indexes_array[0] = (index + number_points-1) % number_points;
    for(int i = 1;i<number_points-1;i++) {
        indexes_array[i] = (indexes_array[i-1]+1)%number_points;
    }
    getCatmullRomPoint(t, controlPoints[indexes_array[0]], controlPoints[indexes_array[1]], controlPoints[indexes_array[2]], controlPoints[indexes_array[3]], pos, deriv);
}

void renderCatmullRomCurve(float controlPoints[][3], int number_points) { 
    float deriv[3];
    float pos[3];
    glBegin(GL_LINE_LOOP);
    for(float i=0.01; i< 100; i += 0.01) {
        getGlobalCatmullRomPoint(i,pos,deriv, controlPoints, number_points);
        glVertex3f(pos[0],pos[1],pos[2]);

    }
    glEnd();

}