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
                if(argc>5){
                    System.out.println("Too many arguments");
                }else if(argc<5)
                    System.out.println("Not enough arguments");
                else {
                    Box bo = new Box(Float.parseFloat(cmd[1]), Float.parseFloat(cmd[2]), Float.parseFloat(cmd[3]), cmd[4]);
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
                s.generateFile();
                r.generateFile();
                XMLcreate();
                break;
            case "comet":
                    Comet c = new Comet(cmd[1], Integer.parseInt(cmd[2]));
                    c.generateFile();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }

    }

    public static void XMLcreate(){
        String s="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><scene>\n" +
                " <group>\n" +
                "  <translate X=\"-8\"/>\n" +
                "  <scale X=\"8\" Y=\"8\" Z=\"8\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"1.265\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-0.1\"/>\n" +
                "  <scale X=\"0.218\" Y=\"0.218\" Z=\"0.218\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"2.4\"/>\n" +
                "  <rotate angle=\"-177\" axisX=\"0\" axisY=\"0\" axisZ=\"1\"/>\n" +
                "  <scale X=\"0.54\" Y=\"0.54\" Z=\"0.54\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"4.2\"/>\n" +
                "  <scale X=\"0.568\" Y=\"0.568\" Z=\"0.568\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-23.5\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                "  <group>\n" +
                "   <translate Y=\"2\"/>\n" +
                "   <scale X=\"0.4\" Y=\"0.4\" Z=\"0.4\"/>\n" +
                "   <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"18.35\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/sphere.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"6.065\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-25\"/>\n" +
                "  <scale X=\"0.302\" Y=\"0.302\" Z=\"0.302\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                "  <group>\n" +
                "   <translate Y=\"1.85\"/>\n" +
                "   <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"24\"/>\n" +
                "   <scale X=\"0.5\" Y=\"0.5\" Z=\"0.5\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/sphere.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"17.335\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-3\"/>\n" +
                "  <scale X=\"2.2\" Y=\"2.2\" Z=\"2.2\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                "  <group>\n" +
                "   <translate X=\"-2.16\" Y=\"2.43\"/>\n" +
                "   <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"2.8\"/>\n" +
                "   <scale X=\"0.2\" Y=\"0.2\" Z=\"0.2\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/sphere.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"31.65\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-27\"/>\n" +
                "  <scale X=\"2\" Y=\"2\" Z=\"2\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                "  <group>\n" +
                "   <scale X=\"1.2\" Z=\"1.2\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/ring.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                "  <group>\n" +
                "   <translate Y=\"3.84\"/>\n" +
                "   <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"26.67\"/>\n" +
                "   <scale X=\"0.12\" Y=\"0.12\" Z=\"0.12\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/sphere.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"50.995\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-98\"/>\n" +
                "  <scale X=\"1.2\" Y=\"1.2\" Z=\"1.2\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                "  <group>\n" +
                "   <translate Y=\"-2.28\"/>\n" +
                "   <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"97.86\"/>\n" +
                "   <scale X=\"0.15\" Y=\"0.15\" Z=\"0.15\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/sphere.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                " </group>\n" +
                "\n" +
                " <group>\n" +
                "  <translate X=\"75\"/>\n" +
                "  <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-30\"/>\n" +
                "  <scale X=\"1.1\" Y=\"1.1\" Z=\"1.1\"/>\n" +
                "  <models>\n" +
                "   <model file=\"../../Files/sphere.3d\"/>\n" +
                "  </models>\n" +
                "  <group>\n" +
                "   <translate Y=\"-2.23\"/>\n" +
                "   <rotate X=\"0\" Y=\"0\" Z=\"1\" angle=\"-127.35\"/>\n" +
                "   <scale X=\"0.2\" Y=\"0.2\" Z=\"0.2\"/>\n" +
                "   <models>\n" +
                "    <model file=\"../../Files/sphere.3d\"/>\n" +
                "   </models>\n" +
                "  </group>\n" +
                " </group>\n" +
                "</scene>\n";
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
