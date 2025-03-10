/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.data.bindings.async;

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.async.AsyncHttpCookieBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AsyncHttpCookieTest {

	@Test
	void it_should_return_name() {
		String expectedName = "foo";
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setName(expectedName).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
	}

	@Test
	void it_should_return_value() {
		String expectedValue = "foo";
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setValue(expectedValue).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
	}

	@Test
	void it_should_return_domain() {
		String expectedDomain = "foo";
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setDomain(expectedDomain).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
	}

	@Test
	void it_should_return_path() {
		String expectedPath = "foo";
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setPath(expectedPath).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
	}

	@Test
	void it_should_check_if_cookie_is_secured() {
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setSecure(true).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
	}

	@Test
	void it_should_check_if_cookie_is_http_only() {
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setHttpOnly(true).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isTrue();
	}

	@Test
	void it_should_get_max_age() {
		long expectedMaxAge = 10;
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setMaxAge(expectedMaxAge).build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		long maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
	}

	@Test
	void it_should_not_implement_expires() {
		io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().build();
		Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);

		assertThatThrownBy(cookie::getExpires)
			.isExactlyInstanceOf(UnsupportedOperationException.class)
			.hasMessage("org.asynchttpclient.cookie.Cookie does not support #getExpires(), please use #getMaxAge() instead.");
	}
}
