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

package com.github.mjeanroy.rest_assert.api.http.charsets;

import com.github.mjeanroy.rest_assert.api.http.AbstractHttpResponseAssertTest;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.ning.NingHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpHeadersMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseMockBuilder;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;

abstract class AbstractHttpAssertCharsetTest extends AbstractHttpResponseAssertTest {

	private static final String CUSTOM_MESSAGE = "foo";

	// == Core HTTP Response

	@Test
	public void core_it_should_pass_with_expected_mime_type() {
		invoke(newCoreHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newCoreHttpResponse(getCharset()));
	}

	@Test
	public void core_it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(newCoreHttpResponse(charset));
			}
		});
	}

	@Test
	public void core_it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newCoreHttpResponse(charset));
			}
		});
	}

	// == Async HTTP Response

	@Test
	public void async_http_it_should_pass_with_expected_mime_type() {
		invoke(newAsyncHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(getCharset()));
	}

	@Test
	public void async_http_it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(newAsyncHttpResponse(charset));
			}
		});
	}

	@Test
	public void async_http_it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(charset));
			}
		});
	}

	// == Ok HTTP Response

	@Test
	public void ok_http_it_should_pass_with_expected_mime_type() {
		invoke(newOkHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newOkHttpResponse(getCharset()));
	}

	@Test
	public void ok_http_it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(newOkHttpResponse(charset));
			}
		});
	}

	@Test
	public void ok_http_it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newOkHttpResponse(charset));
			}
		});
	}

	// == Apache HTTP Response

	@Test
	public void apache_http_it_should_pass_with_expected_mime_type() {
		invoke(newApacheHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newApacheHttpResponse(getCharset()));
	}

	@Test
	public void apache_http_it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(newApacheHttpResponse(charset));
			}
		});
	}

	@Test
	public void apache_http_it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newApacheHttpResponse(charset));
			}
		});
	}

	// == Google HTTP Response

	@Test
	public void google_http_it_should_pass_with_expected_mime_type() {
		invoke(newGoogleHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(getCharset()));
	}

	@Test
	public void google_http_it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(newGoogleHttpResponse(charset));
			}
		});
	}

	@Test
	public void google_http_it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(charset));
			}
		});
	}

	// == Test

	private void invokeFailure(String msg, final Invocation invocation) {
		final String expectedCharset = getCharset();
		final String actualCharset = expectedCharset + "foo";
		final String message = firstNonNull(msg, buildErrorMessage(expectedCharset, actualCharset));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(actualCharset);
			}
		});
	}

	// == Create target HTTP Response

	private com.github.mjeanroy.rest_assert.internal.data.HttpResponse newCoreHttpResponse(String charset) {
		String contentType = format("application/json;charset=%s", charset);
		return new HttpResponseMockBuilder()
				.addHeader("Content-Type", contentType)
				.build();
	}

	private com.ning.http.client.Response newAsyncHttpResponse(String charset) {
		String contentType = format("application/json;charset=%s", charset);
		return new NingHttpResponseMockBuilder()
				.addHeader("Content-Type", contentType)
				.build();
	}

	private okhttp3.Response newOkHttpResponse(String charset) {
		String contentType = format("application/json;charset=%s", charset);
		return new OkHttpResponseMockBuilder()
				.addHeader("Content-Type", contentType)
				.build();
	}

	private org.apache.http.HttpResponse newApacheHttpResponse(String charset) {
		String contentType = format("application/json;charset=%s", charset);
		return new ApacheHttpResponseMockBuilder()
				.addHeader("Content-Type", contentType)
				.build();
	}

	private com.google.api.client.http.HttpResponse newGoogleHttpResponse(String charset) {
		String contentType = format("application/json;charset=%s", charset);
		return new GoogleHttpResponseMockBuilder()
				.setHeaders(new GoogleHttpHeadersMockBuilder()
						.addHeader("Content-Type", contentType)
						.build())
				.build();
	}

	private String buildErrorMessage(String expectedCharset, String actualCharset) {
		return String.format("Expecting response to have charset %s but was %s", expectedCharset, actualCharset);
	}

	// == To Implement

	protected abstract String getCharset();

	interface Invocation {
		void invokeTest(String charset);
	}
}
