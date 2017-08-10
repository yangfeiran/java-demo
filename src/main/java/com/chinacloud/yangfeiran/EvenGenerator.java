package com.chinacloud.yangfeiran;

/**
 * Created by Administrator on 2017/7/18.
 */
public class EvenGenerator {
    private int current = 0;
    private volatile boolean canceled =false;
    public synchronized int next(){
        ++current;
//        Thread.yield();
        ++current;
        return current;
    }
    public static void main(String[] args){
        EvenChecker.test(new EvenGenerator());
    }
    public void cancel(){
        canceled = true;
    }
    public boolean isCanceled(){
        return  canceled;
    }
}
