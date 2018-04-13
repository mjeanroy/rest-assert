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

package com.github.mjeanroy.restassert.api.http.charsets;

import com.github.mjeanroy.restassert.api.AbstractAssertTest;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHttpAssertCharsetTest<T> extends AbstractAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_with_expected_mime_type() {
		invoke(newHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newHttpResponse(getCharset()));
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(newHttpResponse(charset));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(charset));
			}
		});
	}

	private void invokeFailure(String msg, final Invocation invocation) {
		final String expectedCharset = getCharset();
		final String actualCharset = expectedCharset + "foo";
		final String message = firstNonNull(msg, buildErrorMessage(expectedCharset, actualCharset));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(actualCharset);
			}
		});
	}

	private String buildErrorMessage(String expectedCharset, String actualCharset) {
		return String.format("Expecting response to have charset %s but was %s", expectedCharset, actualCharset);
	}

	protected abstract T newHttpResponse(String charset);

	protected abstract String getCharset();

	interface Invocation {
		void invokeTest(String charset);
	}
}
