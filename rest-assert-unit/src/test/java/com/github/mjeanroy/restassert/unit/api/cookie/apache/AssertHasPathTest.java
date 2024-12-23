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

package com.github.mjeanroy.restassert.unit.api.cookie.apache;

import com.github.mjeanroy.restassert.tests.builders.apache.ApacheHttpCookieBuilder;
import org.apache.http.cookie.Cookie;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.unit.api.cookie.ApacheHttpCookieAssert.assertHasPath;

class AssertHasPathTest extends AbstractApacheHttpCookieTest {

	@Override
	protected void run(Cookie actual) {
		assertHasPath(actual, success().getPath());
	}

	@Override
	protected void run(String message, Cookie actual) {
		assertHasPath(message, actual, success().getPath());
	}

	@Override
	protected Cookie success() {
		return cookie("foo");
	}

	@Override
	protected Cookie failure() {
		String expectedPath = success().getPath();
		String actualPath = expectedPath + "foo";
		return cookie(actualPath);
	}

	@Override
	protected String message() {
		String expected = success().getPath();
		String actual = failure().getPath();
		return "Expecting cookie to have path " + fmt(expected) + " but was " + fmt(actual);
	}

	private Cookie cookie(String path) {
		return new ApacheHttpCookieBuilder().setPath(path).build();
	}
}
