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
import com.github.mjeanroy.rest_assert.internal.data.HttpHeader;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

import java.util.List;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveSingleHeader.shouldHaveSingleHeader;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;

abstract class AbstractHeaderEqualToAssertion extends HasHeaderAssertion implements HttpResponseAssertion {

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
			return r1;
		}

		// Extract values.
		List<String> actualValues = httpResponse.getHeader(name);

		// Check if header should not appear more than once.
		// If a single value header appear with more than one value, then this is
		// probably a bug in the generated response, fail fast.
		HttpHeader header = HttpHeader.find(name);
		if (header != null && header.isSingle() && actualValues.size() > 1) {
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
