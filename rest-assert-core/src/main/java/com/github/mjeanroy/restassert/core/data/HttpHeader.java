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

package com.github.mjeanroy.restassert.core.data;

import java.util.List;

public interface HttpHeader {

	/**
	 * Header name.
	 *
	 * @return Header name.
	 */
	String getName();

	/**
	 * Header value:
	 *
	 * <ul>
	 *   <li>May be {@code null}</li>
	 *   <li>For multi-value headers, all values are joined with a comma.</li>
	 * </ul>
	 *
	 * @return Header value.
	 */
	default String getValue() {
		return String.join(",", getValues());
	}

	/**
	 * Get header values, may be empty.
	 *
	 * @return Header values.
	 */
	List<String> getValues();

	/**
	 * Create new HTTP Header.
	 *
	 * Since HTTP header name is case-insensitive, name is normalized so each call returns a consistent
	 * output and name can be used as map key.
	 *
	 * @param name Name.
	 * @param values Values.
	 * @return Header.
	 */
	static HttpHeader of(String name, List<String> values) {
		return new DefaultHttpHeader(name, values);
	}
}
