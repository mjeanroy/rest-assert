/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.api.cookie;

import static com.github.mjeanroy.rest_assert.api.cookie.CookieAssert.assertHasName;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.rest_assert.tests.TestData.newCookie;
import static java.lang.String.format;

import org.junit.Test;

import com.github.mjeanroy.rest_assert.api.AbstractAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.Function;

public class CookieAssert_assertHasName_Test extends AbstractAssertTest<Cookie> {

	@Test
	public void it_should_pass_with_correct_name() {
		Cookie cookie = newCookie(expectedName());
		invoke(cookie);
		invoke("message", cookie);
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		final String expectedName = expectedName();
		final String actualName = expectedName + "foo";
		final Cookie cookie = newCookie(actualName);
		final String message = format("Expecting cookie to have name %s but was %s", expectedName, actualName);

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(cookie);
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		final String expectedName = expectedName();
		final String actualName = expectedName + "foo";
		final Cookie cookie = newCookie(actualName);
		final String message = "foo";

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(message, cookie);
			}
		});
	}

	@Override
	protected void invoke(Cookie actual) {
		assertHasName(actual, expectedName());
	}

	@Override
	protected void invoke(String message, Cookie actual) {
		assertHasName(message, actual, expectedName());
	}

	protected String expectedName() {
		return "foo";
	}
}
