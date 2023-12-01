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

import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.test.tests.TestInvocation;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.common.Objects.firstNonNull;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpHeaderEqualToTest<T> extends AbstractHttpAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	void it_should_pass_with_expected_header() {
		Header header = getHeader();
		run(newHttpResponse(header));
		run(CUSTOM_MESSAGE, newHttpResponse(header));
	}

	@Test
	void it_should_fail_if_response_does_not_contain_header_with_expected_value() {
		doTest(null, new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				run(newHttpResponse(header));
			}
		});
	}

	@Test
	void it_should_fail_with_custom_message_if_response_does_not_contain_header_with_expected_value() {
		doTest(CUSTOM_MESSAGE, new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				run(CUSTOM_MESSAGE, newHttpResponse(header));
			}
		});
	}

	private void doTest(String msg, final TestInvocation<Header> invocation) {
		final Header expectedHeader = getHeader();
		final String expectedName = expectedHeader.getName();
		final String expectedValue = expectedHeader.getValue();
		final String actualValue = failValue();
		final Header header = header(expectedName, actualValue);
		final String message = firstNonNull(msg, buildErrorMessage(expectedName, expectedValue, actualValue));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(header);
			}
		});
	}

	protected abstract Header getHeader();

	protected String failValue() {
		return getHeader().getValue() + "foo";
	}

	private T newHttpResponse(Header header) {
		return getBuilder().addHeader(header).build();
	}

	private static String buildErrorMessage(String expectedName, String expectedValue, String actualValue) {
		return String.format("Expecting response to have header %s equal to %s but was %s", expectedName, expectedValue, actualValue);
	}
}
