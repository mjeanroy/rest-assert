/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Static utilities.
 */
public final class Utils {

	/**
	 * Line separator (system dependent).
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+");

	// Private constructor to ensure non instantiation.
	private Utils() {
	}

	/**
	 * Get first non null parameter.
	 * If both parameters are null, null will be returned.
	 *
	 * @param obj1 First parameter.
	 * @param obj2 Second parameter.
	 * @param <T> Type of parameters.
	 * @return First non null parameters.
	 */
	public static <T> T firstNonNull(T obj1, T obj2) {
		return obj1 != null ? obj1 : obj2;
	}

	/**
	 * Check that a given value is not null.
	 * If value is null, a {@link NullPointerException} will be thrown
	 * with given message.
	 *
	 * @param obj Value to check.
	 * @param message Message given in {@link NullPointerException}.
	 * @param <T> Type of object.
	 * @return Original object if it is not null.
	 */
	public static <T> T notNull(T obj, String message) {
		if (obj == null) {
			throw new NullPointerException(message);
		}
		return obj;
	}

	/**
	 * Check that a given string is not blank (i.e not null, not empty
	 * and contains other characters that whitespaces).
	 *
	 * @param obj String value to check.
	 * @param message Error message if {@code obj} is blank.
	 * @return Original {@code obj}.
	 * @throws NullPointerException If {@code obj} is null.
	 * @throws IllegalArgumentException If {@code obj} is empty or blank.
	 */
	public static String notBlank(String obj, String message) {
		notNull(obj, message);

		for (char c : obj.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return obj;
			}
		}

		throw new IllegalArgumentException(message);
	}

	/**
	 * Check that a given value is greater or equal than given minimum value.
	 *
	 * @param val Value to check.
	 * @param minValue Minimum value.
	 * @param message Error message.
	 * @return The original value.
	 * @throws IllegalArgumentException If {@code val} is less than {@code minValue}.
	 */
	public static int isGreaterThan(int val, int minValue, String message) {
		if (val < minValue) {
			throw new IllegalArgumentException(message);
		}

		return val;
	}

	/**
	 * Check that a given value is in given range (inclusive).
	 *
	 * @param val Value to check.
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * @param message Error message.
	 * @return The original value.
	 * @throws IllegalArgumentException If {@code val} is less than {@code min} or greater than {@code max}.
	 */
	public static int isInRange(int val, int min, int max, String message) {
		if (val < min || val > max) {
			throw new IllegalArgumentException(message);
		}

		return val;
	}

	/**
	 * Parse a given string value to a long number.
	 *
	 * @param value Value to parse.
	 * @param message Error message if conversion fails.
	 * @return The long value.
	 * @throws IllegalArgumentException If {@code value} is not a valid number.
	 */
	public static Long toLong(String value, String message) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(message, ex);
		}
	}

	/**
	 * Map each element of input list to an output list.
	 *
	 * @param inputs Input list.
	 * @param mapper Mapper function.
	 * @param <T> Input type.
	 * @param <U> Output type.
	 * @return Outputs.
	 */
	public static <T, U> List<U> map(List<T> inputs, Mapper<T, U> mapper) {
		List<U> outputs = new ArrayList<>(inputs.size());
		for (T input : inputs) {
			outputs.add(mapper.apply(input));
		}
		return outputs;
	}

	/**
	 * Filter a list using a given predicate.
	 *
	 * @param inputs List input.
	 * @param predicate Predicate function.
	 * @param <T> Type of inputs.
	 * @return Output (i.e filtered inputs).
	 */
	public static <T> List<T> filter(List<T> inputs, Predicate<T> predicate) {
		List<T> outputs = new ArrayList<>(inputs.size());
		for (T t : inputs) {
			if (predicate.apply(t)) {
				outputs.add(t);
			}
		}
		return outputs;
	}

	/**
	 * Returns {@code true} if at least one element in the collection verify
	 * a predicate.
	 *
	 * @param inputs Collection of inputs.
	 * @param predicate Predicate to check.
	 * @param <T> Type of elements in the collection.
	 * @return {@code true} if at least one element in the collection verify the predicate, {@code false} otherwise.
	 */
	public static <T> boolean some(Iterable<T> inputs, Predicate<T> predicate) {
		for (T input : inputs) {
			if (predicate.apply(input)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Read file and return text content.
	 *
	 * @param file File.
	 * @return File content.
	 */
	public static String readFileToString(Path file) {
		try {
			List<String> lines = Files.readAllLines(file, Charset.defaultCharset());
			return join(lines, LINE_SEPARATOR);
		} catch (IOException ex) {
			throw new UnreadableFileException(ex);
		}
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
}
