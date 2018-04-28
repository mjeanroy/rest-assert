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

import static com.github.mjeanroy.restassert.test.data.Range.range;

import com.github.mjeanroy.restassert.test.data.Range;

/**
 * Static pre-configured HTTP status values to use in unit test.
 */
public final class TestStatus {

	// Ensure non instantiation.
	private TestStatus() {
	}

	/**
	 * The status code for the {@code "OK"} response, to use in unit test.
	 */
	public static final int OK = 200;

	/**
	 * The status code for the {@code "CREATED"} response, to use in unit test.
	 */
	public static final int CREATED = 201;

	/**
	 * The status code for the {@code "ACCEPTED"} response, to use in unit test.
	 */
	public static final int ACCEPTED = 202;

	/**
	 * The status code for the {@code "NO CONTENT"} response, to use in unit test.
	 */
	public static final int NO_CONTENT = 204;

	/**
	 * The status code for the {@code "RESET CONTENT"} response, to use in unit test.
	 */
	public static final int RESET_CONTENT = 205;

	/**
	 * The status code for the {@code "PARTIAL CONTENT"} response, to use in unit test.
	 */
	public static final int PARTIAL_CONTENT = 206;

	/**
	 * The status code for the {@code "MOVED PERMANENTLY"} response, to use in unit test.
	 */
	public static final int MOVED_PERMANENTLY = 301;

	/**
	 * The status code for the {@code "MOVED TEMPORARILY"} response, to use in unit test.
	 */
	public static final int MOVED_TEMPORARILY = 302;

	/**
	 * The status code for the {@code "NOT MODIFIED"} response, to use in unit test.
	 */
	public static final int NOT_MODIFIED = 304;

	/**
	 * The status code for the {@code "BAD REQUEST"} response, to use in unit test.
	 */
	public static final int BAD_REQUEST = 400;

	/**
	 * The status code for the {@code "UNAUTHORIZED"} response, to use in unit test.
	 */
	public static final int UNAUTHORIZED = 401;

	/**
	 * The status code for the {@code "FORBIDDEN"} response, to use in unit test.
	 */
	public static final int FORBIDDEN = 403;

	/**
	 * The status code for the {@code "NOT FOUND"} response, to use in unit test.
	 */
	public static final int NOT_FOUND = 404;

	/**
	 * The status code for the {@code "METHOD NOT ALLOWED"} response, to use in unit test.
	 */
	public static final int METHOD_NOT_ALLOWED = 405;

	/**
	 * The status code for the {@code "NOT ACCEPTABLE"} response, to use in unit test.
	 */
	public static final int NOT_ACCEPTABLE = 406;

	/**
	 * The status code for the {@code "CONFLICT"} response, to use in unit test.
	 */
	public static final int CONFLICT = 409;

	/**
	 * The status code for the {@code "PRE CONDITION FAILED"} response, to use in unit test.
	 */
	public static final int PRE_CONDITION_FAILED = 412;

	/**
	 * The status code for the {@code "UNSUPPORTED MEDIA TYPE"} response, to use in unit test.
	 */
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;

	/**
	 * The status code for the {@code "INTERNAL SERVER ERROR"} response, to use in unit test.
	 */
	public static final int INTERNAL_SERVER_ERROR = 500;

	/**
	 * The status code for the {@code "NOT IMPLEMENTED"} response.
	 */
	public static final int NOT_IMPLEMENTED = 501;

	/**
	 * The range of status for "client errors", to use in unit test.
	 */
	public static final Range CLIENT_ERROR = range(400, 499);

	/**
	 * The range of status for "server errors", to use in unit test.
	 */
	public static final Range SERVER_ERROR = range(500, 599);

	/**
	 * The range of status for success calls, to use in unit test.
	 */
	public static final Range SUCCESS = range(200, 299);

	/**
	 * The range of status for redirection calls, to use in unit test.
	 */
	public static final Range REDIRECTION = range(300, 399);
}
