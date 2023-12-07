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

package com.github.mjeanroy.restassert.core.internal.assertions.cookie;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHaveMaxAge;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;

class HasMaxAgeTest extends AbstractCookieTest {

	@Override
	protected AssertionResult run(Cookie cookie) {
		return cookieAssertions.hasMaxAge(cookie, success().getMaxAge());
	}

	@Override
	Cookie success() {
		return cookie(10);
	}

	@Override
	Cookie failure() {
		long expectedMaxAge = success().getMaxAge();
		long actualMaxAge = expectedMaxAge + 1;
		return cookie(actualMaxAge);
	}

	@Override
	Class<?> error() {
		return ShouldHaveMaxAge.class;
	}

	@Override
	String pattern() {
		return "Expecting cookie to have max-age %s but was %s";
	}

	@Override
	Object[] params() {
		long expectedMaxAge = success().getMaxAge();
		long actualMaxAge = failure().getMaxAge();
		return new Long[] {
			expectedMaxAge,
			actualMaxAge,
		};
	}

	private Cookie cookie(long maxAge) {
		return new MockCookieBuilder().setMaxAge(maxAge).build();
	}
}
