/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.assertj.api.http.charsets;

import com.github.mjeanroy.restassert.assertj.api.HttpResponseAssert;
import com.github.mjeanroy.restassert.assertj.api.http.AbstractHttpResponseTest;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.assertj.core.api.AssertionInfo;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class HasCharsetStringTest extends AbstractHttpResponseTest {

	private static final String CHARSET = "UTF-8";

	@Override
	protected HttpResponseAssert createApi() {
		String contentType = format("application/json;charset=%s", CHARSET);
		return new HttpResponseAssert(new HttpResponseBuilderImpl().addHeader("Content-Type", contentType).build());
	}

	@Override
	protected HttpResponseAssert invoke() {
		return api.hasCharset(CHARSET);
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertHasCharset(any(AssertionInfo.class), any(HttpResponse.class), eq(CHARSET));
	}
}
