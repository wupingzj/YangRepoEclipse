package com.yang.connect.client;

public interface ConnectClient {

	public boolean logon(String url, String user, String password);

	public String httpGET(String url);

}
