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
import com.github.mjeanroy.rest_assert.internal.data.bindings.JavaxCookie;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JavaxCookieTest {

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.getName()).thenReturn(expectedName);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
		verify(javaxCookie).getName();
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.getValue()).thenReturn(expectedValue);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
		verify(javaxCookie).getValue();
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.getDomain()).thenReturn(expectedDomain);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
		verify(javaxCookie).getDomain();
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.getPath()).thenReturn(expectedPath);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
		verify(javaxCookie).getPath();
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		boolean expectedSecured = true;
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.getSecure()).thenReturn(expectedSecured);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isEqualTo(expectedSecured);
		verify(javaxCookie).getSecure();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		boolean expectedHttpOnly = true;
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.isHttpOnly()).thenReturn(expectedHttpOnly);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isEqualTo(expectedHttpOnly);
		verify(javaxCookie).isHttpOnly();
	}

	@Test
	public void it_should_get_max_age() {
		int expectedMaxAge = 10;
		javax.servlet.http.Cookie javaxCookie = mock(javax.servlet.http.Cookie.class);
		when(javaxCookie.getMaxAge()).thenReturn(expectedMaxAge);

		Cookie cookie = JavaxCookie.javaxCookie(javaxCookie);
		int maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
		verify(javaxCookie).getMaxAge();
	}
}
