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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

/**
 * Values of valid X-Content-Type-Options value.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options</a>
 */
public enum ContentTypeOptions implements HeaderValue {

	/**
	 * The only defined value, "nosniff", prevents Internet Explorer and Google Chrome
	 * from MIME-sniffing a response away from the declared content-type.
	 */
	NO_SNIFF("nosniff");

	/**
	 * The parser instance.
	 */
	private static final ContentTypeOptionsParser PARSER = new ContentTypeOptionsParser();

	/**
	 * Get parser for {@link ContentTypeOptions} instances.
	 *
	 * @return The parser.
	 */
	public static ContentTypeOptionsParser parser() {
		return PARSER;
	}

	/**
	 * The value value as specified by official specification.
	 */
	private final String value;

	/**
	 * Create Content-Type value value.
	 *
	 * @param value The value.
	 */
	ContentTypeOptions(String value) {
		this.value = value;
	}

	@Override
	public String serializeValue() {
		return value;
	}

	/**
	 * Get {@link #value}
	 *
	 * @return {@link #value}
	 */
	String getValue() {
		return value;
	}
}
