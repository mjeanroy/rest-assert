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

package com.github.mjeanroy.restassert.core.internal.data.bindings.javax;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.javax.JavaxCookieBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class JavaxCookieTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_return_name() {
		String expectedName = "foo";
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setName(expectedName)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
	}

	@Test
	public void it_should_return_value() {
		String expectedValue = "foo";
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setValue(expectedValue)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
	}

	@Test
	public void it_should_return_domain() {
		String expectedDomain = "foo";
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setDomain(expectedDomain)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
	}

	@Test
	public void it_should_return_path() {
		String expectedPath = "foo";
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setPath(expectedPath)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setSecured(true)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setHttpOnly(true)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isTrue();
	}

	@Test
	public void it_should_get_max_age() {
		int expectedMaxAge = 10;
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder()
			.setMaxAge(expectedMaxAge)
			.build();

		Cookie cookie = JavaxCookie.create(javaxCookie);
		long maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
	}

	@Test
	public void it_should_fail_on_getExpires() {
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder().build();
		Cookie cookie = JavaxCookie.create(javaxCookie);

		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("javax.servlet.http.Cookie does not support #getExpires(), please use #getMaxAge() instead.");

		cookie.getExpires();
	}
}
