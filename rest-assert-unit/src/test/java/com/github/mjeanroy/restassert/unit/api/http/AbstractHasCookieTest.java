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

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.Function;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.github.mjeanroy.restassert.unit.api.TestInvocation;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHasCookieTest<T> extends AbstractHttpAssertTest<T> {

	/**
	 * The custom message used as first parameter when optional message
	 * is specified in assertion.
	 */
	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void core_it_should_pass_with_expected_cookie() {
		invoke(newHttpResponse(cookie()));
		invoke(CUSTOM_MESSAGE, newHttpResponse(cookie()));
	}

	@Test
	public void core_it_should_fail_with_response_without_expected_cookie() {
		doTest(null, new TestInvocation<Cookie>() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newHttpResponse(cookie));
			}
		});
	}

	@Test
	public void core_it_should_pass_with_custom_message_with_response_without_expected_cookie() {
		doTest(CUSTOM_MESSAGE, new TestInvocation<Cookie>() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(cookie));
			}
		});
	}

	/**
	 * Invoke test with a fail test case.
	 *
	 * @param msg The custom error message, optional and may be {@code null}.
	 * @param invocation The test invocation.
	 */
	private void doTest(String msg, final TestInvocation<Cookie> invocation) {
		final Cookie cookie = new CookieBuilder().setName("foo").setValue("bar").build();
		final String message = firstNonNull(msg, buildErrorMessage());

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(cookie);
			}
		});
	}

	/**
	 * Create cookie to be tested.
	 *
	 * @return The cookie to be tested.
	 */
	protected abstract Cookie cookie();

	/**
	 * Create the default error message returned with a failed test.
	 *
	 * @return Default error message.
	 */
	protected abstract String buildErrorMessage();

	/**
	 * Get the HTTP response to be tested.
	 *
	 * @param cookie Expected cookie.
	 * @return The HTTP response.
	 */
	private T newHttpResponse(Cookie cookie) {
		return getBuilder().addCookie(cookie).build();
	}
}
