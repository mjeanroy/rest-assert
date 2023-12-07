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
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HasCookieAssertionTest {

	@Test
	void it_should_not_fail_if_header_has_cookie() {
		String name = "foo";
		String value = "bar";
		Cookie c1 = new MockCookieBuilder().setName(name).setValue(value).build();
		Cookie c2 = new MockCookieBuilder().setName(name).setValue(value).build();

		HasCookieAssertion assertion = new HasCookieAssertion(c1);
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(c2).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_not_fail_if_header_has_cookie_with_name() {
		String name = "foo";
		String value = "bar";
		Cookie cookie = new MockCookieBuilder().setName(name).setValue(value).build();

		HasCookieAssertion assertion = new HasCookieAssertion(name);
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_not_fail_if_header_has_cookie_with_name_and_value() {
		String name = "foo";
		String value = "bar";
		Cookie cookie = new MockCookieBuilder().setName(name).setValue(value).build();

		HasCookieAssertion assertion = new HasCookieAssertion(name, value);
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_fail_if_response_does_not_have_cookie() {
		String name = "foo";
		String value = "bar";
		Cookie c1 = new MockCookieBuilder().setName(name).setValue(value).build();
		Cookie c2 = new MockCookieBuilder().setName(name).setValue(value + value).build();

		HasCookieAssertion assertion = new HasCookieAssertion(c1);
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(c2).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response to contains cookie Cookie{name: foo}");
	}

	@Test
	void it_should_fail_if_response_does_not_have_cookie_with_name() {
		String name = "foo";
		String value = "bar";
		Cookie cookie = new MockCookieBuilder().setName(name).setValue(value).build();

		HasCookieAssertion assertion = new HasCookieAssertion(name + name);
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response to contains cookie with name foofoo");
	}

	@Test
	void it_should_fail_if_response_does_not_have_cookie_with_name_and_value() {
		String name = "foo";
		String value = "bar";
		Cookie cookie = new MockCookieBuilder().setName(name).setValue(value).build();

		HasCookieAssertion assertion = new HasCookieAssertion(name, value + value);
		HttpResponse rsp = new HttpResponseBuilderImpl().addCookie(cookie).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response to contains cookie with name foo and value barbar");
	}

	@Test
	void it_should_fail_if_cookie_is_null() {
		assertThatThrownBy(() -> new HasCookieAssertion((Cookie) null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Cookie must be defined");
	}

	@Test
	void it_should_fail_if_cookie_name_null() {
		assertThatThrownBy(() -> new HasCookieAssertion((String) null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	void it_should_fail_if_cookie_name_is_empty() {
		assertThatThrownBy(() -> new HasCookieAssertion(""))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	void it_should_fail_if_cookie_name_is_blank() {
		assertThatThrownBy(() -> new HasCookieAssertion("   "))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	void it_should_fail_if_cookie_name_is_null_and_value_is_set() {
		assertThatThrownBy(() -> new HasCookieAssertion(null, "value"))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_empty_an_value_is_set() {
		assertThatThrownBy(() -> new HasCookieAssertion("", "value"))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_blank_and_value_is_set() {
		assertThatThrownBy(() -> new HasCookieAssertion("   ", "value"))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cookie name must be defined");
	}

	@Test
	public void it_should_fail_if_cookie_value_is_null() {
		assertThatThrownBy(() -> new HasCookieAssertion("name", null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Cookie value must be defined");
	}
}
