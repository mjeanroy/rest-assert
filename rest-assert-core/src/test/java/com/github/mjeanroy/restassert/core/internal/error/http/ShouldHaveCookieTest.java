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

package com.github.mjeanroy.restassert.core.internal.error.http;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveCookie.shouldHaveCookie;
import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveCookie.shouldNotHaveCookie;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldHaveCookieTest {

	@Test
	void it_should_create_error_with_cookie_name() {
		String expectedCookieName = "foo";
		ShouldHaveCookie error = shouldHaveCookie(expectedCookieName);

		assertThat(error).isNotNull();
		assertThat(error.message()).isEqualTo("Expecting http response to contains cookie with name %s");
		assertThat(error.args()).hasSize(1).containsExactly(expectedCookieName);
		assertThat(error.buildMessage()).isEqualTo("Expecting http response to contains cookie with name " + fmt(expectedCookieName));
		assertThat(error.toString()).isEqualTo("Expecting http response to contains cookie with name " + fmt(expectedCookieName));
	}

	@Test
	void it_should_create_error_with_cookie_name_and_value() {
		String expectedCookieName = "foo";
		String expectedCookieValue = "bar";
		ShouldHaveCookie error = shouldHaveCookie(expectedCookieName, expectedCookieValue);

		assertThat(error).isNotNull();
		assertThat(error.message()).isEqualTo("Expecting http response to contains cookie with name %s and value %s");
		assertThat(error.args()).hasSize(2).containsExactly(expectedCookieName, expectedCookieValue);
		assertThat(error.buildMessage()).isEqualTo("Expecting http response to contains cookie with name " + fmt(expectedCookieName) + " and value " + fmt(expectedCookieValue));
		assertThat(error.toString()).isEqualTo("Expecting http response to contains cookie with name " + fmt(expectedCookieName) + " and value " + fmt(expectedCookieValue));
	}

	@Test
	void it_should_create_error_with_cookie() {
		Cookie expectedCookie = new MockCookieBuilder().setName("foo").setValue("bar").build();
		ShouldHaveCookie error = shouldHaveCookie(expectedCookie);

		assertThat(error).isNotNull();
		assertThat(error.message()).isEqualTo("Expecting http response to contains cookie %s");
		assertThat(error.args()).hasSize(1).containsExactly(expectedCookie);
		assertThat(error.buildMessage()).isEqualTo("Expecting http response to contains cookie Cookie{name: foo}");
		assertThat(error.toString()).isEqualTo("Expecting http response to contains cookie Cookie{name: foo}");
	}

	@Test
	void it_should_create_error_with_unexpected_cookie_name() {
		String unexpectedCookieName = "foo";
		ShouldHaveCookie error = shouldNotHaveCookie(unexpectedCookieName);

		assertThat(error).isNotNull();
		assertThat(error.message()).isEqualTo("Expecting http response not to contains cookie with name %s");
		assertThat(error.args()).hasSize(1).containsExactly(unexpectedCookieName);
		assertThat(error.buildMessage()).isEqualTo("Expecting http response not to contains cookie with name " + fmt(unexpectedCookieName));
		assertThat(error.toString()).isEqualTo("Expecting http response not to contains cookie with name " + fmt(unexpectedCookieName));
	}

	@Test
	void it_should_create_error_with_unexpected_cookies() {
		ShouldHaveCookie error = shouldNotHaveCookie();

		assertThat(error).isNotNull();
		assertThat(error.message()).isEqualTo("Expecting http response not to contains cookies");
		assertThat(error.args()).isNotNull().isEmpty();
		assertThat(error.buildMessage()).isEqualTo("Expecting http response not to contains cookies");
		assertThat(error.toString()).isEqualTo("Expecting http response not to contains cookies");
	}
}
