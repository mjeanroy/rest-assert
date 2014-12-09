/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.bindings;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.internal.data.bindings.AsyncHttpCookie;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AsyncHttpCookieTest {

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.getName()).thenReturn(expectedName);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
		verify(asyncHttpCookie).getName();
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.getValue()).thenReturn(expectedValue);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
		verify(asyncHttpCookie).getValue();
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.getDomain()).thenReturn(expectedDomain);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
		verify(asyncHttpCookie).getDomain();
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.getPath()).thenReturn(expectedPath);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
		verify(asyncHttpCookie).getPath();
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		boolean expectedSecured = true;
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.isSecure()).thenReturn(expectedSecured);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isEqualTo(expectedSecured);
		verify(asyncHttpCookie).isSecure();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		boolean expectedHttpOnly = true;
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.isHttpOnly()).thenReturn(expectedHttpOnly);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isEqualTo(expectedHttpOnly);
		verify(asyncHttpCookie).isHttpOnly();
	}

	@Test
	public void it_should_get_max_age() {
		int expectedMaxAge = 10;
		com.ning.http.client.cookie.Cookie asyncHttpCookie = mock(com.ning.http.client.cookie.Cookie.class);
		when(asyncHttpCookie.getMaxAge()).thenReturn(expectedMaxAge);

		Cookie cookie = AsyncHttpCookie.asyncHttpCookie(asyncHttpCookie);
		int maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
		verify(asyncHttpCookie).getMaxAge();
	}
}
