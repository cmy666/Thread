package com.cmy.Executor.demo2;

//使用submit函数带来的问题

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolDemo {
    public static void main(String[] args){
        ExecutorService service= Executors.newFixedThreadPool(4);

        for(int i=0;i<5;i++){
            int index=i;
            //此处index的初值为0 但控制台上不会输出任何异常（原因 内部捕获了异常）
            //service.submit(()-> Calc(100,index));

            //1:替换为execute
            //service.execute(()->Calc(100,index));

            //2:或者使用Future模式 自定义异常捕获和信息
            Future future=service.submit(()->Calc(100,index));

            try {
                future.get();
            } catch (Exception e) {
                System.out.println("我是异常信息");
            }
        }

        service.shutdown();
    }

    public static void Calc(int a,int b){
          double c=a/b;
          System.out.println(c);
    }
}