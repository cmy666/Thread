package com.cmy.Executor.demo1;


import java.util.concurrent.*;

/**
 * 线程复用:线程池(为了避免系统频繁的创建和销毁线程，我们可以将创建的线程进行复用)
 * JDK对线程池的支持:
 *       JDK提供的Executors框架，可以让我们有效的管理和控制我们的线程，其本质上就是一个线程池
 *       Executor接口的继承关系如下所示:
 *                       Executor
 *                          ↑
 *                    ExecutorService
 *                ↑                        ↑
 *     AbstractExecutorService    ScheduledExecutorService
 *                ↑                        ↑
 *      ThreadPoolExecutor                 ↑
 *                ↑                        ↑
 *                 ScheduledThreadPoolExecutor
 *
 *
 *    Executors类常用方法如下:
 *
 *    1、newFixedThreadPool：该方法返回一个固定线程数量的线程池；
 *
 *    2、newSingleThreadExecutor：该方法返回一个只有一个现成的线程池；
 *
 *    3、newCachedThreadPool：返回一个可以根据实际情况调整线程数量的线程池；
 *
 *    4、newSingleThreadScheduledExecutor：该方法和newSingleThreadExecutor的区别是给定了时间执行某任务的功能，可以进行定时执行等；
 *
 *    5、newScheduledThreadPool：在4的基础上可以指定线程数量。
 *
 *
 *    查看Executors类的源码如下: 例如newFixedThreadPool (同学们可自主查看)
 *
 *    以下为:newFixedThreadPool的实现:
 *            public static ExecutorService newFixedThreadPool(int nThreads) {
 *                   return new ThreadPoolExecutor(nThreads, nThreads,
 *                      0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
 *            }
 *
 *    本质上是创建了ThreadPoolExecutor的实例而已！
 *
 *    ThreadPoolExecutor构造参数如下:
 *        public ThreadPoolExecutor(int corePoolSize,
             int maximumPoolSize, 核心线程池大小
             long keepAliveTime,  线程最大容量
             TimeUnit unit,       空闲时，线程的存活时间
             ThreadFactory,       线程工厂
             BlockingQueue<Runnable> workQueue, 任务队列
             RejectedExecutionHandler handler) {  线程的拒绝策略
                       this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                             Executors.defaultThreadFactory(), handler);
          }
 *
 *
 *
 */
public class ThreadPoolDemo {
    public static void main(String[] args){
         /*在此 根据alibaba编码规范的提示

             ‘不推荐使用Executors中的方法创建线程池 而是推荐创建ThreadPoolExecutor实例的方式’
             以下为alibaba介绍:

               线程池不允许使用Executors去创建，而是使用ThreadPoolExecutor的方式，这样做可以规避很多风险

               说明:各个方法的弊端

               newFixedThreadPool和newSingleThreadExecutor:
                  主要问题是堆积的请求处理队列可能会耗费非常大的内存

               newCachedThreadPool和newScheduledThreadPool
                  主要问题是线程最大数Integer.MAX_VALUE 可能会创建过多的线程 导致资源浪费

          */

         //不推荐做法:ExecutorService service=Executors.newFixedThreadPool(4); //获取拥有4条线程的线程池

         //推荐做法
         ExecutorService service=new ThreadPoolExecutor(2,
                                                        2,
                                                        0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(10),Executors.defaultThreadFactory());

         for (int i=0;i<10;i++){
             int index=i;
             //使用线程池中的线程重复打印index的值
             service.submit(()->
                 System.out.println(Thread.currentThread().getName()+" i:"+index+"service"));
         }

         service.shutdown();//线程停止运行
    }
}