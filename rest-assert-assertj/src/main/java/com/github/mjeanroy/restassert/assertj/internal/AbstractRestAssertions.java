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

package com.github.mjeanroy.restassert.assertj.internal;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;

/**
 * Commons methods to class assertions.
 */
abstract class AbstractRestAssertions {

	/**
	 * Failures object.
	 */
	private final Failures failures = Failures.instance();

	void assertNotNull(AssertionInfo info, Object actual) {
		Objects.instance().assertNotNull(info, actual);
	}

	/**
	 * Check that result is a success or a failure.
	 * If result is a failure, it will throw an assertion error with
	 * assertion error message.
	 *
	 * @param info Assertion info.
	 * @param result Assertion result.
	 */
	void check(AssertionInfo info, AssertionResult result) {
		if (result.isFailure()) {
			fail(info, result);
		}
	}

	/**
	 * Throw an assertion error using assertion result.
	 * Exception message is build as {@link BasicErrorMessageFactory} using
	 * result message.
	 *
	 * @param info Assertion info.
	 * @param result Assertion result.
	 */
	private void fail(AssertionInfo info, AssertionResult result) {
		RestAssertError error = result.getError();
		throw failures.failure(info, message(error));
	}

	/**
	 * Build a simple {@link BasicErrorMessageFactory} error using
	 * error message.
	 *
	 * @param error Error.
	 * @return Assertj error message
	 */
	private BasicErrorMessageFactory message(RestAssertError error) {
		return new BasicErrorMessageFactory(error.message(), error.args());
	}
}
