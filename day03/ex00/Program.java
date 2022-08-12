import java.util.Scanner;

public class Program {
        public static void main(String[] args) throws InterruptedException{
            int count = 0;

            if (args.length == 1 && args[0] != null){
                count = pars_count(args);
            }
            if (count == 0){
                System.out.println("Error Argument");
                return;
            }
            char[] str = args[0].toCharArray();
            System.out.println(args[0]);
            MyThread egg = new MyThread("egg", count);
            MyThread hen = new MyThread("hen", count);
            egg.start();
            hen.start();
            egg.join();
            hen.join();
            for(int i = 0; i < count; i++){
                System.out.println("human");
            }
        }
        public static int pars_count(String[] args){
            Scanner scanner = new Scanner(args[0]).useDelimiter("=");
            if(scanner.next().equals("--count")) {
                if (scanner.hasNextInt())
                    return scanner.nextInt();
            }
            return 0;
        }
}
