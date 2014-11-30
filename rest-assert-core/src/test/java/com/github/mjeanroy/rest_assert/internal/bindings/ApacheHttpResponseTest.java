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
import com.github.mjeanroy.rest_assert.internal.data.bindings.ApacheHttpResponse;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApacheHttpResponseTest {

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;
		StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(expectedStatus);
		org.apache.http.HttpResponse response = mock(org.apache.http.HttpResponse.class);
		when(response.getStatusLine()).thenReturn(statusLine);

		HttpResponse httpResponse = ApacheHttpResponse.httpResponse(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
		verify(response).getStatusLine();
		verify(statusLine).getStatusCode();
	}

	@Test
	public void it_should_check_if_http_response_contains_header() {
		String headerName = "header-name";

		Header header1 = newHeader("foo", "foo");
		Header header2 = newHeader(headerName, headerName);
		Header header3 = newHeader("bar", "bar");
		Header[] headers = new Header[] {
				header1, header2, header3
		};

		org.apache.http.HttpResponse response = mock(org.apache.http.HttpResponse.class);
		when(response.getAllHeaders()).thenReturn(headers);

		HttpResponse httpResponse = ApacheHttpResponse.httpResponse(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isTrue();
		verify(response).getAllHeaders();
		verify(header1).getName();
		verify(header2).getName();
		verify(header3, never()).getName();
	}

	@Test
	public void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";

		Header header1 = newHeader("foo", "bar");
		Header header2 = newHeader(headerName, headerValue);
		Header header3 = newHeader("bar", "foo");
		Header[] headers = new Header[] {
				header1, header2, header3
		};

		org.apache.http.HttpResponse response = mock(org.apache.http.HttpResponse.class);
		when(response.getAllHeaders()).thenReturn(headers);

		HttpResponse httpResponse = ApacheHttpResponse.httpResponse(response);
		String result = httpResponse.getHeader(headerName);

		assertThat(result).isEqualTo(headerValue);
		verify(response).getAllHeaders();

		verify(header1).getName();
		verify(header1, never()).getValue();

		verify(header2).getName();
		verify(header2).getValue();

		verify(header3, never()).getName();
		verify(header3, never()).getValue();
	}

	private Header newHeader(String name, String value) {
		Header header = mock(Header.class);
		when(header.getName()).thenReturn(name);
		when(header.getValue()).thenReturn(value);
		return header;
	}
}
