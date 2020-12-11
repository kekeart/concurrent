package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 1 线程竞争引发偏向锁升级到轻量级锁
 * 2 偏向延迟导致直接使用轻量级锁
 * 3 禁用偏向锁
 * 4 hashcode
 *
 *
 *
 */
public class Synchronized05 {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        EmptyObject emptyObject = new EmptyObject();

        Thread t2 = new Thread(()->{
            LockSupport.park();
            synchronized (emptyObject){
                System.out.println(Thread.currentThread().getName()+"::"+ClassLayout.parseInstance(emptyObject).toPrintable());
            }
        },"t2");
        Thread t1 = new Thread(()->{
            synchronized (emptyObject){
                try {
                    System.out.println(Thread.currentThread().getName()+"::"+ ClassLayout.parseInstance(emptyObject).toPrintable());
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.unpark(t2);
            }
            System.out.println("-------------------");
        },"t1");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(Thread.currentThread().getName()+"::"+ClassLayout.parseInstance(emptyObject).toPrintable());
    }
}
