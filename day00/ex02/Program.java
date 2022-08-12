import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        int sum = 0;
        int coffee = 0;
        while (true){
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                if (i == 42)
                    break;
            }
            else{
                scanner.next();
                System.err.println("Illegal Argument, enter the number again");
            }
            while(i / 10 > 0){
                sum += i % 10;
                i = i / 10;
            }
            sum += i % 10;
            if(simple_int(sum))
                coffee++;
            sum = 0;
            i = 0;
        }
        System.out.println("Count of coffee-request – " + coffee);
        scanner.close();
    }

    public static boolean simple_int(int i) {
        if (i <= 1)
            return false;
        if(i == 2)
            return true;
        long _sqrtI = my_sqrt(i);
        int c = 2;
        while(c <= _sqrtI + 1){
            if (i % c == 0){
                c++;
                return false;
            }
            c++;
        }
        return true;
    }

    public static long my_sqrt(int i) {
        long result = 0;
        long div = 1;
        while (i > 0) {
            i -= div;
            div += 2;
            if (i >= 0)
                result++;
        }
        return result;
    }
}
