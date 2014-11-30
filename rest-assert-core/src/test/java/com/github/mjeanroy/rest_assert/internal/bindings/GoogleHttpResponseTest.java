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
import com.github.mjeanroy.rest_assert.internal.data.bindings.GoogleHttpResponse;
import com.google.api.client.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(com.google.api.client.http.HttpResponse.class)
public class GoogleHttpResponseTest {

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;
		com.google.api.client.http.HttpResponse response = mock(com.google.api.client.http.HttpResponse.class);
		when(response.getStatusCode()).thenReturn(expectedStatus);

		HttpResponse httpResponse = GoogleHttpResponse.httpResponse(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
		verify(response).getStatusCode();
	}

	@Test
	public void it_should_check_if_response_contains_header() {
		String headerName = "header-name";
		String headerValue = "header-value";

		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		when(httpHeaders.getFirstHeaderStringValue(headerName)).thenReturn(headerValue);
		com.google.api.client.http.HttpResponse response = mock(com.google.api.client.http.HttpResponse.class);
		when(response.getHeaders()).thenReturn(httpHeaders);

		HttpResponse httpResponse = GoogleHttpResponse.httpResponse(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isTrue();
		verify(response).getHeaders();
		verify(httpHeaders).getFirstHeaderStringValue(headerName);
	}

	@Test
	public void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";

		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		when(httpHeaders.getFirstHeaderStringValue(headerName)).thenReturn(headerValue);
		com.google.api.client.http.HttpResponse response = mock(com.google.api.client.http.HttpResponse.class);
		when(response.getHeaders()).thenReturn(httpHeaders);

		HttpResponse httpResponse = GoogleHttpResponse.httpResponse(response);
		String result = httpResponse.getHeader(headerName);

		assertThat(result).isEqualTo(headerValue);
		verify(response).getHeaders();
		verify(httpHeaders).getFirstHeaderStringValue(headerName);
	}
}
