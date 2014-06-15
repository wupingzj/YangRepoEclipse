package com.yang.connect.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.yang.connect.client.util.LogUtils;

public class ConnectClientImpl implements ConnectClient {
	CookieManager cookieManager = new CookieManager();
	
	public ConnectClientImpl() {
		setup();
	}
	
	private void setup() {
		cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		CookieHandler.setDefault(cookieManager);
	}

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
	protected InputStream downloadUrl(String urlString) throws IOException {
		LogUtils.info("*** download data ***");
		LogUtils.debug("0. Show cookie store");
		displayCookieStore();
		
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setReadTimeout(20000 /* milliseconds */);
		con.setConnectTimeout(5000 /* milliseconds */);
		con.setRequestMethod("GET");
		con.setDoInput(true);
		
		LogUtils.debug("1. Show cookie store");
		displayCookieStore();
		
		// Starts the query
		con.connect();
		LogUtils.debug("2. Show cookie store");
		displayCookieStore();

		int response = con.getResponseCode();
		LogUtils.debug("The response is: " + response);
		LogUtils.debug("3. Show cookie store");
		displayCookieStore();

		InputStream stream = con.getInputStream();
		
		LogUtils.debug("4. Show cookie store");
		displayCookieStore();
		return stream;
	}
	
	protected void displayCookieStore() {
		CookieStore cookieStore = cookieManager.getCookieStore();
		List<URI> urIs = cookieStore.getURIs();
		displayURIs(urIs);
		
		List<HttpCookie> cookies = cookieStore.getCookies();
		displayCookies(cookies);
	}
	
	private void displayURIs(List<URI> urIs) {
		for (URI uri : urIs) {
			LogUtils.debug("URI:" + uri.toString());
		}
	}
	
	private void displayCookies(List<HttpCookie> cookies) {
		for (HttpCookie cookie : cookies) {
			LogUtils.debug("Cookie:" + cookie.toString());
		}
	}
	
	protected void handleCookies(HttpURLConnection con) {
		/*Map<String, List<String>> headerFields = con.getHeaderFields();
		List<String> cookieFeilds = headerFields.get("Set-Cookie");
		
		for (List<String> header : headerFields) {
			
		}*/
	}

}
