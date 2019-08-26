package app;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            System.out.println("Error");
        else if (args[0] == "-h") {
            System.out.println("Enter Path to File To Be Hidden:");
            String mpath = System.console().readLine();
            System.out.println("Enter Path to Image File:");
            String cpath = System.console().readLine();
            System.out.println("Enter Name to Final Image:");
            String fpath = System.console().readLine();
            System.out.println("Enter Password");
            char[] passString = System.console().readPassword();
            String pass = new String(passString);
            try {
                Embedder emb = new Embedder(pass, mpath, cpath,fpath);
                emb.embed();
            } catch (Exception ex) {
                System.out.println("Err: " + ex);
            }
        } else if (args[0] == "-r") {
            System.out.println("Enter Path of Encrypted File:");
            String mpath = System.console().readLine();
            System.out.println("Enter Path to Extract To:");
            String cpath = System.console().readLine();
            System.out.println("Enter Password");
            char[] passString = System.console().readPassword();
            String pass = new String(passString);
            try {
                Extractor ext = new Extractor(pass, mpath, cpath);
                ext.extract();
            } catch (Exception ex) {
                System.out.println("Err: " + ex);
            }

        } else {
            System.out.println("Invalid Arguments");
        }
    }
}