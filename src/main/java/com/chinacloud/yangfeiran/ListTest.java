package com.chinacloud.yangfeiran;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2017/7/26.
 */
public class ListTest implements Iterable<String>{
    List list = new LinkedList();
    Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public Iterator<String> iterator() {
        list.add(1);
        list.add("shit");
        Iterator iterator = list.listIterator(4);
        return null;
    }
    public List get(){
        return list;
    }
    public static void main(String[] args){
//        ListTest listTest = new ListTest();
//        listTest.iterator();
//System.out.println(listTest.get().toString());
//        LinkedList list = new LinkedList();
        LinkedList list1 = new LinkedList<>();
        ArrayList list2 = new ArrayList<>();
list1.add("1");
list1.add("1");
list1.add("1");
list1.add("2");
System.out.println(list1);
list1.remove("1");
list1.remove("1");
list1.remove(1);
list1.add(1,"3");
        System.out.println(list1);

    }
}
class ReversibleArrayList<T> extends ArrayList<T>{
    public ReversibleArrayList(Collection<T> c){super(c);}
    public Iterable<T> reversed(){
        return null;
    }
    public static <T> ReversibleArrayList<T> get(Collection<T> c){
        return new ReversibleArrayList<T>( c);
    }

}
