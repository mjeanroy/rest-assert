/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.data.bindings;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.google.GoogleHttpResponseBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.data.bindings.GoogleHttpResponse.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class GoogleHttpResponseTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;
		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseBuilder()
				.setStatus(expectedStatus)
				.build();

		HttpResponse httpResponse = create(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
	}

	@Test
	public void it_should_check_if_response_contains_header() {
		String headerName = "header-name";
		String headerValue = "header-value";

		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseBuilder()
				.addHeader(headerName, headerValue)
				.build();

		HttpResponse httpResponse = create(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isTrue();
	}

	@Test
	public void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";

		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseBuilder()
				.addHeader(headerName, headerValue)
				.build();

		HttpResponse httpResponse = create(response);
		List<String> result = httpResponse.getHeader(headerName);

		assertThat(result)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1)
				.contains(headerValue);
	}

	@Test
	public void it_should_return_response_body() {
		String body = "foo";
		com.google.api.client.http.HttpResponse response = new GoogleHttpResponseBuilder()
				.setContent(body)
				.build();

		HttpResponse httpResponse = create(response);
		String result = httpResponse.getContent();

		assertThat(result).isEqualTo(body);
	}

	@Test
	public void it_should_return_empty_list_if_set_cookie_header_is_missing() {
		final com.google.api.client.http.HttpResponse response = new GoogleHttpResponseBuilder().build();
		final HttpResponse httpResponse = create(response);
		final List<Cookie> cookies = httpResponse.getCookies();

		assertThat(cookies)
				.isNotNull()
				.isEmpty();
	}

	@Test
	public void it_should_return_all_cookies() {
		final com.google.api.client.http.HttpResponse response = new GoogleHttpResponseBuilder()
				.addHeader("Set-Cookie", "foo=bar")
				.addHeader("Set-Cookie", "quix=123")
				.build();

		final HttpResponse httpResponse = create(response);
		final List<Cookie> cookies = httpResponse.getCookies();

		assertThat(cookies)
				.isNotNull()
				.isNotEmpty()
				.hasSize(2)
				.extracting("name")
				.contains("foo", "quix");
	}
}
