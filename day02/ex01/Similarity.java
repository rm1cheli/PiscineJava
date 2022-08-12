import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Similarity {
    public  void сomparison(String[] str) throws IOException{
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("Dictionary.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(writer == null)
            return;
        ArrayList<String> dict = creationLibrary(str);
        writer.write(listToArray(dict));
        writer.close();
        String[] file1 = null;
        int[][] countWords = new int[2][dict.size()];
        for(int i = 0; i < 2; i++){
            file1 = str[i].split(" ");
            for(int c = 0; c < file1.length; c++){
                for(int t = 0; t < dict.size(); t++){
                    if (dict.get(t).equals(file1[c]))
                        countWords[i][t]++;
                }
            }
        }
        writer.close();
        similarityRes(countWords);
    }

    private void similarityRes(int[][] countWords){
        double numerator = 0;
        double Denominator = 0;
        for (int i = 0; i < countWords[0].length; i++){
            numerator += countWords[0][i] * countWords[1][i];
        }
        double Denominator1 = 0;
        for (int i = 0; i < countWords[0].length; i++){
            Denominator += countWords[0][i] * countWords[0][i];
        }
        for (int i = 0; i < countWords[1].length; i++){
            Denominator1 += countWords[1][i] * countWords[1][i];
        }
        Denominator = Math.sqrt(Denominator) * Math.sqrt(Denominator1);
        Denominator = Denominator * 100;
        Denominator = Math.floor(Denominator);
        Denominator = Denominator / 100;
        System.out.println(numerator);
        System.out.println(Denominator);
        double res = numerator / Denominator;
        BigDecimal result = new BigDecimal( res );
        result = result.setScale(2, RoundingMode.DOWN);
        System.out.println("Similarity = " + result);
    }

    public double mySqrt3(double x) {
        double flag = 0.1d;
        if (x <= 0) {
            return 0;
        }
        double val = x;
        double last;
        do {
            last = val;
            val = (val + x / val) / 2;
        } while (val - last > flag || val - last < -flag);
        return val;
    }

    private ArrayList<String> creationLibrary(String[] str){
        ArrayList<String> dicteonary = new ArrayList<>();
        String dictStr = str[0] + str[1];
        String[] dictStrArr = dictStr.split(" ");
        boolean t = false;
        for (int i = 0; i < dictStrArr.length; i++){
            t = false;
            for(int c = 0; c < dicteonary.size(); c++){
                if(dicteonary.get(c).equals(dictStrArr[i]))
                    t = true;
            }
            if (t == false)
                dicteonary.add(dictStrArr[i]);
        }
        return dicteonary;
    }

    private String listToArray(ArrayList<String> dict){
        String result;
        result = dict.get(0) + "\n";
        for(int i = 1; i < dict.size(); i++){
            result = result + dict.get(i) + "\n";
        }
        return result;
    }
}
