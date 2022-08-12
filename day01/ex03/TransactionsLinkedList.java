import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList{
    private LinkedList lst = null;

    @Override
    public void addTransaction(Transaction tr) {
        LinkedList newTransactionNode = new LinkedList(tr);

        if (lst != null) {
            lst.setPrev(newTransactionNode);
        }
        newTransactionNode.setPrev(null);
        newTransactionNode.setNext(lst);
        lst = newTransactionNode;
    }

    @Override
    public void deleteTransaction(UUID id) {
        LinkedList lst2 = lst;
        while(lst2 != null){
            if (lst2.getTr().getUUID().equals(id)){
                if(lst2.getPrev() != null)
                    lst2.getPrev().setNext(lst2.getNext());
                lst2.getNext().setPrev(lst2.getPrev());
                return;
            }
            lst2 = lst2.getNext();
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] TransformIntoaArray() {
        LinkedList lst = this.lst;
        int i = 0;
        while (lst != null){
            i++;
            lst = lst.getNext();
        }
        System.out.println("SIZE " + i);
        Transaction[] trArr = new Transaction[i];
        lst = this.lst;
        i = 0;
        while (lst != null){
            trArr[i++] = lst.getTr();
            lst = lst.getNext();
        }
        return trArr;
    }
}

class LinkedList{
    LinkedList next;
    Transaction tr;
    LinkedList prev;


    LinkedList(Transaction transaction) {
        this.tr = transaction;
    }

    public LinkedList getNext() {
        return next;
    }

    public Transaction getTr() {
        return tr;
    }

    public LinkedList getPrev() {
        return prev;
    }

    public void setNext(LinkedList next) {
        this.next = next;
    }

    public void setTr(Transaction tr) {
        this.tr = tr;
    }

    public void setPrev(LinkedList prev) {
        this.prev = prev;
    }
}
