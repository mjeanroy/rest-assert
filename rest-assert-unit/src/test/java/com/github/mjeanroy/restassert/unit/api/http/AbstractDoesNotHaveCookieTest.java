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

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static com.github.mjeanroy.restassert.core.internal.common.Objects.firstNonNull;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractDoesNotHaveCookieTest<T> extends AbstractHttpAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	void it_should_pass_without_cookies() {
		run(newHttpResponse(fakeCookie()));
		run(CUSTOM_MESSAGE, newHttpResponse(fakeCookie()));
	}

	@Test
	void it_should_fail_with_response_with_cookie() {
		doTest(null, (cookie) -> run(newHttpResponse(cookie)));
	}

	@Test
	void it_should_pass_with_custom_message_with_response_with_cookie() {
		doTest(CUSTOM_MESSAGE, (cookie) -> run(CUSTOM_MESSAGE, newHttpResponse(cookie)));
	}

	private void doTest(String msg, Consumer<Cookie> testFn) {
		Cookie cookie = cookie();
		String message = firstNonNull(msg, buildErrorMessage());
		assertFailure(message, () -> testFn.accept(cookie));
	}

	protected abstract Cookie cookie();

	protected abstract String buildErrorMessage();

	protected Cookie fakeCookie() {
		return new CookieBuilder().setName("foo").setValue("bar").build();
	}

	private T newHttpResponse(Cookie cookie) {
		HttpResponseBuilder<T> builder = getBuilder();
		if (cookie != null) {
			builder.addCookie(cookie);
		}

		return builder.build();
	}
}
