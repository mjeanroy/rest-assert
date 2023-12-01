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

package com.github.mjeanroy.restassert.unit.api.http;

import com.github.mjeanroy.restassert.test.tests.TestInvocation;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.common.Objects.firstNonNull;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpStatusTest<T> extends AbstractHttpAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	void it_should_pass_with_correct_status() {
		run(newHttpResponse(status()));
		run(CUSTOM_MESSAGE, newHttpResponse(status()));
	}

	@Test
	void it_should_fail_with_response_different_than_expected_status() {
		doTest(null, new TestInvocation<Integer>() {
			@Override
			public void invokeTest(Integer status) {
				run(newHttpResponse(status));
			}
		});
	}

	@Test
	void it_should_pass_with_custom_message_with_response_different_than_200() {
		doTest(CUSTOM_MESSAGE, new TestInvocation<Integer>() {
			@Override
			public void invokeTest(Integer status) {
				run(CUSTOM_MESSAGE, newHttpResponse(status));
			}
		});
	}

	private void doTest(String msg, final TestInvocation<Integer> invocation) {
		final int expectedStatus = status();
		final int status = expectedStatus + 1;
		final String message = firstNonNull(msg, buildErrorMessage(expectedStatus, status));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(status);
			}
		});
	}

	protected abstract int status();

	private T newHttpResponse(int status) {
		return getBuilder().setStatus(status).build();
	}

	private static String buildErrorMessage(int expectedStatus, int status) {
		return String.format("Expecting status code to be %s but was %s", expectedStatus, status);
	}
}
