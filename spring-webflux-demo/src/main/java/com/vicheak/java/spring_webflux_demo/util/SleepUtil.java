package com.vicheak.java.spring_webflux_demo.util;

public class SleepUtil {
	
	public static void sleepSecond(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
