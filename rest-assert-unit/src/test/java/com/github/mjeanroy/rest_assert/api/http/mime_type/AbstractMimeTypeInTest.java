/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.api.http.mime_type;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.rest_assert.tests.TestData.newHttpResponseWithHeader;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;
import static com.github.mjeanroy.rest_assert.utils.Utils.map;
import static java.lang.String.format;

import java.util.List;

import org.junit.Test;

import com.github.mjeanroy.rest_assert.api.AbstractAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import com.github.mjeanroy.rest_assert.utils.Mapper;

public abstract class AbstractMimeTypeInTest extends AbstractAssertTest<HttpResponse> {

	@Test
	public void it_should_pass_with_expected_mime_type() {
		List<Header> headers = getHeaders();
		for (Header header : headers) {
			invoke(newResponse(header));
			invoke("foo", newResponse(header));
		}
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		final List<Header> headers = getHeaders();
		final List<String> mimeTypes = getMimeTypes();

		int i = 0;
		for (Header expectedHeader : headers) {
			final String expectedMimeType = mimeTypes.get(i);
			i++;

			final String actualMimeType = expectedMimeType + "foo";
			final String expectedName = expectedHeader.getName();
			final String expectedValue = expectedHeader.getValue();
			final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
			final Header header = header(expectedName, actualValue);

			final String message = format("Expecting response to have mime type in %s but was %s", mimeTypes, actualMimeType);

			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invoke(newResponse(header));
				}
			});
		}
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		final List<Header> headers = getHeaders();
		final List<String> mimeTypes = getMimeTypes();

		int i = 0;
		for (Header expectedHeader : headers) {
			final String expectedMimeType = mimeTypes.get(i);
			i++;

			final String actualMimeType = expectedMimeType + "foo";
			final String expectedName = expectedHeader.getName();
			final String expectedValue = expectedHeader.getValue();
			final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
			final Header header = header(expectedName, actualValue);

			final String message = "foo";

			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invoke(message, newResponse(header));
				}
			});
		}
	}

	protected HttpResponse newResponse(Header header) {
		return newHttpResponseWithHeader(header);
	}

	protected abstract List<String> getMimeTypes();

	protected List<Header> getHeaders() {
		return map(getMimeTypes(), new Mapper<String, Header>() {
			@Override
			public Header apply(String input) {
				return header("Content-Type", input + ";charset=UTF-8");
			}
		});
	}
}
