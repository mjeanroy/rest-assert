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

package com.github.mjeanroy.restassert.core.internal.error.json;

import com.github.mjeanroy.restassert.core.internal.error.AbstractError;
import com.github.mjeanroy.restassert.core.internal.error.Message;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertJsonError;

/**
 * Abstraction of json error message.
 */
abstract class AbstractJsonError extends AbstractError implements RestAssertJsonError, RestAssertError {

	private final String json;

	/**
	 * Entry name of json object that throws the error.
	 */
	private final String entryName;

	/**
	 * Build new error.
	 *
	 * @param expectation Expectation message.
	 */
	AbstractJsonError(String json, Message expectation) {
		this(json, "", expectation);
	}

	/**
	 * Build new error.
	 *
	 * @param expectation Expectation message.
	 */
	AbstractJsonError(String json, Message expectation, Message mismatch) {
		this(json, "", expectation, mismatch);
	}

	/**
	 * Build new error.
	 *
	 * @param expectation Expectation message.
	 */
	AbstractJsonError(String json, String entry, Message expectation) {
		this(json, entry, expectation, null);
	}

	/**
	 * Build new error.
	 *
	 * @param entryName Entry name that throws error.
	 * @param expectation Expectation message.
	 */
	AbstractJsonError(
		String json,
		String entryName,
		Message expectation,
		Message mismatch
	) {
		super(expectation, mismatch);
		this.json = json;
		this.entryName = entryName;
	}

	@Override
	public String entryName() {
		return entryName;
	}

	@Override
	public String json() {
		return json;
	}
}
