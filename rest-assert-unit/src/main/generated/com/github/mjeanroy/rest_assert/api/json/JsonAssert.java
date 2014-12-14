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

package com.github.mjeanroy.rest_assert.api.json;

import static com.github.mjeanroy.rest_assert.api.AssertUtil.check;

/**
 * Static assertions.
 * This class is generated.
 */
public final class JsonAssert {

	private static com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions assertions = com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions.instance();

	// Private constructor to ensure no instantiation
	private JsonAssert() {
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(java.lang.String actual, java.net.URI arg1) {
		assertIsEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(String message, java.lang.String actual, java.net.URI arg1) {
		check(message, assertions.isEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(java.lang.String actual, java.net.URL arg1) {
		assertIsEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(String message, java.lang.String actual, java.net.URL arg1) {
		check(message, assertions.isEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(java.lang.String actual, java.io.File arg1) {
		assertIsEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(String message, java.lang.String actual, java.io.File arg1) {
		check(message, assertions.isEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(java.lang.String actual, java.lang.String arg1) {
		assertIsEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(String message, java.lang.String actual, java.lang.String arg1) {
		check(message, assertions.isEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(java.lang.String actual, java.nio.file.Path arg1) {
		assertIsEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions#isEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsEqualTo(String message, java.lang.String actual, java.nio.file.Path arg1) {
		check(message, assertions.isEqualTo(actual, arg1));
	}

}

