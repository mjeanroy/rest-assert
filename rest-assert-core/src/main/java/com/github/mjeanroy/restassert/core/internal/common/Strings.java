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
	private static final StringMapper<String> IDENTITY_MAPPER = new StringMapper<String>() {
		@Override
		public String apply(String input) {
			return input;
		}
	};

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
		return join(lines, separator, IDENTITY_MAPPER);
	}

	/**
	 * Join sequence of object into a single string separated by a given
	 * separator.
	 *
	 * The transformation from object of type {@code T} to a {@link String} is made using
	 * given string mapper.
	 *
	 * @param lines Sequence of strings.
	 * @param separator Separator between each strings.
	 * @param mapper The string mapper.
	 * @param <T> Type of inputs.
	 * @return Single string.
	 */
	public static <T> String join(Iterable<T> lines, String separator, StringMapper<T> mapper) {
		boolean first = true;

		StringBuilder sb = new StringBuilder();
		for (T line : lines) {
			if (!first) {
				sb.append(separator);
			}

			sb.append(mapper.apply(line));
			first = false;
		}

		return sb.toString();
	}

	/**
	 * A mapper that can translate any object to a {@link String} value.
	 *
	 * @param <T> Type of input.
	 */
	public interface StringMapper<T> {

		/**
		 * Transform input to a {@link String} output.
		 *
		 * @param input Input value.
		 * @return String output.
		 */
		String apply(T input);
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
