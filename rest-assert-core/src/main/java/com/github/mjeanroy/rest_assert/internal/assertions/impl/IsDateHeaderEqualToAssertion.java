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

package com.github.mjeanroy.rest_assert.internal.assertions.impl;

import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.utils.Mapper;

import java.util.Date;
import java.util.List;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.utils.DateUtils.formatHttpDate;
import static com.github.mjeanroy.rest_assert.utils.DateUtils.parseHttpDate;
import static com.github.mjeanroy.rest_assert.utils.Utils.map;
import static com.github.mjeanroy.rest_assert.utils.Utils.notNull;

/**
 * Check that http response has at least one header with
 * expected name.
 */
public class IsDateHeaderEqualToAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Expected header value.
	 */
	private final String value;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	public IsDateHeaderEqualToAssertion(String name, Date value) {
		super(name);

		notNull(value, "Header value must not be null");
		this.value = formatHttpDate(value);
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		List<String> actualDates = map(values, httpDateMapper);
		return actualDates.contains(value) ?
				success() :
				failure(shouldHaveHeaderWithValue(name, value, values));
	}

	private static final Mapper<String, String> httpDateMapper = new Mapper<String, String>() {
		@Override
		public String apply(String input) {
			Date date = parseHttpDate(input);
			return formatHttpDate(date);
		}
	};
}
