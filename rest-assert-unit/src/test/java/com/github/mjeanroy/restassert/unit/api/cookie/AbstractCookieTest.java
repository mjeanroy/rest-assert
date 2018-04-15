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

package com.github.mjeanroy.restassert.unit.api.cookie;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

import com.github.mjeanroy.restassert.tests.Function;
import com.github.mjeanroy.restassert.unit.api.AbstractAssertTest;
import org.junit.Test;

public abstract class AbstractCookieTest<T> extends AbstractAssertTest<T> {

	@Test
	public void it_should_pass() {
		T cookie = success();
		invoke(cookie);
		invoke("message", cookie);
	}

	@Test
	public void it_should_fail() {
		final T cookie = failure();
		final String message = String.format(pattern(), placeholders());

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(cookie);
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message() {
		final T cookie = failure();
		final String message = "foo";

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(message, cookie);
			}
		});
	}

	/**
	 * Create a cookie that should pass test.
	 *
	 * @return Cookie.
	 */
	protected abstract T success();

	/**
	 * Create a cookie that should not pass test.
	 *
	 * @return Cookie.
	 */
	protected abstract T failure();

	/**
	 * Get expected message pattern when test fails.
	 *
	 * @return The expected error message pattern.
	 */
	protected abstract String pattern();

	/**
	 * Get expected message placeholders when test fails.
	 *
	 * @return The expected error message placeholders.
	 */
	protected abstract Object[] placeholders();
}
