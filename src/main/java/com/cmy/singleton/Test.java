package com.cmy.singleton;

public class Test {
    public static void main(String[] args){
        //创建两个线程对象

        /*线程1*/
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
               SingleClass sc1=SingleClass.getInstance();
               System.out.println(sc1);
            }
        });

        /*线程2*/
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
               SingleClass sc2=SingleClass.getInstance();
               System.out.println(sc2);
            }
        });

        /*启动线程*/
        t1.start();

        t2.start();

    }
}