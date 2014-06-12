package com.yang.connect.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
	private final static Logger LOGGER = Logger.getLogger(LogUtils.class.getName());
	
	public static void error(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.severe(msg);
	}
	
	public static void debug(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.log(Level.FINE, msg);
	}
	
	public static void info(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.log(Level.INFO, msg);
	}
	
	public static void trace(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.log(Level.FINER, msg);
	}
}
