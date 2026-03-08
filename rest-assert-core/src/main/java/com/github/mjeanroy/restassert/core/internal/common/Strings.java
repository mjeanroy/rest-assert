/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.common;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/// Static String Utilities.
public final class Strings {

	private static final char SINGLE_QUOTE = '\'';
	private static final char DOUBLE_QUOTE = '"';
	private static final List<Character> VOWELS = asList(
		'a', 'e', 'i', 'o', 'u', 'y'
	);

	// Ensure non instantiation.
	private Strings() {
	}

	/// Capitalize string:
	/// - Returns `null` if `input` is `null`
	/// - Returns an empty string if `input` is empty
	/// - Returns an uppercase value if `input` is one char length.
	/// - Returns first character as uppercase and rest of input as lowercase.
	///
	/// @param input Input.
	/// @return Capitalized output.
	public static String capitalize(String input) {
		if (isEmpty(input)) {
			return input;
		}

		if (input.length() == 1) {
			return input.toUpperCase();
		}

		return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
	}

	/// Trim given string and returns trimmed value
	/// or `null` if `str` is `null`, empty or blank.
	///
	/// @param str Input.
	/// @return Trimmed output.
	public static String trimToNull(String str) {
		String trimmed = trim(str);
		return isEmpty(trimmed) ? null : trimmed;
	}

	/// Trim given string and returns trimmed value (returns `null`
	/// if `str` is `null`).
	///
	/// @param str Input.
	/// @return Trimmed output.
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/// Repeat given character the specified amount of time to produce
	/// a final String of given length.
	///
	/// @param c Character to repeat.
	/// @param length Required length.
	/// @return The final String.
	public static String repeat(char c, int length) {
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; ++i) {
			sb.append(c);
		}

		return sb.toString();
	}

	/// Check if given char is a vowel.
	///
	/// @param c Character.
	/// @return `true` if `c` is a vowel, `false` otherwise.
	public static boolean isVowel(char c) {
		char lowerC = Character.toLowerCase(c);

		for (char vowel : VOWELS) {
			if (vowel == lowerC) {
				return true;
			}
		}

		return false;
	}

	/// Surround given input with double quotes.
	///
	/// @param input Input.
	/// @return Quoted input.
	public static String quote(String input) {
		return input == null ? null : (DOUBLE_QUOTE + input + DOUBLE_QUOTE);
	}

	/// Check if string values is quoted, using single or double quote.
	///
	/// @param value The input value.
	/// @return `true` if `value` is quoted, `false` otherwise.
	public static boolean isQuoted(String value) {
		return isSurroundedBy(value, SINGLE_QUOTE) || isSurroundedBy(value, DOUBLE_QUOTE);
	}

	private static boolean isSurroundedBy(String value, char leftRight) {
		return isSurroundedBy(value, leftRight, leftRight);
	}

	/// Check that given string value is surrounded by given left and right characters;
	///
	/// @param value Value.
	/// @param left Left character;
	/// @param right Right character.
	/// @return `true` if `value` is surrounded by `left` and `right`, `false` otherwise.
	public static boolean isSurroundedBy(String value, char left, char right) {
		if (value == null || value.length() < 2) {
			return false;
		}

		return value.charAt(0) == left && value.charAt(value.length() - 1) == right;
	}

	/// Check if given string is `null` or empty.
	///
	/// @param value The value.
	/// @return `true` if `value` is `null` or empty, `false` otherwise.
	public static boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	/// Check that given string value is not `null` and not empty.
	///
	/// @param value String value.
	/// @return `true` if `value` is not `null` and not empty, false otherwise (including blank string).
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/// Remove quote of given string.
	///
	/// @param value Input string.
	/// @return Output string.
	public static String removeQuote(String value) {
		return value.substring(1, value.length() - 1);
	}

	/// Serialize given value to a formatted string value.
	///
	/// @param value Value.
	/// @return The formatted value.
	@SuppressWarnings("unchecked")
	public static String serialize(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof String) {
			return Strings.quote((String) value);
		}

		if (value instanceof Collection) {
			return ((Collection<Object>) value).stream().map(Strings::serialize).collect(Collectors.toList()).toString();
		}

		return String.valueOf(value);
	}
}
