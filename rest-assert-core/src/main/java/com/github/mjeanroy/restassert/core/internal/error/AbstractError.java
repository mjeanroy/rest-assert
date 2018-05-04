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

package com.github.mjeanroy.restassert.core.internal.error;

import static java.lang.String.format;
import static java.util.Arrays.copyOf;

/**
 * Abstraction of error message.
 * String representation of error message should be formatted error.
 */
public abstract class AbstractError implements RestAssertError {

	/**
	 * Error message, with placeholders.
	 * This message should not be used without placeholder values.
	 */
	private final String message;

	/**
	 * Arguments that should replace placeholders in original message.
	 * If no argument is provided, this array is an empty array (i.e. not null).
	 */
	private final Object[] args;

	/**
	 * Build new error.
	 *
	 * @param message Original message, with placeholders.
	 * @param args Arguments that will replace placeholders in original message.
	 */
	protected AbstractError(String message, Object[] args) {
		this.message = message;
		this.args = copyOf(args, args.length);
	}

	/**
	 * Get original message with placeholders.
	 *
	 * @return Original message.
	 */
	@Override
	public String message() {
		return message;
	}

	/**
	 * Get a copy of arguments array.
	 *
	 * @return Arguments.
	 */
	@Override
	public Object[] args() {
		return copyOf(args, args.length);
	}

	/**
	 * Build message with placeholder values.
	 *
	 * @return Formatted error message.
	 */
	@Override
	public String buildMessage() {
		return args.length == 0 ? message : format(message, args);
	}

	@Override
	public String toString() {
		return buildMessage();
	}
}
