package com.cmy.ReetranLock;


import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *   ReentranLock具有完全互斥排他的效果 即同一时间只能有一个线程在执行ReentranLock.lock()之后
 *的任务。为了提高效率ReentranLock的升级版ReentranReadWriteLock应运而生。
 *
 *   ReentranReadWriteLock有两个锁:一个是读相关的锁，称为"共享锁"
 *                            一个是写相关的锁，称为"排他锁"。
 *
 *   也就是说多个读锁之间不互斥。 写锁与读锁互斥 写锁与写锁互斥
 *
 *   ReentranReadWriteLock特性: 读读共享 写写互斥 读写互斥 写读互斥
 *
 */

//读读共享的演示:两条线程获取读锁的时间必定非常相近
public class ReentranReadWriteLockDemo1 {


    private  ReentrantReadWriteLock lock=new ReentrantReadWriteLock();

    public static void main(String[] args){
        ReentranReadWriteLockDemo1 demo=new ReentranReadWriteLockDemo1();

        new Thread(()->demo.read(),"Thread1").start();
        new Thread(()->demo.read(),"Thread2").start();
    }

    //获取读锁
    public void read(){
        try {
            lock.readLock().lock();
            System.out.println("获取读锁:"+Thread.currentThread().getName()+"时间:"+System.currentTimeMillis());
            Thread.sleep(5000);//模拟读取操作:5秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }
}