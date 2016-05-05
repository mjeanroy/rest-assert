/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.assertions.impl;

import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.internal.data.HttpHeader;

import java.util.List;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveCharset.shouldHaveCharset;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.utils.Utils.notBlank;

/**
 * Check that http response has a content-type header with
 * expected charset.
 */
public class HasCharsetAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Expected charset.
	 */
	private final String charset;

	/**
	 * Create assertion.
	 *
	 * @param charset Charset name.
	 */
	public HasCharsetAssertion(String charset) {
		super(HttpHeader.CONTENT_TYPE.getName());
		this.charset = notBlank(charset, "Charset value must be defined");
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		String contentType = values.get(0);
		String[] contentTypeParts = contentType.split(";");
		if (contentTypeParts.length == 1) {
			return failure(shouldHaveCharset());
		}

		String actualCharset = contentTypeParts[1].trim().split("=")[1].trim();
		return actualCharset.equalsIgnoreCase(charset) ?
				success() :
				failure(shouldHaveCharset(charset, actualCharset));
	}
}
