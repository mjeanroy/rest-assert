/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

import com.github.mjeanroy.rest_assert.api.http.ApacheHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.AsyncHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.GoogleHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.HttpAssert;
import com.github.mjeanroy.rest_assert.api.http.OkHttpAssert;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.mocks.CookieMockBuilder;
import com.ning.http.client.Response;

public class HttpAssert_assertHasCookie_withNameCookie_Test extends AbstractHasCookieTest {

	private static final String NAME = "JSESSIONID";
	private static final String VALUE = "12345";
	private static final Cookie COOKIE = new CookieMockBuilder()
			.setName(NAME)
			.setValue(VALUE)
			.build();

	@Override
	protected Cookie cookie() {
		return COOKIE;
	}

	@Override
	protected String buildErrorMessage() {
		return String.format("Expecting http response to contains cookie %s", COOKIE);
	}

	@Override
	protected void invoke(HttpResponse actual) {
		HttpAssert.assertHasCookie(actual, COOKIE);
	}

	@Override
	protected void invoke(String message, HttpResponse actual) {
		HttpAssert.assertHasCookie(message, actual, COOKIE);
	}

	@Override
	protected void invoke(Response actual) {
		AsyncHttpAssert.assertHasCookie(actual, COOKIE);
	}

	@Override
	protected void invoke(String message, Response actual) {
		AsyncHttpAssert.assertHasCookie(message, actual, COOKIE);
	}

	@Override
	protected void invoke(okhttp3.Response actual) {
		OkHttpAssert.assertHasCookie(actual, COOKIE);
	}

	@Override
	protected void invoke(String message, okhttp3.Response actual) {
		OkHttpAssert.assertHasCookie(message, actual, COOKIE);
	}

	@Override
	protected void invoke(org.apache.http.HttpResponse actual) {
		ApacheHttpAssert.assertHasCookie(actual, COOKIE);
	}

	@Override
	protected void invoke(String message, org.apache.http.HttpResponse actual) {
		ApacheHttpAssert.assertHasCookie(message, actual, COOKIE);
	}

	@Override
	protected void invoke(com.google.api.client.http.HttpResponse actual) {
		GoogleHttpAssert.assertHasCookie(actual, COOKIE);
	}

	@Override
	protected void invoke(String message, com.google.api.client.http.HttpResponse actual) {
		GoogleHttpAssert.assertHasCookie(message, actual, COOKIE);
	}
}
