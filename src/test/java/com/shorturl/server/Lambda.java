package com.shorturl.server;

public class Lambda {

	
	public static void main(String[] args) {
		
		
		Runnable run  = () -> System.out.println("111111111111");
		new Thread(run).start();
		
		
	}
	
}


