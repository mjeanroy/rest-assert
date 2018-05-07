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
	 * Join sequence of strings into a single string separated by a given
	 * separator.
	 *
	 * @param lines Sequence of strings.
	 * @param separator Separator between each strings.
	 * @return Single string.
	 */
	public static String join(Iterable<String> lines, String separator) {
		boolean first = true;

		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			if (!first) {
				sb.append(separator);
			}

			sb.append(line);
			first = false;
		}

		return sb.toString();
	}

	public static boolean isQuoted(String value) {
		return value != null && value.length() >= 2 && (isSingleQuoted(value) || isDoubleQuoted(value));
	}

	private static boolean isSingleQuoted(String value) {
		return value.charAt(0) == SINGLE_QUOTE && value.charAt(value.length() - 1) == SINGLE_QUOTE;
	}

	private static boolean isDoubleQuoted(String value) {
		return value.charAt(0) == DOUBLE_QUOTE && value.charAt(value.length() - 1) == DOUBLE_QUOTE;
	}
}
