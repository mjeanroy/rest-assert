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

package com.github.mjeanroy.rest_assert.internal.assertions;

import static com.github.mjeanroy.rest_assert.error.cookie.ShouldHaveName.shouldHaveName;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;

/**
 * Re-usable assertion for {@link Cookie} objects.
 */
public final class CookieAssertions {

	/**
	 * Singleton object.
	 */
	private static CookieAssertions INSTANCE = new CookieAssertions();

	/**
	 * Get singleton object.
	 *
	 * @return Singleton object.
	 */
	public static CookieAssertions instance() {
		return INSTANCE;
	}

	// Private constructor to ensure singleton
	private CookieAssertions() {
	}

	/**
	 * Check that status code of http response is {@link com.github.mjeanroy.rest_assert.internal.data.HttpStatus#OK}.
	 *
	 * @param cookie Cookie.
	 * @param name Expected name.
	 * @return Assertion result.
	 */
	public AssertionResult hasName(Cookie cookie, String name) {
		String actualName = cookie.getName();
		return actualName.equals(name) ?
				success() :
				failure(shouldHaveName(name, actualName));
	}

}
