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

package com.github.mjeanroy.restassert.hamcrest.api.http;

import com.github.mjeanroy.restassert.test.data.Range;
import com.github.mjeanroy.restassert.test.tests.TestInvocation;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpResponseStatusBetweenMatcherTest<T> extends AbstractHttpResponseMatcherTest<T> {

	@Test
	public void it_should_pass_with_status_in_bounds() {
		final Range range = getRange();
		for (int i = range.getStart(); i <= range.getEnd(); i++) {
			run(newHttpResponse(i));
		}
	}

	@Test
	public void it_should_fail_with_response_not_in_bounds() {
		doTestWithDefaultMessage(new TestInvocation<Integer>() {
			@Override
			public void invokeTest(Integer status) {
				run(newHttpResponse(status));
			}
		});
	}

	private void doTestWithDefaultMessage(final TestInvocation<Integer> invocation) {
		final Range rang = getRange();
		final int start = rang.getStart();
		final int end = rang.getEnd();

		for (int i = 100; i <= 599; i++) {
			if (i >= start && i <= end) {
				continue;
			}

			final int status = i;
			final String expectation = buildExpectationMessage(start, end);
			final String mismatch = buildMismatchMessage(status);
			final String message = generateHamcrestErrorMessage(expectation, mismatch);
			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invocation.invokeTest(status);
				}
			});
		}
	}

	protected abstract Range getRange();

	private T newHttpResponse(int status) {
		return getBuilder().setStatus(status).build();
	}

	private static String buildExpectationMessage(int start, int end) {
		return String.format("Expecting status code to be between %s and %s", start, end);
	}

	private static String buildMismatchMessage(int status) {
		return String.format("was %s", status);
	}
}
