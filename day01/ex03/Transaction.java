import java.util.UUID;

enum Category{CREDIT, DEBIT};
public class Transaction {
    private UUID _UUID;
    private User Recipient;
    private User Sender;
    private Category category;
    private int sum;

    public Transaction(User recipient, User sender, Category categ, int sum) {
        this._UUID = UUID.randomUUID();
        Recipient = recipient;
        Sender = sender;
        this.category = categ;
        if((categ == Category.CREDIT && sum > 0) || (categ == Category.DEBIT && sum < 0)) {
            System.out.println("transaction error");
            sum = 0;
        }
        else if (recipient.getBalance() < 0 || sender.getBalance() < 0){
            System.out.println("transaction error");
            sum = 0;
        }
        this.sum = sum;
        if (category == Category.CREDIT) {
            sender.setBalance(sender.getBalance() + sum);
        }
        else {
            recipient.setBalance(recipient.getBalance() + sum);
        }
    }

    public UUID getUUID() {
        return _UUID;
    }

    public User getRecipient() {
        return Recipient;
    }

    public User getSender() {
        return Sender;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getSum() {
        return sum;
    }
}
