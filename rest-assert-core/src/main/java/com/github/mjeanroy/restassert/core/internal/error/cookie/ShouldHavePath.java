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

package com.github.mjeanroy.restassert.core.internal.error.cookie;

import com.github.mjeanroy.restassert.core.internal.error.AbstractError;

/**
 * Error thrown when a cookie does not have
 * expected path.
 */
public final class ShouldHavePath extends AbstractError {

	// Private constructor, use static factory instead
	private ShouldHavePath(String message, String expectedValue, String actualValue) {
		super(message, expectedValue, actualValue);
	}

	/**
	 * Build error.
	 *
	 * @param expectedPath Expected cookie domain.
	 * @param actualPath Actual cookie domain.
	 * @return Error.
	 */
	public static ShouldHavePath shouldHavePath(String expectedPath, String actualPath) {
		return new ShouldHavePath("Expecting cookie to have path %s", expectedPath, actualPath);
	}
}
