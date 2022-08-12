import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Object object = new Object();
        int[] count = new int[2];
        int sum = 0;
        if (args.length == 2 && args[0] != null && args[1] != null) {
            count[0] = pars_array(args[0]);
            count[1] = pars_thread(args[1]);
        }
        if (count[0] <= 0 || count[0] > 2000000 || count[1] <= 0 || count[1] > count[0]) {
            System.out.println("Error Argument");
            return;
        }
        final int[] array = new int[count[0]];
        for (int i = 0; i < array.length; i++) {
            array[i] = ((int) ((Math.random() * 2000) - 1000));
            sum += array[i];
        }
        int[] arrayRes = new int[1];
        MyThread[] arrThread = new MyThread[count[1]];
        System.out.println("Sum: " + sum);
        int range = count[0] / count[1];
        int[] pos = new int[1];
        for(int c = 0; c < count[1]; c++){
            if (c == count[1] - 1){
                range = (count[0] % count[1]) + (count[0] / count[1]);
            }
            new MyThread(range, pos, c, array, arrayRes).run();
        }
        System.out.println("Sum by threads: " + sum);
    }
    public static int pars_array(String args){
        Scanner scanner = new Scanner(args).useDelimiter("=");
        if(scanner.next().equals("--arraySize")) {
            if (scanner.hasNextInt())
                return scanner.nextInt();
        }
        return 0;
    }
    public static int pars_thread(String args){
        Scanner scanner = new Scanner(args).useDelimiter("=");
        if(scanner.next().equals("--threadsCount")) {
            if (scanner.hasNextInt())
                return scanner.nextInt();
        }
        return 0;
    }
}
