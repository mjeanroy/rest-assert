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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DoesNotHaveCookieAssertionTest {

	@Test
	public void it_should_not_fail_if_header_does_not_have_cookies() {
		DoesNotHaveCookieAssertion assertion = new DoesNotHaveCookieAssertion();
		HttpResponse rsp = new HttpResponseBuilderImpl().build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_response_has_cookies() {
		Cookie cookie = new CookieBuilder().setName("foo").build();
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();
		DoesNotHaveCookieAssertion assertion = new DoesNotHaveCookieAssertion();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response not to contains cookies");
	}

	@Test
	public void it_should_not_fail_if_response_does_not_have_cookie_with_name() {
		String name = "foo";
		DoesNotHaveCookieAssertion assertion = new DoesNotHaveCookieAssertion(name);
		Cookie cookie = new CookieBuilder().setName(name + name).build();
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_response_has_cookie_with_name() {
		String name = "foo";
		DoesNotHaveCookieAssertion assertion = new DoesNotHaveCookieAssertion(name);
		Cookie cookie = new CookieBuilder().setName(name).build();
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response not to contains cookie with name foo");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_null() {
		assertThatThrownBy(doesNotHaveCookieAssertion(null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_empty() {
		assertThatThrownBy(doesNotHaveCookieAssertion(""))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_blank() {
		assertThatThrownBy(doesNotHaveCookieAssertion("   "))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cookie name must be defined");
	}

	private static ThrowingCallable doesNotHaveCookieAssertion(final String name) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				new DoesNotHaveCookieAssertion(name);
			}
		};
	}
}
