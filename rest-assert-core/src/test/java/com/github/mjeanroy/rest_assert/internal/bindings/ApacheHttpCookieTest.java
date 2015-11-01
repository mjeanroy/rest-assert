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
import com.github.mjeanroy.rest_assert.internal.data.bindings.httpcomponent.ApacheHttpCookie;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApacheHttpCookieTest {

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = mock(org.apache.http.cookie.Cookie.class);
		when(apacheHttpCookie.getName()).thenReturn(expectedName);

		Cookie cookie = ApacheHttpCookie.apacheHttpCookie(apacheHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
		verify(apacheHttpCookie).getName();
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = mock(org.apache.http.cookie.Cookie.class);
		when(apacheHttpCookie.getValue()).thenReturn(expectedValue);

		Cookie cookie = ApacheHttpCookie.apacheHttpCookie(apacheHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
		verify(apacheHttpCookie).getValue();
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = mock(org.apache.http.cookie.Cookie.class);
		when(apacheHttpCookie.getDomain()).thenReturn(expectedDomain);

		Cookie cookie = ApacheHttpCookie.apacheHttpCookie(apacheHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
		verify(apacheHttpCookie).getDomain();
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = mock(org.apache.http.cookie.Cookie.class);
		when(apacheHttpCookie.getPath()).thenReturn(expectedPath);

		Cookie cookie = ApacheHttpCookie.apacheHttpCookie(apacheHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
		verify(apacheHttpCookie).getPath();
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		boolean expectedSecured = true;
		org.apache.http.cookie.Cookie apacheHttpCookie = mock(org.apache.http.cookie.Cookie.class);
		when(apacheHttpCookie.isSecure()).thenReturn(expectedSecured);

		Cookie cookie = ApacheHttpCookie.apacheHttpCookie(apacheHttpCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isEqualTo(expectedSecured);
		verify(apacheHttpCookie).isSecure();
	}
}
