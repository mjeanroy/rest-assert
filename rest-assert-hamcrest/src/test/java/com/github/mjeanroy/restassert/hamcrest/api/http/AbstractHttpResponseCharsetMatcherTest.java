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

import com.github.mjeanroy.restassert.test.tests.TestInvocation;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpResponseCharsetMatcherTest<T> extends AbstractHttpResponseMatcherTest<T> {

	@Test
	public void it_should_pass_with_expected_mime_type() {
		run(newHttpResponse(getCharset()));
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(new TestInvocation<String>() {
			@Override
			public void invokeTest(String charset) {
				run(newHttpResponse(charset));
			}
		});
	}

	private void invokeFailure(final TestInvocation<String> invocation) {
		final String expectedCharset = getCharset();
		final String actualCharset = expectedCharset + "foo";
		final String message = generateHamcrestErrorMessage(
			buildExpectationMessage(expectedCharset),
			buildMismatchMessage(actualCharset)
		);

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(actualCharset);
			}
		});
	}

	protected abstract String getCharset();

	private String buildExpectationMessage(String expectedCharset) {
		return String.format("Expecting response to have charset %s", expectedCharset);
	}

	private String buildMismatchMessage(String actualCharset) {
		return String.format("was %s", actualCharset);
	}

	private T newHttpResponse(String charset) {
		String contentType = String.format("application/json;charset=%s", charset);
		return getBuilder().addHeader("Content-Type", contentType).build();
	}
}
