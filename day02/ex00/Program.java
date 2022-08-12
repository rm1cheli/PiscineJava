import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Program {
    public static void main(String[] args) {
        SignPars sign = new SignPars();
        FileInputStream file  = null;
        try {
            file = new FileInputStream("signatures.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<short[], String> ret = sign.getKey(file);
        FilesSign sig = new FilesSign();
        List<String> t = sig.fileSign(ret);
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
