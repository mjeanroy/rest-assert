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

package com.github.mjeanroy.restassert.core.internal.error.json;

import com.github.mjeanroy.restassert.core.internal.json.JsonType;

/**
 * Error thrown when a json string contain an entry
 * that is not of expected type.
 */
public class ShouldBeEntryOf extends AbstractJsonError {

	// Private constructor, use static factory instead
	private ShouldBeEntryOf(String entryName, String message, Object... args) {
		super(entryName, message, args);
	}

	/**
	 * Build error.
	 *
	 * @param entry Entry name.
	 * @param actualType Actual type.
	 * @param expectedType Expected type.
	 * @return Error.
	 */
	public static ShouldBeEntryOf shouldBeEntryOf(String entry, JsonType actualType, JsonType expectedType) {
		return new ShouldBeEntryOf(entry, "Expecting json entry %s to be %s value but was %s value", entry, expectedType.getFormattedName(), actualType.getFormattedName());
	}
}
