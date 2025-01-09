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

package com.github.mjeanroy.restassert.core.internal.assertions;

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.core.data.Cookie.SameSite;
import com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHaveName;

import java.util.function.Supplier;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.error.common.ShouldNotBeNull.shouldNotBeNull;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldBeHttpOnly.shouldBeHttpOnly;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldBeHttpOnly.shouldNotBeHttpOnly;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldBeSecured.shouldBeSecured;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldBeSecured.shouldNotBeSecured;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHaveDomain.shouldHaveDomain;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHaveMaxAge.shouldHaveMaxAge;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHavePath.shouldHavePath;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHaveSameSite.shouldHaveSameSite;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldHaveValue.shouldHaveValue;

/**
 * Re-usable assertion for {@link Cookie} objects.
 */
public final class CookieAssertions {

	/**
	 * Singleton object.
	 */
	private static final CookieAssertions INSTANCE = new CookieAssertions();

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
	 * Check that cookie has expected name.
	 *
	 * @param cookie Cookie.
	 * @param name Expected name.
	 * @return Assertion result.
	 */
	public AssertionResult hasName(Cookie cookie, String name) {
		return assertWith(cookie, () -> {
			String actualName = cookie.getName();
			return actualName.equals(name) ? success() : failure(ShouldHaveName.shouldHaveName(name, actualName));
		});
	}

	/**
	 * Check that cookie has expected value.
	 *
	 * @param cookie Cookie.
	 * @param value Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult hasValue(Cookie cookie, String value) {
		return assertWith(cookie, () -> {
			String actualValue = cookie.getValue();
			return actualValue.equals(value) ? success() : failure(shouldHaveValue(value, actualValue));
		});
	}

	/**
	 * Check that cookie has expected domain.
	 *
	 * @param cookie Cookie.
	 * @param domain Expected domain.
	 * @return Assertion result.
	 */
	public AssertionResult hasDomain(Cookie cookie, String domain) {
		return assertWith(cookie, () -> {
			String actualDomain = cookie.getDomain();
			return actualDomain.equals(domain) ? success() : failure(shouldHaveDomain(domain, actualDomain));
		});
	}

	/**
	 * Check that cookie has expected path.
	 *
	 * @param cookie Cookie.
	 * @param path Expected path.
	 * @return Assertion result.
	 */
	public AssertionResult hasPath(Cookie cookie, String path) {
		return assertWith(cookie, () -> {
			String actualPath = cookie.getPath();
			return actualPath.equals(path) ? success() : failure(shouldHavePath(path, actualPath));
		});
	}

	/**
	 * Check that cookie has expected max age.
	 *
	 * @param cookie Cookie.
	 * @param maxAge Expected max age.
	 * @return Assertion result.
	 */
	public AssertionResult hasMaxAge(Cookie cookie, long maxAge) {
		return assertWith(cookie, () -> {
			long actualMaxAge = cookie.getMaxAge();
			return actualMaxAge == maxAge ? success() : failure(shouldHaveMaxAge(maxAge, actualMaxAge));
		});
	}

	/**
	 * Check that cookie has expected same site.
	 *
	 * @param cookie Cookie.
	 * @param sameSite Expected same site value.
	 * @return Assertion result.
	 */
	public AssertionResult hasSameSite(Cookie cookie, SameSite sameSite) {
		return assertWith(cookie, () -> {
			SameSite actualSameSite = cookie.getSameSite();
			return actualSameSite == sameSite ? success() : failure(shouldHaveSameSite(sameSite, actualSameSite));
		});
	}

	/**
	 * Check that cookie has expected same site.
	 *
	 * @param cookie Cookie.
	 * @param sameSite Expected same site value.
	 * @return Assertion result.
	 */
	public AssertionResult hasSameSite(Cookie cookie, String sameSite) {
		return assertWith(cookie, () -> (
			hasSameSite(cookie, SameSite.parse(sameSite))
		));
	}

	/**
	 * Check that cookie is secured.
	 *
	 * @param cookie Cookie.
	 * @return Assertion result.
	 */
	public AssertionResult isSecured(Cookie cookie) {
		return assertWith(cookie, () -> (
			cookie.isSecured() ? success() : failure(shouldBeSecured())
		));
	}

	/**
	 * Check that cookie is secured.
	 *
	 * @param cookie Cookie.
	 * @return Assertion result.
	 */
	public AssertionResult isNotSecured(Cookie cookie) {
		return assertWith(cookie, () -> (
			!cookie.isSecured() ? success() : failure(shouldNotBeSecured())
		));
	}

	/**
	 * Check that cookie is flagged as "http only'.
	 *
	 * @param cookie Cookie.
	 * @return Assertion result.
	 */
	public AssertionResult isHttpOnly(Cookie cookie) {
		return assertWith(cookie, () -> (
			cookie.isHttpOnly() ? success() : failure(shouldBeHttpOnly())
		));
	}

	/**
	 * Check that cookie is flagged as "http only'.
	 *
	 * @param cookie Cookie.
	 * @return Assertion result.
	 */
	public AssertionResult isNotHttpOnly(Cookie cookie) {
		return assertWith(cookie, () -> (
			!cookie.isHttpOnly() ? success() : failure(shouldNotBeHttpOnly())
		));
	}

	private static AssertionResult assertWith(Cookie cookie, Supplier<AssertionResult> assertion) {
		AssertionResult r = isNotNull(cookie);
		if (r.isFailure()) {
			return r;
		}

		return assertion.get();
	}

	private static AssertionResult isNotNull(Cookie cookie) {
		return cookie == null ? failure(shouldNotBeNull("cookie")) : success();
	}
}
