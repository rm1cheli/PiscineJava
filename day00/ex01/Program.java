 import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner system= new Scanner(System.in);
        int i = 0;
        if (system.hasNextInt())
            i = system.nextInt();
        else{
            System.err.println("Illegal Argument");
            system.close();
            System.exit(-1);
        }
        if (i <= 1) {
            System.err.println("Illegal Argument");
            system.close();
            System.exit(-1);
        }
       if(i == 2) {
           System.out.println("true " + 1);
           System.exit(0);
       }
        long _sqrtI = my_sqrt(i);
        int c = 2;
        while(c <= _sqrtI + 1){
            if (i % c == 0){
                c++;
                System.out.println("false " + (c - 2));
                system.close();
                System.exit(0);
            }
            c++;
        }
        System.out.println("true " + (c - 2));
        system.close();
    }

    public static long my_sqrt(int i){
        long result = 0;
        long div = 1;
        while(i > 0){
            i -= div;
            div += 2;
            if (i >= 0)
                result++;
        }
        return result;
    }
}
