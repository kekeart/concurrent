package com.infrastructure.concurrent.synchronize;

import java.util.concurrent.TimeUnit;

public class Synchronized01 {

    EmptyObject emptyObject = new EmptyObject();

    public static void main(String[] args) {
        Synchronized01 synchronized01 = new Synchronized01();

        Thread t1 = new Thread(()->{
            synchronized01.doSomething();
        },"t1");

        Thread t2 = new Thread(()->{
            synchronized01.doSomething();
        },"t2");

        t1.start();
        t2.start();
    }

    public void doSomething(){
        synchronized (emptyObject){
            try {
                TimeUnit.SECONDS.sleep(2L);
                System.out.println(Thread.currentThread().getName()+" is complete");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

