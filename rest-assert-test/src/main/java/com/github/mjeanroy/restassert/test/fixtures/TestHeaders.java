/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.restassert.test.fixtures;

import com.github.mjeanroy.restassert.test.data.Header;

import static com.github.mjeanroy.restassert.test.data.Header.header;

/**
 * Static pre-configured headers to use in unit test.
 */
public final class TestHeaders {

	// Ensure non instantiation.
	private TestHeaders() {
	}

	/**
	 * The {@code "Access-Control-Allow-Credentials"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_CREDENTIALS = header("Access-Control-Allow-Credentials", "true");

	/**
	 * The {@code "Access-Control-Allow-Headers"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_HEADERS = header("Access-Control-Allow-Headers", "X-Requested-With");

	/**
	 * The {@code "Access-Control-Allow-Methods"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_METHODS = header("Access-Control-Allow-Methods", "GET, POST");

	/**
	 * The {@code "Access-Control-Expose-TestHeaders"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_EXPOSE_HEADERS = header("Access-Control-Expose-Headers", "Content-Length");

	/**
	 * The {@code "Access-Control-Allow-Max-Age"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_MAX_AGE = header("Access-Control-Allow-Max-Age", "3600");

	/**
	 * The {@code "Access-Control-Allow-Origin"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_ORIGIN = header("Access-Control-Allow-Origin", "*");

	/**
	 * The {@code "Cache-Control"} header, to use in unit test.
	 */
	public static final Header CACHE_CONTROL = header("Cache-Control", "public, no-transform, max-age=300");

	/**
	 * The {@code "Content-Disposition"} header, to use in unit test.
	 */
	public static final Header CONTENT_DISPOSITION = header("Content-Disposition", "attachment; filename=\"fname.ext\"");

	/**
	 * The {@code "Content-Length"} header, to use in unit test.
	 */
	public static final Header CONTENT_LENGTH = header("Content-Length", "100");

	/**
	 * The {@code "Content-Security-Policy"} header, to use in unit test.
	 */
	public static final Header CONTENT_SECURITY_POLICY = header("Content-Security-Policy", "default-src 'none';");

	/**
	 * The {@code "ETag"} header value, to use in unit test.
	 */
	public static final Header ETAG = header("ETag", "123");

	/**
	 * The {@code "Expires"} header, to use in unit test.
	 */
	public static final Header EXPIRES = header("Expires", "Mon, 01 Jun 2009 08:56:18 GMT");

	/**
	 * The {@code "Content-Encoding"} header with {@code "gzip"} value, to use in unit test.
	 */
	public static final Header GZIP_CONTENT_ENCODING = header("Content-Encoding", "gzip");

	/**
	 * The {@code "Content-Type"} header with {@code "application/json"} value, to use in unit test.
	 */
	public static final Header JSON_CONTENT_TYPE = header("Content-Type", "application/json; charset=utf-8");

	/**
	 * The {@code "Last-Modified"} header, to use in unit test.
	 */
	public static final Header LAST_MODIFIED = header("Last-Modified", "Mon, 01 Jun 2009 08:56:18 GMT");

	/**
	 * The {@code "Location"} header, to use in unit test.
	 */
	public static final Header LOCATION = header("Location", "http://www.google.fr");

	/**
	 * The {@code "Pragma"} header, to use in unit test.
	 */
	public static final Header PRAGMA = header("Pragma", "no-cache");

	/**
	 * The {@code "Strict-Transport-Security"} header, to use in unit test.
	 */
	public static final Header STRICT_TRANSPORT_SECURITY = header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

	/**
	 * The {@code "X-Content-Type-Options"} header, to use in unit test.
	 */
	public static final Header X_CONTENT_TYPE_OPTIONS = header("X-Content-Type-Options", "nosniff");

	/**
	 * The {@code "X-Frame-Options"} header, to use in unit test.
	 */
	public static final Header X_FRAME_OPTIONS = header("X-Frame-Options", "DENY");

	/**
	 * The {@code "X-XSS-Protection"} header, to use in unit test.
	 */
	public static final Header X_XSS_PROTECTION = header("X-XSS-Protection", "0");
}
