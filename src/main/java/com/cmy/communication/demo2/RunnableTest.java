package com.cmy.communication.demo2;

//案例:创建三条线程 A线程打印'我' B线程打印'爱' C线程打印'你'

/**
 * 案例分析:三条线程交互执行
 *
 *
 */
public class RunnableTest implements Runnable{

    private String word;//每个线程要打印的字符
    private Object prev;//当前执行线程的上一个线程所持有的锁
    private Object current;//当前线程持有的锁

    @Override
    public void run() {
        /*
           线程执行必要的条件:(1) 获取上一个线程的持有锁
                             (2) 获取本线程持有的锁
         */
        for(int i=0;i<10;i++){
            synchronized (prev){
                synchronized (current){
                //打印对应线程的字体
                    System.out.print(word);

                current.notify();//唤醒等待current锁的线程
                }
                try {
                   prev.wait();//释放对prev锁的占有 并自身进入等待状态！
                } catch (InterruptedException e) {
                   e.printStackTrace();
                }
            }
        }
    }

    public RunnableTest(String word, Object prev, Object current) {
        this.word = word;
        this.prev = prev;
        this.current = current;
    }
}