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

package com.github.mjeanroy.restassert.internal.assertions.impl;

import com.github.mjeanroy.restassert.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.internal.data.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.mjeanroy.restassert.error.http.ShouldHaveCharset.shouldHaveCharset;
import static com.github.mjeanroy.restassert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.utils.PreConditions.notBlank;

/**
 * Check that http response has a content-type header with
 * expected charset.
 */
public class HasCharsetAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(HasCharsetAssertion.class);

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
		log.debug("Extracting charset from: {}", contentType);

		String[] contentTypeParts = contentType.split(";");
		if (contentTypeParts.length == 1) {
			log.debug("Charset value is not specified, fail");
			return failure(shouldHaveCharset());
		}

		String actualCharset = contentTypeParts[1].split("=")[1].trim();
		log.debug("Comparing charset '{}' with '{}'", charset, actualCharset);

		return actualCharset.equalsIgnoreCase(charset) ?
				success() :
				failure(shouldHaveCharset(charset, actualCharset));
	}
}
