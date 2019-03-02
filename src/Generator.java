public class Generator {


    public static void generateCmd (String [] cmd){

        String type = cmd[0];

        switch(type){

            case "plane":
                Plane plane = new Plane (cmd[1], Float.parseFloat(cmd[2]));
                plane.generateFile();
                break;
            case "sphere":
                Sphere sp = new Sphere(cmd[1],Float.parseFloat(cmd[2]),Float.parseFloat(cmd[3]),Float.parseFloat(cmd[4]));
                sp.generateFile();
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
