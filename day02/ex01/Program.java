import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2){
            System.err.println("Error arguments");
            return;
        }
        ParseFile tt = new ParseFile();
        try {
            args = tt.ReadFile(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Similarity sim  = new Similarity();
        try {
            sim.сomparison(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
