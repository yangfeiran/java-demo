package com.chinacloud.yangfeiran;

import java.util.*;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

public class MapTest implements Collection<String>{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Map<String, String> options = Maps.newHashMap();
		 options.remove("xixi");
		Queue<String> queue = new PriorityQueue<>(1, String.CASE_INSENSITIVE_ORDER);
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<String> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(String s) {
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {

	}
}
