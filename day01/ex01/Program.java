public class Program {
    public static void main(String[] args){
        User Murad = new User("Murad", 500);
        User Gadzhi = new User("Gadzhi", 500);
        User Murad1 = new User("Murad", 500);
        User Gadzhi1 = new User("Gadzhi", 500);
        User Murad2 = new User("Murad", 500);
        User Gadzhi2 = new User("Gadzhi", 500);
        System.out.println(Murad.getID());
        System.out.println(Gadzhi.getID());
        System.out.println(Murad1.getID());
        System.out.println(Gadzhi1.getID());
        System.out.println(Murad2.getID());
        System.out.println(Gadzhi2.getID());
        UserIdsGenerator.getInstance().generateId();
    }
}
