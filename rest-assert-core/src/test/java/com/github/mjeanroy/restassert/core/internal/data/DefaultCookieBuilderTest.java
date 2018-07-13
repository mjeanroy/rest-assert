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

package com.github.mjeanroy.restassert.core.internal.data;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultCookieBuilderTest {

	@Test
	public void it_should_build_default_cookie() {
		final String name = "foo";
		final String value = "bar";
		final Cookie cookie = Cookies.builder(name, value).build();

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo(name);
		assertThat(cookie.getValue()).isEqualTo(value);
		assertThat(cookie.getDomain()).isNull();
		assertThat(cookie.getPath()).isNull();
		assertThat(cookie.isSecured()).isFalse();
		assertThat(cookie.isHttpOnly()).isFalse();
		assertThat(cookie.getMaxAge()).isNull();
		assertThat(cookie.getExpires()).isNull();
	}

	@Test
	public void it_should_build_cookie() {
		final String name = "foo";
		final String value = "bar";
		final String domain = "domain.com";
		final String path = "/";
		final long maxAge = 3600;
		final Date expires = new Date();

		final Cookie cookie = Cookies.builder(name, value)
				.setDomain(domain)
				.setPath(path)
				.setSecure()
				.setHttpOnly()
				.setMaxAge(maxAge)
				.setExpires(expires)
				.build();

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo(name);
		assertThat(cookie.getValue()).isEqualTo(value);
		assertThat(cookie.getDomain()).isEqualTo(domain);
		assertThat(cookie.getPath()).isEqualTo(path);
		assertThat(cookie.isSecured()).isTrue();
		assertThat(cookie.isHttpOnly()).isTrue();
		assertThat(cookie.getMaxAge()).isEqualTo(maxAge);
		assertThat(cookie.getExpires()).isNotSameAs(expires).isEqualTo(expires);
	}

	@Test
	public void it_should_build_cookie_with_expires_timestamp() {
		final String name = "foo";
		final String value = "bar";
		final Date expires = new Date();

		final Cookie cookie = Cookies.builder(name, value)
				.setExpires(expires.getTime())
				.build();

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo(name);
		assertThat(cookie.getValue()).isEqualTo(value);
		assertThat(cookie.getExpires()).isNotSameAs(expires).isEqualTo(expires);
	}
}
