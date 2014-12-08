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

package com.github.mjeanroy.rest_assert.internal.assertions.http;

import static com.github.mjeanroy.rest_assert.tests.TestData.newHttpResponseWithHeader;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.mjeanroy.rest_assert.error.http.ShouldHaveMimeType;
import com.github.mjeanroy.rest_assert.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import com.github.mjeanroy.rest_assert.utils.Mapper;
import com.github.mjeanroy.rest_assert.utils.Utils;

public abstract class AbstractMimeTypeInTest extends AbstractAssertionsTest<HttpResponse> {

	protected HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_expected_mime_type() {
		List<Header> headers = getHeaders();
		for (Header header : headers) {
			AssertionResult result = invoke(newResponse(header));
			checkSuccess(result);
		}
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		final List<Header> headers = getHeaders();
		final List<String> mimeType = getMimeTypes();

		int i = 0;
		for (Header h : headers) {
			final String expectedMimeType = mimeType.get(i);
			i++;

			final String actualMimeType = expectedMimeType + "foo";

			final String expectedName = h.getName();
			final String expectedValue = h.getValue();
			final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
			final Header header = header(expectedName, actualValue);

			AssertionResult result = invoke(newResponse(header));

			checkError(result,
					ShouldHaveMimeType.class,
					"Expecting response to have mime type in %s but was %s",
					mimeType, actualMimeType
			);
		}
	}

	protected HttpResponse newResponse(Header header) {
		return newHttpResponseWithHeader(header);
	}

	protected abstract List<String> getMimeTypes();

	protected List<Header> getHeaders() {
		return Utils.map(getMimeTypes(), new Mapper<String, Header>() {
			@Override
			public Header apply(String input) {
				return header("Content-Type", input + ";charset=UTF-8");
			}
		});
	}
}
