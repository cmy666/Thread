package com.cmy.CountDownLatch.demo2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * CyclicBarrier是另一种多线程并发控制使用工具，和CountDownLatch非常类似，
 * 他也可以实现线程间的计数等待，但他的功能要比CountDownLatch更加强大一些。
 *
 * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。
 * 它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，
 * 屏障才会开门，所有被屏障拦截的线程才会继续干活。
 *
 * CountDownLatch的计数器只能使用一次。而CyclicBarrier的计数器可以使用reset() 方法重置。
 * 所以CyclicBarrier能处理更为复杂的业务场景，比如如果计算发生错误，
 * 可以重置计数器，并让线程们重新执行一次
 */

//龙珠案例:七位法师凑齐七颗龙珠召唤神龙

public class CyclicBarrierDemo {
    private static final int THREAD_COUNT=7;

    public static void main(String[] args){

        //第一个屏障点 集齐七位法师
        CyclicBarrier cb=new CyclicBarrier(THREAD_COUNT, new Runnable() {
            @Override
            public void run() {
                System.out.println("7位法师召集完毕，即刻出发寻找7颗龙珠");
                getSummon();//法师凑齐-->收集龙珠-->召唤神龙
            }
        });
        //召集七位法师
        for(int i=0;i<THREAD_COUNT;i++){
            int index=i;
            new Thread(()->{
                System.out.println("召集第"+(index+1)+"位法师");
                try {
                    cb.await(); //等待法师的召集完成
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    //获取龙珠 召唤神龙
    public static void getSummon(){
        //第二个屏障 召唤神龙
        CyclicBarrier cb1=new CyclicBarrier(THREAD_COUNT, new Runnable() {
            @Override
            public void run() {
                System.out.println("龙珠已凑齐 召唤神龙！");
            }
        });
        for(int i=0;i<THREAD_COUNT;i++){
            int index=i;
            new Thread(()->{
                System.out.println("收集第"+(index+1)+"颗龙珠");
                try {
                    cb1.await(); //等待法师的召集完成
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}