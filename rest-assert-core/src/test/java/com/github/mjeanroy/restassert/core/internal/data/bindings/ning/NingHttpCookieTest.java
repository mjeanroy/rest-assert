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

package com.github.mjeanroy.restassert.core.internal.data.bindings.ning;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.ning.NingHttpCookieBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NingHttpCookieTest {

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setName(expectedName)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setValue(expectedValue)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setDomain(expectedDomain)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setPath(expectedPath)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setSecure(true)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setHttpOnly(true)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isTrue();
	}

	@Test
	public void it_should_get_max_age() {
		long expectedMaxAge = 10;
		com.ning.http.client.cookie.Cookie asyncHttpCookie = new NingHttpCookieBuilder()
			.setMaxAge(expectedMaxAge)
			.build();

		Cookie cookie = NingHttpCookie.create(asyncHttpCookie);
		long maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
	}
}
