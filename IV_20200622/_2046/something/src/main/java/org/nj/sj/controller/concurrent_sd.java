package org.nj.sj.controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.concurrent.*;
public class concurrent_sd {
}


class ForkJionCalculate extends RecursiveTask<Long> implements Serializable {
    private static final long serialVersionUID = 2462375556031755900L;


    private long start;

    private long end;

    private static final long THRESHOLD = 10000L;//临界值

    public ForkJionCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end -start;
        if(length<=THRESHOLD){
            long sum =0;
            for (long i = start; i <=end ; i++) {
                sum+=i;
            }
            return sum;
        }else {
            long middle =(start+end)/2;
            ForkJionCalculate left = new ForkJionCalculate(start, middle);
            left.fork();//拆分，并将该子任务压入线程队列
            ForkJionCalculate right = new ForkJionCalculate(middle + 1, end);
            right.fork();
            return left.join()+right.join();
        }
    }


    public static void main(String[] args) {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJionCalculate task = new ForkJionCalculate(0, 10000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间"+ Duration.between(start,end).toMillis());


/*
        start = Instant.now();
        sum = 0L;
        for (long i = 0L; i < 10000000000L; i++) {
            sum+=i;
        }
        System.out.println(sum);
        end = Instant.now();
        System.out.println("耗费时间"+Duration.between(start,end).toMillis());



        start = Instant.now();
        sum = LongStream.rangeClosed(0, 10000000000L).parallel().sum();
        System.out.println(sum);
        end = Instant.now();
        System.out.println("耗费时间"+Duration.between(start,end).toMillis());


        start = Instant.now();
        Long reduce = Stream.iterate(1L, x -> x + 1).limit(10000000000L).parallel().reduce(0L, Long::sum);
        System.out.println(reduce);
        end = Instant.now();
        System.out.println("耗费时间"+Duration.between(start,end).toMillis());
*/
        Semaphore s = new Semaphore(1);
    }

}


class Box {
    public int boxValue=0;
}

class Producer extends Thread{
    private Box box;
    public Producer(Box box){
        this.box=box;
    }
    public void run(){
        for(int i=1;i<=10;i++){
            synchronized(box){
                while(box.boxValue!=0){
                    System.out.println("生产者：满了");
                    try {
                        box.wait();//线程休眠，等待唤醒，并释放box对象锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                box.boxValue=i;
                System.out.println("生产者放入了数字："+i);
                box.notify();//随机唤醒一个正在等待box的线程
            }
        }
    }
}


class Consumerr extends Thread{
    private Box box;
    public Consumerr(Box box){
        this.box=box;
    }
    public void run(){
        for(int i=1;i<=5;i++){
            synchronized(box){
                while(box.boxValue==0){
                    System.out.println("消费者：空了");
                    try {
                        box.wait();//线程休眠，等待唤醒，并释放box对象锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("消费者取出了数字："+box.boxValue);
                box.boxValue=0;
                box.notify();//随机唤醒一个正在等待box的线程
            }
        }
    }
}


class FooBar {
    private int n;
    private int f = 1;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (f == 0) wait();
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                f = 0;
                notify();
            }

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (this){
            while (f == 1) wait();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            f = 1;
            notify();
            }
        }

    }


    public static void main(String[] args) {
        FooBar fb = new FooBar(2);
        new Thread(() -> {
            try {
                fb.foo(() -> System.out.print("foo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fb.bar(() -> System.out.print("bar"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();



    }
}


class FooBar1 {
    private int n;
    private volatile boolean flag;
    public FooBar1(int n) {
        this.n = n;
    }
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(flag){
                Thread.yield();
            }
            printFoo.run();
            flag = true;
        }
    }
    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(!flag){
                Thread.yield();
            }
            printBar.run();
            flag = false;
        }
    }

    public static void main(String[] args) {
        FooBar fb = new FooBar(2);

        new Thread(() -> {
            try {
                fb.foo(() -> System.out.print("foo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fb.bar(() -> System.out.print("bar"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}


class FooBar2 {
    private int n;
    private volatile boolean flag;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public FooBar2(int n) {
        this.n = n;
    }
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (flag) {
                    condition.await();
                }
                printFoo.run();
                flag = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (!flag) {
                    condition.await();
                }
                printBar.run();
                flag = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}


class H2O {
    String f = new String();
    private Semaphore o,h;
    public H2O(String f) {
        this.f = f;
        o = new Semaphore(1);
        h = new Semaphore(2);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        for(int i = 0; i < f.toCharArray().length / 2; i ++)
        {
            if(h.availablePermits() > 0){
                h.acquire();
                // releaseHydrogen.run() outputs "H". Do not change or remove this line.
                releaseHydrogen.run();
                if (o.availablePermits() == 0){
                    o.release();
                }
            }
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        for(int i = 0; i < f.toCharArray().length / 2; i ++)
        {
        if(o.availablePermits() > 0){
            o.acquire();
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseOxygen.run();
            if (h.availablePermits() == 0){
                h.release();
            }
        }
        }
    }


    public static void main(String[] args) {
        H2O h2 = new H2O("OOHHHH");
        new Thread(() -> {
            try {
                h2.hydrogen(() -> System.out.print("H"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                h2.oxygen(() -> System.out.print("O"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}




class H2O1 {
    Semaphore hSemaphore = new Semaphore(2);
    Semaphore oSemaphore = new Semaphore(1);
    CyclicBarrier barrier = new CyclicBarrier(3);
    public H2O1() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        hSemaphore.acquire();
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        releaseHydrogen.run();

        hSemaphore.release();

    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {

        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        oSemaphore.acquire();
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        releaseOxygen.run();

        oSemaphore.release();
    }


}




class SynTest01 implements Runnable{
    static int a=0;
    public static void main(String[] args)
            throws InterruptedException {
        SynTest01 syn= new SynTest01();
        Thread thread1 = new Thread(syn);
        Thread thread2 = new Thread(syn);
        thread1.start();thread1.join();
        thread2.start();thread2.join();
        System.out.println(a);
    }
    @Override
    public void run() {
            for (int i = 0; i < 1000; i++) {
                a++;
            }
    }
}


class SynTest02 implements Runnable {
    Object object = new Object();
    public static void main(String[] args) throws InterruptedException {
        SynTest02 syn = new SynTest02();
        Thread thread1 = new Thread(syn);
        Thread thread2 = new Thread(syn);
        thread1.start();
        thread2.start();
        //线程1和线程2只要有一个还存活就一直执行
        while (thread1.isAlive() || thread2.isAlive()) {}
        System.out.println("main程序运行结束");
    }
    @Override
    public void run() {
        synchronized (object) {
            try {
                System.out.println(Thread.currentThread().getName()
                        + "线程执行了run方法");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()
                        + "执行2秒钟之后完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



class SynTest6 implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        SynTest6 instance1 = new SynTest6();
        SynTest6 instance2 = new SynTest6();
        Thread thread1 = new Thread(instance1);
        Thread thread2 = new Thread(instance2);
        thread1.start();
        thread2.start();
    }
    @Override
    public void run() {
        method1();
    }
    public synchronized static void method1() {
        try {
            System.out.println(Thread.currentThread().getName() + "进入到了静态方法");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "离开静态方法，并释放锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class DeadThreadDemo implements Runnable{
    public String username;
    public Object lock1 = new Object();
    public Object lock2 = new Object();
    public void setFlag(String username) {
        this.username = username;
    }
    @Override
    public void run(){
        if(username.equals("a")) {
            synchronized (lock1) {
                try {
                    System.out.println("username = " + username);
                    Thread.sleep(3000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("按 lock1->lock2代码 顺序执行了");
                }
            }
        }
        if(username.equals("b")) {
            synchronized (lock2) {
                try {
                    System.out.println("username = " + username);
                    Thread.sleep(3000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("按lock2->lock1代码顺序执行了");
                }
            }
        }
    }
}

class lock {
    public static void main(String[] args) {
        try {
            DeadThreadDemo dtd1 = new DeadThreadDemo();
            dtd1.setFlag("a");
            Thread thread1 = new Thread(dtd1);
            thread1.start();
            Thread.sleep(100);
            dtd1.setFlag("b");
            Thread thread2 = new Thread(dtd1);
            thread2.start();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
