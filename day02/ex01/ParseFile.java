import java.io.*;

public class ParseFile {
    public String[] ReadFile(String[] files) throws IOException {
        BufferedReader file = null;
        String[] strbuff = new String[2];
        for(int i = 0; i < 2; i++){
            try {
                file = new BufferedReader(new FileReader(files[i]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String res;
            res = file.readLine();
            strbuff[i] = res + " ";
            while((res = file.readLine()) != null){
                strbuff[i] = strbuff[i] + res + " " ;
            }
        }
        file.close();
        return strbuff;
    }
}
