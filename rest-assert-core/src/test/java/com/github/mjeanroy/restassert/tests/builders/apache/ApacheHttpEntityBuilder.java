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

package com.github.mjeanroy.restassert.tests.builders.apache;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * DefaultCookieBuilder to create mock instances of {@link HttpEntity} class.
 */
class ApacheHttpEntityBuilder {

	/**
	 * Body content.
	 */
	private String content;

	/**
	 * Create builder.
	 */
	ApacheHttpEntityBuilder() {
		this.content = "";
	}

	/**
	 * Set {@link #content}.
	 *
	 * @param content New {@link #content}.
	 * @return Current builder.
	 */
	ApacheHttpEntityBuilder setContent(String content) {
		this.content = content;
		return this;
	}

	/**
	 * Create mock instance of {@link HttpEntity} class.
	 *
	 * @return Mock instance.
	 */
	HttpEntity build() {
		return new StringEntity(content, ContentType.DEFAULT_TEXT);
	}
}
