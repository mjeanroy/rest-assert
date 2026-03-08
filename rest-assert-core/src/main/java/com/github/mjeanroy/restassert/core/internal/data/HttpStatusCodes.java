/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

/// List of standard http status.
public final class HttpStatusCodes {

	// Ensure non instantiation
	private HttpStatusCodes() {
	}

	// 2XX

	/// HTTP 200: OK ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1)).
	public static final int OK = 200;

	/// HTTP 201: CREATED ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2)).
	public static final int CREATED = 201;

	/// HTTP 202: ACCEPTED ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3)).
	public static final int ACCEPTED = 202;

	/// HTTP 204: NO CONTENT ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5)).
	public static final int NO_CONTENT = 204;

	/// HTTP 205: RESET CONTENT ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6)).
	public static final int RESET_CONTENT = 205;

	/// HTTP 206: PARTIAL CONTENT ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7)).
	public static final int PARTIAL_CONTENT = 206;

	// 3XX

	/// HTTP 300: MULTIPLE CHOICES ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.1)).
	public static final int MULTIPLE_CHOICES = 300;

	/// HTTP 301: MOVED PERMANENTLY ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2)).
	public static final int MOVED_PERMANENTLY = 301;

	/// HTTP 302: MOVED TEMPORARILY ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3)).
	public static final int MOVED_TEMPORARILY = 302;

	/// HTTP 303: SEE OTHER ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3)).
	public static final int SEE_OTHER = 303;

	/// HTTP 304: NOT MODIFIED ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5)).
	public static final int NOT_MODIFIED = 304;

	/// HTTP 307: TEMPORARY REDIRECT ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8)).
	public static final int TEMPORARY_REDIRECT = 307;

	/// HTTP 308: PERMANENT REDIRECT ([RFC 7538](https://tools.ietf.org/html/rfc7538)).
	public static final int PERMANENT_REDIRECT = 308;

	// 4XX

	/// HTTP 400: BAD REQUEST ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1)).
	public static final int BAD_REQUEST = 400;

	/// HTTP 401: UNAUTHORIZED ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2)).
	public static final int UNAUTHORIZED = 401;

	/// HTTP 403: FORBIDDEN ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4)).
	public static final int FORBIDDEN = 403;

	/// HTTP 404: NOT FOUND ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5)).
	public static final int NOT_FOUND = 404;

	/// HTTP 405: METHOD NOT ALLOWED ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6)).
	public static final int METHOD_NOT_ALLOWED = 405;

	/// HTTP 406: NOT ACCEPTABLE ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7)).
	public static final int NOT_ACCEPTABLE = 406;

	/// HTTP 408: REQUEST TIMEOUT ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9)).
	public static final int REQUEST_TIMEOUT = 408;

	/// HTTP 409: CONFLICT ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10)).
	public static final int CONFLICT = 409;

	/// HTTP 410: GONE ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11)).
	public static final int GONE = 410;

	/// HTTP 412: PRE CONDITION FAILED ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13)).
	public static final int PRE_CONDITION_FAILED = 412;

	/// HTTP 413: REQUEST ENTITY TOO LARGE. ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14)).
	public static final int REQUEST_ENTITY_TOO_LARGE = 413;

	/// HTTP 414: REQUEST URI TOO LONG ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15)).
	public static final int REQUEST_URI_TOO_LONG = 414;

	/// HTTP 415: UNSUPPORTED MEDIA TYPE ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16)).
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;

	/// HTTP 416: REQUESTED RANGE NOT SATISFIABLE ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17)).
	public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;

	// 5XX

	/// HTTP 500: INTERNAL SERVER ERROR ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1)).
	public static final int INTERNAL_SERVER_ERROR = 500;

	/// HTTP 501: NOT IMPLEMENTED. ([RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2))
	public static final int NOT_IMPLEMENTED = 501;
}
