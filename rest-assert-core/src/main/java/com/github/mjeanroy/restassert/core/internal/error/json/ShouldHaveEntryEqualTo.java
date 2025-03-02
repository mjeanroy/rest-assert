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

package com.github.mjeanroy.restassert.core.internal.error.json;

import com.github.mjeanroy.restassert.core.internal.error.Message;

/**
 * Error thrown when a json string contain an entry
 * that is not of expected type.
 */
public final class ShouldHaveEntryEqualTo extends AbstractJsonError {

	// Private constructor, use static factory instead
	private ShouldHaveEntryEqualTo(
		String json,
		String entryName,
		Message expectation,
		Message mismatch
	) {
		super(json, entryName, expectation, mismatch);
	}

	/**
	 * Build error.
	 *
	 * @param json Original JSON input.
	 * @param entry Entry name.
	 * @param actualValue Actual value.
	 * @param expectedValue Expected value.
	 * @return Error.
	 */
	public static ShouldHaveEntryEqualTo shouldHaveEntryEqualTo(
		String json,
		String entry,
		Object actualValue,
		Object expectedValue
	) {
		return new ShouldHaveEntryEqualTo(
			json,
			entry,
			Message.message("Expecting json entry %s to be equal to %s", entry, expectedValue),
			Message.message("was %s", actualValue)
		);
	}
}
