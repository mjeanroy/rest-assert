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

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static java.lang.String.format;

import org.junit.Test;

import com.github.mjeanroy.rest_assert.api.AbstractAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.Function;

public abstract class AbstractCookieTest extends AbstractAssertTest<Cookie> {

	@Test
	public void it_should_pass() {
		Cookie cookie = success();
		invoke(cookie);
		invoke("message", cookie);
	}

	@Test
	public void it_should_fail() {
		final Cookie cookie = failure();
		final String message = format(pattern(), placeholders());

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(cookie);
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message() {
		final Cookie cookie = failure();
		final String message = "foo";

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(message, cookie);
			}
		});
	}

	protected abstract Cookie success();

	protected abstract Cookie failure();

	protected abstract String pattern();

	protected abstract Object[] placeholders();
}