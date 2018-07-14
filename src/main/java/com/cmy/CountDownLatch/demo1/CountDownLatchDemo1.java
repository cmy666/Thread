package com.cmy.CountDownLatch.demo1;

import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch是一个非常实用的多线程工具控制类，称之为‘倒计时器’
 *
 *  它允许一个或多个线程一直等待，知道其他线程操作完再执行
 */
/*
 * 案例:创建七条线程 每一条线程分配的任务:寻找龙珠 当集齐七个龙珠时召唤神龙 当每条线程获取龙珠后返回告诉我
 *      剩余的龙珠数量。
 */

public class CountDownLatchDemo1 {

    private static final int THREAD_COUNT=7; //线程数量

    private static CountDownLatch count=new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
         for (int i=0;i<THREAD_COUNT;i++){
             int index=i;
             new Thread(()->{
                 try {
                     System.out.println("第"+(index+1)+"颗龙珠已被找到");
                     Thread.sleep(1000);//模拟寻找龙珠的时间
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 count.countDown();//计数递减
             }).start();
         }
         //等待检查 当上述七条线程的操作执行结束之后 执行await后的代码
         count.await();
         System.out.println("召唤神龙");
    }
}