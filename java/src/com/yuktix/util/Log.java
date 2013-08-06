package com.yuktix.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	private static final Logger logger= Logger.getLogger(com.yuktix.util.Log.class.getName());
	
	public static void error(String message) {
		logger.log(Level.SEVERE, message);
	}
	
	public static void error(String message, Throwable ex) {
		logger.log(Level.SEVERE, message,ex);
	}
	
	public static void error(Throwable ex) {
		logger.log(Level.SEVERE, ex.getMessage(),ex);
	}
	
	public static void info(String message) {
		logger.log(Level.CONFIG, message);
	}
	
	public static void info(String message, Throwable ex) {
		logger.log(Level.CONFIG, message,ex);
	}
	
	public static void debug(String message,Throwable ex) {
		logger.log(Level.FINE, message,ex);
	}
	
	
}
