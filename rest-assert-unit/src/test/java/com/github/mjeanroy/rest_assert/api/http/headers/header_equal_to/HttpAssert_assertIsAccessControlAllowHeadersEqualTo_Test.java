/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.api.http.headers.header_equal_to;

import com.github.mjeanroy.rest_assert.api.http.*;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import okhttp3.Response;

import static com.github.mjeanroy.rest_assert.tests.models.Header.header;

public class HttpAssert_assertIsAccessControlAllowHeadersEqualTo_Test extends AbstractHttpHeaderEqualToTest {

	private static final String VALUE = "X-Requested-With";

	@Override
	protected Header getHeader() {
		return header("Access-Control-Allow-Headers", VALUE);
	}

	@Override
	protected void invoke(HttpResponse actual) {
		HttpAssert.assertIsAccessControlAllowHeadersEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, HttpResponse actual) {
		HttpAssert.assertIsAccessControlAllowHeadersEqualTo(message, actual, VALUE);
	}

	@Override
	protected void invoke(com.ning.http.client.Response actual) {
		NingHttpAssert.assertIsAccessControlAllowHeadersEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, com.ning.http.client.Response actual) {
		NingHttpAssert.assertIsAccessControlAllowHeadersEqualTo(message, actual, VALUE);
	}

	@Override
	protected void invoke(org.asynchttpclient.Response actual) {
		AsyncHttpAssert.assertIsAccessControlAllowHeadersEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, org.asynchttpclient.Response actual) {
		AsyncHttpAssert.assertIsAccessControlAllowHeadersEqualTo(message, actual, VALUE);
	}

	@Override
	protected void invoke(Response actual) {
		OkHttpAssert.assertIsAccessControlAllowHeadersEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, Response actual) {
		OkHttpAssert.assertIsAccessControlAllowHeadersEqualTo(message, actual, VALUE);
	}

	@Override
	protected void invoke(org.apache.http.HttpResponse actual) {
		ApacheHttpAssert.assertIsAccessControlAllowHeadersEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, org.apache.http.HttpResponse actual) {
		ApacheHttpAssert.assertIsAccessControlAllowHeadersEqualTo(message, actual, VALUE);
	}

	@Override
	protected void invoke(com.google.api.client.http.HttpResponse actual) {
		GoogleHttpAssert.assertIsAccessControlAllowHeadersEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, com.google.api.client.http.HttpResponse actual) {
		GoogleHttpAssert.assertIsAccessControlAllowHeadersEqualTo(message, actual, VALUE);
	}
}