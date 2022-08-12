public class Program {

    public static void main(String[] args) {
        User user1 = new User(1, "John", 10000);
        User user2 = new User(2, "John", 10000);
        User user3 = new User(3, "Mike", 10000);

        System.out.printf("User1 id - %d, name - %s, balance - %d\n",
                user1.getID(), user1.getName(), user1.getBalance());
        System.out.printf("User2 id - %d, name - %s, balance - %d\n",
                user2.getID(), user2.getName(), user2.getBalance());
        System.out.printf("User3 id - %d, name - %s, balance - %d\n",
                user3.getID(), user3.getName(), user3.getBalance());

        Transaction transaction1 = new Transaction(user1, user2,
                Category.CREDIT, 100);
        System.out.println(transaction1.getUUID());
        System.out.println(transaction1.getRecipient().getID());
        System.out.println(transaction1.getSender().getID());
        System.out.println(transaction1.getCategory());
        System.out.println(transaction1.getSum());

        Transaction transaction2 = new Transaction(user1, user2,
                Category.CREDIT, -100);
        System.out.println(transaction2.getUUID());
        System.out.println(transaction2.getRecipient().getID());
        System.out.println(transaction2.getSender().getID());
        System.out.println(transaction2.getCategory());
        System.out.println(transaction2.getSum());

        Transaction transaction3 = new Transaction(user1, user2,
                Category.DEBIT, 100);
        System.out.println(transaction3.getUUID());
        System.out.println(transaction3.getRecipient().getID());
        System.out.println(transaction3.getSender().getID());
        System.out.println(transaction3.getCategory());
        System.out.println(transaction3.getSum());

        Transaction transaction4 = new Transaction(user1, user2,
                Category.DEBIT, -100);
        System.out.println(transaction4.getUUID());
        System.out.println(transaction4.getRecipient().getID());
        System.out.println(transaction4.getSender().getID());
        System.out.println(transaction4.getCategory());
        System.out.println(transaction4.getSum());
    }
}
