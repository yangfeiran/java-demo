package com.chinacloud.yangfeiran;

/**
 * Created by Administrator on 2017/7/18.
 */
public class CountDown {
    private int down = 10;
    public synchronized int next(){
        return --down;
    }
}
