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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveMimeType.shouldHaveMimeType;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.utils.Utils.notBlank;
import static com.github.mjeanroy.rest_assert.utils.Utils.notEmpty;
import static java.util.Collections.singleton;

/**
 * Check that http response has expected mime type.
 */
public class HasMimeTypeAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(HasMimeTypeAssertion.class);

	/**
	 * Expected mime-type.
	 */
	private final Iterable<String> mimeTypes;

	/**
	 * Flag to know if assertion is made against a single value or an list of values.
	 */
	private final boolean singular;

	/**
	 * Create assertion.
	 *
	 * @param mimeType Mime-Type value.
	 */
	public HasMimeTypeAssertion(String mimeType) {
		super(HttpHeader.CONTENT_TYPE.getName());

		notBlank(mimeType, "Mime-Type value must be defined");
		this.mimeTypes = singleton(mimeType);
		this.singular = true;
	}

	/**
	 * Create assertion.
	 *
	 * @param mimeTypes Mime-Type value.
	 */
	public HasMimeTypeAssertion(Iterable<String> mimeTypes) {
		super(HttpHeader.CONTENT_TYPE.getName());
		this.mimeTypes = notEmpty(mimeTypes, "Mime-Type values must be defined");
		this.singular = false;
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		String contentType = values.get(0);
		log.debug("Parsing Content-Type: '{}'", contentType);

		String actualMimeType = contentType.split(";")[0].trim();
		log.debug("-> Extracted mime-type: '{}'", actualMimeType);

		boolean found = false;
		for (String current : mimeTypes) {
			if (actualMimeType.equalsIgnoreCase(current)) {
				log.debug("-> Found a match with: '{}'", current);
				found = true;
				break;
			}
		}

		return found ?
				success() :
				failure(
						singular ?
								shouldHaveMimeType(mimeTypes.iterator().next(), actualMimeType) :
								shouldHaveMimeType(mimeTypes, actualMimeType)
					);
	}
}
