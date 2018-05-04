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

package com.github.mjeanroy.restassert.core.internal.error.http;

import com.github.mjeanroy.restassert.core.internal.error.AbstractError;

/**
 * Error thrown when an http response should contain
 * specific mime type.
 */
public class ShouldHaveMimeType extends AbstractError {

	// Private constructor, use static factory instead
	private ShouldHaveMimeType(String message, Object... args) {
		super(message, args);
	}

	/**
	 * Build error.
	 *
	 * @param expectedMimeType Expected mime type.
	 * @param actualMimeType   Actual mime type.
	 * @return Error.
	 */
	public static ShouldHaveMimeType shouldHaveMimeType(String expectedMimeType, String actualMimeType) {
		return new ShouldHaveMimeType("Expecting response to have mime type %s but was %s", expectedMimeType, actualMimeType);
	}

	/**
	 * Build error.
	 *
	 * @param expectedMimeType Expected mime types.
	 * @param actualMimeType   Actual mime type.
	 * @return Error.
	 */
	public static ShouldHaveMimeType shouldHaveMimeType(Iterable<String> expectedMimeType, String actualMimeType) {
		return new ShouldHaveMimeType("Expecting response to have mime type in %s but was %s", expectedMimeType, actualMimeType);
	}
}
