/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import static org.mockito.Mockito.spy;

/**
 * Builder to create mock for {@link Header} class.
 */
public class ApacheHttpHeaderMockBuilder {

	/**
	 * Header name.
	 */
	private String name;

	/**
	 * Header value.
	 */
	private String value;

	/**
	 * Set {@link #name}.
	 *
	 * @param name New {@link #name}.
	 * @return Current builder.
	 */
	public ApacheHttpHeaderMockBuilder setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set {@link #value}.
	 *
	 * @param value New {@link #value}.
	 * @return Current builder.
	 */
	public ApacheHttpHeaderMockBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Create mock instance of {@link Header} class.
	 *
	 * @return Mock instance.
	 */
	public Header build() {
		return spy(new BasicHeader(name, value));
	}
}