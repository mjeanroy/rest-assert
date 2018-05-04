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

import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveCookie;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.common.Collections.Predicate;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.Cookies;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveCookie.shouldHaveCookie;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.some;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * Check that http response contains expected cookie.
 */
public class HasCookieAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(HasCookieAssertion.class);

	/**
	 * Expected cookie.
	 */
	private final Cookie cookie;

	/**
	 * Expected cookie.
	 */
	private final String name;

	/**
	 * Expected cookie.
	 */
	private final String value;

	/**
	 * Predicate used to match cookie.
	 */
	private final Predicate<Cookie> predicate;

	/**
	 * Create assertion.
	 *
	 * @param name Cookie name.
	 * @throws NullPointerException If {@code name} is {@code null}.
	 * @throws IllegalArgumentException If {@code name} is empty or blank.
	 */
	public HasCookieAssertion(String name) {
		this.name = notBlank(name, "Cookie name must be defined");
		this.predicate = new CookieNamePredicate(name);

		this.value = null;
		this.cookie = null;
	}

	/**
	 * Create assertion.
	 *
	 * @param name Cookie name.
	 * @throws NullPointerException If {@code name} is {@code null}.
	 * @throws IllegalArgumentException If {@code name} is empty or blank.
	 */
	public HasCookieAssertion(String name, String value) {
		this.name = notBlank(name, "Cookie name must be defined");
		this.value = notNull(value, "Cookie value must be defined");
		this.predicate = new CookieNameValuePredicate(name, value);

		this.cookie = null;
	}

	/**
	 * Create assertion.
	 *
	 * @param cookie Cookie.
	 * @throws NullPointerException If {@code cookie} is {@code null}.
	 */
	public HasCookieAssertion(Cookie cookie) {
		this.cookie = notNull(cookie, "Cookie must be defined");
		this.predicate = new CookiePredicate(cookie);

		this.name = null;
		this.value = null;
	}

	@Override
	public AssertionResult handle(HttpResponse httpResponse) {
		log.debug("Extracting list of cookies");
		List<Cookie> cookies = httpResponse.getCookies();
		if (cookies.isEmpty()) {
			log.debug("Cannot find any cookies, fail");
			return failure(getError());
		}

		log.debug("Try to find cookie: ");
		if (cookie != null) {
			log.debug("-> Matching: {}", cookies);
		} else if (value != null) {
			log.debug("-> With name / value: '{}' / '{}'", name, value);
		} else {
			log.debug("-> With name: '{}'", name);
		}

		boolean found = some(cookies, predicate);
		return found ? success() : failure(getError());
	}

	private ShouldHaveCookie getError() {
		if (cookie != null) {
			return shouldHaveCookie(cookie);
		}

		if (value == null) {
			return shouldHaveCookie(name);
		}

		return shouldHaveCookie(name, value);
	}

	/**
	 * Predicate used to check if a cookie match another cookie.
	 */
	private static class CookiePredicate implements Predicate<Cookie> {
		/**
		 * Expected cookie.
		 */
		private final Cookie cookie;

		/**
		 * Create predicate.
		 *
		 * @param cookie Expected cookie.
		 * @throws NullPointerException If {@code cookie} is {@code null}.
		 */
		private CookiePredicate(Cookie cookie) {
			this.cookie = notNull(cookie, "Cookie must not be null");
		}

		@Override
		public boolean apply(Cookie cookie) {
			return Cookies.equals(this.cookie, cookie);
		}
	}

	/**
	 * Predicate used to check if a cookie match expected name
	 * and value.
	 */
	private static class CookieNameValuePredicate extends CookieNamePredicate implements Predicate<Cookie> {

		/**
		 * Expected value.
		 */
		private final String value;

		/**
		 * Create predicate.
		 *
		 * @param name Expected name.
		 * @param value Expected value.
		 * @throws NullPointerException If {@code name} or {@code value} is {@code null}.
		 */
		private CookieNameValuePredicate(String name, String value) {
			super(name);
			this.value = notNull(value, "Cookie value must not be null");
		}

		@Override
		public boolean apply(Cookie cookie) {
			return super.apply(cookie) && value.equals(cookie.getValue());
		}
	}
}
