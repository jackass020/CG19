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
    private int number_of_patches;
    private int number_of_control_points;
    private static final int CONTROL_POINTS_PER_PATCH = 16;
    private int[][] control_points_index;
    private float[][] control_points;
    private List<Point> vertexes;
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

    public Comet(String file,int tesselation) {
        vertexes = new ArrayList<>();
        this.file=file;
        this.tesselation=tesselation;
    }

    public void generateFile() {
        readFile();
        calculate_Bezier();
        // Tentei fazer caminhos relativos no meu mas escaxou, podes alterar se funcar com relativos
        Path p = Paths.get("/home/marcoriano/Documents/Cadeiras/18-19/CG/TP/CG19/Files/comet.3d");
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
                    vertexes.add(point);
                    vertexes.add(point1);
                    vertexes.add(point2);
                    vertexes.add(point2);
                    vertexes.add(point1);
                    vertexes.add(point3);
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
