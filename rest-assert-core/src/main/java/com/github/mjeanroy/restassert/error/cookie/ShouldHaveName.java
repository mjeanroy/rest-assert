/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.error.cookie;

import com.github.mjeanroy.restassert.error.AbstractError;

/**
 * Error thrown when a cookie does not have
 * expected name.
 */
public class ShouldHaveName extends AbstractError {

	// Private constructor, use static factory instead
	private ShouldHaveName(String message, Object... args) {
		super(message, args);
	}

	/**
	 * Build error.
	 *
	 * @param expectedName Expected cookie name.
	 * @param actualName Actual cookie name.
	 * @return Error.
	 */
	public static ShouldHaveName shouldHaveName(String expectedName, String actualName) {
		return new ShouldHaveName("Expecting cookie to have name %s but was %s", expectedName, actualName);
	}
}