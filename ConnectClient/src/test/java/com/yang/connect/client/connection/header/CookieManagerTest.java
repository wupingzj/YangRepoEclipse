package com.yang.connect.client.connection.header;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CookieManagerTest {
	private CookieManager cookieManager = new CookieManager();

	@Before
	public void setup() {
	}

	@Test
	@Ignore
	public void testExtractCookie() {
		String cookieString1 = "Set-Cookie: LSID=DQAAAK…Eaem_vYg; Path=/accounts; Expires=Wed, 13 Jan 2021 22:23:01 GMT; Secure; HttpOnly";
		Map<String, Map<String, String>> domainStore = new HashMap<String, Map<String, String>>();
		cookieManager.extractCookies(domainStore, cookieString1);

		String cookieString2 = "Set-Cookie: HSID=AYQEVn….DKrdst; Domain=.foo.com; Path=/; Expires=Wed, 13 Jan 2021 22:23:01 GMT; HttpOnly";
		String cookieString3 = "Set-Cookie: SSID=Ap4P….GTEq; Domain=foo.com; Path=/; Expires=Wed, 13 Jan 2021 22:23:01 GMT; Secure; HttpOnly";

	}

	// method to deep compare two maps
	private boolean compareMaps(Map<String, Map<String, String>> domainStore1,
			Map<String, Map<String, String>> domainStore2) {
		// Refer to class MapCompareTest.java for implementation.
		return domainStore1.equals(domainStore2);
	}
}
