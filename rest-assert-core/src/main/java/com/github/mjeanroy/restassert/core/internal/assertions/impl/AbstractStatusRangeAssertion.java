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

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.isGreaterThan;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.isPositive;

/**
 * Abstract assertion with range of status code.
 */
abstract class AbstractStatusRangeAssertion implements HttpResponseAssertion {

	/**
	 * Lower bound.
	 */
	final int start;

	/**
	 * Upper bound.
	 */
	final int end;

	/**
	 * Create assertion.
	 *
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @throws IllegalArgumentException If {@code start} or {@code end} are negative or if {@code start} is less than or equals to {@code end}.
	 */
	AbstractStatusRangeAssertion(int start, int end) {
		isPositive(start, "Http status code must be positive");
		isPositive(end, "Http status code must be positive");
		isGreaterThan(end, start, "Lower bound must be strictly less than upper bound");

		this.start = start;
		this.end = end;
	}
}
