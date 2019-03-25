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

public class Plane {

    private String file_de;
    private float comp;

    public Plane() {
        this.file_de = "";
        this.comp=0;

    }

    public Plane(float comp,String file) {
        this.file_de = file;
        this.comp=comp;
    }

    public void generateFile() {
        Path p = Paths.get("/Files/" + this.file_de);
            try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            writer.write(Float.toString(-comp/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(comp/2)+ "\n");
            writer.write(Float.toString(comp/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(comp/2)+ "\n");
            writer.write(Float.toString(comp/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-comp/2)+ "\n");
            writer.write(Float.toString(-comp/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(comp/2)+ "\n");
            writer.write(Float.toString(comp/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-comp/2)+ "\n");
            writer.write(Float.toString(-comp/2)+ "\n");
            writer.write(Float.toString(0)+ "\n");
            writer.write(Float.toString(-comp/2)+ "\n");


        } catch (IOException e) {
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
            attr.setValue("../Files/" + this.file_de);
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
                attr.setValue("../Files/" + this.file_de);
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
