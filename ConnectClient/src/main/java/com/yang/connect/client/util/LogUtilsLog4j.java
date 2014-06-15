package com.yang.connect.client.util;

import org.apache.log4j.Logger;

public class LogUtilsLog4j {
	private static final Logger LOGGER = Logger.getLogger(LogUtilsLog4j.class);

	public void error(String msg) {
		// Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.error(msg);
	}

	public void debug(String msg) {
		// Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.debug(msg);
	}

	public void info(String msg) {
		// Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.info(msg);
	}

	public void trace(String msg) {
		// Log.d(DEBUG_TAG, "The response is: " + response);
		LOGGER.trace(msg);
	}
}
