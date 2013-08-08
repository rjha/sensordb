package com.yuktix.util;

import java.util.Date;

public class AzureUtil {
	private static long d_max = 9223372036854775807L;
	
	// ticks left till Date.max in milli seconds
	// left zero padded, 19 column wide string
	
	public static String ticks() {
		// Date() defaults to GMT
		long d_now = new Date().getTime();
		String ticks = String.format("%019d", (AzureUtil.d_max - d_now));
		return ticks ;
		
	}
	
	public static String ticks(long d_now) {
		String ticks = String.format("%019d", (AzureUtil.d_max - d_now));
		return ticks ;
		
	}
}
