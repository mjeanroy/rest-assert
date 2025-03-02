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

import com.github.mjeanroy.restassert.core.internal.common.Strings;
import com.github.mjeanroy.restassert.core.internal.error.Message;
import com.github.mjeanroy.restassert.core.internal.json.JsonType;

/**
 * Error thrown when a json string should be an array
 * but is an object.
 */
public final class ShouldBeTypeOf extends AbstractJsonError {

	// Private constructor, use static factory instead
	private ShouldBeTypeOf(String json, String expected, String actual) {
		super(
			json,
			Message.message("Expecting json to be " + expected),
			Message.message("was " + actual)
		);
	}

	// Private constructor, use static factory instead
	private ShouldBeTypeOf(String json, String entry, String expected, String actual) {
		super(
			json,
			entry,
			Message.message("Expecting json entry %s to be " + expected, entry),
			Message.message("was " + actual)
		);
	}

	/**
	 * Build error.
	 *
	 * @param json Original JSON input.
	 * @param expected Expected type.
	 * @param actual Actual type.
	 * @return Error.
	 */
	public static ShouldBeTypeOf shouldBeTypeOf(String json, JsonType expected, JsonType actual) {
		return new ShouldBeTypeOf(
			json,
			rawType(expected),
			rawType(actual)
		);
	}

	/**
	 * Build error.
	 *
	 * @param json Original JSON input.
	 * @param entry JSON entry.
	 * @param expected Expected type.
	 * @param actual Actual type.
	 * @return Error.
	 */
	public static ShouldBeTypeOf shouldBeTypeOf(
		String json,
		String entry,
		JsonType expected,
		JsonType actual
	) {
		return new ShouldBeTypeOf(
			json,
			entry,
			rawType(expected),
			rawType(actual)
		);
	}

	private static String rawType(JsonType type) {
		if (type == JsonType.NULL) {
			return "null";
		}

		String name = type.name().toLowerCase();
		String article = Strings.isVowel(name.charAt(0)) ? "an" : "a";
		return article + " " + name;
	}
}
