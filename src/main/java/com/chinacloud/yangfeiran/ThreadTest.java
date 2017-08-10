package com.chinacloud.yangfeiran;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ThreadTest extends Thread {
    private static Integer countDown = 5;
    private static int threadCount = 0;
    private CountDown Down;

    public ThreadTest(CountDown countDown) {
        super(Integer.toString(++threadCount));
        Down = countDown;
        start();
    }

    public synchronized String toshitString() {
        return "#" + getName() + "(" + countDown + ")";
    }

    public synchronized int next() {
        if (--countDown >= 0) {
            yield();
            System.out.println(this.toshitString());
        }
        return countDown;
    }

    public void run() {
        while (true) {
            System.out.println("run!!!!!!!!"+getName());
            int val = Down.next();
            if (val >= 0) {

                System.out.println(val + "!!!!!!!!"+getName());

            }else{
                break;
            }
        }
    }

    public static void main(String[] args) {
        CountDown down = new CountDown();
        for (int i = 0; i < 5; i++) {
            new ThreadTest(down);
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
    }
}
