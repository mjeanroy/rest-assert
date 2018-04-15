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

package com.github.mjeanroy.restassert.core.internal.data;

/**
 * List of standard http status.
 */
public final class HttpStatusCodes {

	// Ensure non instantiation
	private HttpStatusCodes() {
	}

	// 2XX

	/**
	 * HTTP 200: OK.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1
	 */
	public static final int OK = 200;

	/**
	 * HTTP 201: CREATED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2
	 */
	public static final int CREATED = 201;

	/**
	 * HTTP 202: ACCEPTED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3
	 */
	public static final int ACCEPTED = 202;

	/**
	 * HTTP 204: NO CONTENT.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5
	 */
	public static final int NO_CONTENT = 204;

	/**
	 * HTTP 205: RESET CONTENT.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6
	 */
	public static final int RESET_CONTENT = 205;

	/**
	 * HTTP 206: PARTIAL CONTENT.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7
	 */
	public static final int PARTIAL_CONTENT = 206;

	// 3XX

	/**
	 * HTTP 301: MOVED PERMANENTLY.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2
	 */
	public static final int MOVED_PERMANENTLY = 301;

	/**
	 * HTTP 302: MOVED TEMPORARILY.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3
	 */
	public static final int MOVED_TEMPORARILY = 302;

	/**
	 * HTTP 304: NOT MODIFIED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5
	 */
	public static final int NOT_MODIFIED = 304;

	// 4XX

	/**
	 * HTTP 400: BAD REQUEST.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1
	 */
	public static final int BAD_REQUEST = 400;

	/**
	 * HTTP 401: UNAUTHORIZED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2
	 */
	public static final int UNAUTHORIZED = 401;

	/**
	 * HTTP 401: FORBIDDEN.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4
	 */
	public static final int FORBIDDEN = 403;

	/**
	 * HTTP 404: NOT FOUND.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5
	 */
	public static final int NOT_FOUND = 404;

	/**
	 * HTTP 405: METHOD NOT ALLOWED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6
	 */
	public static final int METHOD_NOT_ALLOWED = 405;

	/**
	 * HTTP 406: NOT ACCEPTABLE.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7
	 */
	public static final int NOT_ACCEPTABLE = 406;

	/**
	 * HTTP 409: CONFLICT.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10
	 */
	public static final int CONFLICT = 409;

	/**
	 * HTTP 412: PRE CONDITION FAILED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13
	 */
	public static final int PRE_CONDITION_FAILED = 412;

	/**
	 * HTTP 415: UNSUPPORTED MEDIA TYPE.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16
	 */
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;

	// 5XX

	/**
	 * HTTP 500: INTERNAL SERVER ERROR.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1
	 */
	public static final int INTERNAL_SERVER_ERROR = 500;

	/**
	 * HTTP 501: NOT IMPLEMENTED.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2
	 */
	public static final int NOT_IMPLEMENTED = 501;
}
