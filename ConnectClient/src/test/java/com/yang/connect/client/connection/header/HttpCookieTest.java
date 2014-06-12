package com.yang.connect.client.connection.header;

import java.net.HttpCookie;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author Ping
 *
 *
 */
// This page allows testing cookie: http://staff.washington.edu/fmf/2009/06/19/setting-cookies/
public class HttpCookieTest {

	@Test
	public void test1() {
		String cookieString = "Set-Cookie2: LSID=DQAAAK…Eaem_vYg; Path=/accounts; Expires=Wed, 13 Jan 2021 22:23:01 GMT; Secure; HttpOnly; ";
		//cookieString = "Set-Cookie2: TestCookie=\"RFC 2965 Cookie 1 (no path)\"; Version=1, TestCookie2=\"RFC 2965 Cookie 2 (no path)\"; Version=1";
		cookieString = "Set-Cookie2: TestCookie=RFC 2965 Cookie 1 (no path),TestCookie2=\"RFC 2965 Cookie 2 (no path)\"";
		System.out.println(cookieString);
		
		List<HttpCookie> cookies = HttpCookie.parse(cookieString);
		System.out.println(String.format("Total %s cookies", cookies.size() ));
		 for (HttpCookie cookie : cookies) {
			 System.out.println(cookie);
		 }
		
	}

}
