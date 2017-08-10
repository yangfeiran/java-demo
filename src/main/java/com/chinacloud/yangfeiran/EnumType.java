package com.chinacloud.yangfeiran;

import java.util.EnumSet;

/**
 * Created by Administrator on 2017/7/6.
 */
 class EnumType {

   public static void main(String[] args){
        Type type = Type.BULL;
        type.shit();
        Type.class.getEnumConstants();

   }
   void en(Type type){
        switch (type){
            case BULL:;
            case SHIT:type=Type.BULL;
        }
   }
   public static void print(){
       System.out.println();
   }

}
enum Type{

    BULL,SHIT;
    public void shit(){
        System.out.println(valueOf(name()));
    }
}
