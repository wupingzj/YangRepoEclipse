package com.yang.connect.client;

import com.yang.connect.client.util.LogUtils;

import junit.framework.TestCase;

public class ConnectClientTest extends TestCase {
	
	public void testHttpGET() {
		ConnectClientImpl client = new ConnectClientImpl();
		//String url = "https://www.google.com";
		String url = "https://www.google.com";
		
		String httpGET = client.httpGET(url);
		LogUtils.info(httpGET);
	}

	/*@Ignore("TO BE IMPLEMENTED")
	public void testLogon() {
		ConnectClient client = new ConnectClientImpl();
		
		String url;
		String user;
		String password;
		client.logon(url, user, password);
	}*/
}
