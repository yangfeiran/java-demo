package com.chinacloud.yangfeiran;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/21.
 */
public class WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WaxOn(car));
        executorService.execute(new WaxOff(car));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
        executorService.shutdown();

    }

}
class Car{
    private boolean waxOn = false;
    public synchronized void waxed(){
        waxOn=true;
        notifyAll();
    }
    public synchronized void buffed(){
        waxOn=false;
        notifyAll();
    }
    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn==false){
            wait();
        }
    }
    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn==true){
            wait();
        }
    }
}
class WaxOn implements Runnable{
    private Car car;
    public WaxOn(Car car){ this.car = car;}
    public void run(){
        try{
            while(!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(200);
                car.waitForBuffing();
                System.out.println("Wax on! ");
                car.waxed();
            }
        } catch (InterruptedException e) {
            System.out.println("Wax On Exiting via interrupt");
        }
        System.out.println("Ending Wax On Task");
    }
}
class WaxOff implements Runnable{
    private Car car;
    public WaxOff(Car car){ this.car = car;}
    public void run(){
        try{
            while(!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(200);
                car.waitForWaxing();
                System.out.println("Wax off! ");
                car.buffed();
            }
        } catch (InterruptedException e) {
            System.out.println("Wax off Exiting via interrupt");
        }
        System.out.println("Ending Wax off Task");
    }
}
