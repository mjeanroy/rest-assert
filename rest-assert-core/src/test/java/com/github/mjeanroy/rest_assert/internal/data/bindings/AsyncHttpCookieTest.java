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

package com.github.mjeanroy.rest_assert.internal.data.bindings;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.mocks.async.AsyncHttpCookieMockBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class AsyncHttpCookieTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setName(expectedName)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
		verify(asyncHttpCookie).getName();
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setValue(expectedValue)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
		verify(asyncHttpCookie).getValue();
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setDomain(expectedDomain)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
		verify(asyncHttpCookie).getDomain();
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setPath(expectedPath)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
		verify(asyncHttpCookie).getPath();
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setSecure(true)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
		verify(asyncHttpCookie).isSecure();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setHttpOnly(true)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isTrue();
		verify(asyncHttpCookie).isHttpOnly();
	}

	@Test
	public void it_should_get_max_age() {
		long expectedMaxAge = 10;
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder()
			.setMaxAge(expectedMaxAge)
			.build();

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		long maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
		verify(asyncHttpCookie).getMaxAge();
	}

	@Test
	public void it_should_not_implement_expires() {
		org.asynchttpclient.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieMockBuilder().build();

		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("org.asynchttpclient.cookie.Cookie does not support expires value, use #getMaxAge() instead");

		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		cookie.getExpires();
	}
}
