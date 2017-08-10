package com.chinacloud.yangfeiran.inner;

/**
 * Created by Administrator on 2017/8/9.
 */
import static java.lang.System.out;
interface Big{}
class Egg2{
    protected class Yolk{
        public Yolk(){out.println("Egg2.Yolk");}
        public void f(){out.println("Egg2.Yolk.f");}
    }
    Big getBig(){
        return new Big(){
            private int i;
        };
    }
}
public class BigEgg2 {
}
