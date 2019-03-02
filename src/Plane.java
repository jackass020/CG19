import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Plane {

    private String file_de;
    private float comp;

    public Plane() {
        this.file_de = "";
        this.comp=0;

    }

    public Plane(String file, float comp) {
        this.file_de = file;
        this.comp=comp;
    }

    public void generateFile() {
        try (PrintWriter pr = new PrintWriter(this.file_de)) {

            pr.write(Float.toString(-comp/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(comp/2)+ "\n");
            pr.write(Float.toString(comp/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(comp/2)+ "\n");
            pr.write(Float.toString(comp/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-comp/2)+ "\n");
            pr.write(Float.toString(-comp/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(comp/2)+ "\n");
            pr.write(Float.toString(comp/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-comp/2)+ "\n");
            pr.write(Float.toString(-comp/2)+ "\n");
            pr.write(Float.toString(0)+ "\n");
            pr.write(Float.toString(-comp/2)+ "\n");


        } catch (IOException e) {
            e.getMessage();
        }
    }
}
