import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Comet {

    private String file;
    private String dest;
    private int number_of_patches;
    private int number_of_control_points;
    private static final int CONTROL_POINTS_PER_PATCH = 16;
    private int[][] control_points_index;
    private float[][] control_points;
    private List<Point> vertexes;
    private List<Point> normals;
    private static int[][] m ={{-1,3,-3,1},{3,-6,3,0},{-3,3,0,0},{1,0,0,0}};
    private int tesselation;
    private float[][] prod_matrix_x;
    private float[][] prod_matrix_y;
    private float[][] prod_matrix_z;

    private class Point {
        public float x;
        public float y;
        public float z;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public Comet(String file,int tesselation,String dest) {
        vertexes = new ArrayList<>();
        this.file=file;
        this.tesselation=tesselation;
        this.dest =dest;
        this.dest =dest;
    }

    private float[] normalize_vector(float[] array) {
        float distance = (float) Math.sqrt(array[0]*array[0] + array[1]*array[1] + array[2]*array[2]);
        float res[] = new float[3];
        res[0] = array[0] / distance;
        res[1] = array[1] / distance;
        res[2] = array[2] / distance;
        return array;
    }

    private float[] cross_product(float[] u, float[] v) {
        float[] vector = new float[3];
        vector[0] = u[1]*v[2] - u[2]*v[1];
        vector[1] = u[2]*v[0] - u[0]*v[2];
        vector[2] = u[0]*v[1] - u[1]*v[0];

        return vector;
    }

    public void generateFile() {
        readFile();
        calculate_Bezier();
        Path p = Paths.get("../Files/" +this.dest);
        try(BufferedWriter bw = Files.newBufferedWriter(p)) {
            for(Point p1 : vertexes) {
                bw.write(Float.toString(p1.x) + "\n");
                bw.write(Float.toString(p1.y) + "\n");
                bw.write(Float.toString(p1.z) + "\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void calculate_Bezier() {
        float[][] temp_control_points = new float[CONTROL_POINTS_PER_PATCH][3];
        float step = (float)1/(float)this.tesselation;
        for(int np = 0; np<number_of_patches;np++) {
            for(int j = 0;j<CONTROL_POINTS_PER_PATCH;j++) {
                temp_control_points[j][0] = control_points[control_points_index[np][j]][0];
                temp_control_points[j][1] = control_points[control_points_index[np][j]][1];
                temp_control_points[j][2] = control_points[control_points_index[np][j]][2];
            }

            calculate_control_points(temp_control_points);


            for(int i=0; i< tesselation+1;i++) {
                for(int j=0; j< tesselation+1;j++) {
                    float u = i*step;
                    float v = j*step;
                    float x = calculate_Bezier_Patch(u,v,0);
                    float y = calculate_Bezier_Patch(u,v,1);
                    float z = calculate_Bezier_Patch(u,v,2);
                    float x1 = calculate_Bezier_Patch(u+step,v,0);
                    float y1 = calculate_Bezier_Patch(u+step,v,1);
                    float z1 = calculate_Bezier_Patch(u+step,v,2);

                    float x2 = calculate_Bezier_Patch(u,v + step,0);
                    float y2 = calculate_Bezier_Patch(u,v + step,1);
                    float z2 = calculate_Bezier_Patch(u,v + step,2);

                    float x3 = calculate_Bezier_Patch(u + step,v + step,0);
                    float y3 = calculate_Bezier_Patch(u + step,v + step,1);
                    float z3 = calculate_Bezier_Patch(u + step,v + step,2);
                    Point point = new Point(x,y,z);
                    Point point1 = new Point(x1,y1,z1);
                    Point point2 = new Point(x2,y2,z2);
                    Point point3 = new Point(x3,y3,z3);

                    float[] dU1 = calculate_U_Derivate(u,v);
                    float[] dV1 = calculate_V_Derivate(u,v);
                    float[] n1 = normalize_vector(cross_product(dU1,dV1));

                    Point n_point1 = new Point(n1[0],n1[1],n1[2]);

                    float[] dU2 = calculate_U_Derivate(u+step,v);
                    float[] dV2 = calculate_V_Derivate(u+step,v);
                    float[] n2 = normalize_vector(cross_product(dU2,dV2));

                    Point n_point2 = new Point(n2[0],n2[1],n2[2]);

                    float[] dU3 = calculate_U_Derivate(u,v+step);
                    float[] dV3 = calculate_V_Derivate(u,v+step);
                    float[] n3 = normalize_vector(cross_product(dU3,dV3));

                    Point n_point3 = new Point(n3[0],n3[1],n3[2]);

                    //float[] dU4 = calculate_U_Derivate(u+step,v+step);
                    //float[] dV4 = calculate_V_Derivate(u+step,v+step);
                    //float[] n4 = normalize_vector(cross_product(dU4,dV4));

                  //  Point n_point4 = new Point(n4[0],n4[1],n4[2]);


                    vertexes.add(point);
                    vertexes.add(point1);
                    vertexes.add(point2);
                    vertexes.add(point2);
                    vertexes.add(point1);
                    vertexes.add(point3);

                    normals.add(n_point1);
                    normals.add(n_point2);
                    normals.add(n_point3);
                    normals.add(n_point3);
                    normals.add(n_point1);
                    normals.add(n_point2);
                }
            }
        }
    }

    public void calculate_control_points(float[][] controlPoints) {

        float[][] temp_matrix_x = new float[4][4];
        float[][] temp_matrix_y = new float[4][4];
        float[][] temp_matrix_z = new float[4][4];
        float[][] temp_const_matrix_x = new float[4][4];
        float[][] temp_const_matrix_y = new float[4][4];
        float[][] temp_const_matrix_z = new float[4][4];
        float[][] temp_prod_matrix_x = new float[4][4];
        float[][] temp_prod_matrix_y = new float[4][4];
        float[][] temp_prod_matrix_z = new float[4][4];


        for(int i=0,k=0; i<4;i++)
            for(int j=0; j<4;j++,k++) {
                temp_matrix_x[i][j] = controlPoints[k][0];
                temp_matrix_y[i][j] = controlPoints[k][1];
                temp_matrix_z[i][j] = controlPoints[k][2];
            }


        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                for(int k=0;k<4;k++) {
                    temp_const_matrix_x[i][j] += m[i][k]*temp_matrix_x[k][j];
                    temp_const_matrix_y[i][j] += m[i][k]*temp_matrix_y[k][j];
                    temp_const_matrix_z[i][j] += m[i][k]*temp_matrix_z[k][j];
                }


        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                for(int k=0;k<4;k++) {
                    temp_prod_matrix_x[i][j] += temp_const_matrix_x[i][k]*m[k][j];
                    temp_prod_matrix_y[i][j] += temp_const_matrix_y[i][k]*m[k][j];
                    temp_prod_matrix_z[i][j] += temp_const_matrix_z[i][k]*m[k][j];
                }

        this.prod_matrix_x = temp_prod_matrix_x;
        this.prod_matrix_y = temp_prod_matrix_y;
        this.prod_matrix_z = temp_prod_matrix_z;
    }

    private float calculate_Bezier_Patch(float u, float v, int index) {

        float[] u_line = {u*u*u , u*u, u , 1};
        float[] v_column = {v*v*v, v*v, v, 1};
        float[] u_prod_m_matrix = new float[4];
        float patch = 0;

        switch(index) {
            case 0:
                for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                        u_prod_m_matrix[i] += u_line[j]*prod_matrix_x[j][i];

                patch = u_prod_m_matrix[0]*v_column[0] + u_prod_m_matrix[1]*v_column[1] + u_prod_m_matrix[2]*v_column[2] + u_prod_m_matrix[3]*v_column[3];
                break;
            case 1:
                for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                        u_prod_m_matrix[i] += u_line[j]*prod_matrix_y[j][i];

                patch = u_prod_m_matrix[0]*v_column[0] + u_prod_m_matrix[1]*v_column[1] + u_prod_m_matrix[2]*v_column[2] + u_prod_m_matrix[3]*v_column[3];
                break;
            case 2:
                for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                        u_prod_m_matrix[i] += u_line[j]*prod_matrix_z[j][i];

                patch = u_prod_m_matrix[0]*v_column[0] + u_prod_m_matrix[1]*v_column[1] + u_prod_m_matrix[2]*v_column[2] + u_prod_m_matrix[3]*v_column[3];
                break;
        }


        return patch;

    }

    private float[] calculate_U_Derivate(float u, float v) {
        float[] u_matrix = {3*u*u,2*u,1,0};
        float[] v_matrix = {v*v*v,v*v,v,1};
        float[] vector = new float[3];
        float[] u_array = new float[4];

        u_array[0] = u_matrix[0]*prod_matrix_x[0][0] + u_matrix[1]*prod_matrix_x[1][0] + u_matrix[2]*prod_matrix_x[2][0] + u_matrix[3]*prod_matrix_x[3][0];
        u_array[1] = u_matrix[0]*prod_matrix_x[0][1] + u_matrix[1]*prod_matrix_x[1][1] + u_matrix[2]*prod_matrix_x[2][1] + u_matrix[3]*prod_matrix_x[3][1];
        u_array[2] = u_matrix[0]*prod_matrix_x[0][2] + u_matrix[1]*prod_matrix_x[1][2] + u_matrix[2]*prod_matrix_x[2][2] + u_matrix[3]*prod_matrix_x[3][2];
        u_array[3] = u_matrix[0]*prod_matrix_x[0][3] + u_matrix[1]*prod_matrix_x[1][3] + u_matrix[2]*prod_matrix_x[2][3] + u_matrix[3]*prod_matrix_x[3][3];
        vector[0] = u_array[0]*v_matrix[0] + u_array[1]*v_matrix[1] + u_array[2]*v_matrix[2] + u_array[3]*v_matrix[3];
        u_array[0] = u_matrix[0]*prod_matrix_y[0][0] + u_matrix[1]*prod_matrix_y[1][0] + u_matrix[2]*prod_matrix_y[2][0] + u_matrix[3]*prod_matrix_y[3][0];
        u_array[1] = u_matrix[0]*prod_matrix_y[0][1] + u_matrix[1]*prod_matrix_y[1][1] + u_matrix[2]*prod_matrix_y[2][1] + u_matrix[3]*prod_matrix_y[3][1];
        u_array[2] = u_matrix[0]*prod_matrix_y[0][2] + u_matrix[1]*prod_matrix_y[1][2] + u_matrix[2]*prod_matrix_y[2][2] + u_matrix[3]*prod_matrix_y[3][2];
        u_array[3] = u_matrix[0]*prod_matrix_y[0][3] + u_matrix[1]*prod_matrix_y[1][3] + u_matrix[2]*prod_matrix_y[2][3] + u_matrix[3]*prod_matrix_y[3][3];
        vector[1] = u_array[0]*v_matrix[0] + u_array[1]*v_matrix[1] + u_array[2]*v_matrix[2] + u_array[3]*v_matrix[3];
        u_array[0] = u_matrix[0]*prod_matrix_z[0][0] + u_matrix[1]*prod_matrix_z[1][0] + u_matrix[2]*prod_matrix_z[2][0] + u_matrix[3]*prod_matrix_z[3][0];
        u_array[1] = u_matrix[0]*prod_matrix_z[0][1] + u_matrix[1]*prod_matrix_z[1][1] + u_matrix[2]*prod_matrix_z[2][1] + u_matrix[3]*prod_matrix_z[3][1];
        u_array[2] = u_matrix[0]*prod_matrix_z[0][2] + u_matrix[1]*prod_matrix_z[1][2] + u_matrix[2]*prod_matrix_z[2][2] + u_matrix[3]*prod_matrix_z[3][2];
        u_array[3] = u_matrix[0]*prod_matrix_z[0][3] + u_matrix[1]*prod_matrix_z[1][3] + u_matrix[2]*prod_matrix_z[2][3] + u_matrix[3]*prod_matrix_z[3][3];
        vector[2] = u_array[0]*v_matrix[0] + u_array[1]*v_matrix[1] + u_array[2]*v_matrix[2] + u_array[3]*v_matrix[3];

        return vector;
    }

    private float[] calculate_V_Derivate(float u, float v) {
        float[] u_matrix = {u*u*u,u*u,u,1};
        float[] v_matrix = {3*v*v,2*v,1,0};
        float[] vector = new float[3];
        float[] u_array = new float[4];

        u_array[0] = u_matrix[0]*prod_matrix_x[0][0] + u_matrix[1]*prod_matrix_x[1][0] + u_matrix[2]*prod_matrix_x[2][0] + u_matrix[3]*prod_matrix_x[3][0];
        u_array[1] = u_matrix[0]*prod_matrix_x[0][1] + u_matrix[1]*prod_matrix_x[1][1] + u_matrix[2]*prod_matrix_x[2][1] + u_matrix[3]*prod_matrix_x[3][1];
        u_array[2] = u_matrix[0]*prod_matrix_x[0][2] + u_matrix[1]*prod_matrix_x[1][2] + u_matrix[2]*prod_matrix_x[2][2] + u_matrix[3]*prod_matrix_x[3][2];
        u_array[3] = u_matrix[0]*prod_matrix_x[0][3] + u_matrix[1]*prod_matrix_x[1][3] + u_matrix[2]*prod_matrix_x[2][3] + u_matrix[3]*prod_matrix_x[3][3];
        vector[0] = u_array[0]*v_matrix[0] + u_array[1]*v_matrix[1] + u_array[2]*v_matrix[2] + u_array[3]*v_matrix[3];
        u_array[0] = u_matrix[0]*prod_matrix_y[0][0] + u_matrix[1]*prod_matrix_y[1][0] + u_matrix[2]*prod_matrix_y[2][0] + u_matrix[3]*prod_matrix_y[3][0];
        u_array[1] = u_matrix[0]*prod_matrix_y[0][1] + u_matrix[1]*prod_matrix_y[1][1] + u_matrix[2]*prod_matrix_y[2][1] + u_matrix[3]*prod_matrix_y[3][1];
        u_array[2] = u_matrix[0]*prod_matrix_y[0][2] + u_matrix[1]*prod_matrix_y[1][2] + u_matrix[2]*prod_matrix_y[2][2] + u_matrix[3]*prod_matrix_y[3][2];
        u_array[3] = u_matrix[0]*prod_matrix_y[0][3] + u_matrix[1]*prod_matrix_y[1][3] + u_matrix[2]*prod_matrix_y[2][3] + u_matrix[3]*prod_matrix_y[3][3];
        vector[1] = u_array[0]*v_matrix[0] + u_array[1]*v_matrix[1] + u_array[2]*v_matrix[2] + u_array[3]*v_matrix[3];
        u_array[0] = u_matrix[0]*prod_matrix_z[0][0] + u_matrix[1]*prod_matrix_z[1][0] + u_matrix[2]*prod_matrix_z[2][0] + u_matrix[3]*prod_matrix_z[3][0];
        u_array[1] = u_matrix[0]*prod_matrix_z[0][1] + u_matrix[1]*prod_matrix_z[1][1] + u_matrix[2]*prod_matrix_z[2][1] + u_matrix[3]*prod_matrix_z[3][1];
        u_array[2] = u_matrix[0]*prod_matrix_z[0][2] + u_matrix[1]*prod_matrix_z[1][2] + u_matrix[2]*prod_matrix_z[2][2] + u_matrix[3]*prod_matrix_z[3][2];
        u_array[3] = u_matrix[0]*prod_matrix_z[0][3] + u_matrix[1]*prod_matrix_z[1][3] + u_matrix[2]*prod_matrix_z[2][3] + u_matrix[3]*prod_matrix_z[3][3];
        vector[2] = u_array[0]*v_matrix[0] + u_array[1]*v_matrix[1] + u_array[2]*v_matrix[2] + u_array[3]*v_matrix[3];

        return vector;

    }




    private void readFile() {
        BufferedReader br = null;
        FileReader fr = null;
        int lines_read = 0;

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String current;
            current = br.readLine();
            lines_read++;
            if(current != null) {
                number_of_patches = Integer.parseInt(current);
                control_points_index = new int[number_of_patches][CONTROL_POINTS_PER_PATCH];
                for(int i = 0; i<number_of_patches; i++) {
                    if((current = br.readLine()) != null) {
                        String[] parts = current.split(",");
                        int[] int_parts = convertToInt(parts);
                        control_points_index[i] = int_parts;
                        lines_read++;
                    }
                }

                current = br.readLine();
                lines_read++;
                if(current!=null) {
                    number_of_control_points = Integer.parseInt(current);
                    control_points = new float[number_of_control_points][3];
                    for(int i = 0; i<number_of_control_points;i++) {
                        if((current = br.readLine()) != null) {
                            String[] parts = current.split(",");
                            float[] float_parts = new float[parts.length];
                            float_parts[0] = Float.parseFloat(parts[0].trim());
                            float_parts[1] = Float.parseFloat(parts[1].trim());
                            float_parts[2] = Float.parseFloat(parts[2].trim());
                            control_points[i] = float_parts;
                            lines_read++;
                        }
                    }
                } else {
                    System.out.println("Couldn't continue reading the file; Wrong at control points\n");
                }
            } else {
                System.out.println("Couldn't start reading the file; Wrong at number of patches\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] convertToInt(String[] parts) {
        int[] int_parts = new int[parts.length];
        for(int i=0; i < parts.length;i++)
            int_parts[i] = Integer.parseInt(parts[i].trim());

        return int_parts;
    }
}
