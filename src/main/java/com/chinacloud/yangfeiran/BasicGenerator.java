package com.chinacloud.yangfeiran;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;
    public BasicGenerator(Class t){type=t;}

    @Override
    public T next() {
        try {
            return  type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static  Generator create(Class type){
        return new BasicGenerator<>(type);
    }
    public static void main(String[] args) {
        Generator<CountedObject> objectGenerator = BasicGenerator.create(CountedObject.class);
        Object next = objectGenerator.next();
        System.out.println(next);
        shit s = new shit();
        shit.bullshit b= new shit.bullshit();
    }
}
class shit extends ArrayList<String>{
    public int i;
    public shit(){

        add("shit");
    }
    C5 c5 = new C5();
    public static class bullshit{

        void fuck(){}

    }
}
class CountedObject{
     shit.bullshit bull = new shit.bullshit();
    private static long counter =0;
    private final long id = counter++;
    public long id(){
        return id;
    }
      protected class c10{
        shit.bullshit bullshit= CountedObject.this.bull;
    }
    public String toString(){
        return "CountedObject "+id;
    }
}
