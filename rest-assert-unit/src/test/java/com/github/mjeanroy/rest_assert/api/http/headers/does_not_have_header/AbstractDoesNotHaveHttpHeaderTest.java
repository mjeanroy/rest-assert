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

package com.github.mjeanroy.rest_assert.api.http.headers.does_not_have_header;

import com.github.mjeanroy.rest_assert.api.http.AbstractHttpResponseAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.async.AsyncHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.ning.NingHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpHeadersMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import org.asynchttpclient.Response;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;

public abstract class AbstractDoesNotHaveHttpHeaderTest extends AbstractHttpResponseAssertTest {

	private static final String CUSTOM_MESSAGE = "foo";

	// == Core HTTP Response

	@Test
	public void core_it_should_pass_with_missing_header() {
		// GIVEN
		final Header header = header("Foo", "Bar");
		final HttpResponse rsp = newCoreHttpResponse(header);

		// WHEN
		invoke(rsp);
		invoke(CUSTOM_MESSAGE, rsp);

		// THEN
	}

	@Test
	public void core_it_should_fail_with_if_response_not_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newCoreHttpResponse(header));
			}
		});
	}

	@Test
	public void core_it_should_fail_with_custom_message_if_response_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newCoreHttpResponse(header));
			}
		});
	}

	// == Ning HTTP Response

	@Test
	public void ning_http_it_should_pass_with_missing_header() {
		Header header = header("Foo", "Bar");
		invoke(newNingHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newNingHttpResponse(header));
	}

	@Test
	public void ning_http_it_should_fail_with_if_response_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newNingHttpResponse(header));
			}
		});
	}

	@Test
	public void ning_http_it_should_fail_with_custom_message_if_response_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newNingHttpResponse(header));
			}
		});
	}

	// == Async HTTP Response

	@Test
	public void async_http_it_should_pass_with_missing_header() {
		Header header = header("Foo", "Bar");
		invoke(newAsyncHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(header));
	}

	@Test
	public void async_http_it_should_fail_with_if_response_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newAsyncHttpResponse(header));
			}
		});
	}

	@Test
	public void async_http_it_should_fail_with_custom_message_if_response_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(header));
			}
		});
	}

	// == Ok HTTP Response

	@Test
	public void ok_http_it_should_pass_with_missing_header() {
		Header header = header("Foo", "Bar");
		invoke(newOkHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newOkHttpResponse(header));
	}

	@Test
	public void ok_http_it_should_fail_with_if_response_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newOkHttpResponse(header));
			}
		});
	}

	@Test
	public void ok_http_it_should_fail_with_custom_message_if_response_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newOkHttpResponse(header));
			}
		});
	}

	// == Apache HTTP Response

	@Test
	public void apache_http_it_should_pass_with_missing_header() {
		Header header = header("Foo", "Bar");
		invoke(newApacheHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newApacheHttpResponse(header));
	}

	@Test
	public void apache_http_it_should_fail_with_if_response_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newApacheHttpResponse(header));
			}
		});
	}

	@Test
	public void apache_http_it_should_fail_with_custom_message_if_response_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newApacheHttpResponse(header));
			}
		});
	}

	// == Google HTTP Response

	@Test
	public void google_http_it_should_pass_with_missing_header() {
		Header header = header("Foo", "Bar");
		invoke(newGoogleHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(header));
	}

	@Test
	public void google_http_it_should_fail_with_if_response_contain_header() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(newGoogleHttpResponse(header));
			}
		});
	}

	@Test
	public void google_http_it_should_fail_with_custom_message_if_response_contain_header() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(header));
			}
		});
	}

	private void doTest(String msg, final Invocation invocation) {
		final Header header = getHeader();
		final String message = firstNonNull(msg, buildErrorMessage(header));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(header);
			}
		});
	}

	protected abstract Header getHeader();

	private String buildErrorMessage(Header expectedHeader) {
		return format("Expecting response not to have header %s", expectedHeader.getName());
	}

	// == Create target HTTP Response

	private com.github.mjeanroy.rest_assert.internal.data.HttpResponse newCoreHttpResponse(Header header) {
		return new HttpResponseMockBuilder()
				.addHeader(header.getName(), header.getValue())
				.build();
	}

	private com.ning.http.client.Response newNingHttpResponse(Header header) {
		return new NingHttpResponseMockBuilder()
				.addHeader(header.getName(), header.getValue())
				.build();
	}

	private Response newAsyncHttpResponse(Header header) {
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

	private interface Invocation {
		void invokeTest(Header header);
	}
}
