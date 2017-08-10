package com.chinacloud.yangfeiran.inner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/8/1.
 */
public class Parcel7 {
    ExecutorService executorService = Executors.newCachedThreadPool();

//    public Contents contents(String s) {
//        return new Contents(s) {
//            private int i = 11;
//            private String is=s;
//            public int value() {
//                if(s!=null)
//                return i;
//                s=null;
//                return i+1;
//            }
//            public String read(){
//                return is;
//            }
//        };
//    }
    public static void main(String[] args){
        Parcel8 parcel8 = new Parcel8();
        Parcel7 contents = parcel8.new Contents("");
        Parcel7 p = new Parcel7();
//        Contents contents = new Contents("");
//        Contents contents = p.contents("");
    }
}

