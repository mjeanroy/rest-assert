/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.assertions;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.mocks.CookieMockBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class CookieNameValuePredicateTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_match_cookie_by_name() {
		final String name = "foo";
		final String value = "bar";
		final CookieNameValuePredicate predicate = new CookieNameValuePredicate(name, value);

		Cookie c1 = new CookieMockBuilder()
				.setName(name)
				.setValue(value)
				.build();

		Cookie c2 = new CookieMockBuilder()
				.setName(value)
				.setValue(value)
				.build();

		Cookie c3 = new CookieMockBuilder()
				.setName(name)
				.setValue(name)
				.build();

		assertThat(predicate.apply(c1)).isTrue();
		assertThat(predicate.apply(c2)).isFalse();
		assertThat(predicate.apply(c3)).isFalse();
	}

	@Test
	public void it_should_fail_if_name_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Cookie name must not be null");

		new CookieNameValuePredicate(null, "value");
	}

	@Test
	public void it_should_fail_if_value_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Cookie value must not be null");

		new CookieNameValuePredicate("name", null);
	}
}
