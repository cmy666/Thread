package com.cmy.singleton;


/**
 * 单例类:所谓单例模式，就是确保一个类只能存在一个实例，并且自动实例化向
 *       整个系统提供这个实例！
 *
 *       双重校验锁的单例！
 */
public class SingleClass {

    /**
     * volatile:关键字
     *     作用:(1)禁止指令重排优化
     *                指令重排:是JVM为了优化指令，提高运行效率的一种手段。在不影响单线程
     *                         执行结果的前提下，尽可能提高并行度(比如:将多条指令并行执行)
     *                         但在多线程的情况下指令重排就会给我们带来问题。
     *
     *          (2)提供内存可见性
     *
     */
    private static volatile SingleClass sc;

    /**
     * 在创建sc实例的时候做了以下的三件事：
     *      (1) 为sc对象分配内存空间
     *      (2) 调用SingleClass类的构造函数实例化
     *      (3) 将实例化好的对象分配到对应的内存空间中
     *
     * 如果不使用volatile关键字则会带来以下问题:无法保证 1-2-3的执行顺序
     * 可能会是 1-3-2 如果步骤(3)在步骤(2)之前执行的话 这时的sc对象已经不为null了
     * 再次执行步骤(2)则会丢失单例带来的效果 也就是说无法实现单例
     *
     * @return SingleClass对象
     */
    public static SingleClass getInstance(){
        if(sc==null){  //第一次校验
            synchronized (SingleClass.class){ //同步代码块
                 if(sc==null){  //第二次校验
                     sc=new SingleClass(); //创建实例
                 }
            }
        }
        return sc;
    }

    /**
     * 构造函数私有化：该类外部无法访问
     */
    private SingleClass(){

    }
}