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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.data.MediaType;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.common.Collections.Mapper;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeader;
import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveMimeType;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

import java.util.Collection;
import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.map;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notEmpty;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveMimeType.shouldHaveMimeType;
import static java.util.Collections.singleton;

/**
 * Check that http response has expected mime type.
 */
public class HasMimeTypeAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(HasMimeTypeAssertion.class);

	/**
	 * Expected mime-type.
	 */
	private final Collection<MediaType> mimeTypes;

	/**
	 * Flag to know if assertion is made against a single value or an list of values.
	 */
	private final boolean singular;

	/**
	 * Create assertion.
	 *
	 * @param mimeType Mime-Type value.
	 */
	public HasMimeTypeAssertion(MediaType mimeType) {
		super(HttpHeader.CONTENT_TYPE.getName());
		this.mimeTypes = singleton(notNull(mimeType, "Mime-Type value must be defined"));
		this.singular = true;
	}

	/**
	 * Create assertion.
	 *
	 * @param mimeTypes Mime-Type value.
	 */
	public HasMimeTypeAssertion(Collection<MediaType> mimeTypes) {
		super(HttpHeader.CONTENT_TYPE.getName());
		this.mimeTypes = notEmpty(mimeTypes, "Mime-Type values must be defined");
		this.singular = false;
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		String contentType = values.get(0);
		log.debug("Parsing Content-Type: {}", contentType);

		String rawValue = contentType.split(";", 2)[0].trim();
		MediaType mediaType = MediaType.parser().parse(rawValue);
		log.debug("-> Extracted mime-type: {} --> {}", rawValue, mediaType);

		boolean found = false;

		for (MediaType current : mimeTypes) {
			if (current.equals(mediaType)) {
				log.debug("-> Found a match with: '{}'", current);
				found = true;
				break;
			}
		}

		return found ? success() : failure(getFailure(rawValue));
	}

	/**
	 * Generate error instance.
	 *
	 * @param actualMimeType The actual mime-type.
	 * @return The error instance.
	 */
	private ShouldHaveMimeType getFailure(String actualMimeType) {
		return singular ? getSingularFailure(actualMimeType) : getPluralFailure(actualMimeType);
	}

	/**
	 * Get failure instance when there was only one expected mime-type to test.
	 *
	 * @param actualMimeType The actual mime type.
	 * @return The failure instance.
	 */
	private ShouldHaveMimeType getSingularFailure(String actualMimeType) {
		MediaType mediaType = mimeTypes.iterator().next();
		String rawValue = mediaType.serializeValue();
		return shouldHaveMimeType(rawValue, actualMimeType);
	}

	/**
	 * Get failure instance when there was a list of expected media-type.
	 *
	 * @param actualMimeType The actual mime type.
	 * @return The failure instance.
	 */
	private ShouldHaveMimeType getPluralFailure(String actualMimeType) {
		List<String> rawValues = map(mimeTypes, new Mapper<MediaType, String>() {
			@Override
			public String apply(MediaType input) {
				return input.serializeValue();
			}
		});

		return shouldHaveMimeType(rawValues, actualMimeType);
	}
}
