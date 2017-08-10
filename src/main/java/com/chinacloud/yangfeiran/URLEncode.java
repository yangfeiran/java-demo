package com.chinacloud.yangfeiran;
import java.net.URLEncoder;
public class URLEncode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
String s = "ddlf.doc";
String after = URLEncoder.encode(s);
System.out.println(after);
	}

}
