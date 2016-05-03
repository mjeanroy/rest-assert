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

import java.io.IOException;
import java.util.List;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.exceptions.NonParsableResponseBodyException;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpResponseMockBuilder;
import org.apache.http.HttpEntity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.github.mjeanroy.rest_assert.internal.data.bindings.ApacheHttpResponse.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;

public class ApacheHttpResponseTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_return_status_code() {
		int expectedStatus = 200;

		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.setStatus(expectedStatus)
				.build();

		HttpResponse httpResponse = create(response);
		int status = httpResponse.getStatus();

		assertThat(status).isEqualTo(expectedStatus);
	}

	@Test
	public void it_should_check_if_http_response_contains_header() {
		final String headerName = "header-name";

		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.addHeader("foo", "foo")
				.addHeader(headerName, headerName)
				.addHeader("bar", "bar")
				.build();

		HttpResponse httpResponse = create(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isTrue();
	}

	@Test
	public void it_should_return_false_if_http_response_does_not_contain_header() {
		final String headerName = "header-name";

		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.addHeader("foo", "foo")
				.addHeader("bar", "bar")
				.build();

		HttpResponse httpResponse = create(response);
		boolean containsHeader = httpResponse.hasHeader(headerName);

		assertThat(containsHeader).isFalse();
	}

	@Test
	public void it_should_return_header_value() {
		final String headerName = "header-name";
		final String headerValue = "header-value";

		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.addHeader("foo", "bar")
				.addHeader(headerName, headerValue)
				.addHeader("bar", "foo")
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
	public void it_should_return_header_value_with_null_if_header_does_not_exist() {
		final String headerName = "header-name";
		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.addHeader("foo", "bar")
				.addHeader("bar", "foo")
				.build();

		HttpResponse httpResponse = create(response);
		List<String> result = httpResponse.getHeader(headerName);

		assertThat(result)
				.isNotNull()
				.isEmpty();
	}

	@Test
	public void it_should_return_response_body() throws Exception {
		String body = "foo";

		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.setContent(body)
				.build();

		HttpResponse httpResponse = create(response);
		String result = httpResponse.getContent();

		assertThat(result).isEqualTo(body);
	}

	@Test
	public void it_should_return_custom_exception_if_body_is_not_parsable() throws Exception {
		org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.setContent("test")
				.build();

		HttpEntity entity = response.getEntity();
		when(entity.getContent()).thenThrow(new IOException());

		thrown.expect(NonParsableResponseBodyException.class);

		HttpResponse httpResponse = create(response);
		httpResponse.getContent();
	}

	@Test
	public void it_should_return_empty_list_if_set_cookie_header_is_missing() {
		final org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder().build();
		final HttpResponse httpResponse = ApacheHttpResponse.create(response);
		final List<Cookie> cookies = httpResponse.getCookies();

		assertThat(cookies)
				.isNotNull()
				.isEmpty();
	}

	@Test
	public void it_should_return_all_cookies() {
		final org.apache.http.HttpResponse response = new ApacheHttpResponseMockBuilder()
				.addHeader("Set-Cookie", "foo=bar")
				.addHeader("Set-Cookie", "quix=123")
				.build();

		final HttpResponse httpResponse = ApacheHttpResponse.create(response);
		final List<Cookie> cookies = httpResponse.getCookies();

		assertThat(cookies)
				.isNotNull()
				.isNotEmpty()
				.hasSize(2)
				.extracting("name")
				.contains("foo", "quix");
	}
}
