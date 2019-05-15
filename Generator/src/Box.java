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
    private float div;
    private String file_dest;

    public Box() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.file_dest = "n/a";
    }

    public Box(float x, float y, float z,  float div,String file_dest) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.div = div;
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
        generatePoints();
        generateNormals();
        generateTextures();
    }

    public void generatePoints() {
        Path p = Paths.get("../Files/" + this.file_dest);
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            faceEsq(writer);
            faceDir(writer);
            faceCima(writer);
            faceBaixo(writer);
            faceFrente(writer);
            faceAtras(writer);
        } catch (IOException e) {
            e.getMessage();
        }
    }


    public void generateNormals() {
        Path p = Paths.get("../Files/" + this.file_dest + ".n");
        try (BufferedWriter writer = Files.newBufferedWriter(p)){
            normalEsquerda(writer);
            normalDireita(writer);
            normalCima(writer);
            normalBaixo(writer);
            normalFrente(writer);
            normalTras(writer);
        } catch (Exception e) {

        }
    }

    public void generateTextures() {
        Path p = Paths.get("../Files/" + this.file_dest + ".t");
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            texturaEsquerda(writer);
            texturaDireita(writer);
            texturaCima(writer);
            texturaBaixo(writer);
            texturaFrente(writer);
            texturaTras(writer);
        } catch (Exception e) {

        }
    }
    public void faceDir(BufferedWriter writer){
        try{
            int l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    writer.write(Float.toString(x)+"\n");
                    writer.write(Float.toString(-y + l * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c + 1) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(x)+"\n");
                    writer.write(Float.toString(-y + (l + 1) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(x)+"\n");
                    writer.write(Float.toString(-y + (l) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c) * 2 * z / this.div)+"\n");

                    writer.write(Float.toString(x)+"\n");
                    writer.write(Float.toString(-y + l * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c + 1) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(x)+"\n");
                    writer.write(Float.toString(-y + (l + 1) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c+1) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(x)+"\n");
                    writer.write(Float.toString(-y + (l+1) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c) * 2 * z / this.div)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void faceEsq(BufferedWriter writer){
        try{
            int l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    writer.write(Float.toString(-x)+"\n");
                    writer.write(Float.toString(-y + (l + 1) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(-x)+"\n");
                    writer.write(Float.toString(-y + l * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c + 1) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(-x)+"\n");
                    writer.write(Float.toString(-y + (l) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c) * 2 * z / this.div)+"\n");

                    writer.write(Float.toString(-x)+"\n");
                    writer.write(Float.toString(-y + (l + 1) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c+1) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(-x)+"\n");
                    writer.write(Float.toString(-y + l * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c + 1) * 2 * z / this.div)+"\n");
                    writer.write(Float.toString(-x)+"\n");
                    writer.write(Float.toString(-y + (l+1) * 2 * y / this.div)+"\n");
                    writer.write(Float.toString(z - (c) * 2 * z / this.div)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void faceCima(BufferedWriter writer){
        try{
            int l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(y)+"\n");
                    writer.write(Float.toString(z-(c)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(y)+"\n");
                    writer.write(Float.toString(z-(c+1)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(y)+"\n");
                    writer.write(Float.toString(z-(c)*2*z/div)+"\n");

                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(y)+"\n");
                    writer.write(Float.toString(z-(c)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(y)+"\n");
                    writer.write(Float.toString(z-(c+1)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(y)+"\n");
                    writer.write(Float.toString(z-(c+1)*2*z/div)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void faceBaixo(BufferedWriter writer){
        try{
            int l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(-y)+"\n");
                    writer.write(Float.toString(z-(c+1)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(-y)+"\n");
                    writer.write(Float.toString(z-(c)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(-y)+"\n");
                    writer.write(Float.toString(z-(c)*2*z/div)+"\n");

                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(-y)+"\n");
                    writer.write(Float.toString(z-(c+1)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(-y)+"\n");
                    writer.write(Float.toString(z-(c)*2*z/div)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(-y)+"\n");
                    writer.write(Float.toString(z-(c+1)*2*z/div)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void faceFrente(BufferedWriter writer){
        try{
            int l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(-y+c*2*y/div)+"\n");
                    writer.write(Float.toString(z)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(-y+(c+1)*2*y/div)+"\n");
                    writer.write(Float.toString(z)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(-y+c*2*y/div)+"\n");
                    writer.write(Float.toString(z)+"\n");

                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(-y+c*2*y/div)+"\n");
                    writer.write(Float.toString(z)+"\n");
                    writer.write(Float.toString(-x+(l+1)*2*x/div)+"\n");
                    writer.write(Float.toString(-y+(c+1)*2*y/div)+"\n");
                    writer.write(Float.toString(z)+"\n");
                    writer.write(Float.toString(-x+(l)*2*x/div)+"\n");
                    writer.write(Float.toString(-y+(c+1)*2*y/div)+"\n");
                    writer.write(Float.toString(z)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void faceAtras(BufferedWriter writer) {
        try {
            int l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    writer.write(Float.toString(-x + (l) * 2 * x / div) + "\n");
                    writer.write(Float.toString(-y + (c + 1) * 2 * y / div) + "\n");
                    writer.write(Float.toString(-z) + "\n");
                    writer.write(Float.toString(-x + (l + 1) * 2 * x / div) + "\n");
                    writer.write(Float.toString(-y + c * 2 * y / div) + "\n");
                    writer.write(Float.toString(-z) + "\n");
                    writer.write(Float.toString(-x + (l) * 2 * x / div) + "\n");
                    writer.write(Float.toString(-y + c * 2 * y / div) + "\n");
                    writer.write(Float.toString(-z) + "\n");

                    writer.write(Float.toString(-x + (l + 1) * 2 * x / div) + "\n");
                    writer.write(Float.toString(-y + (c + 1) * 2 * y / div) + "\n");
                    writer.write(Float.toString(-z) + "\n");
                    writer.write(Float.toString(-x + (l + 1) * 2 * x / div) + "\n");
                    writer.write(Float.toString(-y + c * 2 * y / div) + "\n");
                    writer.write(Float.toString(-z) + "\n");
                    writer.write(Float.toString(-x + (l) * 2 * x / div) + "\n");
                    writer.write(Float.toString(-y + (c + 1) * 2 * y / div) + "\n");
                    writer.write(Float.toString(-z) + "\n");
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void normalFrente(BufferedWriter writer) {
        try{
            int l,c;
            for(l=0;l<this.div;l++){
                for(c=0;c<this.div;c++){
                    writer.write("0\n0\n1\n");
                    writer.write("0\n0\n1\n");
                    writer.write("0\n0\n1\n");
                    writer.write("0\n0\n1\n");
                    writer.write("0\n0\n1\n");
                    writer.write("0\n0\n1\n");

                }
            }
        } catch (Exception e) {}

    }

    public void normalTras(BufferedWriter writer) {
        try{
            int l,c;
            for(l=0;l<this.div;l++){
                for(c=0;c<this.div;c++) {
                    writer.write("0\n0\n-1\n");
                    writer.write("0\n0\n-1\n");
                    writer.write("0\n0\n-1\n");
                    writer.write("0\n0\n-1\n");
                    writer.write("0\n0\n-1\n");
                    writer.write("0\n0\n-1\n");
                }
            }
        } catch(Exception e) {}
    }

    public void normalEsquerda(BufferedWriter writer) {
        try{
            int l,c;
            for(l=0;l<this.div;l++){
                for(c=0;c<this.div;c++) {
                    writer.write("-1\n0\n0\n");
                    writer.write("-1\n0\n0\n");
                    writer.write("-1\n0\n0\n");
                    writer.write("-1\n0\n0\n");
                    writer.write("-1\n0\n0\n");
                    writer.write("-1\n0\n0\n");
                }
            }

        } catch(Exception e) {}
    }

    public void normalDireita(BufferedWriter writer) {
        try{
            int l,c;
            for(l=0;l<this.div;l++){
                for(c=0;c<this.div;c++) {
                    writer.write("1\n0\n0\n");
                    writer.write("1\n0\n0\n");
                    writer.write("1\n0\n0\n");
                    writer.write("1\n0\n0\n");
                    writer.write("1\n0\n0\n");
                    writer.write("1\n0\n0\n");
                }
            }

        } catch(Exception e) {}
    }

    public void normalCima(BufferedWriter writer){
        try{
            int l,c;
            for(l=0;l<this.div;l++){
                for(c=0;c<this.div;c++) {
                    writer.write("0\n1\n0\n");
                    writer.write("0\n1\n0\n");
                    writer.write("0\n1\n0\n");
                    writer.write("0\n1\n0\n");
                    writer.write("0\n1\n0\n");
                    writer.write("0\n1\n0\n");
                }
            }

        } catch(Exception e) {}
    }

    public void normalBaixo(BufferedWriter writer){
        try{
            int l,c;
            for(l=0;l<this.div;l++){
                for(c=0;c<this.div;c++) {
                    writer.write("0\n-1\n0\n");
                    writer.write("0\n-1\n0\n");
                    writer.write("0\n-1\n0\n");
                    writer.write("0\n-1\n0\n");
                    writer.write("0\n-1\n0\n");
                    writer.write("0\n-1\n0\n");
                }
            }
        } catch(Exception e) {}
    }
    private void texturaDireita(BufferedWriter writer){
        try{
            float c,l;
            for (l = 0; l < this.div; l++) {

                float y1 = l/(div*2);
                float y2 = (l+1) / (div*2);

                for (c = 0; c < this.div; c++) {
                    float x1= Math.abs((c)/(div*3));
                    float x2= Math.abs((c+1)/(div*3));
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y1)+"\n");

                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void texturaEsquerda(BufferedWriter writer){
        try{
            float c,l;
            for (l = 0; l < this.div; l++) {
                float y1 = 0.5f + l /(div*2);
                float y2 = 0.5f + (l+1) /(div*2);
                for (c = 0; c < this.div; c++) {
                    float x1= c/(div*3);
                    float x2= (c+1)/(div*3);
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y1)+"\n");

                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void texturaCima(BufferedWriter writer){
        try{
            float l,c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    float y1=(c)/(div*2);
                    float y2=(c+1)/(div*2);
                    float x1=(1f/3f) + (l)/(div*3);
                    float x2=(1f/3f) + (l+1)/(div*3);
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y1)+"\n");

                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void texturaBaixo(BufferedWriter writer){
        try{
            float l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    float y1 = 0.5f + (c/2) / div;
                    float y2 = 0.5f + ((c+1)/2) / div;
                    float x1 = (1f/3f) + (l/3) / div;
                    float x2 = (1f/3f) + ((l+1)/3) / div;
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y1)+"\n");

                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void texturaFrente(BufferedWriter writer){
        try{
            float l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    float y1 = (c/2) / div;
                    float y2 = ((c+1)/2) / div;
                    float x1 = (2f/3f) + (l/3) / div;
                    float x2 = (2f/3f) + ((l+1)/3) / div;
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y1)+"\n");

                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void texturaTras(BufferedWriter writer){
        try{
            float l, c;
            for (l = 0; l < this.div; l++) {
                for (c = 0; c < this.div; c++) {
                    float y1 = 0.5f +  c  / (div*2);
                    float y2 = 0.5f + (c+1) /(div*2);
                    float x1 = 1f - l /(div*3);
                    float x2 = 1f - (l+1)/(div*3);

                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y1)+"\n");

                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                    writer.write(Float.toString(x2)+"\n");
                    writer.write(Float.toString(y1)+"\n");
                    writer.write(Float.toString(x1)+"\n");
                    writer.write(Float.toString(y2)+"\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
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
