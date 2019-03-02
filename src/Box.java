import java.io.IOException;
import java.io.PrintWriter;

public class Box {

    private String file_de;
    private float comp;


    //requires X, Y and Z dimensions, and optionally the number of divisions
    private float x;
    private float y;
    private float z;
    private float div;
    private String file_dest;

    public Box() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.div = 0;
        this.file_dest = "n/a";
    }

    public Box(float x, float y, float z, float div, String file_dest) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.div = div;
        this.file_dest = file_dest;
    }

    public Box(float x, float y, float z, String file_dest) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.div = 1;
        this.file_dest = file_dest;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getDiv() {
        return div;
    }

    public void setDiv(float div) {
        this.div = div;
    }

    public void generateFile() {
        try (PrintWriter pr = new PrintWriter(this.file_de)) {


        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void faceDir(PrintWriter pr){

        try {

            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceEsq(PrintWriter pr){

        try {

            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceCima(PrintWriter pr){

        try {

            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceBaixo(PrintWriter pr){

        try {

            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceFrente(PrintWriter pr){

        try {

            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceAtras(PrintWriter pr){

        try {

            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(-x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");
            pr.write(Float.toString(x/2)+ "\n");
            pr.write(Float.toString(y)+ "\n");
            pr.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }



}
