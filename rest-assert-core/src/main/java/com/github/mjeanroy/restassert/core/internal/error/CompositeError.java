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

package com.github.mjeanroy.restassert.core.internal.error;

import java.util.LinkedList;
import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.common.Files.LINE_SEPARATOR;
import static java.util.Collections.addAll;

/**
 * Composite error representation.
 */
public class CompositeError implements RestAssertError {

	public static CompositeError composeErrors(Iterable<RestAssertError> errors) {
		return new CompositeError(errors);
	}

	/**
	 * The error message.
	 */
	private final String message;

	/**
	 * The message arguments.
	 */
	private final Object[] args;

	private CompositeError(Iterable<RestAssertError> errors) {
		this.message = composeMessage(errors);
		this.args = composeArgs(errors);
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public Object[] args() {
		return args;
	}

	@Override
	public String buildMessage() {
		return args.length == 0 ? message : String.format(message, args);
	}

	@Override
	public String getExpectation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getMismatch() {
		throw new UnsupportedOperationException();
	}

	private static String composeMessage(Iterable<RestAssertError> errors) {
		String separator = "," + LINE_SEPARATOR;
		StringBuilder sb = new StringBuilder();
		for (RestAssertError error : errors) {
			sb.append(error.message()).append(separator);
		}

		return sb.substring(0, sb.length() - separator.length()).trim();
	}

	private static Object[] composeArgs(Iterable<RestAssertError> errors) {
		List<Object> args = new LinkedList<>();
		for (RestAssertError error : errors) {
			addAll(args, error.args());
		}

		return args.toArray();
	}
}
