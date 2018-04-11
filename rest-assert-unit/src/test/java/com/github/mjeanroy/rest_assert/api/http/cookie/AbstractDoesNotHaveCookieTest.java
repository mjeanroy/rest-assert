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

package com.github.mjeanroy.rest_assert.api.http.cookie;

import com.github.mjeanroy.rest_assert.api.AbstractAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.mocks.CookieMockBuilder;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractDoesNotHaveCookieTest<T> extends AbstractAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_without_cookies() {
		invoke(newHttpResponse(fakeCookie()));
		invoke(CUSTOM_MESSAGE, newHttpResponse(fakeCookie()));
	}

	@Test
	public void it_should_fail_with_response_with_cookie() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newHttpResponse(cookie));
			}
		});
	}

	@Test
	public void it_should_pass_with_custom_message_with_response_with_cookie() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(cookie));
			}
		});
	}

	private void doTest(String msg, final Invocation invocation) {
		final Cookie cookie = cookie();
		final String message = firstNonNull(msg, buildErrorMessage());
		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(cookie);
			}
		});
	}

	protected abstract Cookie cookie();

	protected abstract String buildErrorMessage();

	protected Cookie fakeCookie() {
		return new CookieMockBuilder().setName("foo").setValue("bar").build();
	}

	protected abstract T newHttpResponse(Cookie cookie);

	interface Invocation {
		void invokeTest(Cookie cookie);
	}
}
