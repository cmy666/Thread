package com.cmy.Lock.demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionDemo {

    private Lock lock=new ReentrantLock();

    /**
     * Condition按字面意思理解就是条件，
     * 当然，我们也可以将其认为是条件进行使用，这样的话我们可以通过上述的代码创建多个Condition条件，
     * 我们就可以根据不同的条件来控制现成的等待和通知。
     * 而我们还知道，在使用关键字synchronized与wait()方法和notify()方式结合实现线程间通信的时候，
     * notify/notifyAll的通知等待的线程时是随机的，显然使用Condition相对灵活很多，可以实现”选择性通知”
     *
     *
     *
     * (1)Object的wait()方法相当于Condition类中的await()方法；
     * (2)Object的notify()方法相当于Condition类中的signal()方法；
     * (3)Object的notifyAll()方法相当于Condition类中的signalAll()方法；
     */
    private Condition cd=lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
       LockConditionDemo lcd=new LockConditionDemo();
       new Thread(()->lcd.await(),"ThreadA").start();
       Thread.sleep(3000); //线程休眠:3秒
       new Thread(()->lcd.signal(),"ThreadB").start();

    }

    private void await() {
       try{
           lock.lock();
           System.out.println("开始等待await！ThreadName:"+Thread.currentThread().getName());
           cd.await();//开始等待 释放对lock锁的占有 使线程b能够顺利执行
           System.out.println("等待结束！ThreadName:"+Thread.currentThread().getName());
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           lock.unlock();
       }
    }

    private void signal(){
        lock.lock();
        System.out.println("发送通知signal！ThreadName:"+Thread.currentThread().getName());
        cd.signal();//改变Condition条件 停止线程a的阻塞状态 使其恢复运行！
        lock.unlock();
    }

}