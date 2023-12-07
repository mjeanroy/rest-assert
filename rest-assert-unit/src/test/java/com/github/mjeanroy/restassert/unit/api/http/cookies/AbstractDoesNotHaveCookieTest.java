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

package com.github.mjeanroy.restassert.unit.api.http.cookies;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.unit.api.http.AbstractHttpTest;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.randomString;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractDoesNotHaveCookieTest extends AbstractHttpTest<Cookie> {

	abstract Cookie cookie();

	@Override
	protected Stream<Cookie> testInputs() {
		return Stream.of(randomCookie());
	}

	@Override
	protected void runTestFailure(String msg, Consumer<Cookie> testFn) {
		Cookie cookie = cookie();
		String message = msg == null ? defaultErrorMessage() : msg;
		assertFailure(message, () ->
			testFn.accept(cookie)
		);
	}

	@Override
	protected final <T> void setupHttpResponse(HttpResponseBuilder<T> builder, Cookie cookie) {
		builder.addCookie(cookie);
	}

	abstract String defaultErrorMessage();

	Cookie randomCookie() {
		return new MockCookieBuilder().setName(randomString()).setValue(randomString()).build();
	}
}
