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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.data.HeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.common.Collections.Predicate;

import java.util.List;
import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.some;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * Check that http response has at least one header matching
 * expected header expected object.
 */
public class IsHeaderMatchingAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Expected header expected.
	 */
	private final HeaderValue expected;

	/**
	 * The parser that will be used to build comparison.
	 */
	private final HeaderParser parser;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	public IsHeaderMatchingAssertion(String name, HeaderValue expected, HeaderParser parser) {
		super(name);
		this.expected = notNull(expected, "Header expected value must not be null");
		this.parser = notNull(parser, "Header parser must not be null");
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		boolean found = some(values, new HeaderPredicate(expected, parser));
		return found ? success() : failure(shouldHaveHeaderWithValue(name, expected.serializeValue(), values));
	}

	private static class HeaderPredicate implements Predicate<String> {
		private final HeaderValue expected;
		private final HeaderParser parser;

		private HeaderPredicate(HeaderValue expected, HeaderParser parser) {
			this.expected = expected;
			this.parser = parser;
		}

		@Override
		public boolean apply(String input) {
			return Objects.equals(expected, parser.parse(input));
		}
	}
}
