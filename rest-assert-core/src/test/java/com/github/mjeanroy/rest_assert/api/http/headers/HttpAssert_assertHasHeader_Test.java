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

package com.github.mjeanroy.rest_assert.api.http.headers;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.Header;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.api.http.HttpAssert.assertHasHeader;
import static com.github.mjeanroy.rest_assert.tests.Header.header;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static java.lang.String.format;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpAssert_assertHasHeader_Test {

	@Test
	public void it_should_pass_with_expected_header() {
		Header header = header("foo", "bar");
		invoke(newResponse(header), header.getName());
		invoke("foo", newResponse(header), header.getName());
	}

	@Test
	public void it_should_fail_if_header_is_not_available() {
		final Header expectedHeader = header("foo", "bar");
		final Header header = header("bar", "foo");
		final String message = format("Expecting response to have header %s", expectedHeader.getName());

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(newResponse(header), expectedHeader.getName());
			}
		});
	}

	@Test
	public void it_should_pass_with_custom_message_with_response_different_than_200() {
		final Header expectedHeader = header("foo", "bar");
		final Header header = header("bar", "foo");
		final String message = format("Expecting response to have header %s", expectedHeader.getName());

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(message, newResponse(header), expectedHeader.getName());
			}
		});
	}

	private void invoke(HttpResponse httpResponse, String headerName) {
		assertHasHeader(httpResponse, headerName);
	}

	private void invoke(String message, HttpResponse httpResponse, String headerName) {
		assertHasHeader(message, httpResponse, headerName);
	}

	private HttpResponse newResponse(Header header) {
		HttpResponse response = mock(HttpResponse.class);
		when(response.hasHeader(header.getName())).thenReturn(true);
		return response;
	}
}
