package com.infrastructure.concurrent.synchronize;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

/**
 * 1 对象头的内容
 * 2 锁的膨胀过程
 * 3 synchronized 使用注意事项
 *
 */
public class Sychronized01 {

    public static void main(String[] args) {
        MyLock myLock = new MyLock();
        System.out.println(ByteOrder.nativeOrder());
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(myLock).toPrintable());
    }
}
