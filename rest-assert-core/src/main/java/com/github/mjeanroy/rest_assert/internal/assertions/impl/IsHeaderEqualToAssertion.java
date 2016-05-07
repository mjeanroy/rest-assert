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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.utils.PreConditions.notNull;

/**
 * Check that http response has at least one header with
 * expected name.
 */
public class IsHeaderEqualToAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(IsHeaderEqualToAssertion.class);

	/**
	 * Expected header value.
	 */
	private final String value;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	public IsHeaderEqualToAssertion(String name, String value) {
		super(name);
		this.value = notNull(value, "Header value must not be null");
	}

	@Override
	AssertionResult doAssertion(List<String> actualValues) {
		log.debug("Comparing '{}' with '{}'", value, actualValues);
		return actualValues.contains(value) ?
				success() :
				failure(shouldHaveHeaderWithValue(name, value, actualValues));
	}
}
