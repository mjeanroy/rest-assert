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

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpResponseHasCookieMatcherTest<T> extends AbstractHttpResponseMatcherTest<T> {

	@Test
	public void core_it_should_pass_with_expected_cookie() {
		run(newHttpResponse(cookie()));
	}

	@Test
	public void core_it_should_fail_with_response_without_expected_cookie() {
		doTest((cookie) -> run(newHttpResponse(cookie)));
	}

	private void doTest(Consumer<Cookie> testFn) {
		Cookie cookie = new CookieBuilder().setName("foo").setValue("bar").build();
		String message = generateHamcrestErrorMessage(
			buildExpectationMessage(),
			buildMismatchMessage()
		);

		assertFailure(message, () -> testFn.accept(cookie));
	}

	protected abstract Cookie cookie();

	protected abstract String buildExpectationMessage();

	protected abstract String buildMismatchMessage();

	private T newHttpResponse(Cookie cookie) {
		return getBuilder().addCookie(cookie).build();
	}
}
