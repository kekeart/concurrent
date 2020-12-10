package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 默认开启偏向锁
 * 禁用偏向锁:-XX:-UseBiasedLocking
 * 偏向锁 延迟 4s
 *-XX:BiasedLockingStartupDelay=0
 *
 * 无锁
 *
 * 无锁没有对资源进行锁定，所有的线程都能访问并修改同一个资源，但同时只有一个线程能修改成功。
 *
 * 偏向锁
 *
 * 偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁，降低获取锁的代价。
 * 在大多数情况下，锁总是由同一线程多次获得，不存在多线程竞争，所以出现了偏向锁。其目标就是在只有一个线程执行同步代码块时能够提高性能。
 * 当一个线程访问同步代码块并获取锁时，会在Mark Word里存储锁偏向的线程ID。在线程进入和退出同步块时不再通过CAS操作来加锁和解锁，
 * 而是检测Mark Word里是否存储着指向当前线程的偏向锁。引入偏向锁是为了在无多线程竞争的情况下尽量减少不必要的轻量级锁执行路径，
 * 因为轻量级锁的获取及释放依赖多次CAS原子指令，而偏向锁只需要在置换ThreadID的时候依赖一次CAS原子指令即可。
 * 偏向锁只有遇到其他线程尝试竞争偏向锁时，持有偏向锁的线程才会释放锁，线程不会主动释放偏向锁。
 * 偏向锁的撤销，需要等待全局安全点（在这个时间点上没有字节码正在执行），它会首先暂停拥有偏向锁的线程，判断锁对象是否处于被锁定状态。
 * 撤销偏向锁后恢复到无锁（标志位为“01”）或轻量级锁（标志位为“00”）的状态。
 * 偏向锁在JDK 6及以后的JVM里是默认启用的。可以通过JVM参数关闭偏向锁：-XX:-UseBiasedLocking，关闭之后程序默认会进入轻量级锁状态。
 *
 */
public class Synchronized02 {

    static EmptyObject emptyObject = null;

    public static void main(String[] args) throws InterruptedException {
        Synchronized02 synchronized02 = new Synchronized02();
        emptyObject = new EmptyObject();
        System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());

        Thread t1 = new Thread(()->{
            synchronized02.doSomething1();
            synchronized02.doSomething2();
        },"t1");

        t1.start();
        t1.join();
        System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
    }

    public void doSomething1(){
        synchronized (emptyObject){
            System.out.println("======================doSomething1============================");
            System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
        }
    }


    public void doSomething2(){
        synchronized (emptyObject){
            System.out.println("=====================doSomething2=============================");
            System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
        }
    }
}
