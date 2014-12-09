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

import static com.github.mjeanroy.rest_assert.api.AssertUtil.check;

/**
 * Static assertions.
 * This class is generated.
 */
public final class CookieAssert {

	private static com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions assertions = com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions.instance();

	// Private constructor to ensure no instantiation
	private CookieAssert() {
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#hasDomain}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasDomain(com.github.mjeanroy.rest_assert.internal.data.Cookie actual, java.lang.String arg1) {
		assertHasDomain(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#hasDomain}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasDomain(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual, java.lang.String arg1) {
		check(message, assertions.hasDomain(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#hasName}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasName(com.github.mjeanroy.rest_assert.internal.data.Cookie actual, java.lang.String arg1) {
		assertHasName(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#hasName}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasName(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual, java.lang.String arg1) {
		check(message, assertions.hasName(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#hasValue}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasValue(com.github.mjeanroy.rest_assert.internal.data.Cookie actual, java.lang.String arg1) {
		assertHasValue(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#hasValue}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasValue(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual, java.lang.String arg1) {
		check(message, assertions.hasValue(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isHttpOnly}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsHttpOnly(com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		assertIsHttpOnly(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isHttpOnly}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsHttpOnly(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		check(message, assertions.isHttpOnly(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isNotHttpOnly}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNotHttpOnly(com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		assertIsNotHttpOnly(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isNotHttpOnly}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNotHttpOnly(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		check(message, assertions.isNotHttpOnly(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isNotSecured}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNotSecured(com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		assertIsNotSecured(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isNotSecured}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNotSecured(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		check(message, assertions.isNotSecured(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isSecured}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsSecured(com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		assertIsSecured(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions#isSecured}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsSecured(String message, com.github.mjeanroy.rest_assert.internal.data.Cookie actual) {
		check(message, assertions.isSecured(actual));
	}

}

