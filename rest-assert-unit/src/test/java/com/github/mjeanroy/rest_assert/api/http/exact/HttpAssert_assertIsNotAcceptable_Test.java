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

package com.github.mjeanroy.rest_assert.api.http.exact;

import com.github.mjeanroy.rest_assert.api.http.ApacheHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.AsyncHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.GoogleHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.HttpAssert;
import com.github.mjeanroy.rest_assert.api.http.NingHttpAssert;
import com.github.mjeanroy.rest_assert.api.http.OkHttpAssert;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import okhttp3.Response;

public class HttpAssert_assertIsNotAcceptable_Test extends AbstractHttpStatusTest {

	@Override
	protected int status() {
		return 406;
	}

	@Override
	protected void invoke(HttpResponse actual) {
		HttpAssert.assertIsNotAcceptable(actual);
	}

	@Override
	protected void invoke(String message, HttpResponse actual) {
		HttpAssert.assertIsNotAcceptable(message, actual);
	}

	@Override
	protected void invoke(com.ning.http.client.Response actual) {
		NingHttpAssert.assertIsNotAcceptable(actual);
	}

	@Override
	protected void invoke(String message, com.ning.http.client.Response actual) {
		NingHttpAssert.assertIsNotAcceptable(message, actual);
	}

	@Override
	protected void invoke(org.asynchttpclient.Response actual) {
		AsyncHttpAssert.assertIsNotAcceptable(actual);
	}

	@Override
	protected void invoke(String message, org.asynchttpclient.Response actual) {
		AsyncHttpAssert.assertIsNotAcceptable(message, actual);
	}

	@Override
	protected void invoke(Response actual) {
		OkHttpAssert.assertIsNotAcceptable(actual);
	}

	@Override
	protected void invoke(String message, Response actual) {
		OkHttpAssert.assertIsNotAcceptable(message, actual);
	}

	@Override
	protected void invoke(org.apache.http.HttpResponse actual) {
		ApacheHttpAssert.assertIsNotAcceptable(actual);
	}

	@Override
	protected void invoke(String message, org.apache.http.HttpResponse actual) {
		ApacheHttpAssert.assertIsNotAcceptable(message, actual);
	}

	@Override
	protected void invoke(com.google.api.client.http.HttpResponse actual) {
		GoogleHttpAssert.assertIsNotAcceptable(actual);
	}

	@Override
	protected void invoke(String message, com.google.api.client.http.HttpResponse actual) {
		GoogleHttpAssert.assertIsNotAcceptable(message, actual);
	}
}
