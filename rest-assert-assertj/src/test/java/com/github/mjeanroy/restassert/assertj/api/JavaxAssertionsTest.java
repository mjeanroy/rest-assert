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

package com.github.mjeanroy.restassert.assertj.api;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.bindings.javax.JavaxCookie;
import com.github.mjeanroy.restassert.tests.builders.javax.JavaxCookieBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaxAssertionsTest {

	@Test
	public void it_should_create_new_cookie_assertion_object() throws Exception {
		javax.servlet.http.Cookie javaxCookie = new JavaxCookieBuilder().build();
		CookieAssert assertions = JavaxAssertions.assertThat(javaxCookie);

		assertThat(assertions).isNotNull();
		Cookie cookie = (Cookie) FieldUtils.readField(assertions, "actual", true);
		assertThat(cookie)
				.isNotNull()
				.isExactlyInstanceOf(JavaxCookie.class);
	}
}
