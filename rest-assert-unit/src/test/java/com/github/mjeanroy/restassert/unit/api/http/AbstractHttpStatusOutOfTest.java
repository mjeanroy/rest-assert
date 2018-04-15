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

package com.github.mjeanroy.restassert.unit.api.http;

import com.github.mjeanroy.restassert.unit.api.AbstractAssertTest;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHttpStatusOutOfTest<T> extends AbstractAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	// == Core HTTP Response

	@Test
	public void it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() || i > end()) {
				invoke(newHttpResponse(i));
				invoke(CUSTOM_MESSAGE, newHttpResponse(i));
			}
		}
	}

	@Test
	public void it_should_fail() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newHttpResponse(status));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(status));
			}
		});
	}

	private void doTest(String msg, final Invocation invocation) {
		final int start = start();
		final int end = end();

		for (int i = start; i <= end; i++) {
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

	private String buildErrorMessage(int start, int end, int status) {
		return String.format("Expecting status code to be out of %s and %s but was %s", start, end, status);
	}

	protected abstract T newHttpResponse(int status);

	protected abstract int start();

	protected abstract int end();

	private interface Invocation {
		void invokeTest(int status);
	}
}
