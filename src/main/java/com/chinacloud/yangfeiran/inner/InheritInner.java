package com.chinacloud.yangfeiran.inner;

/**
 * Created by Administrator on 2017/8/9.
 */
class WithInner{
    WithInner(){
        System.out.println("!!!");
    }
    class Inner{

        Inner(int i){
            i++;
            System.out.println(i);
        }
    }
}

public class InheritInner extends WithInner{
    InheritInner(){
        System.out.println("???");
    }

    public static void main(String[] args){
//        WithInner withInner = new WithInner("");
        InheritInner inheritInner = new InheritInner();
    }
}
