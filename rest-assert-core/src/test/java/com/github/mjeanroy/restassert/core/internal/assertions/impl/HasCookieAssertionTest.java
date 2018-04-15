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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class HasCookieAssertionTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_not_fail_if_header_has_cookie() {
		final String name = "foo";
		final String value = "bar";
		final Cookie c1 = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final Cookie c2 = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final HasCookieAssertion assertion = new HasCookieAssertion(c1);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(c2)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_header_has_cookie_with_name() {
		final String name = "foo";
		final String value = "bar";
		final Cookie cookie = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final HasCookieAssertion assertion = new HasCookieAssertion(name);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(cookie)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_header_has_cookie_with_name_and_value() {
		final String name = "foo";
		final String value = "bar";
		final Cookie cookie = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final HasCookieAssertion assertion = new HasCookieAssertion(name, value);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(cookie)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_response_does_not_have_cookie() {
		final String name = "foo";
		final String value = "bar";
		final Cookie c1 = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final Cookie c2 = new CookieBuilder()
				.setName(name)
				.setValue(value + value)
				.build();

		final HasCookieAssertion assertion = new HasCookieAssertion(c1);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(c2)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response to contains cookie Cookie{name: foo}");
	}

	@Test
	public void it_should_fail_if_response_does_not_have_cookie_with_name() {
		final String name = "foo";
		final String value = "bar";
		final Cookie cookie = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final HasCookieAssertion assertion = new HasCookieAssertion(name + name);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(cookie)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response to contains cookie with name foofoo");
	}

	@Test
	public void it_should_fail_if_response_does_not_have_cookie_with_name_and_value() {
		final String name = "foo";
		final String value = "bar";
		final Cookie cookie = new CookieBuilder()
				.setName(name)
				.setValue(value)
				.build();

		final HasCookieAssertion assertion = new HasCookieAssertion(name, value + value);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(cookie)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting http response to contains cookie with name foo and value barbar");
	}

	@Test
	public void it_should_fail_if_cookie_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Cookie must be defined");
		new HasCookieAssertion((Cookie) null);
	}

	@Test
	public void it_should_fail_if_cookie_name_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Cookie name must be defined");
		new HasCookieAssertion((String) null);
	}

	@Test
	public void it_should_fail_if_cookie_name_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cookie name must be defined");
		new HasCookieAssertion("");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_blank() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cookie name must be defined");
		new HasCookieAssertion("   ");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_null_and_value_is_set() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Cookie name must be defined");
		new HasCookieAssertion(null, "value");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_empty_an_value_is_set() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cookie name must be defined");
		new HasCookieAssertion("", "value");
	}

	@Test
	public void it_should_fail_if_cookie_name_is_blank_and_value_is_set() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cookie name must be defined");
		new HasCookieAssertion("   ", "value");
	}

	@Test
	public void it_should_fail_if_cookie_value_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Cookie value must be defined");
		new HasCookieAssertion("name", null);
	}
}
