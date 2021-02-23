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

package com.github.mjeanroy.restassert.tests.builders;

import com.github.mjeanroy.restassert.core.internal.error.Message;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;

import java.util.Collection;
import java.util.LinkedList;

import static java.util.Collections.addAll;

/**
 * Create mock instance of {@link RestAssertError} class.
 */
public class RestAssertErrorBuilder {

	/**
	 * Error message.
	 */
	private String message;

	/**
	 * Message arguments (i.e placeholder values).
	 */
	private final Collection<Object> args;

	/**
	 * Create builder.
	 */
	public RestAssertErrorBuilder() {
		this.args = new LinkedList<>();
	}

	/**
	 * Set {@link #message}.
	 *
	 * @param message New {@link #message}.
	 * @return Current builder.
	 */
	public RestAssertErrorBuilder setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * Set {@link #args}.
	 *
	 * @param args New {@link #args}.
	 * @return Current builder.
	 */
	public RestAssertErrorBuilder setArgs(Object... args) {
		addAll(this.args, args);
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public RestAssertError build() {
		return new MockRestAssertError(message, args);
	}

	private static final class MockRestAssertError implements RestAssertError {

		private final String message;
		private final Object[] args;

		private MockRestAssertError(String message, Collection<Object> args) {
			this.message = message;
			this.args = args.toArray(new Object[0]);
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
			throw new UnsupportedOperationException();
		}

		@Override
		public Message getExpectation() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Message getMismatch() {
			throw new UnsupportedOperationException();
		}
	}
}
