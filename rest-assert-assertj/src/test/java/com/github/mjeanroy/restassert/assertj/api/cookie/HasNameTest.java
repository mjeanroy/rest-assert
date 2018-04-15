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

package com.github.mjeanroy.restassert.assertj.api.cookie;

import com.github.mjeanroy.restassert.assertj.api.AbstractApiTest;
import com.github.mjeanroy.restassert.assertj.api.CookieAssert;
import com.github.mjeanroy.restassert.assertj.internal.Cookies;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import org.assertj.core.api.AssertionInfo;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HasNameTest extends AbstractApiTest<Cookies, CookieAssert> {

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
		return api.hasName(actual().getName());
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertHasName(any(AssertionInfo.class), any(Cookie.class), nullable(String.class));
	}

	private Cookie actual() {
		return new CookieBuilder().build();
	}
}
