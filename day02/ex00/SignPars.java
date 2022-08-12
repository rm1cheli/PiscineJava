import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignPars {
    public Map<short[], String> getKey(FileInputStream signature){
        Scanner scanner = new Scanner(signature);
        Map<short[], String> ret = new HashMap<>();
        String line = null;
        if (scanner.hasNextLine())
            line = scanner.nextLine();
        else
            line = "";
        while(!line.isEmpty()){
            short[] key = convertKey(line.substring(line.indexOf(",") + 2));
            String value = line.substring(0, line.indexOf(","));
            if (key.length > 0 && !value.isEmpty()) {
                ret.put(key, value);
            }
            if (scanner.hasNextLine())
                line = scanner.nextLine();
            else
                line = "";
        }
        scanner.close();
        return ret;
    }

    private int countBytes(String key) {
        int count = 0;

        Scanner keyParser = new Scanner(key).useRadix(16);

        while (keyParser.hasNextShort()) {
            keyParser.nextShort();
            count++;
        }
        keyParser.close();
        return (count);
    }

    private short[] convertKey(String line){
        int count = countBytes(line);
        short[] keys = new short[count];
        Scanner keyParser = new Scanner(line).useRadix(16);
        for(int i = 0; i < count; i++){
            keys[i] = keyParser.nextShort();
        }
        keyParser.close();
        return keys;
    }
}
