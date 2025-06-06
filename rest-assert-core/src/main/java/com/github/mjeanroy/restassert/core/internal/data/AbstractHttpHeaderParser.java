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

package com.github.mjeanroy.restassert.core.internal.data;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;

/**
 * Abstract template implementation for {@link HttpHeaderParser} interface.
 */
public abstract class AbstractHttpHeaderParser<T extends HttpHeaderValue> implements HttpHeaderParser<T> {

	@Override
	public T parse(String value) {
		notBlank(value, "Header value must be defined to be parsed");
		return doParse(value.trim());
	}

	/**
	 * Parse header value as a raw string and returns valid {@link HttpHeaderValue} instance.
	 * Note that, unlike {@link #parse(String)} method, this method guarantee that raw value
	 * is not {@code null} and not blank.
	 *
	 * @param value Raw value.
	 * @return The {@link HttpHeaderValue} instance.
	 */
	protected abstract T doParse(String value);
}
