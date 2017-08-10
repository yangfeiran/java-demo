package com.chinacloud.yangfeiran;

import groovy.lang.Tuple;

import java.util.concurrent.Callable;
import static com.chinacloud.yangfeiran.BasicGenerator.*;
/**
 * Created by Administrator on 2017/7/10.
 */
public class B<T> implements Runnable,Callable{
    B(){
        System.out.println("b");
    }
    public void f(){System.out.println("b");}


    @Override
    public void run() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("shit!");
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
    public static  void main(String[] args){
        create(String.class);
        B b = new B(){
            public void run(){

            }
        };
    }
}
