package com.chinacloud.yangfeiran.inner;

/**
 * Created by Administrator on 2017/8/9.
 */
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.List;

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
            List<String> list = Lists.newArrayList();
            List<String> list2 = new ArrayList<String>(list);

        };
    }
}
public class BigEgg2 {
    public static void main(String[] args){
        Log log = LogFactory.getLog(BigEgg2.class);
        int i = 0;
    }
}
