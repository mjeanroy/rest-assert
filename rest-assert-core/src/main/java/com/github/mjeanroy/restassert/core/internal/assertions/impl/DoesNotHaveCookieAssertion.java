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

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveCookie.shouldNotHaveCookie;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.some;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;

/**
 * Check that http response does not contains any cookies.
 */
public class DoesNotHaveCookieAssertion implements HttpResponseAssertion {

	/**
	 * Cookie name.
	 */
	private final String name;

	/**
	 * Create assertion.
	 */
	public DoesNotHaveCookieAssertion() {
		this.name = null;
	}

	/**
	 * Create assertion.
	 *
	 * @param name Expected cookie name.
	 * @throws NullPointerException If {@code name} is {@code null}.
	 * @throws IllegalArgumentException If {@code name} is empty or blank.
	 */
	public DoesNotHaveCookieAssertion(String name) {
		this.name = notBlank(name, "Cookie name must be defined");
	}

	@Override
	public AssertionResult handle(HttpResponse httpResponse) {
		List<Cookie> cookies = httpResponse.getCookies();
		boolean doesNotHave = name == null ? cookies.isEmpty() : !some(cookies, new CookieNamePredicate(name));
		return doesNotHave ? success() : getFailure();
	}

	/**
	 * Get the {@link AssertionResult} failure instance.
	 *
	 * @return The result.
	 */
	private AssertionResult getFailure() {
		return failure(name == null ? shouldNotHaveCookie() : shouldNotHaveCookie(name));
	}
}
