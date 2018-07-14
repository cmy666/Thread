package com.cmy.ReetranLock;


//写写互斥:在线程A释放写锁之前 线程B无法获取写锁 时间跨越度较大

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentranReadWriteLockDemo2 {

    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();


    public static void main(String[] args){
        ReentranReadWriteLockDemo2  demo=new ReentranReadWriteLockDemo2();

        new Thread(()->demo.write(),"Thread1").start();

        new Thread(()->demo.write(),"Thread2").start();

    }

    //写的方法
    public void write(){
        try {
            lock.writeLock().lock();
            System.out.println("获取写锁:"+Thread.currentThread().getName()+"时间:"+System.currentTimeMillis());
            Thread.sleep(5000);//模拟写操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }

    }
}