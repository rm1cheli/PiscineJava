import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread extends Thread{

    private String word;
    private int count;
    Object object;
    AtomicBoolean atomicBoolean;

    public MyThread(String word, int count, AtomicBoolean atomicBoolean, Object object){
        this.atomicBoolean = atomicBoolean;
        this.object = object;
        this.word = word;
        this.count = count;
    }
    @Override
    public void run() {
        int c = this.count;
        System.out.println(c);
        while (c-- >= 0) {
            System.out.println(c);
            synchronized (object) {
                while (!atomicBoolean.get()) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.word);
                atomicBoolean.set(false);
                object.notify();
            }
        }
    }
}
