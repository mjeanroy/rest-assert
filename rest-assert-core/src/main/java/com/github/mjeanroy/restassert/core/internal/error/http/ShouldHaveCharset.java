/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

/**
 * Error thrown when an http response should have specific
 * charset.
 */
public final class ShouldHaveCharset extends AbstractError {

	// Private constructor, use static factory instead
	private ShouldHaveCharset(String message, String expectedValue, String actualValue) {
		super(message, expectedValue, actualValue);
	}

	// Private constructor, use static factory instead
	private ShouldHaveCharset(String message) {
		super(message);
	}

	/**
	 * Build error.
	 *
	 * @return Error.
	 */
	public static ShouldHaveCharset shouldHaveCharset() {
		return new ShouldHaveCharset("Expecting response to have defined charset");
	}

	/**
	 * Build error.
	 *
	 * @param expectedCharset Expected charset.
	 * @param actualCharset Actual charset.
	 * @return Error.
	 */
	public static ShouldHaveCharset shouldHaveCharset(String expectedCharset, String actualCharset) {
		return new ShouldHaveCharset("Expecting response to have charset %s", expectedCharset, actualCharset);
	}
}
