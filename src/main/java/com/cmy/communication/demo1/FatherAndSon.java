package com.cmy.communication.demo1;


/**
 *   我们知道线程是操作系统中独立的个体，但是这个单独的个体之间没有一种特殊的处理方式使之成为一个整体，
 * 线程之间没有任何交流和沟通的话，他就是一个个单独的个体，不足以形成一个强大的交互性较强的整体。
 *   为了提高CPU的利用率和各线程之间相互协作，Java的一种实现线程间通信的机制是：wait/notify线程间通信。
 *
 * 案例:创建子父线程
 * 保证顺序如下:子线程执行 3次 父线程执行 5次 如此循环 5次
 */

public class FatherAndSon {

    boolean flag=true;//控制哪条线程先执行
    /**
     *  this:谁调用方法谁就是this对象
     *  synchronized: 方法=====> synchronize(this){}:同步方法即Lock对象为this
     *                代码块 synchronized(Object){}
     */
    //父线程要执行的函数:同步方法
    public synchronized void FatherMade(){
        /**
         * 此处判断的作用是:按照题目要求 子线程先执行 (但线程的执行几率是随机的
         *   所以此处的flag的默认值为true 如果父线程先执行 则使父线程进入阻塞状态
         *   并释放对this锁的占用 将执行权转交给子线程！
         * )
         */
        if(flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //父线程执行5次
        for(int i=0;i<5;i++){
            System.out.println("父线程第"+(i+1)+"次执行！");
        }

        flag=true;//设置flag的值为true的目的:因为在父线程执行结束后仍旧有一定几率继续执行
                  //所以:需要满足if条件阻塞父线程！

        this.notify(); //唤醒正在等待this锁的线程:子线程

    }

    //子线程要执行的函数:同步方法
    public synchronized  void SonMade(){
        if(!flag){ //同理 在子线程执行结束之后 仍旧有几率子线程仍旧执行
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i=0;i<3;i++){
            System.out.println("子线程第"+(i+1)+"次执行");
        }

        flag=false;

        this.notify();
    }


}