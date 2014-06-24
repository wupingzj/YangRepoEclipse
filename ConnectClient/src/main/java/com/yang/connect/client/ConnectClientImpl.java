package com.yang.connect.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.yang.connect.client.util.LogUtils;

public class ConnectClientImpl implements ConnectClient {
	private static String ENCODING_UTF_8 = "UTF-8";
	private final static String USER_AGENT_MOZILLA5 = "Mozilla/5.0";
	
	private CookieManager cookieManager = new CookieManager();
	
	/**
	 * Makes this class a singleton.
	 */
	private static ConnectClient connectClient = new ConnectClientImpl() ;
	public static ConnectClient getInstance() {
		return connectClient;
	}
	
	/**
	 * Note: This constructor sets system-wide cookie handler SHARED BY ALL CONNECTIONS.
	 */
	private ConnectClientImpl() {
		cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		
		// *************** Sets system-wide cookie handler SHARED BY ALL CONNECTIONS ****************
		CookieHandler.setDefault(cookieManager);
	}

	public boolean logon(String url, String user, String password) {
		throw new RuntimeException("TO BE IMPLEMENTED");
	}

	public String httpGET(String url) {
		try {
			return sendGET(url);
		} catch (IOException e) {
			String msg = "Unable to retrieve web page. Error:" + e.getMessage();
			LogUtils.error(msg, e);
			return msg;
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
			LogUtils.error("Failed to read data from input stream", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LogUtils.error("Failed to close the BufferedReader.", e);
				}
			}
		}

		return sb.toString();
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	protected String sendGET(String urlString) throws IOException {
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

		InputStream is = con.getInputStream();
		
		LogUtils.debug("4. Show cookie store");
		displayCookieStore();
		
		String responseString = parseInputStream(is);
		return responseString;
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

	@Override
	public String httpPOST(String url, Map<String, Object> dataMap) {
		try {
			return sendPOST2(url, dataMap);
		} catch (IOException e) {
			String msg = String.format("Unable to post to url %s. Error:%s", url, e.getMessage());
			LogUtils.error(msg, e);
			return msg;
		}
	}
	
	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	/*protected InputStream sendPOST(String urlString, Map<String, Object> dataMap) throws IOException {
		LogUtils.info(String.format("*** POST to URL %s ***", urlString));
		LogUtils.debug("0. Show cookie store");
		displayCookieStore();
		
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setReadTimeout(20000  milliseconds );
		con.setConnectTimeout(5000  milliseconds );
		con.setRequestMethod("POST");
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
	*/
	
	// Ref: http://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily
	protected String sendPOST2(String urlString, Map<String, Object> dataMap) throws IOException {
		//By default, new instances of HttpCookie work only with servers that support RFC 2965 cookies. Many web servers support only the older specification, RFC 2109. For compatibility with the most web servers, set the cookie version to 0.
		// cookie.setVersion(0);
		
		/*
		 * Prior to Android 2.2 (Froyo), this class had some frustrating bugs. In particular, calling close() on a readable InputStream could poison the connection pool. Work around this by disabling connection pooling:
		 * private void disableConnectionReuseIfNecessary() {
			   // Work around pre-Froyo bugs in HTTP connection reuse.
			   if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
			     System.setProperty("http.keepAlive", "false");
			   
			 }}
		*/
		
		LogUtils.info(String.format("*** POST to URL %s ***", urlString));
		LogUtils.debug("0. Show cookie store");
		displayCookieStore();
		
		final String encoding = ENCODING_UTF_8;
		
		// prepare Test data
		String postData = getPostData(dataMap, encoding);
        byte[] postDataBytes = postData.getBytes(encoding);
        
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // URL.openConnection(Proxy) 
        
        conn.setDoInput(true);
        conn.setDoOutput(true);
        
        conn.setReadTimeout(20000 /* milliseconds */);
        conn.setConnectTimeout(5000 /* milliseconds */);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("User-Agent", USER_AGENT_MOZILLA5);
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		// what are they?
		conn.setInstanceFollowRedirects(false); 
		conn.setUseCaches (false);
        
        
        conn.getOutputStream().write(postDataBytes);
        
        int responseCode = conn.getResponseCode();
		LogUtils.debug("The response is: " + responseCode);
		
		
		LogUtils.debug("3. Show cookie store");
		displayCookieStore();
        
        InputStream is = conn.getInputStream();
		String responseString = parseInputStream(is);
		
		//if (responseCode>=300 && responseCode<400) {
		if (responseCode == 302 || responseCode == 303) {
			//TODO - the response code check needs to be re-written carefully
			String originalHost = url.getHost();
			String respondedHost = conn.getURL().getHost();
			//if (originalHost.equals(respondedHost)) {
			       // we were redirected! Kick the user out to the browser to sign on?
				LogUtils.info(String.format("******* WE ARE REDIRECTED from %s to %s. ********* ", originalHost, respondedHost));
				
				String newLocation = conn.getHeaderField( "Location" );
				LogUtils.info(String.format("Redirected to %s", newLocation));
				
				String redirectedResponseHtml = sendGET(newLocation);
				
				LogUtils.info(String.format("Redirected done! Returned: %s", redirectedResponseHtml));
				LogUtils.debug("4. Show cookie store AFTER POST REDIRECTION.");
				displayCookieStore();
		}

		return responseString;
	}
	
	/**
	 * Converts parameter map to post data string with specified encoding, which is ready to send over wire via http POST.
	 * Note: the encoding is handled as UTF-8.
	 * 
	 * @param params
	 * @return encoded post data string with specified encoding
	 * @throws UnsupportedEncodingException
	 */
	private String getPostData(Map<String,Object> params, String encoding) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), encoding));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), encoding));
        }
        
        return postData.toString();
	}
	
	// TO BE DELETED
	protected void sendPOSTV3(String urlString, Map<String, String> dataMap) throws IOException {
		URL url = new URL(urlString);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT_MOZILLA5);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}

}
