package com.cmy.communication.demo1;


//测试类
public class Test {
    public static void main(String[] args){
        final FatherAndSon fs=new FatherAndSon();//匿名内部类只能引用外部类的常量

        //创建子线程
        new Thread(()->{
            for(int i=0;i<3;i++){
                fs.SonMade();//子线程的方法
            }
        }).start();


        //父线程:无需创建 main线程即父线程
        for(int i=0;i<3;i++){
            fs.FatherMade();//父线程的方法
        }
    }

}