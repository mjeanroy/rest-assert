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

package com.github.mjeanroy.rest_assert.tests.mocks;

import java.util.LinkedHashSet;
import java.util.Set;

import com.github.mjeanroy.rest_assert.internal.data.HeaderValue;

import static java.util.Collections.addAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder used to create mock instance of {@link HeaderValue} class.
 */
public class HeaderValueMockBuilder {

	/**
	 * String value of header.
	 */
	private String value;

	/**
	 * String value that are strictly equivalent to current value.
	 */
	private final Set<String> matches;

	/**
	 * Create builder.
	 */
	public HeaderValueMockBuilder() {
		this.matches = new LinkedHashSet<>();
	}

	/**
	 * Set {@link #value}.
	 *
	 * @param value New {@link #value}.
	 * @return Current builder.
	 */
	public HeaderValueMockBuilder setValue(String value) {
		this.value = value;
		this.matches.add(value);
		return this;
	}

	/**
	 * Add new matches.
	 *
	 * @param match First match, required.
	 * @param other Other, optional, matches.
	 * @return Current builder.
	 */
	public HeaderValueMockBuilder addMatch(String match, String... other) {
		matches.add(match);
		addAll(matches, match);
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public HeaderValue build() {
		HeaderValue hValue = mock(HeaderValue.class);
		when(hValue.value()).thenReturn(value);

		for (String match : matches) {
			when(hValue.match(match)).thenReturn(true);
		}

		return hValue;
	}
}
