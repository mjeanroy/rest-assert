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

package com.github.mjeanroy.restassert.core.internal.exceptions;

import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderParser;

/**
 * Exception thrown when an {@link HttpHeaderParser} cannot parse
 * header value because of an invalid or a non-authorized value.
 */
@SuppressWarnings("serial")
public class InvalidHeaderValue extends IllegalArgumentException {

	/**
	 * The header name.
	 */
	private final String headerName;

	/**
	 * The invalid header value.
	 */
	private final String headerValue;

	/**
	 * Create the exception.
	 *
	 * @param headerName The header name.
	 * @param headerValue The invalid header value.
	 */
	public InvalidHeaderValue(String headerName, String headerValue) {
		super(createMessage(headerName, headerValue));
		this.headerName = headerName;
		this.headerValue = headerValue;
	}

	/**
	 * Get {@link #headerName}
	 *
	 * @return {@link #headerName}
	 */
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * Get {@link #headerValue}
	 *
	 * @return {@link #headerValue}
	 */
	public String getHeaderValue() {
		return headerValue;
	}

	/**
	 * Create error message.
	 *
	 * @param headerName The header name.
	 * @param headerValue The invalid header value.
	 * @return The error message.
	 */
	private static String createMessage(String headerName, String headerValue) {
		return String.format("%s value '%s' is not a valid one.", headerName, headerValue);
	}
}
