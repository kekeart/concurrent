package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 线程交替执行
 * cas算法
 *
 * * 轻量级锁
 *  *
 *  * 是指当锁是偏向锁的时候，被另外的线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，从而提高性能。
 *  * 在代码进入同步块的时候，如果同步对象锁状态为无锁状态（锁标志位为“01”状态，是否为偏向锁为“0”），
 *  * 虚拟机首先将在当前线程的栈帧中建立一个名为锁记录（Lock Record）的空间，用于存储锁对象目前的Mark Word的拷贝，然后拷贝对象头中的Mark Word复制到锁记录中。
 *  * 拷贝成功后，虚拟机将使用CAS操作尝试将对象的Mark Word更新为指向Lock Record的指针，并将Lock Record里的owner指针指向对象的Mark Word。
 *  * 如果这个更新动作成功了，那么这个线程就拥有了该对象的锁，并且对象Mark Word的锁标志位设置为“00”，表示此对象处于轻量级锁定状态。
 *  * 如果轻量级锁的更新操作失败了，虚拟机首先会检查对象的Mark Word是否指向当前线程的栈帧，如果是就说明当前线程已经拥有了这个对象的锁，
 *  * 那就可以直接进入同步块继续执行，否则说明多个线程竞争锁。
 *  * 若当前只有一个等待线程，则该线程通过自旋进行等待。但是当自旋超过一定的次数，或者一个线程在持有锁，
 *  * 一个在自旋，又有第三个来访时，轻量级锁升级为重量级锁。
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
