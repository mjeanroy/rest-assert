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

package com.github.mjeanroy.restassert.core.internal.common;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * Static Number Utilities.
 */
public final class Numbers {

	// Ensure non instantiation.
	private Numbers() {
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
		notNull(value, message);

		try {
			return Long.valueOf(value);
		}
		catch (NumberFormatException ex) {
			throw new IllegalArgumentException(message, ex);
		}
	}
}
