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

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveSingleHeader.shouldHaveSingleHeader;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;

import java.util.List;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeader;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

abstract class AbstractHeaderEqualToAssertion extends HasHeaderAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(AbstractHeaderEqualToAssertion.class);

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	AbstractHeaderEqualToAssertion(String name) {
		super(name);
	}

	@Override
	public AssertionResult handle(HttpResponse httpResponse) {
		AssertionResult r1 = super.handle(httpResponse);

		// Header is not set, fail fast.
		if (r1.isFailure()) {
			log.debug("Cannot find header: '{}', fail", name);
			return r1;
		}

		// Extract values.
		List<String> actualValues = httpResponse.getHeader(name);
		log.debug("-> Found header values: {}", actualValues);

		// Check if header should not appear more than once.
		// If a single value header appear with more than one value, then this is
		// probably a bug in the generated response, fail fast.
		HttpHeader header = HttpHeader.find(name);
		if (header != null && header.isSingle() && actualValues.size() > 1) {
			log.debug("Header {} should appear once, but found {} values, fail", name, actualValues.size());
			return failure(shouldHaveSingleHeader(name, actualValues));
		}

		return doAssertion(actualValues);
	}

	/**
	 * Do assertion on actual header values.
	 *
	 * @param values Header values.
	 * @return Assertion result.
	 */
	abstract AssertionResult doAssertion(List<String> values);
}
