/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

import com.github.mjeanroy.restassert.core.internal.data.Cookie.SameSite;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultCookieBuilderTest {

	@Test
	public void it_should_build_default_cookie() {
		String name = "foo";
		String value = "bar";
		Cookie cookie = Cookies.builder(name, value).build();

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo(name);
		assertThat(cookie.getValue()).isEqualTo(value);
		assertThat(cookie.getDomain()).isNull();
		assertThat(cookie.getPath()).isNull();
		assertThat(cookie.isSecured()).isFalse();
		assertThat(cookie.isHttpOnly()).isFalse();
		assertThat(cookie.getMaxAge()).isNull();
		assertThat(cookie.getExpires()).isNull();
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.LAX);
	}

	@Test
	public void it_should_build_cookie() {
		String name = "foo";
		String value = "bar";
		String domain = "domain.com";
		String path = "/";
		long maxAge = 3600;
		Date expires = new Date();
		SameSite sameSite = SameSite.STRICT;

		Cookie cookie = Cookies.builder(name, value)
				.setDomain(domain)
				.setPath(path)
				.setSecure()
				.setHttpOnly()
				.setMaxAge(maxAge)
				.setExpires(expires)
				.setSameSite(sameSite)
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
		assertThat(cookie.getSameSite()).isEqualTo(sameSite);
	}

	@Test
	public void it_should_build_cookie_with_expires_timestamp() {
		String name = "foo";
		String value = "bar";
		Date expires = new Date();

		Cookie cookie = Cookies.builder(name, value)
				.setExpires(expires.getTime())
				.build();

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo(name);
		assertThat(cookie.getValue()).isEqualTo(value);
		assertThat(cookie.getExpires()).isNotSameAs(expires).isEqualTo(expires);
	}

	@Test
	public void it_should_build_with_same_site_lax() {
		Cookie cookie = Cookies.builder("foo", "bar").setSameSite("lax").build();
		assertThat(cookie).isNotNull();
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.LAX);
	}

	@Test
	public void it_should_build_with_same_site_strict() {
		Cookie cookie = Cookies.builder("foo", "bar").setSameSite("strict").build();
		assertThat(cookie).isNotNull();
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.STRICT);
	}

	@Test
	public void it_should_build_with_same_site_none() {
		Cookie cookie = Cookies.builder("foo", "bar").setSameSite("none").build();
		assertThat(cookie).isNotNull();
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.NONE);
	}

	@Test
	public void it_should_fail_to_build_with_same_site_unknown_value() {
		ThrowableAssert.ThrowingCallable run = new ThrowableAssert.ThrowingCallable() {
			@Override
			public void call() {
				Cookies.builder("foo", "bar").setSameSite("value").build();
			}
		};

		assertThatThrownBy(run).isInstanceOf(IllegalArgumentException.class).hasMessage(
			"Unknown SameSite value: value"
		);
	}
}
