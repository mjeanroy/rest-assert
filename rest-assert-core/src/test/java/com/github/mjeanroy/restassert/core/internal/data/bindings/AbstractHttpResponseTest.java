/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHttpResponseTest<T> {

	@Test
	void it_should_create_null_http_response() {
		assertThat(create(null)).isNull();
	}

	@Test
	void it_should_return_status_code() {
		int expectedStatus = 200;
		T response = getBuilder().setStatus(expectedStatus).build();

		HttpResponse httpResponse = create(response);
		int status = httpResponse.getStatus();
		assertThat(status).isEqualTo(expectedStatus);
	}

	@Test
	void it_should_check_if_http_response_contains_header() {
		String headerName = "header-name";
		T response = getBuilder()
			.addHeader("foo", "foo")
			.addHeader(headerName, headerName)
			.addHeader("bar", "bar")
			.build();

		HttpResponse httpResponse = create(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);
		assertThat(containsHeader).isTrue();
	}

	@Test
	void it_should_return_false_if_http_response_does_not_contain_header() {
		String headerName = "header-name";
		T response = getBuilder()
			.addHeader("foo", "foo")
			.addHeader("bar", "bar")
			.build();

		HttpResponse httpResponse = create(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);
		assertThat(containsHeader).isFalse();
	}

	@Test
	void it_should_return_header_value() {
		String headerName = "header-name";
		String headerValue = "header-value";
		T response = getBuilder()
			.addHeader("foo", "bar")
			.addHeader(headerName, headerValue)
			.addHeader("bar", "foo")
			.build();

		HttpResponse httpResponse = create(response);
		List<String> result = httpResponse.getHeader(headerName);
		assertThat(result).hasSize(1).containsOnly(
			headerValue
		);
	}

	@Test
	void it_should_return_header_value_with_null_if_header_does_not_exist() {
		String headerName = "header-name";
		T response = getBuilder()
			.addHeader("foo", "bar")
			.addHeader("bar", "foo")
			.build();

		HttpResponse httpResponse = create(response);
		List<String> result = httpResponse.getHeader(headerName);
		assertThat(result).isNotNull().isEmpty();
	}

	@Test
	void it_should_return_response_body() {
		String body = "foo";
		T response = getBuilder().setContent(body).build();

		HttpResponse httpResponse = create(response);
		String result = httpResponse.getContent();
		assertThat(result).isEqualTo(body);
	}

	@Test
	void it_should_return_empty_list_if_set_cookie_header_is_missing() {
		T response = getBuilder().build();
		HttpResponse httpResponse = create(response);
		List<Cookie> cookies = httpResponse.getCookies();
		assertThat(cookies).isNotNull().isEmpty();
	}

	@Test
	void it_should_return_all_cookies() {
		T response = getBuilder()
			.addHeader("Set-Cookie", "foo=bar")
			.addHeader("Set-Cookie", "quix=123")
			.build();

		HttpResponse httpResponse = create(response);
		List<Cookie> cookies = httpResponse.getCookies();
		assertThat(cookies).hasSize(2).extracting(Cookie::getName).contains(
			"foo",
			"quix"
		);
	}

	protected abstract HttpResponseBuilder<T> getBuilder();

	protected abstract HttpResponse create(T response);
}
