package org.nj.sj.controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.concurrent.*;
public class concurrent_test {
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
        /*
        Box box = new Box();
        Thread producer = new Producer(box);
        Thread consumer1 = new Consumerr(box);

        consumer1.start();
        producer.start();

         */
    }
}


class ZeroEvenOdd {
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


