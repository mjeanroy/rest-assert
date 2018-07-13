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

package com.github.mjeanroy.restassert.core.internal.data.bindings.async;

import com.github.mjeanroy.junit4.runif.RunIf;
import com.github.mjeanroy.junit4.runif.RunIfRunner;
import com.github.mjeanroy.junit4.runif.conditions.AtLeastJava8Condition;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.async.AsyncHttpCookieBuilder;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(RunIfRunner.class)
@RunIf(AtLeastJava8Condition.class)
public class AsyncHttpCookieTest {

	@Test
	public void it_should_return_name() {
		final String expectedName = "foo";
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setName(expectedName).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final String name = cookie.getName();

		assertThat(name).isEqualTo(expectedName);
	}

	@Test
	public void it_should_return_value() {
		final String expectedValue = "foo";
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setValue(expectedValue).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final String value = cookie.getValue();

		assertThat(value).isEqualTo(expectedValue);
	}

	@Test
	public void it_should_return_domain() {
		final String expectedDomain = "foo";
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setDomain(expectedDomain).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final String domain = cookie.getDomain();

		assertThat(domain).isEqualTo(expectedDomain);
	}

	@Test
	public void it_should_return_path() {
		final String expectedPath = "foo";
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setPath(expectedPath).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final String path = cookie.getPath();

		assertThat(path).isEqualTo(expectedPath);
	}

	@Test
	public void it_should_check_if_cookie_is_secured() {
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setSecure(true).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final boolean secured = cookie.isSecured();

		assertThat(secured).isTrue();
	}

	@Test
	public void it_should_check_if_cookie_is_http_only() {
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setHttpOnly(true).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final boolean httpOnly = cookie.isHttpOnly();

		assertThat(httpOnly).isTrue();
	}

	@Test
	public void it_should_get_max_age() {
		final long expectedMaxAge = 10;
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().setMaxAge(expectedMaxAge).build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);
		final long maxAge = cookie.getMaxAge();

		assertThat(maxAge).isEqualTo(expectedMaxAge);
	}

	@Test
	public void it_should_not_implement_expires() {
		final io.netty.handler.codec.http.cookie.Cookie asyncHttpCookie = new AsyncHttpCookieBuilder().build();
		final Cookie cookie = AsyncHttpCookie.create(asyncHttpCookie);

		assertThatThrownBy(getExpires(cookie))
				.isExactlyInstanceOf(UnsupportedOperationException.class)
				.hasMessage("org.asynchttpclient.cookie.Cookie does not support #getExpires(), please use #getMaxAge() instead.");
	}

	private ThrowingCallable getExpires(final Cookie cookie) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				cookie.getExpires();
			}
		};
	}
}
