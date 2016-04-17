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

package com.github.mjeanroy.rest_assert.internal.data.bindings;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.exceptions.NonParsableResponseBodyException;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpHeadersMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpResponseMockBuilder;
import com.google.api.client.http.HttpHeaders;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.mjeanroy.rest_assert.internal.data.bindings.GoogleHttpResponse.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(com.google.api.client.http.HttpResponse.class)
public class GoogleHttpResponseTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;
		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseMockBuilder()
			.setStatusCode(expectedStatus)
			.build();

		HttpResponse httpResponse = create(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
		verify(response).getStatusCode();
	}

	@Test
	public void it_should_check_if_response_contains_header() {
		String headerName = "header-name";
		String headerValue = "header-value";

		HttpHeaders httpHeaders = new GoogleHttpHeadersMockBuilder()
			.addHeader(headerName, headerValue)
			.build();

		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseMockBuilder()
			.setHeaders(httpHeaders)
			.build();

		HttpResponse httpResponse = create(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isTrue();
		verify(response).getHeaders();
		verify(httpHeaders).getFirstHeaderStringValue(headerName);
	}

	@Test
	public void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";

		HttpHeaders httpHeaders = new GoogleHttpHeadersMockBuilder()
			.addHeader(headerName, headerValue)
			.build();

		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseMockBuilder()
			.setHeaders(httpHeaders)
			.build();

		HttpResponse httpResponse = create(response);
		String result = httpResponse.getHeader(headerName);

		assertThat(result).isEqualTo(headerValue);
		verify(response).getHeaders();
		verify(httpHeaders).getFirstHeaderStringValue(headerName);
	}

	@Test
	public void it_should_return_response_body() throws Exception {
		String body = "foo";
		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseMockBuilder()
			.setContent(Charset.defaultCharset(), body)
			.build();

		HttpResponse httpResponse = create(response);
		String result = httpResponse.getContent();

		assertThat(result).isEqualTo(body);
		verify(response).getContent();
		verify(response).getContentCharset();
	}

	@Test
	public void it_should_return_custom_exception_if_body_is_not_parsable() throws Exception {
		IOException ex = mock(IOException.class);
		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseMockBuilder().build();
		when(response.getContent()).thenThrow(ex);

		thrown.expect(NonParsableResponseBodyException.class);

		HttpResponse httpResponse = create(response);
		httpResponse.getContent();
	}
}
