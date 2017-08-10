package com.chinacloud.yangfeiran.document.endpoint;

import com.chinacloud.yangfeiran.document.ws.HelloWorldImpl;
import javax.xml.ws.Endpoint;

//Endpoint publisher
public class HelloWorldPublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:6901/ws/hello", new HelloWorldImpl());
	}
}