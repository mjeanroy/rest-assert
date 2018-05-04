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

package com.github.mjeanroy.restassert.tests.builders;

import com.github.mjeanroy.restassert.core.internal.data.HeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder used to create mock instance of {@link HeaderValue} class.
 */
public class HeaderParserBuilder {

	/**
	 * Pair of {@link String} to {@link HeaderValue}.
	 */
	private final Map<String, HeaderValue> pairs;

	/**
	 * Create builder.
	 */
	public HeaderParserBuilder() {
		this.pairs = new LinkedHashMap<>();
	}

	/**
	 * Add new mapping.
	 *
	 * @param value Raw value.
	 * @param headerValue Header value.
	 * @return Current builder.
	 */
	public HeaderParserBuilder add(String value, HeaderValue headerValue) {
		this.pairs.put(value, headerValue);
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public HeaderParser build() {
		HeaderParser parser = mock(HeaderParser.class);
		for (Map.Entry<String, HeaderValue> entry : pairs.entrySet()) {
			when(parser.parse(entry.getKey())).thenReturn(entry.getValue());
		}

		return parser;
	}
}
