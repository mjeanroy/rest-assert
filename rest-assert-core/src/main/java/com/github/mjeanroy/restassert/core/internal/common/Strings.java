/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

/**
 * Static String Utilities.
 */
public final class Strings {

	private static final char SINGLE_QUOTE = '\'';
	private static final char DOUBLE_QUOTE = '"';

	// Ensure non instantiation.
	private Strings() {
	}

	/**
	 * Repeat given character the specified amount of time to produce
	 * a final String of given length.
	 *
	 * @param c Character to repeat.
	 * @param length Required length.
	 * @return The final String.
	 */
	public static String repeat(char c, int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; ++i) {
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Check if string values is quoted, using single or double quote.
	 *
	 * @param value The input value.
	 * @return {@code true} if {@code value} is quoted, {@code false} otherwise.
	 */
	public static boolean isQuoted(String value) {
		return value != null && value.length() >= 2 && (isSingleQuoted(value) || isDoubleQuoted(value));
	}

	/**
	 * Remove quote of given string.
	 *
	 * @param value Input string.
	 * @return Output string.
	 */
	public static String removeQuote(String value) {
		return value.substring(1, value.length() - 1);
	}

	private static boolean isSingleQuoted(String value) {
		return value.charAt(0) == SINGLE_QUOTE && value.charAt(value.length() - 1) == SINGLE_QUOTE;
	}

	private static boolean isDoubleQuoted(String value) {
		return value.charAt(0) == DOUBLE_QUOTE && value.charAt(value.length() - 1) == DOUBLE_QUOTE;
	}
}
