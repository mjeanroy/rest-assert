/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.data;

/**
 * Values of valid XSS protection header.
 */
public enum XssProtection implements HeaderValue {

	/**
	 * Disables the XSS Protections offered by the user-agent.
	 */
	DISABLE("0"),

	/**
	 * Enables the XSS Protections.
	 */
	ENABLE("1"),

	/**
	 * Enables XSS protections and instructs the user-agent to block the response in the
	 * event that script has been inserted from user input, instead of sanitizing.
	 */
	ENABLE_BLOCK("1; mode=block");

	private final String header;

	XssProtection(String header) {
		this.header = header;
	}

	@Override
	public String value() {
		return header;
	}

	@Override
	public boolean match(String actualValue) {
		return header.equals(actualValue);
	}
}
