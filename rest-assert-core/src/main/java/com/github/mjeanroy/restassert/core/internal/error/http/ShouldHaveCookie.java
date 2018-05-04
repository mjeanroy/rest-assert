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

package com.github.mjeanroy.restassert.core.internal.error.http;

import com.github.mjeanroy.restassert.core.internal.error.AbstractError;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;

/**
 * Error thrown when an http response should contains
 * expected cookie.
 */
public class ShouldHaveCookie extends AbstractError {

	// Private constructor, use static factory instead
	private ShouldHaveCookie(String message, Object... args) {
		super(message, args);
	}

	/**
	 * Build error.
	 *
	 * @param name Cookie name.
	 * @return Error.
	 */
	public static ShouldHaveCookie shouldHaveCookie(String name) {
		return new ShouldHaveCookie("Expecting http response to contains cookie with name %s", name);
	}

	/**
	 * Build error.
	 *
	 * @param name Cookie name.
	 * @param value Cookie value.
	 * @return Error.
	 */
	public static ShouldHaveCookie shouldHaveCookie(String name, String value) {
		return new ShouldHaveCookie("Expecting http response to contains cookie with name %s and value %s", name, value);
	}

	/**
	 * Build error.
	 *
	 * @param cookie Cookie.
	 * @return Error.
	 */
	public static ShouldHaveCookie shouldHaveCookie(Cookie cookie) {
		return new ShouldHaveCookie("Expecting http response to contains cookie %s", cookie);
	}

	/**
	 * Build error.
	 *
	 * @param name Cookie name.
	 * @return Error.
	 */
	public static ShouldHaveCookie shouldNotHaveCookie(String name) {
		return new ShouldHaveCookie("Expecting http response not to contains cookie with name %s", name);
	}

	/**
	 * Build error.
	 *
	 * @return Error.
	 */
	public static ShouldHaveCookie shouldNotHaveCookie() {
		return new ShouldHaveCookie("Expecting http response not to contains cookies");
	}
}
