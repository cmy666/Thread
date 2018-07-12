package com.cmy.ThreadLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * ThreadLocal: *对象与对象之间的变量共享(使用static关键字修饰成员变量)
 *
 *              *每一个线程都拥有自己的共享变量:ThreadLocal
 *
 * 类ThreadLocal主要解决的就是每个线程绑定自己的值，可以将ThreadLocal类比喻成全局存放数据的盒子
 *
 * 盒子中可以存储每个线程的私有变量 在主线程中可以打印出对应线程的变量。
 */
public class ThreadLocalDemo {

    //实例化ThreadLocal
    public static ThreadLocal<List<String>> local=new ThreadLocal<List<String>>();

    public void  getLocal() {
        System.out.println("当前线程名:"+Thread.currentThread().getName());
        //lambda表达式
        local.get().forEach(name-> System.out.println(name));
    }

    public void setLocal(List<String> lists) {
        local.set(lists);
    }

    public static void main(String[] args){
        final ThreadLocalDemo threadLocalDemo=new ThreadLocalDemo();

        //lambda表达式

        new Thread(()->{
           List<String> lists=new ArrayList<String>(3);
           lists.add("张三");
           lists.add("李四");
           lists.add("王五");
           threadLocalDemo.setLocal(lists);//放入

           threadLocalDemo.getLocal();//打印

        }).start();

        new Thread(()->{
           List<String> lists=new ArrayList<String>();
           lists.add("First");
           lists.add("Second");
           threadLocalDemo.setLocal(lists);

           threadLocalDemo.getLocal();

        }).start();
    }
}