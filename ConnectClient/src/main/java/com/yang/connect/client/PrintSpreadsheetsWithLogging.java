package com.yang.connect.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.docs.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

public class PrintSpreadsheetsWithLogging {
	public static void main(String[] args) throws AuthenticationException,
			ServiceException, IOException {
		// Configure the logging mechanisms.
		Logger httpLogger = Logger
				.getLogger("com.google.gdata.client.http.HttpGDataRequest");
		httpLogger.setLevel(Level.ALL);
		Logger xmlLogger = Logger.getLogger("com.google.gdata.util.XmlParser");
		xmlLogger.setLevel(Level.ALL);
		// Create a log handler which prints all log events to the console.
		ConsoleHandler logHandler = new ConsoleHandler();
		logHandler.setLevel(Level.ALL);
		httpLogger.addHandler(logHandler);
		xmlLogger.addHandler(logHandler);

		SpreadsheetService service = new SpreadsheetService(
				"testing-loggingExampleApp-1");
		service.setUserCredentials("email", "password");

		// Get a list of your spreadsheets.
		URL metafeedUrl = new URL(
				"http://spreadsheets.google.com/feeds/spreadsheets/private/full ");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl,
				SpreadsheetFeed.class);

		// Print the title of each spreadsheet.
		List spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
			SpreadsheetEntry entry = (SpreadsheetEntry) spreadsheets.get(i);
			System.out.println("\t" + entry.getTitle().getPlainText());
		}
		
		
	}

	
}
