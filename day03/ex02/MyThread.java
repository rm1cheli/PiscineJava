public class MyThread implements Runnable {

    private int i = 0;
    private int[] array;
    private int[] res;
    private int range;
    private int[] pos;

    MyThread(int range, int[] pos, int i, int[] array, int[] res){
        this.range = range;
        this.pos = pos;
        this.i = i;
        this.array = array;
        this.res = res;
    }

    @Override
    public void run() {
            for (int c = 0; c < range; c++) {
                this.res[0] += array[pos[0]++];
            }
            System.out.println("Thread " + (i + 1) + ": from " + (pos[0] - range) + " to " + (pos[0] - 1) +" sum is " + range);
    }
}
