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

package com.github.mjeanroy.restassert.core.internal.error;

import com.github.mjeanroy.restassert.core.internal.common.Strings;

import java.util.Arrays;
import java.util.Objects;

/**
 * Abstraction of error message.
 * String representation of error message should be formatted error.
 */
public abstract class AbstractError implements RestAssertError {

	/**
	 * Expectation message.
	 */
	private final Message expectation;

	/**
	 * Mismatch message.
	 */
	private final Message mismatch;

	/**
	 * Build new error.
	 *
	 * @param message Expectation message.
	 */
	protected AbstractError(String message) {
		this(Message.message(message), null);
	}

	/**
	 * Build new error.
	 *
	 * @param message Expectation message.
	 * @param expectedValue The expected value.
	 */
	protected AbstractError(String message, Object expectedValue) {
		this(Message.message(message, expectedValue), null);
	}

	/**
	 * Build new error.
	 *
	 * @param message Expectation message.
	 * @param expectedValue The expected value.
	 * @param actualValue The actual value.
	 */
	protected AbstractError(String message, Object expectedValue, Object actualValue) {
		this(
			Message.message(message, expectedValue),
			Message.message("was %s", actualValue)
		);
	}

	/**
	 * Build new error.
	 *
	 * @param expectation Expectation message.
	 */
	protected AbstractError(Message expectation) {
		this(expectation, null);
	}

	/**
	 * Build new error.
	 *
	 * @param expectation Expectation message.
	 * @param mismatch Mismatch message.
	 */
	protected AbstractError(Message expectation, Message mismatch) {
		this.expectation = expectation;
		this.mismatch = mismatch;
	}

	/**
	 * Build new error from given set of errors.
	 *
	 * @param errors Original errors.
	 */
	AbstractError(Iterable<RestAssertError> errors) {
		Message expectation = null;
		Message mismatch = null;

		for (RestAssertError error : errors) {
			expectation = Message.concat(expectation, error.getExpectation());
			mismatch = Message.concat(mismatch, error.getMismatch());
		}

		this.expectation = expectation;
		this.mismatch = mismatch;
	}

	/**
	 * Get original message with placeholders.
	 *
	 * @return Original message.
	 */
	@Override
	public final String message() {
		String rawMessage = expectation.getMessage();

		if (mismatch != null) {
			rawMessage += " but " + mismatch.getMessage();
		}

		return rawMessage;
	}

	/**
	 * Get a copy of arguments array.
	 *
	 * @return Arguments.
	 */
	@Override
	public final Object[] args() {
		int nbArgs = 0;

		if (expectation != null) {
			nbArgs += expectation.getNbArgs();
		}

		if (mismatch != null) {
			nbArgs += mismatch.getNbArgs();
		}

		if (nbArgs == 0) {
			return new Object[0];
		}

		Object[] args = new Object[nbArgs];
		int i = 0;

		if (expectation != null) {
			for (Object o : expectation.getArgs()) {
				args[i] = o;
				++i;
			}
		}

		if (mismatch != null) {
			for (Object o : mismatch.getArgs()) {
				args[i] = o;
				++i;
			}
		}

		return args;
	}

	@Override
	public final Message getExpectation() {
		return expectation;
	}

	@Override
	public final Message getMismatch() {
		return mismatch;
	}

	/**
	 * Build message with placeholder values.
	 *
	 * @return Formatted error message.
	 */
	@Override
	public final String buildMessage() {
		String rawMessage = message();
		Object[] args = formatArgs();
		return args.length == 0 ? rawMessage : String.format(rawMessage, args);
	}

	private Object[] formatArgs() {
		return Arrays.stream(args())
			.map(Strings::serialize)
			.toArray();
	}

	@Override
	public String toString() {
		return buildMessage();
	}

	@Override
	public final boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (!Objects.equals(o.getClass(), getClass())) {
			return false;
		}

		AbstractError e = (AbstractError) o;
		return Objects.equals(expectation, e.expectation) && Objects.equals(mismatch, e.mismatch);
	}

	@Override
	public final int hashCode() {
		return Objects.hash(expectation, mismatch);
	}
}
