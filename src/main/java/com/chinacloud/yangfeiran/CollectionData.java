package com.chinacloud.yangfeiran;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/11.
 */
public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> gen,int quantity){
        for(int i=0;i<quantity;i++){
            add(gen.next());
        }
    }
    public static <T> CollectionData<T> list(Generator<T> gen,int quantity){
        return new CollectionData<T>(gen,quantity);
    }
    public static void main(String[] args){
    }
}
class Government implements Generator<String>{
    String[] s = "this is my demo".split(" ");
    private int index;
    @Override
    public String next() {
        return s[index++];
    }
}