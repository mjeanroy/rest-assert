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

package com.github.mjeanroy.rest_assert.assertj.api.http.cookie;

import com.github.mjeanroy.rest_assert.assertj.api.HttpResponseAssert;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import org.assertj.core.api.AssertionInfo;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class HttpResponseAssert_hasCookie_withNameAndValue_Test extends AbstractCookieTest {

	private static final String NAME = "JSESSIONID";

	private static final String VALUE = "12345";

	@Override
	protected HttpResponseAssert invoke() {
		return api.hasCookie(NAME, VALUE);
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertHasCookie(any(AssertionInfo.class), any(HttpResponse.class), eq(NAME), eq(VALUE));
	}
}