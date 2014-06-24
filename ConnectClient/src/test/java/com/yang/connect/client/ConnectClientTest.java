package com.yang.connect.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.yang.connect.client.util.LogUtils;

public class ConnectClientTest extends TestCase {

	/**
	 * This test case shows that cookies are stored in the cookieStore of cookieManager.
	 * 
	 * Different cookieManagers have different cookieStore. 
	 * To share cookies between connections, the same cookieManager (i.e. cookieStore) must be used.  
	 */
	@Test
	@Ignore
	// temporarily ignored. working OK
	public void testHttpGET() {
		ConnectClient connectClient = ConnectClientImpl.getInstance();
		
		String url1 = "http://www.google.com";
		//String url2 = url1;
		String url2 = "https://www.google.com";
		//String url3 = "http://www.google-analytics.com/analytics.js";
//		String url3 = "http://www.tinybeans.com.au";
		String url3 = "https://tinybeans.com/login";
		
		
		LogUtils.info("******** First Round HTTPGET **********");
//		String first = doHttpGET(connectClient, url1);
		
		LogUtils.info("******** Second Round HTTPGET ********");
//		String second = doHttpGET(connectClient, url2);
		
		LogUtils.info("******** Third Round HTTPGET ********");
		String third = doHttpGET(connectClient, url3);
		
		
		// Assert.assertEquals(first, second);
	}
	
	private String doHttpGET(ConnectClient connectClient, String url) {
		 
		String httpGET = connectClient.httpGET(url);
		LogUtils.info(httpGET);
		return httpGET;
	}

	
	// *****************************************************************************************
	@Test
	public void testHttpPOST() {
		ConnectClient connectClient = ConnectClientImpl.getInstance();
		
		LogUtils.info("0. ******** HTTP GET - login to build session ********");
		String url0 = "https://tinybeans.com/login";
		String third = doHttpGET(connectClient, url0);
		
		LogUtils.info("1. ******** HTTP POST - authenticate ********");
		String url = "https://tinybeans.com/authenticate";
		Map<String, Object> dataMap = getTestParams();
		String postResult = doHttpPOST(connectClient, url, dataMap);
		
		LogUtils.info("Done! ******** Post Authenticate result:" + postResult);
		
		
		// Assert.assertEquals(first, second);
	}
	
	private Map<String,Object> getTestParams() {
		Map<String,Object> params = new LinkedHashMap<String, Object>();
		params.put("username", "wupingzj");
		params.put("password", "tinybeans1");
		
        /*params.put("name", "Freddie the Fish");
        params.put("email", "fishie@seemail.com");
        params.put("reply_to_thread", 10394);
        params.put("message", "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");
        */
        return params;
	}
	
	private String doHttpPOST(ConnectClient connectClient, String url, Map<String,Object> dataMap) {
		 
		String httpGET = connectClient.httpPOST(url, dataMap);
		LogUtils.info(httpGET);
		return httpGET;
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
