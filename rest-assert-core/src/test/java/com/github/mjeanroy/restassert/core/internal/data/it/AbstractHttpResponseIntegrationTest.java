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

package com.github.mjeanroy.restassert.core.internal.data.it;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.junit.WireMockTest;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@WireMockTest
abstract class AbstractHttpResponseIntegrationTest {

	private String path;
	private String body;
	private String url;
	private String uuid;

	@BeforeEach
	void setUp(WireMockServer wireMockServer) {
		this.path = "/api/users/1";
		this.url = "http://localhost:" + wireMockServer.port() + path;
		this.body = "{ \"id\": 1, name: \"John Doe\" }";
		this.uuid = "297c59f6-efa6-4fe7-8211-01917a32c3b6";
		stubGetRequest();
	}

	private void stubGetRequest() {
		stubFor(get(urlEqualTo(path))
			.willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withHeader("X-Auth-Token", uuid)
				.withHeader("Set-Cookie", "foo=bar")
				.withBody(body)));
	}

	@Test
	void it_should_call_http_request_and_get_status_code() throws Exception {
		HttpResponse response = createHttpResponse(url);
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@Test
	void it_should_call_http_request_and_read_response_body() throws Exception {
		HttpResponse response = createHttpResponse(url);
		assertThat(response).isNotNull();
		assertThat(response.getContent()).isNotNull().isNotEmpty().isEqualTo(body);
	}

	@Test
	void it_should_call_http_request_and_check_for_header_availability_case_insensitively() throws Exception {
		HttpResponse response = createHttpResponse(url);
		assertThat(response).isNotNull();
		assertThat(response.hasHeader("X-Auth-Token")).isTrue();
		assertThat(response.hasHeader("X-AUTH-TOKEN")).isTrue();
		assertThat(response.hasHeader("x-auth-token")).isTrue();
	}

	@Test
	void it_should_call_http_request_and_read_header() throws Exception {
		HttpResponse response = createHttpResponse(url);
		assertThat(response).isNotNull();
		assertThat(response.getHeader("X-Auth-Token")).isNotNull().isNotEmpty().isEqualTo(singletonList(uuid));
	}

	@Test
	void it_should_call_http_request_and_read_cookie() throws Exception {
		HttpResponse response = createHttpResponse(url);
		assertThat(response).isNotNull();
		assertThat(response.getCookies()).hasSize(1)
			.extracting("name", "value")
			.containsOnly(
				tuple("foo", "bar")
			);
	}

	abstract HttpResponse createHttpResponse(String url) throws Exception;
}
