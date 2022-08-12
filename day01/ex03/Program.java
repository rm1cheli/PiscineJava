import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        int max_usrs = (int)(Math.random() * 10 + 15);
        UUID[] tt = new UUID[max_usrs];
        System.out.println("Generating " + max_usrs + " users.");
        TransactionsLinkedList transactions = new TransactionsLinkedList();
        User sender = new User("Pupa", 123);
        User reciever = new User("Lupa", 0);

        for (int i = 0; i < max_usrs; i++) {
            Transaction tmp;
            if (i % 2 == 0) {
                tmp = new Transaction(sender, reciever, Category.DEBIT, 123);
            } else {
                tmp = new Transaction(reciever, sender, Category.CREDIT, -123);
            }
            tt[i] = tmp.getUUID();
            transactions.addTransaction(tmp);
        }

        Transaction[] t = transactions.TransformIntoaArray();
        int i = 0;
        while (i < max_usrs) {
            System.out.println(t[i++].getUUID());
        }
        System.out.println("DELETED");
        System.out.println(tt[5]);
        try {
            transactions.deleteTransaction(tt[5]);
        } catch (TransactionNotFoundException e){
            System.out.println(e.toString());
        }
        System.out.println("DELETED");
        t = transactions.TransformIntoaArray();
        i = 0;
        while (i < max_usrs - 1) {
            System.out.println(t[i++].getUUID());
        }
    }
}
