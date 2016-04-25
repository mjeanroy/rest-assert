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

package com.github.mjeanroy.rest_assert.api.http.cookie;

import com.github.mjeanroy.rest_assert.api.http.AbstractHttpResponseAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.mocks.CookieMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.asynchttp.AsyncHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpHeadersMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.googlehttp.GoogleHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent.ApacheHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.okhttp.OkHttpResponseMockBuilder;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractDoesNotHaveCookieTest extends AbstractHttpResponseAssertTest {

	private static final String CUSTOM_MESSAGE = "foo";

	// == Core HTTP response

	@Test
	public void core_it_should_pass_with_expected_cookie() {
		invoke(newCoreHttpResponse(fakeCookie()));
		invoke(CUSTOM_MESSAGE, newCoreHttpResponse(fakeCookie()));
	}

	@Test
	public void core_it_should_fail_with_response_without_expected_cookie() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newCoreHttpResponse(cookie));
			}
		});
	}

	@Test
	public void core_it_should_pass_with_custom_message_with_response_without_expected_cookie() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newCoreHttpResponse(cookie));
			}
		});
	}

	// == Async HTTP response

	@Test
	public void async_http_it_should_pass_with_expected_cookie() {
		invoke(newAsyncHttpResponse(fakeCookie()));
		invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(fakeCookie()));
	}

	@Test
	public void async_http_it_should_fail_with_response_without_expected_cookie() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newAsyncHttpResponse(cookie));
			}
		});
	}

	@Test
	public void async_http_it_should_pass_with_custom_message_with_response_without_expected_cookie() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newAsyncHttpResponse(cookie));
			}
		});
	}

	// == Ok HTTP response

	@Test
	public void ok_http_it_should_pass_with_expected_cookie() {
		invoke(newOkHttpResponse(fakeCookie()));
		invoke(CUSTOM_MESSAGE, newOkHttpResponse(fakeCookie()));
	}

	@Test
	public void ok_http_it_should_fail_with_response_without_expected_cookie() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newOkHttpResponse(cookie));
			}
		});
	}

	@Test
	public void ok_http_it_should_pass_with_custom_message_with_response_without_expected_cookie() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newOkHttpResponse(cookie));
			}
		});
	}

	// == Apache HTTP response

	@Test
	public void apache_http_it_should_pass_with_expected_cookie() {
		invoke(newApacheHttpResponse(fakeCookie()));
		invoke(CUSTOM_MESSAGE, newApacheHttpResponse(fakeCookie()));
	}

	@Test
	public void apache_http_it_should_fail_with_response_without_expected_cookie() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newApacheHttpResponse(cookie));
			}
		});
	}

	@Test
	public void apache_http_it_should_pass_with_custom_message_with_response_without_expected_cookie() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newApacheHttpResponse(cookie));
			}
		});
	}

	// == Google HTTP response

	@Test
	public void google_http_it_should_pass_with_expected_cookie() {
		invoke(newGoogleHttpResponse(fakeCookie()));
		invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(fakeCookie()));
	}

	@Test
	public void google_http_it_should_fail_with_response_without_expected_cookie() {
		doTest(null, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(newGoogleHttpResponse(cookie));
			}
		});
	}

	@Test
	public void google_http_it_should_pass_with_custom_message_with_response_without_expected_cookie() {
		doTest(CUSTOM_MESSAGE, new Invocation() {
			@Override
			public void invokeTest(Cookie cookie) {
				invoke(CUSTOM_MESSAGE, newGoogleHttpResponse(cookie));
			}
		});
	}

	// == Test

	private void doTest(String msg, final Invocation invocation) {
		final Cookie cookie = cookie();
		final String message = firstNonNull(msg, buildErrorMessage());
		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(cookie);
			}
		});
	}

	protected abstract Cookie cookie();

	protected abstract String buildErrorMessage();

	private Cookie fakeCookie() {
		return new CookieMockBuilder()
				.setName("foo")
				.setValue("bar")
				.build();
	}

	private com.github.mjeanroy.rest_assert.internal.data.HttpResponse newCoreHttpResponse(Cookie cookie) {
		return new HttpResponseMockBuilder()
				.addCookie(cookie)
				.build();
	}

	private com.ning.http.client.Response newAsyncHttpResponse(Cookie cookie) {
		return new AsyncHttpResponseMockBuilder()
				.addCookie(cookie)
				.build();
	}

	private okhttp3.Response newOkHttpResponse(Cookie cookie) {
		return new OkHttpResponseMockBuilder()
				.addCookie(cookie)
				.build();
	}

	private org.apache.http.HttpResponse newApacheHttpResponse(Cookie cookie) {
		return new ApacheHttpResponseMockBuilder()
				.addCookie(cookie)
				.build();
	}

	private com.google.api.client.http.HttpResponse newGoogleHttpResponse(Cookie cookie) {
		return new GoogleHttpResponseMockBuilder()
				.setHeaders(new GoogleHttpHeadersMockBuilder()
						.addCookie(cookie)
						.build())
				.build();
	}

	interface Invocation {
		void invokeTest(Cookie cookie);
	}
}
