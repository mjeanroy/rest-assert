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

package com.github.mjeanroy.restassert.api.http.between;

import com.github.mjeanroy.restassert.api.AbstractAssertTest;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHttpStatusBetweenTest<T> extends AbstractAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_with_status_in_bounds() {
		for (int i = start(); i <= end(); i++) {
			invoke(newHttpResponse(i));
			invoke(CUSTOM_MESSAGE, newHttpResponse(i));
		}
	}

	@Test
	public void it_should_fail_with_response_not_in_bounds() {
		doTestWithDefaultMessage(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newHttpResponse(status));
			}
		});
	}

	@Test
	public void it_should_fail_with_response_not_in_bounds_with_custom_message() {
		doTestWithDefaultMessage(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(status));
			}
		});
	}

	private void doTestWithDefaultMessage(String msg, final Invocation invocation) {
		final int start = start();
		final int end = end();

		for (int i = 100; i <= 599; i++) {
			if (i >= start && i <= end) {
				continue;
			}

			final int status = i;
			final String message = firstNonNull(msg, buildErrorMessage(start, end, status));
			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invocation.invokeTest(status);
				}
			});
		}
	}

	// == Create target HTTP Response

	protected abstract T newHttpResponse(int status);

	private String buildErrorMessage(int start, int end, int status) {
		return String.format("Expecting status code to be between %s and %s but was %s", start, end, status);
	}

	// == To implement

	protected abstract int start();

	protected abstract int end();

	interface Invocation {
		void invokeTest(int status);
	}
}
