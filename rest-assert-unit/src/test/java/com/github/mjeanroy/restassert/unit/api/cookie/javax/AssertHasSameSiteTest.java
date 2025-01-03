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

package com.github.mjeanroy.restassert.unit.api.cookie.javax;

import com.github.mjeanroy.restassert.tests.builders.javax.JavaxCookieBuilder;
import com.github.mjeanroy.restassert.unit.api.cookie.JavaxCookieAssert;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;

import static com.github.mjeanroy.restassert.core.internal.data.Cookie.SameSite.STRICT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AssertHasSameSiteTest {

	@Test
	void it_should_fail_because_of_unsupported_operation() {
		Cookie cookie = new JavaxCookieBuilder().build();
		assertThatThrownBy(() -> JavaxCookieAssert.assertHasSameSite(cookie, STRICT))
			.isExactlyInstanceOf(UnsupportedOperationException.class)
			.hasMessage("javax.servlet.http.Cookie does not support #getSameSite()");
	}

	@Test
	void it_should_fail_with_custom_message_because_of_unsupported_operation() {
		Cookie cookie = new JavaxCookieBuilder().build();
		assertThatThrownBy(() -> JavaxCookieAssert.assertHasSameSite("message", cookie, STRICT))
			.isExactlyInstanceOf(UnsupportedOperationException.class)
			.hasMessage("javax.servlet.http.Cookie does not support #getSameSite()");
	}
}
