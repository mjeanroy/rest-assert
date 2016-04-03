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

package com.github.mjeanroy.rest_assert.internal.bindings;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.data.bindings.asynchttp.AsyncHttpResponse;
import com.github.mjeanroy.rest_assert.internal.exceptions.UnparseableResponseBodyException;
import com.github.mjeanroy.rest_assert.tests.mocks.async_http.AsyncHttpResponseMockBuilder;
import com.ning.http.client.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AsyncHttpResponseTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;
		Response response = new AsyncHttpResponseMockBuilder()
			.setStatusCode(expectedStatus)
			.build();

		HttpResponse httpResponse = AsyncHttpResponse.create(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
		verify(response).getStatusCode();
	}

	@Test
	public void it_should_check_if_http_response_contains_header() {
		String headerName = "header-name";

		Response response = new AsyncHttpResponseMockBuilder()
			.addHeader(headerName, "foo")
			.build();

		HttpResponse httpResponse = AsyncHttpResponse.create(response);
		assertThat(httpResponse.hasHeader(headerName)).isTrue();
		assertThat(httpResponse.hasHeader(headerName.toUpperCase())).isTrue();
		assertThat(httpResponse.hasHeader(headerName.toLowerCase())).isTrue();
	}

	@Test
	public void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";

		Response response = new AsyncHttpResponseMockBuilder()
			.addHeader(headerName, headerValue)
			.build();

		HttpResponse httpResponse = AsyncHttpResponse.create(response);
		String result = httpResponse.getHeader(headerName);

		assertThat(result).isEqualTo(headerValue);
		verify(response).getHeader(headerName);
	}

	@Test
	public void it_should_return_response_body() throws Exception {
		String body = "foo";
		Response response = new AsyncHttpResponseMockBuilder()
			.setResponseBody(body)
			.build();

		HttpResponse httpResponse = AsyncHttpResponse.create(response);
		String result = httpResponse.getContent();

		assertThat(result).isEqualTo(body);
		verify(response).getResponseBody();
	}

	@Test
	public void it_should_return_custom_exception_if_body_is_not_parseable() throws Exception {
		IOException ex = mock(IOException.class);
		Response response = new AsyncHttpResponseMockBuilder().build();
		when(response.getResponseBody()).thenThrow(ex);

		thrown.expect(UnparseableResponseBodyException.class);

		HttpResponse httpResponse = AsyncHttpResponse.create(response);
		httpResponse.getContent();
	}
}
