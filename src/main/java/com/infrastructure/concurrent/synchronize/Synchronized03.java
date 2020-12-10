package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 偏向锁---> 轻量级锁
 * 1 hashcode
 * 2 禁用偏向锁：-XX:-UseBiasedLocking
 * 3 偏向锁延迟：默认4s  -XX:BiasedLockingStartupDelay=0
 * 4
 */
public class Synchronized03 {

    public static void main(String[] args) throws InterruptedException {
        EmptyObject emptyObject = new EmptyObject();
        System.out.println(emptyObject.hashCode());
        System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
        //即使没有线程竞争也会升级到轻量级锁
        synchronized (emptyObject){
            System.out.println("============================");
            System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
        }
    }
}
