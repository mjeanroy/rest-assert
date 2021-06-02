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

import com.github.mjeanroy.restassert.core.internal.error.AbstractError;
import com.github.mjeanroy.restassert.core.internal.error.Message;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;

/**
 * Create mock instance of {@link RestAssertError} class.
 */
public class RestAssertErrorBuilder {

	/**
	 * Error message for expectation field.
	 */
	private Message expectation;

	/**
	 * Error message for mismatch field.
	 */
	private Message mismatch;

	/**
	 * Create builder.
	 */
	public RestAssertErrorBuilder() {
		this.expectation = Message.message("Expectation");
	}

	/**
	 * Set {@link #expectation}.
	 *
	 * @param message New {@link #expectation} message.
	 * @param args New {@link #expectation} arguments.
	 * @return Current builder.
	 */
	public RestAssertErrorBuilder setExpectation(String message, Object[] args) {
		this.expectation = Message.message(message, args);
		return this;
	}

	/**
	 * Set {@link #mismatch}.
	 *
	 * @param message New {@link #mismatch} message.
	 * @param args New {@link #mismatch} arguments.
	 * @return Current builder.
	 */
	public RestAssertErrorBuilder setMismatch(String message, Object[] args) {
		this.mismatch = Message.message(message, args);
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public RestAssertError build() {
		return new MockRestAssertError(expectation, mismatch);
	}

	private static final class MockRestAssertError extends AbstractError {
		private MockRestAssertError(Message expectation, Message mismatch) {
			super(expectation, mismatch);
		}
	}
}
