package com.yang.connect.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.yang.connect.client.util.LogUtils;

public class ConnectClientImpl implements ConnectClient {

	public boolean logon(String url, String user, String password) {
		throw new RuntimeException("TO BE IMPLEMENTED");
	}

	public String httpGET(String url) {
		try {
			InputStream is = null;

			try {
				is = downloadUrl(url);
				return parseInputStream(is);
			} finally {
				if (is != null) {
					is.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.error(e.getMessage());
			return "Unable to retrieve web page. Error:" + e.getMessage();
		}
	}

	private String parseInputStream(InputStream is) throws IOException {
		// convert InputStream to String
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(20000 /* milliseconds */);
		conn.setConnectTimeout(5000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();

		int response = conn.getResponseCode();
		LogUtils.debug("The response is: " + response);

		InputStream stream = conn.getInputStream();
		return stream;
	}

}
