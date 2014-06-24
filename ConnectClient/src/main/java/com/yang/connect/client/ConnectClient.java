package com.yang.connect.client;

import java.util.Map;

public interface ConnectClient {

	public boolean logon(String url, String user, String password);

	public String httpGET(String url);
	
	public String httpPOST(String url, Map<String, Object> dataMap);

}
