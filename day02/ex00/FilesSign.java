import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FilesSign {

    public List<String> fileSign(Map<short[], String> sign) {
        List<String> list = new ArrayList<>();
        boolean i;
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        while(!str.equals("42")) {
            i = false;
            for (short[] e : sign.keySet()) {
                try {

                    FileInputStream input = new FileInputStream(str);
                    short[] fileContent = getBytes(e.length, input);
                    input.close();
                    if (compareBytes(e, fileContent)) {
                        list.add(sign.get(e));
                        i = true;
                        break;
                    }
                    input.close();
                } catch (IOException as) {
                    System.out.print("");
                }
            }
            if (i)
                System.out.println("PROCESSED");
            else
                System.out.println("UNDEFINED");
            str = scanner.nextLine();
        }
        scanner.close();
        return list;
    }
    private short[] getBytes(int length, FileInputStream fileScanner) throws IOException {
        short[] bytes = new short[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (short)fileScanner.read();
        }
        return (bytes);
    }

    private boolean compareBytes(short[] key, short[] fileContent) {
        for (int i = 0; i < key.length; i++) {
            if (key[i] != fileContent[i]) {
                return (false);
            }
        }
        return (true);
    }
}
