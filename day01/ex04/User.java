import java.util.UUID;

public class User {
    private int ID;
    private String Name;
    private int Balance;
    private TransactionsList lst = new TransactionsLinkedList();

    public User(String name, int balance) {
        this.ID = UserIdsGenerator.getInstance().generateId();
        Name = name;
        Balance = balance;
    }

    public void addTr(Transaction tr){
        lst.addTransaction(tr);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
    Balance = balance;
}

    public TransactionsList getLst() {
        return lst;
    }

    public void setLst(TransactionsList lst) {
        this.lst = lst;
    }
}
