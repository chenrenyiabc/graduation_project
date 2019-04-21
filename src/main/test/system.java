public class system {
    public static void main(String[] args) {
        String system = System.getProperty("os.name");
        if (system.toLowerCase().startsWith("windows")){

        }else if(system.toLowerCase().startsWith("linux")){

        }else {

        }
        System.out.println(system);
    }
}
