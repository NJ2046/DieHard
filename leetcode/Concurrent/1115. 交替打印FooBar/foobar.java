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
