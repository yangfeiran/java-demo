package com.chinacloud.yangfeiran.document.ws;

import javax.jws.WebService;

//Service Implementation
@WebService(endpointInterface = "com.chinacloud.yangfeiran.document.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}
}