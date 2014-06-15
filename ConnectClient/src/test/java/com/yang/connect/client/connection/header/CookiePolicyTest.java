package com.yang.connect.client.connection.header;

import static org.junit.Assert.fail;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import org.junit.Test;

public class CookiePolicyTest {

	@Test
	public void test() {
		CookiePolicy p = CookiePolicy.ACCEPT_ALL;

		CookieManager cm = new CookieManager();
		cm.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		CookieHandler.setDefault(cm);

		CookieHandler defaultCookieHandler = CookieHandler.getDefault();
	}

}
