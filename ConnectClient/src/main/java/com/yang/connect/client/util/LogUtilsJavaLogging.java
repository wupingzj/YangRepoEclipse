package com.yang.connect.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtilsJavaLogging {
private final static Logger LOGGER = Logger.getLogger(LogUtilsJavaLogging.class.getName());
	
	public void error(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.severe(msg);
	}
	
	public void debug(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.log(Level.FINE, msg);
	}
	
	public void info(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.log(Level.INFO, msg);
	}
	
	public void trace(String msg) {
		//Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.log(Level.FINER, msg);
	}
}
