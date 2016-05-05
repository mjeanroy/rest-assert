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
import com.github.mjeanroy.rest_assert.tests.mocks.ning.NingHttpCookieMockBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class NingHttpCookieTest {

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setName(expectedName)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
		verify(asyncHttpCookie).getName();
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setValue(expectedValue)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
		verify(asyncHttpCookie).getValue();
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setDomain(expectedDomain)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
		verify(asyncHttpCookie).getDomain();
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setPath(expectedPath)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
		verify(asyncHttpCookie).getPath();
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setSecure(true)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
		verify(asyncHttpCookie).isSecure();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setHttpOnly(true)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isTrue();
		verify(asyncHttpCookie).isHttpOnly();
	}

	@Test
	public void it_should_get_max_age() {
		long expectedMaxAge = 10;
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieMockBuilder()
			.setMaxAge(expectedMaxAge)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		long maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
		verify(asyncHttpCookie).getMaxAge();
	}
}
