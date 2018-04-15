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

package com.github.mjeanroy.restassert.tests.builders.apache;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicStatusLine;

/**
 * Build mock instance of {@link StatusLine} class.
 */
class ApacheHttpStatusLineBuilder {

	/**
	 * Cookie name.
	 */
	private int statusCode;

	/**
	 * Protocol version.
	 * This field is required to build valid status line instances.
	 */
	private final ProtocolVersion protocolVersion;

	/**
	 * Create builder.
	 */
	ApacheHttpStatusLineBuilder() {
		this.statusCode = 200;
		this.protocolVersion = new ProtocolVersion("http", 1, 0);
	}

	/**
	 * Set {@link #statusCode}.
	 *
	 * @param statusCode New {@link #statusCode}.
	 * @return Current builder.
	 */
	ApacheHttpStatusLineBuilder setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	StatusLine build() {
		return new BasicStatusLine(protocolVersion, statusCode, null);
	}
}
