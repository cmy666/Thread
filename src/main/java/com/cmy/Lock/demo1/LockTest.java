package com.cmy.Lock.demo1;

//Lock对象也可以 实现线程间的通信和同步

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock其实是一个接口，在JDK1.5以后开始提供，其实现类常用的有ReentrantLock
 *
 * 这里所说的Lock对象即是只Lock接口的实现类，为了方便记忆或理解，都简称为Lock对象
 */
public class LockTest {
    public static void main(String[] args){
         Lock lock=new ReentrantLock();

         new Thread(()->
            runMethod(lock),"ThreadA").start();

        new Thread(()->
                runMethod(lock),"ThreadB").start();

        new Thread(()->
                runMethod(lock),"ThreadC").start();

        new Thread(()->
                runMethod(lock),"ThreadD").start();

    }

    //线程要执行的函数
    private static void runMethod(Lock lock){
        lock.lock(); //上锁的方法
        for (int i=0;i<5;i++){
            System.out.println("ThreadName:"+Thread.currentThread().getName()+"i="+(i+1));
        }
        System.out.println();
        lock.unlock();
    }
}