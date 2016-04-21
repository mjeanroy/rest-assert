/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseBodyMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseMockBuilder;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Response.class)
public class OkHttpResponseTest {

	@Test
	public void it_should_get_status_code() {
		final int code = 200;
		final Response response = new OkHttpResponseMockBuilder()
				.setCode(code)
				.build();

		OkHttpResponse okHttpResponse = OkHttpResponse.create(response);

		assertThat(okHttpResponse.getStatus()).isEqualTo(code);
		verify(response).code();
	}

	@Test
	public void it_should_check_if_response_has_header() {
		final Response response = new OkHttpResponseMockBuilder()
				.addHeader("foo", "bar")
				.build();

		OkHttpResponse okHttpResponse = OkHttpResponse.create(response);

		assertThat(okHttpResponse.hasHeader("foo")).isTrue();
		assertThat(okHttpResponse.hasHeader("bar")).isFalse();
	}

	@Test
	public void it_should_get_header_value() {
		final Response response = new OkHttpResponseMockBuilder()
				.addHeader("foo", "bar")
				.build();

		OkHttpResponse okHttpResponse = OkHttpResponse.create(response);

		List<String> h1 = okHttpResponse.getHeader("foo");
		assertThat(h1)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.contains("bar");

		List<String> h2 = okHttpResponse.getHeader("bar");
		assertThat(h2)
			.isNotNull()
			.isEmpty();
	}

	@Test
	public void it_should_get_content() {
		final String content = "Hello World";
		final Response response = new OkHttpResponseMockBuilder()
				.setBody(new OkHttpResponseBodyMockBuilder()
						.setBody(content)
						.build())
				.build();

		OkHttpResponse okHttpResponse = OkHttpResponse.create(response);

		assertThat(okHttpResponse.getContent()).isEqualTo(content);
		verify(response).body();
	}
}
