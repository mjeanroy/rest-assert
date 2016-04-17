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

package com.github.mjeanroy.rest_assert.api.http.headers;

import com.github.mjeanroy.rest_assert.api.http.AbstractHttpResponseAssertTest;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.asynchttp.AsyncHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpHeadersMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHttpHeaderEqualToTest<T> extends AbstractHttpResponseAssertTest {

	private static final String CUSTOM_MESSAGE = "foo";

	// == Core HTTP Response

	@Test
	public void core_it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newCoreHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newCoreHttpResponse(header));
	}

	@Test
	public void core_it_should_fail_with_if_response_does_not_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newCoreHttpResponse(header));
			}
		});
	}

	@Test
	public void core_it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newCoreHttpResponse(header));
			}
		});
	}

	// == Async HTTP Response

	@Test
	public void async_http_it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newAsyncHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(header));
	}

	@Test
	public void async_http_it_should_fail_with_if_response_does_not_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newAsyncHttpResponse(header));
			}
		});
	}

	@Test
	public void async_http_it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(header));
			}
		});
	}

	// == Ok HTTP Response

	@Test
	public void ok_http_it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newOkHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newOkHttpResponse(header));
	}

	@Test
	public void ok_http_it_should_fail_with_if_response_does_not_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newOkHttpResponse(header));
			}
		});
	}

	@Test
	public void ok_http_it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newOkHttpResponse(header));
			}
		});
	}

	// == Apache HTTP Response

	@Test
	public void apache_http_it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newApacheHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newApacheHttpResponse(header));
	}

	@Test
	public void apache_http_it_should_fail_with_if_response_does_not_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newApacheHttpResponse(header));
			}
		});
	}

	@Test
	public void apache_http_it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newApacheHttpResponse(header));
			}
		});
	}

	// == Google HTTP Response

	@Test
	public void google_http_it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newGoogleHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(header));
	}

	@Test
	public void google_http_it_should_fail_with_if_response_does_not_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newGoogleHttpResponse(header));
			}
		});
	}

	@Test
	public void google_http_it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(header));
			}
		});
	}

	private void doTest(String msg, final Invocation invocation) {
		final Header expectedHeader = getHeader();
		final String expectedName = expectedHeader.getName();
		final String expectedValue = expectedHeader.getValue();
		final String actualValue = failValue();
		final Header header = header(expectedName, actualValue);
		final String message = firstNonNull(msg, buildErrorMessage(expectedName, expectedValue, actualValue));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(header);
			}
		});
	}

	protected abstract Header getHeader();

	String failValue() {
		return getHeader().getValue() + "foo";
	}

	private String buildErrorMessage(String expectedName, String expectedValue, String actualValue) {
		return String.format("Expecting response to have header %s equal to %s but was %s", expectedName, expectedValue, actualValue);
	}

	// == Create target HTTP Response

	private com.github.mjeanroy.rest_assert.internal.data.HttpResponse newCoreHttpResponse(Header header) {
		return new HttpResponseMockBuilder()
			.addHeader(header.getName(), header.getValue())
			.build();
	}

	private com.ning.http.client.Response newAsyncHttpResponse(Header header) {
		return new AsyncHttpResponseMockBuilder()
			.addHeader(header.getName(), header.getValue())
			.build();
	}

	private okhttp3.Response newOkHttpResponse(Header header) {
		return new OkHttpResponseMockBuilder()
			.addHeader(header.getName(), header.getValue())
			.build();
	}

	private org.apache.http.HttpResponse newApacheHttpResponse(Header header) {
		return new ApacheHttpResponseMockBuilder()
			.addHeader(header.getName(), header.getValue())
			.build();
	}

	private com.google.api.client.http.HttpResponse newGoogleHttpResponse(Header header) {
		return new GoogleHttpResponseMockBuilder()
			.setHeaders(new GoogleHttpHeadersMockBuilder()
				.addHeader(header.getName(), header.getValue())
				.build())
			.build();
	}

	interface Invocation {
		void invokeTest(Header header);
	}
}
