/*****************************************************************************
 ** Copyright (c) 2010 - 2012 Ushahidi Inc
 ** All rights reserved
 ** Contact: team@ushahidi.com
 ** Website: http://www.ushahidi.com
 **
 ** GNU Lesser General Public License Usage
 ** This file may be used under the terms of the GNU Lesser
 ** General Public License version 3 as published by the Free Software
 ** Foundation and appearing in the file LICENSE.LGPL included in the
 ** packaging of this file. Please review the following information to
 ** ensure the GNU Lesser General Public License version 3 requirements
 ** will be met: http://www.gnu.org/licenses/lgpl.html.
 **
 **
 ** If you have questions regarding the use of this file, please contact
 ** Ushahidi developers at team@ushahidi.com.
 **
 *****************************************************************************/
package com.crowdmap.java.sdk.net;

import static com.crowdmap.java.sdk.net.ICrowdmapConstants.USER_AGENT;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.GZIP_DEFLATE;

import java.io.IOException;
import java.io.InputStream;

import com.crowdmap.java.sdk.CrowdmapException;
import com.crowdmap.java.sdk.net.content.Body;

/**
 * The main HTTP Client to interact with the Ushahidi API
 * 
 * @author eyedol
 * 
 */
public class CrowdmapHttpClient extends BaseCrowdmapHttpClient {

	/** The user agent to use */
	private String userAgent = null;

	private static final String METHOD_GET = "GET";

	/**
	 * METHOD_PUT
	 */
	private static final String METHOD_PUT = "PUT";

	/**
	 * METHOD_POST
	 */
	private static final String METHOD_POST = "POST";

	/**
	 * METHOD_DELETE
	 */
	private static final String METHOD_DELETE = "DELETE";

	/**
	 * METHOD_MULTIPART. Not really a method. Calling it so maintain consistency
	 */
	private static final String METHOD_MULTIPART = "MULTIPART";

	public CrowdmapHttpClient() {
		requestHeaders.put("Accept-Encoding", GZIP_DEFLATE);
	}

	/**
	 * Set the value to set as the user agent header on every request created.
	 * Specifying a null or empty agent parameter will reset this client to use
	 * the default user agent header value.
	 * 
	 * @param agent
	 */
	public void setUserAgent(String agent) {
		if (agent != null && agent.length() > 0) {
			userAgent = agent;
		} else {
			userAgent = USER_AGENT;
		}
	}

	/**
	 * Get the user agent
	 * 
	 * @return The user agent
	 */
	public String getUserAgent() {
		if (userAgent != null && userAgent.length() > 0) {
			return this.userAgent;
		}
		return USER_AGENT;
	}

	private String request(String url, String method, Body body) {
		if (url == null || method.equals("")) {
			throw new IllegalArgumentException("URL cannot be null or empty");
		}

		if (method == null || method.equals("")) {
			throw new IllegalArgumentException("Method cannot be null or empty");
		}

		InputStream inputStream = null;
		try {
			addRequestHeader("User-Agent", getUserAgent());

			// Which HTTP request method is being executed
			if (method.equals(METHOD_POST)) {

				if (body != null) {
					inputStream = postRequest(url, body);
				} else {
					inputStream = postRequest(url);
				}

			} else if (method.equals(METHOD_GET)) {

				inputStream = getRequest(url);
			} else if (method.equals(METHOD_MULTIPART)) {

				if (body != null) {
					inputStream = postMultipartRequest(url, body);
				}
			}

			if (inputStream != null) {
				return streamToString(inputStream);
			} else {
				throw new CrowdmapException(
						"Unknown content found in response.");
			}
		} catch (Exception e) {
			throw new CrowdmapException(e);
		} finally {
			closeStream(inputStream);
		}
	}

	/**
	 * Sends a GET request to the supplied URL. Converts the input stream as
	 * received from the server to string.
	 * 
	 * @param url
	 *            The URL to send the GET request to.
	 * 
	 * @return The HTTP response string as returned from the server
	 * @throws IOException
	 */
	public String sendGetRequest(String url) {
		return request(url, METHOD_GET, null);

	}

	/**
	 * Sends a POST request to the supplied URL. Converts the input stream as
	 * received from the server to string.
	 * 
	 * @param url
	 *            The URL to send the POST request to.
	 * @param body
	 *            The form fields to be sent
	 * 
	 * @return The HTTP response string as returned from the server
	 * @throws IOException
	 */
	public String sendPostRequest(String url, Body body) {
		return request(url, METHOD_POST, body);

	}

	/**
	 * Sends a POST request to the supplied URL. Converts the input stream as
	 * received from the server to string.
	 * 
	 * @param url
	 *            The URL to send the POST request to.
	 * 
	 * @return The HTTP response string as returned from the server
	 * @throws IOException
	 */
	public String sendPostRequest(String url) {
		return request(url, METHOD_POST, null);
	}

	/**
	 * Sends a POST request to the supplied URL. Converts the input stream as
	 * received from the server to string.
	 * 
	 * @param url
	 *            The URL to send the POST request to.
	 * @param body
	 *            The parameters to
	 * 
	 * @return The HTTP response string as returned from the server
	 * @throws IOException
	 */
	public String sendMultipartPostRequest(String url, Body body) {
		return request(url, METHOD_MULTIPART, body);

	}

	/**
	 * Set the request parameters
	 * 
	 * @param key
	 *            The variable name
	 * @param value
	 *            The variable value
	 */
	public void setRequestParameters(String key, String value) {
		requestParameters.put(key, value);
	}
}
