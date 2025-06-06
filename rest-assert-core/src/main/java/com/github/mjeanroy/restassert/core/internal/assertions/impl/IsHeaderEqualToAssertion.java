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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;

/**
 * Check that http response has at least one header with
 * expected name.
 */
public class IsHeaderEqualToAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(IsHeaderEqualToAssertion.class);

	/**
	 * Expected header value.
	 */
	private final String value;

	/**
	 * If header value comparison should be case insensitive.
	 */
	private final boolean caseInsensitive;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 * @param value Header value (will be serialized as a string).
	 * @param caseInsensitive If assertion should be case insensitive.
	 * @throws NullPointerException If {@code name} or {value} are {@code null}
	 */
	public IsHeaderEqualToAssertion(String name, String value, boolean caseInsensitive) {
		super(name);
		this.value = notNull(value, "Header value must not be null");
		this.caseInsensitive = caseInsensitive;
	}

	@Override
	AssertionResult doAssertion(List<String> actualValues) {
		log.debug("Comparing '{}' with '{}'", value, actualValues);
		return contains(actualValues) ? success() : failure(shouldHaveHeaderWithValue(name, value, actualValues));
	}

	/**
	 * Check if expected value is in actual list.
	 *
	 * @param actualValues Actual values.
	 * @return {@code true} if {@link #value} is in {@code actualValues}, {@code false} otherwise.
	 */
	private boolean contains(List<String> actualValues) {
		return caseInsensitive ? containsCaseInsensitive(actualValues) : containsNonCaseInsensitive(actualValues);
	}

	/**
	 * Check if expected value is in actual list, using a <strong>>case-sensitive</strong comparison.
	 *
	 * @param actualValues Actual values.
	 * @return {@code true} if {@link #value} is in {@code actualValues}, {@code false} otherwise.
	 */
	private boolean containsNonCaseInsensitive(List<String> actualValues) {
		return actualValues.contains(value);
	}

	/**
	 * Check if expected value is in actual list, using a <strong>case-insensitive</strong> comparison.
	 *
	 * @param actualValues Actual values.
	 * @return {@code true} if {@link #value} is in {@code actualValues}, {@code false} otherwise.
	 */
	private boolean containsCaseInsensitive(List<String> actualValues) {
		return actualValues.stream().anyMatch(value::equalsIgnoreCase);
	}
}
