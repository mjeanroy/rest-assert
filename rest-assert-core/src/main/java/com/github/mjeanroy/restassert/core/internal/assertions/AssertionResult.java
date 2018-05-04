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

package com.github.mjeanroy.restassert.core.internal.assertions;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;

/**
 * Result of assertion test case.
 * A result is a failure or a success.
 * For a success, error object will be always null.
 * For a failure, error object will be always non null.
 */
public class AssertionResult {

	/**
	 * Error object, not null if result is a failure.
	 */
	private final RestAssertError error;

	/**
	 * Build a success result.
	 *
	 * @return Success result.
	 */
	public static AssertionResult success() {
		return new AssertionResult(null);
	}

	/**
	 * Build a failure result.
	 *
	 * @param error Error object, must not be null.
	 * @return Failure result.
	 */
	public static AssertionResult failure(RestAssertError error) {
		return new AssertionResult(notNull(error, "Error object must not be null"));
	}

	// Private constructor, use static factories instead.
	private AssertionResult(RestAssertError error) {
		this.error = error;
	}

	/**
	 * Check if result is a success result.
	 *
	 * @return True if result is a success result, false otherwise.
	 */
	public boolean isSuccess() {
		return error == null;
	}

	/**
	 * Check if result is a failure result.
	 *
	 * @return True if result is a failure result, false otherwise.
	 */
	public boolean isFailure() {
		return error != null;
	}

	/**
	 * Get error object associated with result.
	 * Error object will always be null with a success result, and always
	 * non null with a failure result.
	 *
	 * @return Error object.
	 */
	public RestAssertError getError() {
		return error;
	}
}
