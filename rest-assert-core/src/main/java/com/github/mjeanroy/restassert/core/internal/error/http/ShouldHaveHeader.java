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

import java.util.List;

/**
 * Error thrown when an http response should contain
 * specific header.
 */
public class ShouldHaveHeader extends AbstractError {

	// Private constructor, use static factory instead
	private ShouldHaveHeader(String message, Object... args) {
		super(message, args);
	}

	/**
	 * Build error.
	 *
	 * @param headerName Expected header name.
	 * @return Error.
	 */
	public static ShouldHaveHeader shouldHaveHeader(String headerName) {
		return new ShouldHaveHeader("Expecting response to have header %s", headerName);
	}

	/**
	 * Build error.
	 *
	 * @param headerName Expected header name.
	 * @param headerValue Expected header value.
	 * @param actualValues Actual header values.
	 * @return Error.
	 */
	public static ShouldHaveHeader shouldHaveHeaderWithValue(String headerName, String headerValue, List<String> actualValues) {
		if (actualValues.size() == 1) {
			return shouldHaveHeaderWithValue(headerName, headerValue, actualValues.get(0));
		}

		return new ShouldHaveHeader("Expecting response to have header %s equal to %s but contains only %s", headerName, headerValue, actualValues);
	}

	/**
	 * Build error.
	 *
	 * @param headerName Expected header name.
	 * @param headerValue Expected header value.
	 * @param actualValue Actual header value.
	 * @return Error.
	 */
	public static ShouldHaveHeader shouldHaveHeaderWithValue(String headerName, String headerValue, String actualValue) {
		return new ShouldHaveHeader("Expecting response to have header %s equal to %s but was %s", headerName, headerValue, actualValue);
	}
}
