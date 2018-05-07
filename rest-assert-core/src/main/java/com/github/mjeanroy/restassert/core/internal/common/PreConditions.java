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

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Set of pre-conditions.
 */
public final class PreConditions {

	private PreConditions() {
	}

	/**
	 * Check that a given string is not empty (i.e not null, not empty).
	 *
	 * @param input String to check.
	 * @param message Error message if {@code obj} is blank.
	 * @return Original {@code obj}.
	 * @throws NullPointerException If {@code obj} is null.
	 * @throws IllegalArgumentException If {@code obj} is empty.
	 */
	public static String notEmpty(String input, String message) {
		notNull(input, message);

		if (input.isEmpty()) {
			throw new IllegalArgumentException(message);
		}

		return input;
	}

	/**
	 * Check that a given collection is not empty (i.e not null, not empty).
	 *
	 * @param list Collection to check.
	 * @param message Error message if {@code obj} is blank.
	 * @return Original {@code obj}.
	 * @throws NullPointerException If {@code obj} is null.
	 * @throws IllegalArgumentException If {@code obj} is empty.
	 */
	public static <T> Iterable<T> notEmpty(Iterable<T> list, String message) {
		notNull(list, message);
		if (!list.iterator().hasNext()) {
			throw new IllegalArgumentException(message);
		}

		return list;
	}

	/**
	 * Check that a given collection is not empty (i.e not null, not empty).
	 *
	 * @param list Collection to check.
	 * @param message Error message if {@code obj} is blank.
	 * @return Original {@code obj}.
	 * @throws NullPointerException If {@code obj} is null.
	 * @throws IllegalArgumentException If {@code obj} is empty.
	 */
	public static <T> Collection<T> notEmpty(Collection<T> list, String message) {
		notNull(list, message);

		if (list.isEmpty()) {
			throw new IllegalArgumentException(message);
		}

		return list;
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
	 * Check that a given value is greater or equal than zero.
	 *
	 * @param val Value to check.
	 * @param message Error message.
	 * @return The original value.
	 * @throws IllegalArgumentException If {@code val} is less than zero.
	 */
	public static int isPositive(int val, String message) {
		return isGreaterThan(val, 0, message);
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
	 * Check that given string match given pattern.
	 * @param value String value.
	 * @param pattern Pattern to check.
	 * @param message Error message.
	 * @return Original string.
	 * @throws NullPointerException If {@code value} or {@code pattern} are {@code null}.
	 * @throws IllegalArgumentException If {@code value} does not match {@code pattern}.
	 */
	public static String match(String value, Pattern pattern, String message) {
		notNull(value, message);
		notNull(pattern, message);

		if (!pattern.matcher(value).matches()) {
			throw new IllegalArgumentException(message);
		}

		return value;
	}
}
