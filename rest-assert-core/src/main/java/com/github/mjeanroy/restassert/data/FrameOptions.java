/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.data;

/**
 * Values of valid X-Frame-Options header.
 * Specification: https://tools.ietf.org/html/rfc7034
 *
 * Important: values are case-insensitive!
 */
public enum FrameOptions implements HeaderValue {

	/**
	 * A browser receiving content with this header MUST NOT display
	 * this content in any frame.
	 */
	DENY("DENY"),

	/**
	 * A browser receiving content with this header MUST NOT display
	 * this content in any frame from a page of different origin than
	 * the content itself.
	 *
	 * If a browser or plugin can not reliably determine whether the
	 * origin of the content and the frame have the same origin, this
	 * MUST be treated as "DENY".
	 */
	SAME_ORIGIN("SAMEORIGIN"),

	/**
	 * A browser receiving content with this header MUST NOT display
	 * this content in any frame from a page of different origin than
	 * the listed origin.  While this can expose the page to risks by
	 * the trusted origin, in some cases it may be necessary to use
	 * content from other domains.
	 */
	ALLOW_FROM("ALLOW-FROM") {
		@Override
		public boolean match(String actualValue) {
			return actualValue.toLowerCase().startsWith(value().toLowerCase());
		}
	};

	private final String header;

	FrameOptions(String header) {
		this.header = header;
	}

	@Override
	public String value() {
		return header;
	}

	@Override
	public boolean match(String actualValue) {
		return header.equalsIgnoreCase(actualValue);
	}
}
