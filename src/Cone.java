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

    public Cone(String dest_file, float radius, float height, float slices, float stacks) {
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
        Path p = Paths.get("./" + this.dest_file);
        System.out.println(p);

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


    public void writeToXML() {
        StringBuilder sb = new StringBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./Config.xml");
            Element root = (Element) doc.getDocumentElement();
            Element newModel = doc.createElement("model");
            root.appendChild(newModel);
            Attr attr = doc.createAttribute("file");
            attr.setValue(this.dest_file);
            newModel.setAttributeNode(attr);
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File("./Config.xml"));
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
                attr.setValue(this.dest_file);
                model.setAttributeNode(attr);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("./Config.xml"));
                transformer.transform(source, result);
            } catch (ParserConfigurationException ex1) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (TransformerConfigurationException ex1) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (TransformerException ex1) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (TransformerException ex) {
        }
    }

}
