package com.yang.connect.client;

import junit.framework.TestCase;

public class ConnectClientTest extends TestCase {
	
	public void testHttpGET() {
		ConnectClientImpl client = new ConnectClientImpl();
		String url = "www.google.com";
		
		client.httpGET(url);
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
