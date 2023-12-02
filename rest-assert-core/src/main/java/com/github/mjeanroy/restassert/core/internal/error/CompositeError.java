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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.mjeanroy.restassert.core.internal.common.Collections.sizeOf;
import static com.github.mjeanroy.restassert.core.internal.common.Files.LINE_SEPARATOR;

/**
 * Composite error representation.
 */
public class CompositeError implements RestAssertError {

	public static final String MESSAGES_SEPARATOR = "," + LINE_SEPARATOR;

	public static CompositeError composeErrors(Iterable<RestAssertError> errors) {
		return new CompositeError(errors);
	}

	/**
	 * The original error.
	 */
	private final List<RestAssertError> errors;

	private CompositeError(Iterable<RestAssertError> errors) {
		this.errors = new ArrayList<>(sizeOf(errors, 10));
		for (RestAssertError error : errors) {
			this.errors.add(error);
		}
	}

	@Override
	public String message() {
		return errors.stream()
			.map(RestAssertError::message)
			.collect(Collectors.joining(MESSAGES_SEPARATOR));
	}

	@Override
	public Object[] args() {
		int nbArgs = 0;
		for (RestAssertError error : errors) {
			nbArgs += error.args().length;
		}

		Object[] args = new Object[nbArgs];
		if (nbArgs == 0) {
			return args;
		}

		int i = 0;
		for (RestAssertError error : errors) {
			for (Object arg : error.args()) {
				args[i] = arg;
				++i;
			}
		}

		return args;
	}

	@Override
	public String buildMessage() {
		Object[] args = args();
		String message = message();
		return args.length == 0 ? message : String.format(message, args);
	}

	@Override
	public Message getExpectation() {
		Message message = null;
		for (RestAssertError error : errors) {
			message = Message.concat(message, error.getExpectation());
		}

		return message;
	}

	@Override
	public Message getMismatch() {
		Message message = null;
		for (RestAssertError error : errors) {
			message = Message.concat(message, error.getMismatch());
		}

		return message;
	}
}
