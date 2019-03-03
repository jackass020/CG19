public class Generator {


    public static void generateCmd (String [] cmd){

        String type = cmd[0];

        switch(type){

            case "plane":
                Plane plane = new Plane (Float.parseFloat(cmd[1]),cmd[2]);
                plane.generateFile();
                plane.writeToXML();
                break;
            case "sphere":
                Sphere sp = new Sphere(Float.parseFloat(cmd[1]),Float.parseFloat(cmd[2]),Float.parseFloat(cmd[3]), cmd[4]);
                sp.generateFile();
                sp.writeToXML();
                break;
            case "cone":
                Cone co = new Cone(Float.parseFloat(cmd[1]),Float.parseFloat(cmd[2]),Float.parseFloat(cmd[3]),Float.parseFloat(cmd[4]),cmd[5]);
                co.generateFile();
                co.writeToXML();
                break;
            case "box":
                Box bo = new Box(Float.parseFloat(cmd[1]),Float.parseFloat(cmd[2]),Float.parseFloat(cmd[3]),cmd[4]);
                bo.generateFile();
                break;
        }

    }
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("You need to provide more argumemts");
            return;
        }
        generateCmd(args);
    }
}
