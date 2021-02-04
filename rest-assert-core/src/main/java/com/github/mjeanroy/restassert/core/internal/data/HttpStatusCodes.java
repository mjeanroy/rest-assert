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
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1</a>
	 */
	public static final int OK = 200;

	/**
	 * HTTP 201: CREATED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2</a>
	 */
	public static final int CREATED = 201;

	/**
	 * HTTP 202: ACCEPTED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3</a>
	 */
	public static final int ACCEPTED = 202;

	/**
	 * HTTP 204: NO CONTENT.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5</a>
	 */
	public static final int NO_CONTENT = 204;

	/**
	 * HTTP 205: RESET CONTENT.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6</a>
	 */
	public static final int RESET_CONTENT = 205;

	/**
	 * HTTP 206: PARTIAL CONTENT.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7</a>
	 */
	public static final int PARTIAL_CONTENT = 206;

	// 3XX

	/**
	 * HTTP 301: MOVED PERMANENTLY.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2</a>
	 */
	public static final int MOVED_PERMANENTLY = 301;

	/**
	 * HTTP 302: MOVED TEMPORARILY.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3</a>
	 */
	public static final int MOVED_TEMPORARILY = 302;

	/**
	 * HTTP 303: SEE OTHER.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3</a>
	 */
	public static final int SEE_OTHER = 303;

	/**
	 * HTTP 304: NOT MODIFIED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5</a>
	 */
	public static final int NOT_MODIFIED = 304;

	/**
	 * HTTP 307: TEMPORARY REDIRECT.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8</a>
	 */
	public static final int TEMPORARY_REDIRECT = 307;

	/**
	 * HTTP 308: PERMANENT REDIRECT.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc7538">https://tools.ietf.org/html/rfc7538</a>
	 */
	public static final int PERMANENT_REDIRECT = 308;

	// 4XX

	/**
	 * HTTP 400: BAD REQUEST.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1</a>
	 */
	public static final int BAD_REQUEST = 400;

	/**
	 * HTTP 401: UNAUTHORIZED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2</a>
	 */
	public static final int UNAUTHORIZED = 401;

	/**
	 * HTTP 401: FORBIDDEN.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4</a>
	 */
	public static final int FORBIDDEN = 403;

	/**
	 * HTTP 404: NOT FOUND.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5</a>
	 */
	public static final int NOT_FOUND = 404;

	/**
	 * HTTP 405: METHOD NOT ALLOWED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6</a>
	 */
	public static final int METHOD_NOT_ALLOWED = 405;

	/**
	 * HTTP 406: NOT ACCEPTABLE.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7</a>
	 */
	public static final int NOT_ACCEPTABLE = 406;

	/**
	 * HTTP 408: REQUEST TIMEOUT.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9</a>
	 */
	public static final int REQUEST_TIMEOUT = 408;

	/**
	 * HTTP 409: CONFLICT.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10</a>
	 */
	public static final int CONFLICT = 409;

	/**
	 * HTTP 410: GONE.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11</a>
	 */
	public static final int GONE = 410;

	/**
	 * HTTP 412: PRE CONDITION FAILED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13</a>
	 */
	public static final int PRE_CONDITION_FAILED = 412;

	/**
	 * HTTP 413: REQUEST ENTITY TOO LARGE.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14</a>
	 */
	public static final int REQUEST_ENTITY_TOO_LARGE = 413;

	/**
	 * HTTP 414: REQUEST URI TOO LONG.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15</a>
	 */
	public static final int REQUEST_URI_TOO_LONG = 414;

	/**
	 * HTTP 415: UNSUPPORTED MEDIA TYPE.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16</a>
	 */
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;

	/**
	 * HTTP 416: REQUESTED RANGE NOT SATISFIABLE
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17</a>
	 */
	public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;

	// 5XX

	/**
	 * HTTP 500: INTERNAL SERVER ERROR.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1</a>
	 */
	public static final int INTERNAL_SERVER_ERROR = 500;

	/**
	 * HTTP 501: NOT IMPLEMENTED.
	 *
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2">http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2</a>
	 */
	public static final int NOT_IMPLEMENTED = 501;
}
