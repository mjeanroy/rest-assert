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
import com.github.mjeanroy.rest_assert.internal.data.bindings.AsyncHttpResponse;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AsyncHttpResponseTest {

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;
		Response response = mock(Response.class);
		when(response.getStatusCode()).thenReturn(expectedStatus);

		HttpResponse httpResponse = AsyncHttpResponse.httpResponse(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
		verify(response).getStatusCode();
	}

	@Test
	public void it_should_check_if_http_response_contains_header() {
		String headerName = "header-name";
		FluentCaseInsensitiveStringsMap map = mock(FluentCaseInsensitiveStringsMap.class);
		when(map.containsKey(headerName)).thenReturn(true);

		Response response = mock(Response.class);
		when(response.getHeaders()).thenReturn(map);

		HttpResponse httpResponse = AsyncHttpResponse.httpResponse(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isTrue();
		verify(response).getHeaders();
		verify(map).containsKey(headerName);
	}

	@Test
	public void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";
		FluentCaseInsensitiveStringsMap map = mock(FluentCaseInsensitiveStringsMap.class);
		when(map.containsKey(headerName)).thenReturn(true);

		Response response = mock(Response.class);
		when(response.getHeaders()).thenReturn(map);
		when(response.getHeader(headerName)).thenReturn(headerValue);

		HttpResponse httpResponse = AsyncHttpResponse.httpResponse(response);
		String result = httpResponse.getHeader(headerName);

		assertThat(result).isEqualTo(headerValue);
		verify(response).getHeader(headerName);
	}
}
