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
import com.github.mjeanroy.restassert.core.internal.json.JsonType;

import static com.github.mjeanroy.restassert.core.internal.common.Strings.isVowel;

/**
 * Error thrown when a json string contain an entry
 * that is not of expected type.
 */
public final class ShouldBeEntryOf extends AbstractJsonError {

	// Private constructor, use static factory instead
	private ShouldBeEntryOf(
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
	 * @param actualType Actual type.
	 * @param expectedType Expected type.
	 * @return Error.
	 */
	public static ShouldBeEntryOf shouldBeEntryOf(
		String json,
		String entry,
		JsonType actualType,
		JsonType expectedType
	) {
		return new ShouldBeEntryOf(
			json,
			entry,
			Message.message("Expecting json entry %s to be " + serializeType(expectedType), entry),
			Message.message("was " + serializeType(actualType))
		);
	}

	private static String serializeType(JsonType type) {
		if (type == JsonType.NULL) {
			return "null";
		}

		String formattedName = type.name().toLowerCase();
		char firstChar = formattedName.charAt(0);
		String article = isVowel(firstChar) ? "an" : "a";
		return article + " " + formattedName;
	}
}
