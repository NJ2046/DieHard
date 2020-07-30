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



class ZeroEvenOdd {
    private int n;
    private Semaphore s,s1,s2;


    public ZeroEvenOdd(int n) {
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
        /*
        ZeroEvenOdd n = new ZeroEvenOdd(6);
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

         */




        Box box = new Box();
        Thread producer = new Producer(box);
        Thread consumer1 = new Consumerr(box);

        consumer1.start();
        producer.start();
    }
}



