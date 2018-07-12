package com.cmy.communication.demo2;

public class Test {
    public static void main(String[] args) throws InterruptedException {
         //创建对应的三把锁

         Object a=new Object();

         Object b=new Object();

         Object c=new Object();

         //创建三条线程

         Thread threadA=new Thread(new RunnableTest("我",c,a));

         Thread threadB=new Thread(new RunnableTest("爱",a,b));

         Thread threadC=new Thread(new RunnableTest("你",b,c));


         //三条线程轮流进入就绪状态

        threadA.start();
        Thread.sleep(1000);
        threadB.start();
        Thread.sleep(1000);
        threadC.start();

    }
}