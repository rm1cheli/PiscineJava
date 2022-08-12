import java.util.Scanner;



public class Program {

    private static final int MAX_CHAR_CODES = 65_535;
    private static final int GRADES_COUNT = 5;
    private static final int WEEK_COUNT = 18;
    private static final int MIN_GRADE = 1;
    private static final int MAX_GRADE = 9;

    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        int number = 1;
        long grades = 0;
        long sum = 10;
        String str;
        while(number <= WEEK_COUNT){
            str = scanner.nextLine();
            if (str.equals("42"))
                break;
            if (!str.equals("Week " + number)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            for(int i = 0; i < GRADES_COUNT; i++){
                int y = 0;
                if(scanner.hasNextInt()) {
                    y = scanner.nextInt();
                    if (y > MAX_GRADE || y < MIN_GRADE){
                        System.err.println("IllegalArgument");
                        System.exit(-1);
                    }
                    if (y < sum)
                        sum = y;
                }
                else {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
            }
            scanner.nextLine();
            for(int i = 1; i < number; i++)
                sum = sum * 10;
            grades += sum;
            sum = 10;
            number++;
        }
        printWeek(grades);
        scanner.close();
    }

    public static void printWeek(long grades){
        int week = 1;
        while (grades != 0){
            long i = grades % 10;
            System.out.print("Week " + week + " ");
            for(int c = 0; c < i; c++){
                System.out.print("=");
            }
            System.out.println(">");
            grades = grades / 10;
            week++;
        }
    }
}
