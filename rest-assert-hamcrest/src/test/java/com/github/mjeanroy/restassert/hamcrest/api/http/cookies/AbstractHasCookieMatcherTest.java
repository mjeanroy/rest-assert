/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.hamcrest.api.http.cookies;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.hamcrest.api.http.AbstractHttpMatcherTest;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractHasCookieMatcherTest extends AbstractHttpMatcherTest<Cookie> {

	abstract Cookie cookie();

	@Override
	protected final Stream<Cookie> testInputs() {
		return Stream.of(cookie());
	}

	@Override
	protected final void doTest(Consumer<Cookie> testFn) {
		Cookie cookie = new MockCookieBuilder().setName("foo").setValue("bar").build();
		String message = generateHamcrestErrorMessage(
			buildExpectationMessage(),
			buildMismatchMessage()
		);

		assertFailure(message, () ->
			testFn.accept(cookie)
		);
	}

	@Override
	protected final <T> void setupHttpResponse(
		HttpResponseBuilder<T> httpResponseBuilder,
		Cookie cookie
	) {
		httpResponseBuilder.addCookie(cookie);
	}

	abstract String buildExpectationMessage();

	abstract String buildMismatchMessage();
}
