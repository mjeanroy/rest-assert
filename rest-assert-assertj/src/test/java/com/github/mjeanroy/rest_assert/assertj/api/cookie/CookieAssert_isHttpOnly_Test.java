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

package com.github.mjeanroy.rest_assert.assertj.api.cookie;

import com.github.mjeanroy.rest_assert.assertj.api.AbstractApiTest;
import com.github.mjeanroy.rest_assert.assertj.api.CookieAssert;
import com.github.mjeanroy.rest_assert.assertj.internal.Cookies;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import org.assertj.core.api.AssertionInfo;

import static com.github.mjeanroy.rest_assert.tests.TestData.newCookie;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CookieAssert_isHttpOnly_Test extends AbstractApiTest<Cookies, CookieAssert> {

	@Override
	protected Cookies createAssertions() {
		return mock(Cookies.class);
	}

	@Override
	protected CookieAssert createApi() {
		return new CookieAssert(actual());
	}

	@Override
	protected CookieAssert invoke() {
		return api.isHttpOnly();
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertIsHttpOnly(any(AssertionInfo.class), any(Cookie.class));
	}

	protected Cookie actual() {
		return newCookie("foo", "bar", "domain", "path", 10, true, true);
	}
}