package com.yang.connect.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import com.yang.connect.client.util.LogUtils;

public class ConnectClientTest extends TestCase {

	@Test
	public void testHttpGET() {
		ConnectClientImpl client = new ConnectClientImpl();
		 String url = "http://www.google.com";
		url = "https://www.google.com";
		//url = "http://www.google-analytics.com/analytics.js";
		
		String httpGET = client.httpGET(url);
		LogUtils.info(httpGET);
	}

	@Test
	@Ignore
	public void testCookie() {
		String urlString = "http://www.google.com";
		HttpURLConnection con = null;
		InputStream stream = null;
		try {

			URL url = new URL(urlString);
			URI uri = new URI(urlString);

			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(20000 /* milliseconds */);
			con.setConnectTimeout(5000 /* milliseconds */);
			con.setRequestMethod("GET");
			con.setDoInput(true);
			// Starts the query
			con.connect();

			int response = con.getResponseCode();
			LogUtils.debug("The response is: " + response);

			Map<String, List<String>> headerFields = con.getHeaderFields();
			
			stream = con.getInputStream();
			
			CookieManager cookieManager =  new CookieManager();
			CookieHandler.setDefault(cookieManager);
			
			//cookieManager.get(uri, requestHeaders);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				con.disconnect();
			}
		}
	}
}
