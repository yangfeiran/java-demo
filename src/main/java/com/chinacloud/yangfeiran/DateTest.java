package com.chinacloud.yangfeiran;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

public class DateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new Timestamp(System.currentTimeMillis()).toString());
		Calendar nowTime = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
		System.out.println(s.format(nowTime.getTime()));
		int i = 5;
		nowTime.add(Calendar.MINUTE, -i);
		System.out.println(nowTime.getTimeInMillis());
		ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
	}

}
