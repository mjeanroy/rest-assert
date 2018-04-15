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

package com.github.mjeanroy.restassert.unit.api.cookie.apache;

import static com.github.mjeanroy.restassert.unit.api.cookie.ApacheHttpCookieAssert.assertHasName;

import com.github.mjeanroy.restassert.tests.builders.apache.ApacheHttpCookieBuilder;
import org.apache.http.cookie.Cookie;

public class AssertHasNameTest extends AbstractApacheHttpCookieTest {

	@Override
	protected void invoke(Cookie actual) {
		assertHasName(actual, success().getName());
	}

	@Override
	protected void invoke(String message, Cookie actual) {
		assertHasName(message, actual, success().getName());
	}

	@Override
	protected Cookie success() {
		return cookie("foo");
	}

	@Override
	protected Cookie failure() {
		final String expectedName = success().getName();
		final String actualName = expectedName + "foo";
		return cookie(actualName);
	}

	@Override
	protected String pattern() {
		return "Expecting cookie to have name %s but was %s";
	}

	@Override
	protected Object[] placeholders() {
		final String expectedName = success().getName();
		final String actualName = failure().getName();
		return new Object[]{
				expectedName, actualName
		};
	}

	private Cookie cookie(String name) {
		return new ApacheHttpCookieBuilder()
				.setName(name)
				.build();
	}
}
