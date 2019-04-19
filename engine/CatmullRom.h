void build_Rotation_Matrix(float *x, float *y, float *z, float *m);
void cross_product(float *a, float *b, float *res);
void normalize_vector(float *a);
float length(float *v);
void mult_Matrix_Vector(float *m, float *v, float *res);
void getCatmullRomPoint(float t, float *p0, float *p1, float *p2, float *p3, float *pos, float *deriv);
void getGlobalCatmullRomPoint(float gt, float *pos, float *deriv, float controlPoints[][3], int number_points);
void renderCatmullRomCurve(float controlPoints[][3], int number_points);