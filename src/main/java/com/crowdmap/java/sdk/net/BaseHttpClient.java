/*******************************************************************************
 * Copyright (c) 2010 - 2013 Ushahidi Inc.
 * All rights reserved
 * Website: http://www.ushahidi.com
 *
 * GNU AFFERO GENERAL PUBLIC LICENSE Version 3 Usage
 * This file may be used under the terms of the GNU AFFERO GENERAL
 * PUBLIC LICENSE Version 3 as published by the Free Software
 * Foundation and appearing in the file LICENSE included in the
 * packaging of this file. Please review the following information to
 * ensure the GNU AFFERO GENERAL PUBLIC LICENSE Version 3 requirements
 * will be met: http://www.gnu.org/licenses/agpl.html.
 ******************************************************************************/
package com.crowdmap.java.sdk.net;

import com.crowdmap.java.sdk.CrowdmapException;
import com.crowdmap.java.sdk.net.content.Body;
import com.crowdmap.java.sdk.net.content.Field;
import com.crowdmap.java.sdk.net.content.FileBody;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import static com.crowdmap.java.sdk.net.ICrowdmapConstants.CHARSET_UTF8;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.CONTENT_TYPE_FORM_URLENCODED;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.CONTENT_TYPE_JSON;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.GZIP_DEFLATE;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.REFERRER;

/**
 * This is a custom implementation of an HTTP client based on the existing classes provided by the
 * standard java.net package. <p> It implements both POST and GET requests. In addition it supports
 * multi-part POST request to allow file uploads. </p>
 *
 * @author eyedol
 */
public abstract class BaseHttpClient {

    protected final Logger logger = Logger.getLogger(getClass()
            .getCanonicalName());

    /**
     * The request headers.
     */
    protected Map<String, String> requestHeaders = new HashMap<String, String>();

    /**
     * The request parameters.
     */
    protected Map<String, String> requestParameters = new HashMap<String, String>();

    /**
     * The default time to timeout both connection and socket reads
     */
    private static final int TIMEOUT = 60000;

    /**
     * The default connection timeout is 3 secs. You can override this value by calling the {@link
     * BaseHttpClient#setConnectionTimeout(int)}.
     */
    private int connectionTimeout = 0;

    /**
     * The default socket timeout is 3 secs. You can override this value by calling the {@link
     * BaseHttpClient#setSocketTimeout(int)}
     */
    private int socketTimeout = 0;

    private int bufferSize = 8192;

    protected String baseUri;

    public String requestUrl;

    /**
     * Create default HTTP client
     */
    public BaseHttpClient(final String hostname, final int port, final String scheme) {
        final StringBuilder uri = new StringBuilder(scheme);
        uri.append("://");
        uri.append(hostname);
        if (port > 0) {
            uri.append(':').append(port);
        }
        baseUri = uri.toString();
    }

    /**
     * Sets the request headers.
     *
     * @param requestHeaders the request headers
     */
    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /**
     * Gets the request headers.
     *
     * @return the request headers
     */
    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * Adds the request header.
     *
     * @param headerName  the header name
     * @param headerValue the header value
     */
    public void addRequestHeader(String headerName, String headerValue) {
        requestHeaders.put(headerName, headerValue);
    }

    /**
     * Removes the request header.
     *
     * @param headerName the header name
     */
    public void removeRequestHeader(String headerName) {
        requestHeaders.remove(headerName);
    }

    /**
     * Sets the referrer.
     *
     * @param referrer the new referrer
     */
    public void setReferrer(String referrer) {
        requestHeaders.put(REFERRER, referrer);
    }

    /**
     * Set the default connection timeout. The default connection timeout is 3 secs.
     *
     * @param connectionTimeout The connection timeout in miliseconds
     */
    public void setConnectionTimeout(int connectionTimeout) {
        if (connectionTimeout > 0) {
            this.connectionTimeout = connectionTimeout;
        } else {
            this.connectionTimeout = TIMEOUT;
        }
    }

    /**
     * Get the default connection timeout.
     *
     * @return The set connection timeout
     */
    public int getConnectionTimeout() {
        if (connectionTimeout > 0) {
            return this.connectionTimeout;
        }
        return TIMEOUT;

    }

    /**
     * Set the default socket timeout. The default socket timeout is 3 secs.
     *
     * @param socketTimeout An <code>int</code> that specifies the socket timeout value in
     *                      milliseconds
     */
    public void setSocketTimeout(int socketTimeout) {
        if (socketTimeout > 0) {
            this.socketTimeout = socketTimeout;
        } else {
            this.socketTimeout = TIMEOUT;
        }
    }

    /**
     * Get the default socket timeout.
     *
     * @return The socket timeout in milliseconds
     */
    public int getSocketTimeout() {
        if (socketTimeout > 0) {
            return this.socketTimeout;
        }
        return TIMEOUT;
    }

    /**
     * Create full URI from path
     *
     * @return uri
     */
    protected String createUri(String uri) {
        String url = baseUri + uri;
        return url;
    }

    /**
     * Convert stream to string
     *
     * @param is the input stream to be converted to string
     * @return the converted string
     */
    protected String streamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(is, CHARSET_UTF8), bufferSize);
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * Call a GET request.
     *
     * @param url the API url
     * @return the input stream
     */
    protected InputStream getRequest(String url) {
        return getRequest(url, HttpURLConnection.HTTP_OK);
    }

    /**
     * Make a POST request
     *
     * @param url  The API URL
     * @param body The parameters to be passed to the URL
     * @return The input stream
     */
    protected InputStream postRequest(String url, Body body) {
        return postRequest(url, body, HttpURLConnection.HTTP_OK);
    }

    /**
     * Make a PUT request
     *
     * @param url  The API URL
     * @param body The parameters to be passed to the URL
     * @return The input stream
     */
    protected InputStream putRequest(String url, Body body) {
        return putRequest(url, body, HttpURLConnection.HTTP_OK);
    }

    /**
     * Make a DELETE request
     *
     * @param url The API URL
     * @return The input stream
     */
    protected InputStream deleteRequest(String url) {
        return deleteRequest(url, HttpURLConnection.HTTP_OK);
    }

    /**
     * Make a POST request
     *
     * @param url The API URL
     * @return The input stream
     */
    protected InputStream postRequest(String url) {
        return postRequest(url, HttpURLConnection.HTTP_OK);
    }

    /**
     * Make a Multi-part POST request. This request makes it possible to upload files.
     *
     * @param url  The API URL
     * @param body The parameters to be passed to the URL
     * @return the input stream
     */
    protected InputStream postMultipartRequest(String url, Body body) {
        return postMultipartRequest(url, body, HttpURLConnection.HTTP_OK);
    }

    /**
     * Make a GET request.
     *
     * @param url      The API URL
     * @param expected The expected
     * @return The input stream
     */
    protected InputStream getRequest(String url, int expected) {
        HttpURLConnection request;

        try {

            URL apiUrl = initUrl(url);
            if (!requestParameters.isEmpty()) {
                if (apiUrl.getQuery() == null) {
                    apiUrl = initUrl(url + "?"
                            + getParametersString(requestParameters));
                } else {
                    apiUrl = initUrl(url + "&"
                            + getParametersString(requestParameters));
                }
            }

            requestUrl = apiUrl.getHost()+apiUrl.getHost()+apiUrl.getPath()+apiUrl.getQuery();

            request = openConnection(apiUrl, "GET");
            request.setRequestProperty("Content-Type", CONTENT_TYPE_JSON
                    + "; charset=" + CHARSET_UTF8);
            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));
            }

        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {
            //closeConnection(request);
        }

    }

    /**
     * Make a DELETE request.
     *
     * @param url      The API URL
     * @param expected The expected
     * @return The input stream
     */
    protected InputStream deleteRequest(String url, int expected) {
        HttpURLConnection request = null;
        try {

            URL apiUrl = initUrl(url);
            if (!requestParameters.isEmpty()) {
                if (apiUrl.getQuery() == null) {
                    apiUrl = initUrl(url + "?"
                            + getParametersString(requestParameters));
                } else {
                    apiUrl = initUrl(url + "&"
                            + getParametersString(requestParameters));
                }
            }

            request = openConnection(apiUrl, "DELETE");
            request.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URLENCODED
                    + "; charset=" + CHARSET_UTF8);
            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));
            }

        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {
            //closeConnection(request);
        }

    }

    /**
     * Make a POST request.
     *
     * @param apiUrl   The API URL
     * @param body     The parameters
     * @param expected The expected
     * @return The input stream
     */
    protected InputStream postRequest(String apiUrl, Body body, int expected) {
        HttpURLConnection request = null;
        try {
            URL url = initUrl(apiUrl);
            request = openConnection(url, "POST");
            request.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URLENCODED
                    + "; charset=" + CHARSET_UTF8);

            StringBuilder builder = new StringBuilder();
            // for request header passed earlier on
            final String strParams = getParametersString(requestParameters);
            builder.append(strParams);
            // for request passed via body object
            // couldn't figure out a better way of doing this
            if (strParams.length() > 0) {
                builder.append("&");
            }
            builder.append(getBodyString(body));
            PrintStream out = new PrintStream(new BufferedOutputStream(
                    request.getOutputStream()));
            out.print(builder.toString());
            out.flush();
            out.close();

            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));
            }
        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {
            //closeConnection(request);
        }
    }

    /**
     * Make a PUT request.
     *
     * @param apiUrl   The API URL
     * @param body     The parameters
     * @param expected The expected
     * @return The input stream
     */
    protected InputStream putRequest(String apiUrl, Body body, int expected) {
        HttpURLConnection request = null;
        try {
            URL url = initUrl(apiUrl);
            request = openConnection(url, "PUT");
            request.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URLENCODED
                    + "; charset=" + CHARSET_UTF8);
            StringBuilder builder = new StringBuilder();
            // for request header passed earlier on
            final String strParams = getParametersString(requestParameters);
            builder.append(strParams);
            // for request passed via body object
            // couldn't figure out a better way of doing this
            if (strParams.length() > 0) {
                builder.append("&");
            }
            builder.append(getBodyString(body));

            PrintStream out = new PrintStream(new BufferedOutputStream(
                    request.getOutputStream()));

            out.print(builder.toString());
            out.flush();
            out.close();

            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));
            }
        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {
            //closeConnection(request);
        }
    }


    private URL initUrl(String uri) throws MalformedURLException {
        return new URL(createUri(uri));
    }

    /**
     * Make a POST request.
     *
     * @param apiUrl   The API URL
     * @param expected The expected
     * @return The input stream
     */
    protected InputStream postRequest(String apiUrl, int expected) {
        HttpURLConnection request = null;
        try {
            URL url = initUrl(apiUrl);
            request = openConnection(url, "POST");
            request.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URLENCODED
                    + "; charset=" + CHARSET_UTF8);
            PrintStream out = new PrintStream(new BufferedOutputStream(
                    request.getOutputStream()));
            out.print(getParametersString(requestParameters));
            out.flush();
            out.close();

            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));
            }
        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {
            //closeConnection(request);
        }
    }

    /**
     * Gets the parameters string.
     *
     * @param parameters the parameters
     * @return the parameters string
     */
    protected String getParametersString(Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<Map.Entry<String, String>> iterator = parameters
                .entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(encodeUrl(entry.getValue()));
            if (iterator.hasNext()) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    /**
     * Gets the Body string.
     *
     * @param body the parameters
     * @return the parameters string
     */
    protected String getBodyString(Body body) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<Field> iterator = body.getFields().iterator(); iterator
                .hasNext(); ) {
            Field field = iterator.next();
            builder.append(field.getName());
            builder.append("=");
            if (field.getValue() != null) {
                builder.append(encodeUrl(field.getValue().toString()));
            }
            if (iterator.hasNext()) {
                builder.append("&");
            }

        }

        return builder.toString();
    }

    /**
     * Make a POST request.
     *
     * @param apiUrl   The API URL
     * @param body     The parameters to be passed to the multipart request
     * @param expected The expected output
     * @return the input stream
     */
    protected InputStream postMultipartRequest(String apiUrl, Body body,
            int expected) {
        HttpURLConnection request = null;
        try {

            URL url = initUrl(apiUrl);
            request = openConnection(url, "POST");
            String boundary = "00content0boundary00";

            request.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            BufferedOutputStream output = new BufferedOutputStream(
                    request.getOutputStream());
            byte[] boundarySeparator = ("--" + boundary + "\r\n")
                    .getBytes(CHARSET_UTF8);
            byte[] newline = "\r\n".getBytes(CHARSET_UTF8);

            // process request parameters if we have them
            if (!requestParameters.isEmpty() && requestParameters != null) {
                for (Iterator<Map.Entry<String, String>> iterator = requestParameters
                        .entrySet().iterator(); iterator.hasNext(); ) {

                    Map.Entry<String, String> entry = iterator.next();

                    output.write(boundarySeparator);
                    StringBuilder partBuffer = new StringBuilder(
                            "Content-Disposition: ");
                    partBuffer.append("form-data; name=\"");
                    partBuffer.append(entry.getKey());
                    partBuffer.append('"');
                    output.write(partBuffer.toString().getBytes(CHARSET_UTF8));
                    output.write(newline);
                    output.write(newline);

                    output.write(entry.getValue().toString()
                            .getBytes(CHARSET_UTF8));

                    output.write(newline);

                }

            }

            // process field names
            try {
                for (Field field : body.getFields()) {
                    output.write(boundarySeparator);
                    final Object value = field.getValue();
                    StringBuilder partBuffer = new StringBuilder(
                            "Content-Disposition: ");
                    partBuffer.append("form-data; name=\"");
                    partBuffer.append(field.getName());
                    partBuffer.append('"');
                    if (value instanceof FileBody) {
                        FileBody fileBody = (FileBody) value;
                        partBuffer.append("; filename=\"")
                                .append(fileBody.getFilename()).append('"');
                    }
                    output.write(partBuffer.toString().getBytes(CHARSET_UTF8));
                    output.write(newline);
                    output.write(newline);

                    // Get file to be uploaded
                    if (value instanceof FileBody) {
                        FileBody fileBody = (FileBody) value;
                        fileBody.writeTo(output);

                    } else {
                        // don't write null fields
                        if (field.getValue() != null) {
                            output.write(field.getValue().toString()
                                    .getBytes(CHARSET_UTF8));
                        }
                    }
                    output.write(newline);
                }
                output.write(("--" + boundary + "--\r\n").getBytes(CHARSET_UTF8));
            } finally {
                output.close();
            }

            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));
            }
        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {
            //closeConnection(request);
        }
    }

    /**
     * A generic HTTP method
     *
     * @param apiUrl      the API URL
     * @param contentType The content type
     * @param method      The HTTP method
     * @param expected    The expected output
     * @return the input stream
     */
    protected InputStream requestMethod(String apiUrl, String contentType,
            String method, int expected) {
        HttpURLConnection request = null;
        try {
            URL url = initUrl(apiUrl);
            request = openConnection(url, method);

            if (contentType != null) {
                request.setRequestProperty("Content-Type", contentType);
            }

            request.connect();

            if (request.getResponseCode() != expected) {
                throw new CrowdmapException(
                        streamToString(getWrappedInputStream(
                                request.getErrorStream(),
                                GZIP_DEFLATE.equalsIgnoreCase(request
                                        .getContentEncoding()))));
            } else {
                return getWrappedInputStream(request.getInputStream(),
                        GZIP_DEFLATE.equalsIgnoreCase(request
                                .getContentEncoding()));

            }
        } catch (IOException e) {
            throw new CrowdmapException(e);
        } finally {

        }
    }

    /**
     * Gets the wrapped input stream.
     *
     * @param is   the is
     * @param gzip the gzip
     * @return the wrapped input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected InputStream getWrappedInputStream(InputStream is, boolean gzip)
            throws IOException {
        if (gzip) {
            return new BufferedInputStream(new GZIPInputStream(is), bufferSize);
        }
        return new BufferedInputStream(is, bufferSize);

    }

    /**
     * Encode url.
     *
     * @param original the original
     * @return the string
     */
    private static String encodeUrl(String original) {
        if (original == null) {
            return "";
        } else {
            try {
                return URLEncoder.encode(original, CHARSET_UTF8);
            } catch (UnsupportedEncodingException e) {
                // should never be here..
                return original;
            }
        }
    }

    /**
     * Close stream.
     *
     * @param is the is
     */
    protected void closeStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while closing stream.",
                    e);
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    protected void closeConnection(HttpURLConnection connection) {
        try {
            if (connection != null) {
                connection.disconnect();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "An error occurred while disconnecting connection.", e);
        }
    }

    /**
     * Helper method for creating a Proxy object
     *
     * @param ip   ip address
     * @param port port number
     * @return the created Proxy object
     */
    public static Proxy createProxy(String ip, int port) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }

    private Proxy proxy;

    /**
     * Set the proxy to use for HTTP requests
     *
     * @param ip   ip address
     * @param port port number
     */
    public void setProxy(String ip, int port) {
        setProxy(createProxy(ip, port));
    }

    /**
     * Set the proxy to use for HTTP requests
     */
    public void setProxy(Proxy p) {
        proxy = p;
    }

    /**
     * Convenient method for openConnection(u, method, true)
     */
    private HttpURLConnection openConnection(URL u, String method)
            throws IOException {
        // This fixes an issue with Android sending POST request if setDoOutput is true instead
        // of the GET request
        if(method.equals("GET")) {
            return openConnection(u, method, false);
        }
        return openConnection(u, method, true);
    }

    /**
     * create a HttpURLConnection with default parameters
     *
     * @param u        url
     * @param method   request method ("GET"/"POST")
     * @param doOutput passed to HttpURLConnection.doOutput()
     * @return the created HttpURLConnection
     */
    private HttpURLConnection openConnection(URL u, String method,
            boolean doOutput) throws IOException {
        URLConnection ret = proxy == null ? u.openConnection() : u
                .openConnection(proxy);
        HttpURLConnection request = (HttpURLConnection) ret;

        request.setConnectTimeout(getConnectionTimeout());
        request.setReadTimeout(getSocketTimeout());

        for (String headerName : requestHeaders.keySet()) {
            request.setRequestProperty(headerName,
                    requestHeaders.get(headerName));
        }

        request.setDoOutput(doOutput);
        request.setRequestMethod(method);
        return request;
    }
}
