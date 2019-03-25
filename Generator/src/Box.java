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
public class Box {

    //requires X, Y and Z dimensions, and optionally the number of divisions
    private float x;
    private float y;
    private float z;
    private String file_dest;

    public Box() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.file_dest = "n/a";
    }

    public Box(float x, float y, float z,  String file_dest) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void generateFile() {
        Path p = Paths.get("../Files/" + this.file_dest);
            try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            faceBaixo(writer);
            faceEsq(writer);
            faceDir(writer);
            faceCima(writer);
            faceFrente(writer);
            faceAtras(writer);
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void faceDir(BufferedWriter writer){

        try {
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceEsq(BufferedWriter writer){

        try {

            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceCima(BufferedWriter writer){

        try {

            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceBaixo(BufferedWriter writer){

        try {
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceFrente(BufferedWriter writer){

        try {

            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(-x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");
            writer.write(Float.toString(x/2)+ "\n");
            writer.write(Float.toString(y)+ "\n");
            writer.write(Float.toString(z/2)+ "\n");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void faceAtras(BufferedWriter writer) {

        try {

            writer.write(Float.toString(-x / 2) + "\n");
            writer.write(Float.toString(0) + "\n");
            writer.write(Float.toString(-z / 2) + "\n");
            writer.write(Float.toString(-x / 2) + "\n");
            writer.write(Float.toString(y) + "\n");
            writer.write(Float.toString(-z / 2) + "\n");
            writer.write(Float.toString(x / 2) + "\n");
            writer.write(Float.toString(0) + "\n");
            writer.write(Float.toString(-z / 2) + "\n");
            writer.write(Float.toString(-x / 2) + "\n");
            writer.write(Float.toString(y) + "\n");
            writer.write(Float.toString(-z / 2) + "\n");
            writer.write(Float.toString(x / 2) + "\n");
            writer.write(Float.toString(0) + "\n");
            writer.write(Float.toString(-z / 2) + "\n");
            writer.write(Float.toString(x / 2) + "\n");
            writer.write(Float.toString(y) + "\n");
            writer.write(Float.toString(-z / 2) + "\n");


        } catch (Exception e) {
            e.getMessage();
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
            attr.setValue("../Files/" + this.file_dest);
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
                attr.setValue("../Files/" + this.file_dest);
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
