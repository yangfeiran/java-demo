package com.chinacloud.yangfeiran;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/18.
 */
public class EvenChecker implements Runnable {
    private EvenGenerator generator;
    private final int id;
    public EvenChecker(EvenGenerator g , int ident){
        generator=g;
        id= ident;
    }
    public void run(){
        while(!generator.isCanceled()){
            int val = generator.next();
            if(val % 2 !=0 || val > 5000000){
                System.out.println(val+" not even!");
                generator.cancel();
            }
        }
    }
    public static void test(EvenGenerator gp, int count){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i<count; i++){
            executorService.execute(new EvenChecker(gp,i));
        }
        executorService.shutdown();
    }
    public static void  test(EvenGenerator gp){
        test(gp,10);
    }
}
