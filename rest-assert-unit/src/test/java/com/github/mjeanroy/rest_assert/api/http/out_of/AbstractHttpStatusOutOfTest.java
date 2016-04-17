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

package com.github.mjeanroy.rest_assert.api.http.out_of;

import com.github.mjeanroy.rest_assert.api.http.AbstractHttpResponseAssertTest;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.asynchttp.AsyncHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpStatusLineMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseMockBuilder;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHttpStatusOutOfTest extends AbstractHttpResponseAssertTest {

	private static final String CUSTOM_MESSAGE = "foo";

	// == Core HTTP Response

	@Test
	public void it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() && i > end()) {
				invoke(newCoreHttpResponse(i));
				invoke(CUSTOM_MESSAGE, newCoreHttpResponse(i));
			}
		}
	}

	@Test
	public void it_should_fail() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newCoreHttpResponse(status));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newCoreHttpResponse(status));
			}
		});
	}

	// == Async HTTP Response

	@Test
	public void async_http_it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() && i > end()) {
				invoke(newAsyncHttpResponse(i));
				invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(i));
			}
		}
	}

	@Test
	public void async_http_it_should_fail() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newAsyncHttpResponse(status));
			}
		});
	}

	@Test
	public void async_http_it_should_fail_with_custom_message() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(status));
			}
		});
	}

	// == Ok HTTP Response

	@Test
	public void ok_http_it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() && i > end()) {
				invoke(newOkHttpResponse(i));
				invoke(CUSTOM_MESSAGE, newOkHttpResponse(i));
			}
		}
	}

	@Test
	public void ok_http_it_should_fail() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newOkHttpResponse(status));
			}
		});
	}

	@Test
	public void ok_http_it_should_fail_with_custom_message() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newOkHttpResponse(status));
			}
		});
	}

	// == Apache HTTP Response

	@Test
	public void apache_http_it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() && i > end()) {
				invoke(newApacheHttpResponse(i));
				invoke(CUSTOM_MESSAGE, newApacheHttpResponse(i));
			}
		}
	}

	@Test
	public void apache_http_it_should_fail() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newApacheHttpResponse(status));
			}
		});
	}

	@Test
	public void apache_http_it_should_fail_with_custom_message() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newApacheHttpResponse(status));
			}
		});
	}

	// == Google HTTP Response

	@Test
	public void google_http_it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() && i > end()) {
				invoke(newGoogleHttpResponse(i));
				invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(i));
			}
		}
	}

	@Test
	public void google_http_it_should_fail() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(newGoogleHttpResponse(status));
			}
		});
	}

	@Test
	public void google_http_it_should_fail_with_custom_message() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(int status) {
				invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(status));
			}
		});
	}

	private void doTest(String msg, final Invocation invocation) {
		final int start = start();
		final int end = end();

		for (int i = start; i <= end; i++) {
			final int status = i;
			final String message = firstNonNull(msg, buildErrorMessage(start, end, status));

			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invocation.invokeTest(status);
				}
			});
		}
	}

	protected abstract int start();

	protected abstract int end();

	private String buildErrorMessage(int start, int end, int status) {
		return String.format("Expecting status code to be out of %s and %s but was %s", start, end, status);
	}

	private com.github.mjeanroy.rest_assert.internal.data.HttpResponse newCoreHttpResponse(int status) {
		return new HttpResponseMockBuilder()
			.setStatus(status)
			.build();
	}

	private com.ning.http.client.Response newAsyncHttpResponse(int status) {
		return new AsyncHttpResponseMockBuilder()
			.setStatusCode(status)
			.build();
	}

	private okhttp3.Response newOkHttpResponse(int status) {
		return new OkHttpResponseMockBuilder()
			.setCode(status)
			.build();
	}

	private org.apache.http.HttpResponse newApacheHttpResponse(int status) {
		return new ApacheHttpResponseMockBuilder()
			.setStatusLine(new ApacheHttpStatusLineMockBuilder()
				.setStatusCode(status)
				.build())
			.build();
	}

	private com.google.api.client.http.HttpResponse newGoogleHttpResponse(int status) {
		return new GoogleHttpResponseMockBuilder()
			.setStatusCode(status)
			.build();
	}

	private interface Invocation {
		void invokeTest(int status);
	}
}
