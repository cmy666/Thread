package com.cmy.ReetranLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写互斥/写读互斥 时间较久 说明互斥
public class ReentranReadWriteLockDemo3 {

    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();


    public static void main(String[] args){
       ReentranReadWriteLockDemo3 demo=new ReentranReadWriteLockDemo3();

       new Thread(()->demo.read(),"Thread1").start();

       new Thread(()->demo.write(),"Thread2").start();
    }

    //读操作
    public void read(){
        try {
            lock.readLock().lock();
            System.out.println("线程:"+Thread.currentThread().getName()+"获取读锁 时间:"+System.currentTimeMillis());
            Thread.sleep(5000);//读操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }

    }

    //写操作
    public void write(){
        try {
            lock.writeLock().lock();
            System.out.println("线程:"+Thread.currentThread().getName()+"获取写锁 时间:"+System.currentTimeMillis());
            Thread.sleep(5000);//写操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}