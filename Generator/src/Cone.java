import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cone {
    private String dest_file;
    private float radius;
    private float height;
    private float slices;
    private float stacks;

    public Cone() {
        this.dest_file="n/a";
        this.height=this.slices=this.stacks=0;
    }

    public Cone(float radius, float height, float slices, float stacks,String dest_file) {
        this.dest_file = dest_file;
        this.radius = radius;
        this.height = height;
        this.slices = slices;
        this.stacks = stacks;
    }

    private float calculate_xAxis(float radius ,int i,float stacks,double a1) {
        return (radius-(radius*i)/stacks) * ((float) Math.sin(a1));
    }

    private float calculate_zAxis(float radius,int i,float stacks, double a1){
        return (radius-(radius*i)/stacks) * ((float) Math.cos(a1));
    }

    private float calculate_height(float height,int i,float stacks) {
        return ((height*i)/stacks);
    }

    public void generateFile() {
        Path p = Paths.get("/home/nelson/Desktop/CG/engine/Files/" + this.dest_file);
            try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int j, i;
            double alfa = 2 * Math.PI / (double) slices;
            for(i=0;i<stacks;i++){
                for(j=0;j<slices;j++){
                    double alfa1 = j * alfa;
                    double alfa2 = (j+1) * alfa;
                    writer.write(Float.toString(calculate_xAxis(radius,i,stacks,alfa2)) + "\n");
                    writer.write(Float.toString(calculate_height(height,i,stacks)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius,i,stacks,alfa2)) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius,i+1,stacks,alfa1)) + "\n");
                    writer.write(Float.toString(calculate_height(height,i+1,stacks)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius,i+1,stacks,alfa1)) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius,i,stacks,alfa1)) + "\n");
                    writer.write(Float.toString(calculate_height(height,i,stacks)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius,i,stacks,alfa1)) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius,i,stacks,alfa2)) + "\n");
                    writer.write(Float.toString(calculate_height(height,i,stacks)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius,i,stacks,alfa2))+ "\n");
                    writer.write(Float.toString(calculate_xAxis(radius,i+1,stacks,alfa2)) + "\n");
                    writer.write(Float.toString(calculate_height(height,i+1,stacks)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius,i+1,stacks,alfa2)) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius,i+1,stacks,alfa1)) + "\n");
                    writer.write(Float.toString(calculate_height(height,i+1,stacks)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius,i+1,stacks,alfa1)) + "\n");
                }
            }
            for(i = 0; i < slices; i++){
                double alfa1 = i * alfa;
                double alfa2 = alfa1 + alfa;
                writer.write(Float.toString(0)+ "\n");
                writer.write(Float.toString(0)+ "\n");
                writer.write(Float.toString(0)+ "\n");
                writer.write(Float.toString(calculate_xAxis(radius,0,1,alfa2)) + "\n");
                writer.write(Float.toString(0)+ "\n");
                writer.write(Float.toString(calculate_zAxis(radius,0,1,alfa2)) + "\n");
                writer.write(Float.toString(calculate_xAxis(radius,0,1,alfa1)) + "\n");
                writer.write(Float.toString(0)+ "\n");
                writer.write(Float.toString(calculate_zAxis(radius,0,1,alfa1)) + "\n");
            }
        } catch (IOException ex) {}
    }




}
