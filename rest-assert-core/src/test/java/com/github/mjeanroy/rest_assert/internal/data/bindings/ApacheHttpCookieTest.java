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

package com.github.mjeanroy.rest_assert.internal.data.bindings;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpCookieMockBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class ApacheHttpCookieTest {

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder()
			.setName(expectedName)
			.build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
		verify(apacheHttpCookie).getName();
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder()
			.setValue(expectedValue)
			.build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
		verify(apacheHttpCookie).getValue();
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder()
			.setDomain(expectedDomain)
			.build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
		verify(apacheHttpCookie).getDomain();
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder()
			.setPath(expectedPath)
			.build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
		verify(apacheHttpCookie).getPath();
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder()
			.setSecure(true)
			.build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);

		boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
		verify(apacheHttpCookie).isSecure();
	}

	@Test
	public void it_should_return_zero_for_max_age() {
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder().build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);
		assertThat(cookie.getMaxAge()).isZero();
		verifyZeroInteractions(apacheHttpCookie);
	}

	@Test
	public void it_should_return_false_for_http_only() {
		org.apache.http.cookie.Cookie apacheHttpCookie = new ApacheHttpCookieMockBuilder().build();

		Cookie cookie = ApacheHttpCookie.create(apacheHttpCookie);
		assertThat(cookie.isHttpOnly()).isFalse();
		verifyZeroInteractions(apacheHttpCookie);
	}
}