import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ring {
    private String dest_file;
    private float radius;
    private float outter_radious;
    private int slices;

    public Ring(String dest_file, float radius, float outter_radious, int slices) {
        this.dest_file = dest_file;
        this.radius = radius;
        this.outter_radious = outter_radious;
        this.slices = slices;
    }

    private float calculate_xAxis(float radius,double a1) {
        return (radius) * ((float) Math.sin(a1));
    }

    private float calculate_zAxis(float radius, double a1){
        return (radius) * ((float) Math.cos(a1));
    }

    public void generateFile() {
        Path p = Paths.get("../Files/ " + this.dest_file);
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int j;
            double alfa = 2 * Math.PI / (double) slices;
                for(j=0;j<slices;j++){
                    double alfa1 = j * alfa;
                    double alfa2 = (j+1) * alfa;

                    //lado positivo

                    writer.write(Float.toString(calculate_xAxis(outter_radious,alfa1)) + "\n"); // x1
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(outter_radious,alfa1)) + "\n"); // z1
                    writer.write(Float.toString(calculate_xAxis(outter_radious,alfa2)) + "\n"); // x2
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(outter_radious,alfa2)) + "\n"); // z2
                    writer.write(Float.toString(calculate_xAxis(radius,alfa1)) + "\n"); //x4
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(radius,alfa1))+ "\n"); //z4
                    writer.write(Float.toString(calculate_xAxis(outter_radious,alfa2)) + "\n"); //x2;
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(outter_radious,alfa2)) + "\n"); //z2
                    writer.write(Float.toString(calculate_xAxis(radius,alfa2)) + "\n"); //x3
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(radius,alfa2)) + "\n"); //z3
                    writer.write(Float.toString(calculate_xAxis(radius,alfa1)) + "\n"); //x4
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(radius,alfa1))+ "\n"); //z4


                    // lado negativo

                    writer.write(Float.toString(calculate_xAxis(outter_radious,alfa2)) + "\n"); //x2;
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(outter_radious,alfa2)) + "\n"); //z2

                    writer.write(Float.toString(calculate_xAxis(outter_radious,alfa1)) + "\n"); // x1
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(outter_radious,alfa1)) + "\n"); // z1

                    writer.write(Float.toString(calculate_xAxis(radius,alfa1)) + "\n"); //x4
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(radius,alfa1))+ "\n"); //z4

                    writer.write(Float.toString(calculate_xAxis(radius,alfa1)) + "\n"); //x4
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(radius,alfa1))+ "\n"); //z4

                    writer.write(Float.toString(calculate_xAxis(radius,alfa2)) + "\n"); //x3
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(radius,alfa2)) + "\n"); //z3


                    writer.write(Float.toString(calculate_xAxis(outter_radious,alfa2)) + "\n"); //x2;
                    writer.write(Float.toString(0) + "\n"); // y
                    writer.write(Float.toString(calculate_zAxis(outter_radious,alfa2)) + "\n"); //z2




                }
            } catch(IOException ex) {}
    }

    public void generateNormals() {
        Path p = Paths.get("../Files/ " + this.dest_file + ".n");
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int j;
            double alfa = 2 * Math.PI / (double) slices;
            for(j=0;j<slices;j++){
                double alfa1 = j * alfa;
                double alfa2 = (j+1) * alfa;

                //lado positivo

                writer.write(Float.toString(0) + "\n"); // x1
                writer.write(Float.toString(1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z1

                writer.write(Float.toString(0) + "\n"); // x2
                writer.write(Float.toString(1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z2

                writer.write(Float.toString(0) + "\n"); // x4
                writer.write(Float.toString(1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z4

                writer.write(Float.toString(0) + "\n"); // x2;
                writer.write(Float.toString(1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z2

                writer.write(Float.toString(0) + "\n"); // x3
                writer.write(Float.toString(1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z3

                writer.write(Float.toString(0) + "\n"); // x4
                writer.write(Float.toString(1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z4


                // lado negativo

                writer.write(Float.toString(0) + "\n"); //x2;
                writer.write(Float.toString(-1) + "\n"); // y
                writer.write(Float.toString(0) + "\n");//z2

                writer.write(Float.toString(0) + "\n"); // x1
                writer.write(Float.toString(-1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); // z1

                writer.write(Float.toString(0) + "\n"); //x4
                writer.write(Float.toString(-1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); //z4

                writer.write(Float.toString(0) + "\n"); //x4
                writer.write(Float.toString(-1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); //z4

                writer.write(Float.toString(0) + "\n"); //x3
                writer.write(Float.toString(-1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); //z3


                writer.write(Float.toString(0) + "\n"); //x2;
                writer.write(Float.toString(-1) + "\n"); // y
                writer.write(Float.toString(0) + "\n"); //z2




            }
        } catch(IOException ex) {}

    }

}
