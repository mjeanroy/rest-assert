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

package com.github.mjeanroy.restassert.api.http;

import com.github.mjeanroy.restassert.api.AbstractAssertTest;
import com.github.mjeanroy.restassert.tests.Function;
import com.github.mjeanroy.restassert.tests.models.Header;
import com.github.mjeanroy.restassert.utils.Mapper;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.tests.models.Header.header;
import static com.github.mjeanroy.restassert.utils.Utils.firstNonNull;
import static com.github.mjeanroy.restassert.utils.Utils.map;

public abstract class AbstractMimeTypeInTest<T> extends AbstractAssertTest<T> {

	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_with_expected_mime_type() {
		List<Header> headers = getHeaders();
		for (Header header : headers) {
			invoke(newHttpResponse(header));
			invoke(CUSTOM_MESSAGE, newHttpResponse(header));
		}
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newHttpResponse(header));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(header));
			}
		});
	}

	private void doTest(String msg, final Invocation invocation) {
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

			final String message = firstNonNull(msg, buildErrorMessage(mimeTypes, actualMimeType));

			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invocation.invokeTest(header);
				}
			});
		}
	}

	protected abstract List<String> getMimeTypes();

	private List<Header> getHeaders() {
		return map(getMimeTypes(), new Mapper<String, Header>() {
			@Override
			public Header apply(String input) {
				return header("Content-Type", input + ";charset=UTF-8");
			}
		});
	}

	private String buildErrorMessage(Collection<String> mimeTypes, String actualMimeType) {
		return String.format("Expecting response to have mime type in %s but was %s", mimeTypes, actualMimeType);
	}

	// == Create target HTTP Response

	protected abstract T newHttpResponse(Header header);

	private interface Invocation {
		void invokeTest(Header header);
	}
}
