import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {


    public static void generateCmd (String [] cmd){
        String type = cmd[0];
        int argc =cmd.length;

        switch(type){
            case "plane":
                if(argc>3){
                    System.out.println("Too many arguments");
                }else if(argc<3)
                    System.out.println("Not enough arguments");
                else {
                    Plane plane = new Plane(Float.parseFloat(cmd[1]), cmd[2]);
                    plane.generateFile();

                }
                break;
            case "sphere":
                if(argc>5){
                    System.out.println("Too many arguments");
                }else if(argc<5)
                    System.out.println("Not enough arguments");
                else {
                    Sphere sp = new Sphere(Float.parseFloat(cmd[1]), Float.parseFloat(cmd[2]), Float.parseFloat(cmd[3]), cmd[4]);
                    sp.generateFile();

                }
                break;
            case "cone":
                if(argc>6){
                    System.out.println("Too many arguments");
                }else if(argc<6)
                    System.out.println("Not enough arguments");
                else {
                    Cone co = new Cone(Float.parseFloat(cmd[1]), Float.parseFloat(cmd[2]), Float.parseFloat(cmd[3]), Float.parseFloat(cmd[4]), cmd[5]);
                    co.generateFile();

                }
                break;
            case "box":
                if(argc>6){
                    System.out.println("Too many arguments");
                }else if(argc<6)
                    System.out.println("Not enough arguments");
                else {
                    Box bo = new Box(Float.parseFloat(cmd[1]), Float.parseFloat(cmd[2]), Float.parseFloat(cmd[3]), Float.parseFloat(cmd[4]), cmd[5]);
                    bo.generateFile();

                }
                break;
            case "ring":
                if(argc>5){
                    System.out.println("Too many arguments");
                }else if(argc<5)
                    System.out.println("Not enough arguments");
                else {
                    Ring ri = new Ring(cmd[1], Float.parseFloat(cmd[2]), Float.parseFloat(cmd[3]), Integer.parseInt(cmd[4]));
                    ri.generateFile();
                }
                break;
            case "Solarsystem":
                Sphere s = new Sphere(1,50,50,"sphere.3d");
                Ring r =  new Ring("ring.3d",1,2, 50);
                Comet comet= new Comet ("../Files/teapot.patch", 10, "comet.3d");
                s.generateFile();
                r.generateFile();
                comet.generateFile();
                XMLcreate();
                break;
            case "comet":
                    Comet c = new Comet(cmd[1], Integer.parseInt(cmd[2]), cmd[3]);
                    c.generateFile();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }

    }

    public static void XMLcreate(){
        String s="<scene>\n" +
                " <!--Sol-->\n" +
                " <group>\n" +
                "  <scale X=\"5\" Y=\"5\" Z=\"5\" />\n" +
                "  <rotate time=\"52\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <models>\n" +
                "   <model  file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Mercúrio-->\n" +
                " <group>\n" +
                "   <translate time=\"70\">\n" +
                "   <point X=\"9.265\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"6.55\" Y=\"0\" Z=\"-6.55\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-9.265\"/>\n" +
                "   <point X=\"-6.55\" Y=\"0\" Z=\"-6.55\"/>\n" +
                "   <point X=\"-9.265\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-6.55\" Y=\"0\" Z=\"6.55\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"9.265\"/>\n" +
                "   <point X=\"6.55\" Y=\"0\" Z=\"6.55\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"60\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"0.218\" Y=\"0.218\" Z=\"0.218\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Vénus-->\n" +
                " <group>\n" +
                "  <translate time=\"80\">\n" +
                "   <point X=\"10.4\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"7.35\" Y=\"0\" Z=\"-7.35\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-10.4\"/>\n" +
                "   <point X=\"-7.35\" Y=\"0\" Z=\"-7.35\"/>\n" +
                "   <point X=\"-10.4\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-7.35\" Y=\"0\" Z=\"7.35\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"10.4\"/>\n" +
                "   <point X=\"7.35\" Y=\"0\" Z=\"7.35\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"-100\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"0.54\" Y=\"0.54\" Z=\"0.54\" />\n" +
                "  <models>\n" +
                "   <model  file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Terra-->\n" +
                " <group>\n" +
                "  <translate time=\"94.75\">\n" +
                "   <point X=\"12.2\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"8.627\" Y=\"0\" Z=\"-8.627\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-12.2\"/>\n" +
                "   <point X=\"-8.627\" Y=\"0\" Z=\"-8.627\"/>\n" +
                "   <point X=\"-12.2\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-8.627\" Y=\"0\" Z=\"8.627\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"12.2\"/>\n" +
                "   <point X=\"8.627\" Y=\"0\" Z=\"8.627\"/>\n" +
                "  </translate>\n" +
                "  <scale X=\"0.568\" Y=\"0.568\" Z=\"0.568\" />\n" +
                "  <rotate time=\"10\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Marte-->\n" +
                " <group>\n" +
                "  <translate time=\"105\">\n" +
                "   <point X=\"14.065\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"9.95\" Y=\"0\" Z=\"-9.95\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-14.065\"/>\n" +
                "   <point X=\"-9.95\" Y=\"0\" Z=\"-9.95\"/>\n" +
                "   <point X=\"-14.065\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-9.95\" Y=\"0\" Z=\"9.95\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"14.065\"/>\n" +
                "   <point X=\"9.95\" Y=\"0\" Z=\"9.95\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"12\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"0.302\" Y=\"0.302\" Z=\"0.302\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Júpiter-->\n" +
                " <group>\n" +
                "  <translate time=\"130\">\n" +
                "   <point X=\"25.4\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"17.96\" Y=\"0\" Z=\"-17.96\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-25.4\"/>\n" +
                "   <point X=\"-17.96\" Y=\"0\" Z=\"-17.96\"/>\n" +
                "   <point X=\"-25.4\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-17.96\" Y=\"0\" Z=\"17.96\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"25.4\"/>\n" +
                "   <point X=\"17.96\" Y=\"0\" Z=\"17.96\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"6\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"2.2\" Y=\"2.2\" Z=\"2.2\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Saturno-->\n" +
                " <group>\n" +
                "  <translate time=\"150\">\n" +
                "   <point X=\"39.65\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"28.03\" Y=\"0\" Z=\"-28.03\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-39.65\"/>\n" +
                "   <point X=\"-28.03\" Y=\"0\" Z=\"-28.03\"/>\n" +
                "   <point X=\"-39.65\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-28.03\" Y=\"0\" Z=\"28.03\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"39.65\"/>\n" +
                "   <point X=\"28.03\" Y=\"0\" Z=\"28.03\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"7\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"2\" Y=\"2\" Z=\"2\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Urano-->\n" +
                " <group>\n" +
                "  <color X=\"0.529\" Y=\"0.808\" Z=\"0.922\"/>\n" +
                "  <translate time=\"169\">\n" +
                "   <point X=\"49.66\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"35.12\" Y=\"0\" Z=\"-35.12\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-49.66\"/>\n" +
                "   <point X=\"-35.12\" Y=\"0\" Z=\"-35.12\"/>\n" +
                "   <point X=\"-49.66\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-35.12\" Y=\"0\" Z=\"35.12\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"49.66\"/>\n" +
                "   <point X=\"35.12\" Y=\"0\" Z=\"35.12\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"-6.97\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"1.8\" Y=\"1.8\" Z=\"1.8\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <!--Neptuno-->\n" +
                " <group>\n" +
                "  <translate time=\"180\">\n" +
                "   <point X=\"83\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"58.69\" Y=\"0\" Z=\"-58.69\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-83\"/>\n" +
                "   <point X=\"-58.69\" Y=\"0\" Z=\"-58.69\"/>\n" +
                "   <point X=\"-83\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-58.69\" Y=\"0\" Z=\"58.69\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"83\"/>\n" +
                "   <point X=\"58.69\" Y=\"0\" Z=\"58.69\"/>\n" +
                "  </translate>\n" +
                "  <rotate time=\"8\" X=\"0\" Y=\"1\" Z=\"0\" />\n" +
                "  <scale X=\"1.65\" Y=\"1.65\" Z=\"1.65\" />\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                "<group>  \n" +
                "<translate time=\"30\">\n" +
                "   <point X=\"20\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"10\" Y=\"5\" Z=\"-20\"/>\n" +
                "   <point X=\"0\" Y=\"0\" Z=\"-20\"/>\n" +
                "   <point X=\"-20\" Y=\"0\" Z=\"0\"/>\n" +
                "   <point X=\"-10\" Y=\"-5\" Z=\"-20\"/>\n" +
                "   <point X=\"-15\" Y=\"0\" Z=\"20\"/>\n" +
                "  </translate>\n" +
                "  <scale X=\"0.3\" Y=\"0.3\" Z=\"0.3\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/comet.3d\" />\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                "\n" +
                "</scene>";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("../Files/Config.xml"));
            writer.write(s);
            writer.close();
        }
        catch(IOException e){
            e.getMessage();
        }
    }
    public static void main(String[] args) {
        generateCmd(args);
    }
}
