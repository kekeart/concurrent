package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 自旋 升级到重量级锁
 *
 */
public class Synchronized06 {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        EmptyObject emptyObject = new EmptyObject();

        Thread t3 = new Thread(()->{
            LockSupport.park();
            synchronized (emptyObject){
                System.out.println(Thread.currentThread().getName()+"::"+ ClassLayout.parseInstance(emptyObject).toPrintable());
            }
        },"t3");


        Thread t2 = new Thread(()->{
            LockSupport.park();
            synchronized (emptyObject){
                System.out.println(Thread.currentThread().getName()+"::"+ ClassLayout.parseInstance(emptyObject).toPrintable());
                LockSupport.unpark(t3);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");



        Thread t1 = new Thread(()->{
            synchronized (emptyObject){
                System.out.println(Thread.currentThread().getName()+"::"+ ClassLayout.parseInstance(emptyObject).toPrintable());
            }
            LockSupport.unpark(t2);

        },"t1");
        t1.start();
        t2.start();
        t3.start();
    }
}
