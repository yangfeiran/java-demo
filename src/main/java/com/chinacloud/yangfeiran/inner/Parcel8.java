package com.chinacloud.yangfeiran.inner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/8/1.
 */
public class Parcel8 {
    static {
        ExecutorService executorService = Executors.newCachedThreadPool();

    }
    class Contents extends Parcel7{
        String s = "";
        String is = new String();
        String sis = s+is;
        ExecutorService executorService = Executors.newCachedThreadPool();
        String ex = executorService.toString();
        public String read(){
            return "";
        }
        public Contents(String s){
            this.s=s;
        }
    }
}

//interface Contents{}
