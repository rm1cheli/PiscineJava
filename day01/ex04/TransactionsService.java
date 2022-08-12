import java.util.UUID;

public class TransactionsService {

    UsersList usrList = new UsersArrayList();
    int size = 0;

    public void addUser(User usr){
        usrList.addUser(usr);
        size++;
    }

    public int getBalance(int id){
        User usr = null;
        try{
            usr = usrList.RetId(id);
        } catch (UserNotFoundException e){
            System.out.println(e.toString());
            return 0;
        }
        return usr.getBalance();
    }

    public void executeTransaction(int idS, int idR, int sum){
        User usrR, usrS = null;
        try{
            usrS = usrList.RetId(idS);
            usrR = usrList.RetId(idR);
        } catch (UserNotFoundException e){
            System.out.println(e.toString());
            return;
        }
        if (sum > usrS.getBalance()) {
            throw new IllegalTransactionException();
        }
        Transaction transaction = new Transaction(usrS, usrR, Category.CREDIT, -sum);
        Transaction transaction1 = new Transaction(usrS, usrR, Category.DEBIT, sum);
        transaction1.set_UUID(transaction.getUUID());
        usrS.addTr(transaction);
        usrR.addTr(transaction1);
    }

    public Transaction[] getTrArr(int id){
        User usr = null;
        try{
            usr = usrList.RetId(id);
        } catch (UserNotFoundException e){
            System.out.println(e.toString());
            return null;
        }
        return usr.getLst().TransformIntoaArray();
    }

    public void removeTransaction(UUID idTransaction, int idUser){
        usrList.RetId(idUser).getLst().deleteTransaction(idTransaction);
    }


    public Transaction[] getFailTrArr(){
        TransactionsList notValid = new TransactionsLinkedList();

        for (int i = 0; i < usrList.numUsers(); i++) {
            Transaction[] transactions = this.usrList.RetIndex(i).getLst().TransformIntoaArray();
            for (Transaction t : transactions) {
                boolean valid = false;
                for (Transaction t2 : t.getRecipient().getLst().TransformIntoaArray()) {
                    if (t.getUUID() == t2.getUUID()) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    notValid.addTransaction(t);
                }
            }
        }
        return notValid.TransformIntoaArray();
    }

}
