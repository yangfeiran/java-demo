package com.chinacloud.yangfeiran;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2017/7/26.
 */
public class ListTest implements Iterable<String>{
    List list = new CopyOnWriteArrayList();

    @Override
    public Iterator<String> iterator() {
        list.add(1);
        list.add("shit");
        return null;
    }
}
