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

package com.github.mjeanroy.restassert.unit.api.http.ning.cookie;

import com.github.mjeanroy.restassert.unit.api.http.NingHttpAssert;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.ning.http.client.Response;

public class AssertHasCookieWithNameCookieTest extends AbstractNingHttpHasCookieTest {

	private static final String NAME = "JSESSIONID";
	private static final String VALUE = "12345";
	private static final Cookie COOKIE = new CookieBuilder().setName(NAME).setValue(VALUE).build();

	@Override
	protected Cookie cookie() {
		return COOKIE;
	}

	@Override
	protected String buildErrorMessage() {
		return String.format("Expecting http response to contains cookie %s", COOKIE);
	}

	@Override
	protected void invoke(Response actual) {
		NingHttpAssert.assertHasCookie(actual, COOKIE);
	}

	@Override
	protected void invoke(String message, Response actual) {
		NingHttpAssert.assertHasCookie(message, actual, COOKIE);
	}
}
