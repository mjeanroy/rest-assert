/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.internal.common.Dates;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Dates.formatHttpDate;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;

/**
 * Check that http response has at least one header with
 * expected date value.
 */
public class IsDateHeaderEqualToAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(IsDateHeaderEqualToAssertion.class);

	/**
	 * Expected header value.
	 */
	private final String value;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 * @param value Header value (will be serialized as a string).
	 */
	public IsDateHeaderEqualToAssertion(String name, Date value) {
		super(name);

		notNull(value, "Header value must not be null");
		this.value = formatHttpDate(value);
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		log.debug("Extracting and parsing date values from: {}", values);
		List<String> actualDates = values.stream()
			.map(Dates::parseHttpDate)
			.map(Dates::formatHttpDate)
			.collect(Collectors.toList());

		log.debug("-> Following dates extracted: {}", actualDates);
		log.debug("-> Try to find: {}", value);
		return actualDates.contains(value) ?
				success() :
				failure(shouldHaveHeaderWithValue(name, value, values));
	}
}
