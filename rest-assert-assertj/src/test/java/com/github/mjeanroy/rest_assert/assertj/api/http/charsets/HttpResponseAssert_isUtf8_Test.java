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
 * furnished to do so, subject to the following httpResponses:
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

package com.github.mjeanroy.rest_assert.assertj.api.http.charsets;

import com.github.mjeanroy.rest_assert.assertj.api.HttpResponseAssert;
import com.github.mjeanroy.rest_assert.assertj.api.http.AbstractHttpResponseTest;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import org.assertj.core.api.AssertionInfo;

import static com.github.mjeanroy.rest_assert.tests.TestData.newHttpResponseWithHeader;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;
import static java.lang.String.format;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class HttpResponseAssert_isUtf8_Test extends AbstractHttpResponseTest {

	@Override
	protected HttpResponseAssert createApi() {
		String contentType = format("application/json;charset=%s", getCharset());
		Header header = header("Content-Type", contentType);
		return new HttpResponseAssert(newHttpResponseWithHeader(header));
	}

	protected String getCharset() {
		return "UTF-8";
	}

	protected HttpResponseAssert invoke() {
		return api.isUtf8();
	}

	protected void verifyApiCall() {
		verify(assertions).assertIsUtf8(any(AssertionInfo.class), any(HttpResponse.class));
	}
}