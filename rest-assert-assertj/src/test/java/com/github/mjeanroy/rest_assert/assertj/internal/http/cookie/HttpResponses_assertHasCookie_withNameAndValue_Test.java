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

package com.github.mjeanroy.rest_assert.assertj.internal.http.cookie;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import org.assertj.core.api.AssertionInfo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpResponses_assertHasCookie_withNameAndValue_Test extends AbstractCookieTest {

	private static final String NAME = "JSESSIONID";

	private static final String VALUE = "12345";

	@Override
	protected Cookie cookie() {
		Cookie cookie = mock(Cookie.class);
		when(cookie.getName()).thenReturn(NAME);
		when(cookie.getValue()).thenReturn(VALUE);
		return cookie;
	}

	@Override
	protected void invoke(AssertionInfo info, HttpResponse httpResponse) {
		httpResponses.assertHasCookie(info, httpResponse, NAME, VALUE);
	}

	@Override
	protected String buildErrorMessage() {
		return String.format("Expecting http response to contains cookie with name \"%s\" and value \"%s\"", NAME, VALUE);
	}
}
