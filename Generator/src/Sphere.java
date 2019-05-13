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

public class Sphere {
    private float radius;
    private float slices;
    private float stacks;
    private String dest_file;

    public Sphere() {
        this.radius = 0;
        this.slices = 0;
        this.stacks = 0;
        this.dest_file = null;
    }

    public Sphere(float radius, float slices, float stacks,String dest_file) {
        this.radius = radius;
        this.slices = slices;
        this.stacks = stacks;
        this.dest_file = dest_file;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSlices() {
        return slices;
    }

    public void setSlices(float slices) {
        this.slices = slices;
    }

    public float getStacks() {
        return stacks;
    }

    public void setStacks(float stacks) {
        this.stacks = stacks;
    }

    public String getDest_file() {
        return dest_file;
    }

    public void setDest_file(String dest_file) {
        this.dest_file = dest_file;
    }

    private float calculate_zAxis(float radius, double a1, double a2) {
        return (radius * ((float) Math.cos(a1)) * ((float) Math.cos(a2)));
    }

    private float calculate_xAxis(float radius, double a1, double a2) {
        return (radius * ((float) Math.sin(a1)) * ((float) Math.cos(a2)));
    }

    private float calculate_yAxis(float radius, double a1) {
        return (radius * ((float) Math.sin(a1)));
    }

    public void generateFile() {
        calculate_Points();
        calculate_Normals();
        calculate_Textures();
    }

    private float[] normalize_vector(float[] array) {
        float distance = (float) Math.sqrt(array[0]*array[0] + array[1]*array[1] + array[2]*array[2]);
        float res[] = new float[3];
        res[0] = array[0] / distance;
        res[1] = array[1] / distance;
        res[2] = array[2] / distance;
        return array;
    }

    private void calculate_Points() {
        Path p = Paths.get("../Files/" + this.dest_file);
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int j, i;
            double alfa = 2.0 * Math.PI / (double) slices;
            double beta = Math.PI / (double) stacks;
            for (i = 0; i < (stacks * 2); i++) {
                double beta1 = i * beta;
                double beta2 = (i + 1) * beta;
                for (j = 0; j < slices; j++) {
                    double alfa1 = j * alfa;
                    double alfa2 = (j + 1) * alfa;
                    float z1, z2;
                    float y1, y2;
                    y1 = calculate_yAxis(radius, beta1);
                    y2 = calculate_yAxis(radius, beta2);
                    z1 = calculate_zAxis(radius, alfa1, beta2);
                    z2 = calculate_zAxis(radius, alfa2, beta1);
                    writer.write(Float.toString(z1) + "\n");
                    writer.write(Float.toString(y2) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius, alfa1, beta2)) + "\n");
                    writer.write(Float.toString(z2) + "\n");
                    writer.write(Float.toString(y1) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius, alfa2, beta1)) + "\n");
                    writer.write(Float.toString(calculate_zAxis(radius, alfa1, beta1)) + "\n");
                    writer.write(Float.toString(y1) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius, alfa1, beta1)) + "\n");
                    writer.write(Float.toString(z1) + "\n");
                    writer.write(Float.toString(y2) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius, alfa1, beta2)) + "\n");//
                    writer.write(Float.toString(calculate_zAxis(radius, alfa2, beta2)) + "\n");
                    writer.write(Float.toString(y2) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius, alfa2, beta2)) + "\n");
                    writer.write(Float.toString(z2) + "\n");//
                    writer.write(Float.toString(y1) + "\n");
                    writer.write(Float.toString(calculate_xAxis(radius, alfa2, beta1)) + "\n");
                }
            }
        } catch (IOException ex) {
        }
    }

    private void calculate_Normals(){
        Path p = Paths.get("../Files/" + this.dest_file + ".n");
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int j, i;
            double alfa = 2.0 * Math.PI / (double) slices;
            double beta = Math.PI / (double) stacks;
            for (i = 0; i < (stacks * 2); i++) {
                double beta1 = i * beta;
                double beta2 = (i + 1) * beta;
                for (j = 0; j < slices; j++) {
                    double alfa1 = j * alfa;
                    double alfa2 = (j + 1) * alfa;

                    float z1,z2;
                    float y1,y2;

                    y1 = calculate_yAxis(radius, beta1);
                    y2 = calculate_yAxis(radius, beta2);
                    z1 = calculate_zAxis(radius, alfa1, beta2);
                    z2 = calculate_zAxis(radius, alfa2, beta1);

                    float array[] = {z1,y2,calculate_xAxis(radius,alfa1,beta2)};
                    float n_vector[] = normalize_vector(array);
                    float array1[] = {z2,y1,calculate_xAxis(radius,alfa2,beta1)};
                    float[] n_vector1 = normalize_vector(array1);
                    float array2[] = {calculate_zAxis(radius,alfa1,beta1), y1 , calculate_xAxis(radius,alfa1,beta1)};
                    float[] n_vector2 = normalize_vector(array2);
                    float array3[] = {z1,y2, calculate_xAxis(radius,alfa1,beta2)};
                    float[] n_vector3 = normalize_vector(array3);
                    float array4[] = {calculate_zAxis(radius, alfa2, beta2),y2, calculate_xAxis(radius,alfa2,beta2)};
                    float[] n_vector4 = normalize_vector(array4);
                    float array5[] = {z2,y1, calculate_xAxis(radius,alfa2,beta1)};
                    float[] n_vector5 = normalize_vector(array5);

                    writer.write(Float.toString(n_vector[0]) + "\n");
                    writer.write(Float.toString(n_vector[1]) + "\n");
                    writer.write(Float.toString(n_vector[2]) + "\n");
                    writer.write(Float.toString(n_vector1[0]) + "\n");
                    writer.write(Float.toString(n_vector1[1]) + "\n");
                    writer.write(Float.toString(n_vector1[2]) + "\n");
                    writer.write(Float.toString(n_vector2[0]) + "\n");
                    writer.write(Float.toString(n_vector2[1]) + "\n");
                    writer.write(Float.toString(n_vector2[2]) + "\n");
                    writer.write(Float.toString(n_vector3[0]) + "\n");
                    writer.write(Float.toString(n_vector3[1]) + "\n");
                    writer.write(Float.toString(n_vector3[2]) + "\n");
                    writer.write(Float.toString(n_vector4[0]) + "\n");
                    writer.write(Float.toString(n_vector4[1]) + "\n");
                    writer.write(Float.toString(n_vector4[2]) + "\n");
                    writer.write(Float.toString(n_vector5[0]) + "\n");
                    writer.write(Float.toString(n_vector5[1]) + "\n");
                    writer.write(Float.toString(n_vector5[2]) + "\n");


                }
            }
        } catch (Exception e) {}
    }

    private void calculate_Textures() {
        Path p = Paths.get("../Files/" + this.dest_file + ".t");
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int i,j;
            for (i=0;i<stacks;i++){
                float y1 = i / stacks;
                float y2 = (i+1) / stacks;
                for(j=0;j<slices;j++) {
                    float x1 = j / slices;
                    float x2 = (j+1) / slices;

                    writer.write(Float.toString(x1) + "\n");
                    writer.write(Float.toString(y1) + "\n");

                    writer.write(Float.toString(x2) + "\n");
                    writer.write(Float.toString(y1) + "\n");

                    writer.write(Float.toString(x2) + "\n");
                    writer.write(Float.toString(y2) + "\n");

                    writer.write(Float.toString(x1) + "\n");
                    writer.write(Float.toString(y2) + "\n");

                }
            }


        } catch (Exception e) {

        }

        }

    public void writeToXML() {
        StringBuilder sb = new StringBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("../Files/Config.xml");
            Element root = (Element) doc.getDocumentElement();
            Element newModel = doc.createElement("model");
            root.appendChild(newModel);
            Attr attr = doc.createAttribute("file");
            attr.setValue("../Files/" + this.dest_file);
            newModel.setAttributeNode(attr);
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File("../Files/Config.xml"));
            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            try {
                DocumentBuilderFactory dbFactory
                        = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                Element rootElement = doc.createElement("scene");
                doc.appendChild(rootElement);

                Element model = doc.createElement("model");
                rootElement.appendChild(model);
                Attr attr = doc.createAttribute("file");
                attr.setValue("../Files/" + this.dest_file);
                model.setAttributeNode(attr);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("../Files/Config.xml"));
                transformer.transform(source, result);
            } catch (ParserConfigurationException ex1) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (TransformerConfigurationException ex1) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (TransformerException ex1) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (TransformerException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
