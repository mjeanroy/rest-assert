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

package com.github.mjeanroy.restassert.internal.assertions.impl;

import java.util.List;

import com.github.mjeanroy.restassert.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.data.HeaderValue;
import com.github.mjeanroy.restassert.utils.Predicate;

import static com.github.mjeanroy.restassert.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.restassert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.utils.PreConditions.notNull;
import static com.github.mjeanroy.restassert.utils.Utils.some;

/**
 * Check that http response has at least one header matching
 * expected header value object.
 */
public class IsHeaderMatchingAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Expected header value.
	 */
	private final HeaderValue value;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	public IsHeaderMatchingAssertion(String name, HeaderValue value) {
		super(name);
		this.value = notNull(value, "Header value must not be null");
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		boolean found = some(values, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return value.match(input);
			}
		});

		return found ?
				success() :
				failure(shouldHaveHeaderWithValue(name, value.value(), values));
	}
}
