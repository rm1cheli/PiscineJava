public class User {
    private int ID;
    private String Name;
    private int Balance;
    public User(String name, int balance) {
        this.ID = UserIdsGenerator.getInstance().generateId();
        Name = name;
        Balance = balance;
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
}
