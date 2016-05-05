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

package com.github.mjeanroy.rest_assert.assertj.internal.http.mime_type;

import static com.github.mjeanroy.rest_assert.assertj.tests.AssertJUtils.formatList;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;
import static com.github.mjeanroy.rest_assert.utils.Utils.map;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilderImpl;
import org.junit.Test;

import com.github.mjeanroy.rest_assert.assertj.internal.HttpResponses;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import com.github.mjeanroy.rest_assert.utils.Mapper;

public abstract class AbstractHttpResponsesMimeTypeInTest {

	protected HttpResponses httpResponses = HttpResponses.instance();

	@Test
	public void should_pass_if_mime_type_is_ok() {
		List<Header> headers = getHeader();
		for (Header header : headers) {
			HttpResponse httpResponse = newHttpResponse(header);
			invoke(httpResponse);
		}
	}

	@Test
	public void should_fail_if_header_is_not_available() {
		final List<Header> headers = getHeader();
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

			final HttpResponse httpResponse = newHttpResponse(header);

			try {
				invoke(httpResponse);
				failBecauseExpectedAssertionErrorWasNotThrown();
			}
			catch (AssertionError e) {
				assertThat(e.getMessage())
						.isNotNull()
						.isNotEmpty()
						.isEqualTo(format("Expecting response to have mime type in %s but was \"%s\"", formatList(mimeTypes), actualMimeType));
			}
		}
	}

	protected abstract List<String> getMimeTypes();

	protected List<Header> getHeader() {
		return map(getMimeTypes(), new Mapper<String, Header>() {
			@Override
			public Header apply(String input) {
				return header("Content-Type", input + ";charset=UTF-8");
			}
		});
	}

	protected HttpResponse newHttpResponse(Header header) {
		return new HttpResponseMockBuilderImpl()
			.addHeader(header)
			.build();
	}

	protected abstract void invoke(HttpResponse httpResponse);
}
