package com.chinacloud.yangfeiran;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/7/10.
 */
public class C implements A{
    B b = new B();
    C(){System.out.println("c");}
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
B b = new B();
b.run();
ExecutorService excutorService = Executors.newCachedThreadPool();

Future<?> f = excutorService.submit((Callable) b);
f.cancel(true);
        System.out.println("c");
        A a = new C();
        Integer i = 0;
        List<String> s = Lists.newArrayList();
Map<Integer,String> map = Maps.newHashMap();
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        System.out.println(entries);
        map.put(1,"1");
        System.out.println(entries);
    }

    @Override
    public void f() {

    }
    public void c(){

    }
}
class cc implements Delayed{

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
