package com.chinacloud.yangfeiran;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/28.
 */
public class InterfaceCollision {
}

interface I1 {
    Object f();
}

interface I2 {
    int f(int i);
}

interface I3 {
    String f();
}

class Ci {
    public String f() {
        return null;

    }
}

class C2 implements I1, I2 {

    public Object f() {
        return null;
    }

    public int f(int i) {
        return 0;
    }
}

class C3 extends Ci implements I2 {

    public int f(int i) {
        return 0;
    }
}

class C4 extends Ci implements I3 {
}

class C5 extends Ci implements I1, Iterable<String> {
    shit s = new shit();
    shit.bullshit b = new shit.bullshit();

    @Override
    public Iterator<String> iterator() {
        return null;
    }
   public static class Person {
        private String address = "swjtu,chenDu,China";
        public String mail = "josserchai@yahoo.com";//内部类公有成员

        public void display() {
//System.out.println(id);//不能直接访问外部类的非静态成员
//            System.out.println(name);//只能直接访问外部类的静态成员
            System.out.println("Inner " + address);//访问本内部类成员。
        }
    }

}


