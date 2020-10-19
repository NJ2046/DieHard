//两个解法，但本质不变

//第一种是使用java的synchronized来实现
//第二种是使用Semaphore来实现

public class ZeroEvenOdd {
    private int n;
    int index = 0;
    int num = 1;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for(int i=0;i<n;i++){
            synchronized(this){
                System.out.println("print 0");
                while(index % 2 == 1) this.wait();
                printNumber.accept(0);
                index++;
                this.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for(int i=2;i<=n;i+=2){
            synchronized(this){
                while(index % 2 == 0 || num % 2 == 1) this.wait();
                printNumber.accept(num);
                index++;
                num++;
                this.notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i+=2){
            synchronized(this){
                while(index % 2 == 0 || num % 2 == 0) this.wait();
                printNumber.accept(num);
                index++;
                num++;
                this.notifyAll();
            }
        }
    }

    public static void main(String[] args) {

        ZeroEvenOdd0 n = new ZeroEvenOdd0(6);
        IntConsumer c1 = x -> System.out.print(x);

        new Thread(() -> {
            try {
                n.odd(c1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                n.even(c1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                n.zero(c1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}





class ZeroEvenOdd0 {
    private int n;
    private Semaphore s,s1,s2;


    public ZeroEvenOdd0(int n) {
        this.n = n;
        s = new Semaphore(1);
        s1 = new Semaphore(0);
        s2 = new Semaphore(0);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i++)
        {
            s.acquire();
            printNumber.accept(0);
            if((i&1) == 0)
                s1.release();
            else
                s2.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for(int i=2;i<=n;i+=2)
        {
            s1.acquire();
            printNumber.accept(i);
            s.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i+=2)
        {
            s2.acquire();
            printNumber.accept(i);
            s.release();
        }
    }
    private static void startThread(Runnable task) {
        new Thread(task).start();
    }
    public static void main(String[] args) {

        ZeroEvenOdd0 n = new ZeroEvenOdd0(6);
        IntConsumer c1 = x -> System.out.print(x);

        startThread(()->{
            try{
                n.odd(c1);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        startThread(()->{
            try{
                n.even(c1);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        startThread(()->{
            try{
                n.zero(c1);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}


