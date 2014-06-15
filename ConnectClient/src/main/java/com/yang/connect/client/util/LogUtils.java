package com.yang.connect.client.util;

/**
 * This logging utility class is introduce to allow flexibility to use either one of the following:
 * 
 * java logging
 * Log4j
 * slf4j
 * Log4j Android
 * Android logging
 * 
 * I am not in a hurry to focus on the non-critical non-functional facility for now.
 * Feel free to change the delegate/implementation and maven dependencies as you wish.
 * 
 * @author Ping
 */
public class LogUtils {
	//private static LogUtilsJavaLogging delegate = new LogUtilsJavaLogging();
	private static LogUtilsLog4j delegate = new LogUtilsLog4j();
	
	public static void error(String msg, Exception e) {
		delegate.error(msg, e);
	}
	
	public static void debug(String msg) {
		delegate.debug(msg);
	}
	
	public static void info(String msg)  {
		delegate.info(msg);
	}
	
	public static void trace(String msg) {
		delegate.trace(msg);
	}
}
