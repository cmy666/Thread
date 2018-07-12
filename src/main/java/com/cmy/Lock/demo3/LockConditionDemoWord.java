package com.cmy.Lock.demo3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  lock实现案例:线程A 线程B 线程C 打印 10次 '我爱你'
 *
 *  案例思路:使用一个ReentranLock 对象
 *          2个Condition对象 和synchronize(){}+wait()+notify()大致相仿
 */
public class LockConditionDemoWord implements Runnable{
    // 打印次数
    private static final int PRINT_COUNT = 10;
    // 打印锁
    private final ReentrantLock reentrantLock;
    // 本线程打印所需的condition
    private final Condition thisCondtion;
    // 下一个线程打印所需要的condition
    private final Condition nextCondtion;
    // 打印字符
    private final String printChar;

    public LockConditionDemoWord(ReentrantLock reentrantLock, Condition thisCondtion, Condition nextCondition,
                         String printChar) {
        this.reentrantLock = reentrantLock;
        this.nextCondtion = nextCondition;
        this.thisCondtion = thisCondtion;
        this.printChar = printChar;
    }

    @Override
    public void run() {
        // 获取打印锁 进入临界区
        reentrantLock.lock();
        try {
            // 连续打印PRINT_COUNT次
            for (int i = 0; i < PRINT_COUNT; i++) {
                //打印字符
                System.out.print(printChar);
                // 使用nextCondition唤醒下一个线程
                // 因为只有一个线程在等待，所以signal或者signalAll都可以
                nextCondtion.signal();
                // 不是最后一次则通过thisCondtion等待被唤醒
                // 必须要加判断，不然虽然能够打印10次，但10次后就会直接死锁
                if (i < PRINT_COUNT - 1) {
                    try {
                        // 本线程让出锁并等待唤醒
                        thisCondtion.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        } finally {
            // 释放打印锁
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 写锁
        ReentrantLock lock = new ReentrantLock();
        // 打印a线程的condition
        Condition conditionA = lock.newCondition();
        // 打印b线程的condition
        Condition conditionB = lock.newCondition();
        // 打印c线程的condition
        Condition conditionC = lock.newCondition();
        // 实例化A线程
        Thread printerA = new Thread(new LockConditionDemoWord(lock, conditionA, conditionB, "我"));
        // 实例化B线程
        Thread printerB = new Thread(new LockConditionDemoWord(lock, conditionB, conditionC, "爱"));
        // 实例化C线程
        Thread printerC = new Thread(new LockConditionDemoWord(lock, conditionC, conditionA, "你"));
        // 依次开始A B C线程
        printerA.start();
        Thread.sleep(100);
        printerB.start();
        Thread.sleep(100);
        printerC.start();
    }
}