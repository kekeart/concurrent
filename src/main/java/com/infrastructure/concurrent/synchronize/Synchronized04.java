package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 线程交替执行
 * cas算法
 *
 */
public class Synchronized04 {
    static EmptyObject emptyObject = new EmptyObject();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            doSomething();
        },"t1");
        Thread t2 = new Thread(()->{
            doSomething();
        },"t2");
        t1.start();
        t1.join();
        TimeUnit.MILLISECONDS.sleep(10);
        t2.start();
        t2.join();
        System.out.println("==================================================");
        System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());

    }

    private static void doSomething(){
        synchronized (emptyObject){
            System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
            System.out.println(Thread.currentThread().getName()+"  is end");
        }
    }

}
