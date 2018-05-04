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

/**
 * Simple contract to rest-assert error object.
 * Each error object must provide:
 * - A message with placeholders.
 * - Arguments array that can be used to replace placeholders in original
 *   message.
 * - A formatted message (original message built with placeholders arguments).
 */
public interface RestAssertError {

	/**
	 * Original message.
	 * This message may contain placeholders patterns.
	 *
	 * @return Original message.
	 */
	String message();

	/**
	 * Arguments array that will replace placeholders patterns.
	 * This array may be empty, no placeholders will be replaced.
	 *
	 * @return Arguments array.
	 */
	Object[] args();

	/**
	 * Build formatted error message.
	 * Arguments array will be used in order to replace placeholders pattern
	 * in original message.
	 *
	 * @return Formatted message.
	 */
	String buildMessage();
}
