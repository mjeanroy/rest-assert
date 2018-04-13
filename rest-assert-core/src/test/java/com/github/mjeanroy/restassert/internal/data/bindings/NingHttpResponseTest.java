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

package com.github.mjeanroy.restassert.internal.data.bindings;

import java.util.List;

import com.github.mjeanroy.restassert.internal.data.Cookie;
import com.github.mjeanroy.restassert.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.ning.NingHttpResponseBuilder;
import com.ning.http.client.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;

public class NingHttpResponseTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_return_status_code() throws Exception {
		int expectedStatus = 200;
		Response response = new NingHttpResponseBuilder()
				.setStatus(expectedStatus)
				.build();

		HttpResponse httpResponse = NingHttpResponse.create(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
	}

	@Test
	public void it_should_check_if_http_response_contains_header() throws Exception {
		String headerName = "header-name";

		Response response = new NingHttpResponseBuilder()
				.addHeader(headerName, "foo")
				.build();

		HttpResponse httpResponse = NingHttpResponse.create(response);
		assertThat(httpResponse.hasHeader(headerName)).isTrue();
		assertThat(httpResponse.hasHeader(headerName.toUpperCase())).isTrue();
		assertThat(httpResponse.hasHeader(headerName.toLowerCase())).isTrue();
	}

	@Test
	public void it_should_return_header_values() throws Exception {
		String headerName = "header-name";
		String headerValue = "header-value";

		Response response = new NingHttpResponseBuilder()
				.addHeader(headerName, headerValue)
				.build();

		HttpResponse httpResponse = NingHttpResponse.create(response);
		List<String> result = httpResponse.getHeader(headerName);

		assertThat(result)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1)
				.contains(headerValue);
	}

	@Test
	public void it_should_return_response_body() throws Exception {
		String body = "foo";
		Response response = new NingHttpResponseBuilder()
				.setContent(body)
				.build();

		HttpResponse httpResponse = NingHttpResponse.create(response);
		String result = httpResponse.getContent();

		assertThat(result).isEqualTo(body);
	}

	@Test
	public void it_should_return_empty_list_if_set_cookie_header_is_missing() {
		final Response response = new NingHttpResponseBuilder().build();
		final HttpResponse httpResponse = NingHttpResponse.create(response);
		final List<Cookie> cookies = httpResponse.getCookies();

		assertThat(cookies)
				.isNotNull()
				.isEmpty();
	}

	@Test
	public void it_should_return_all_cookies() {
		final Response response = new NingHttpResponseBuilder()
				.addHeader("Set-Cookie", "foo=bar")
				.addHeader("Set-Cookie", "quix=123")
				.build();

		final HttpResponse httpResponse = NingHttpResponse.create(response);
		final List<Cookie> cookies = httpResponse.getCookies();

		assertThat(cookies)
				.isNotNull()
				.isNotEmpty()
				.hasSize(2)
				.extracting("name")
				.contains("foo", "quix");
	}
}
