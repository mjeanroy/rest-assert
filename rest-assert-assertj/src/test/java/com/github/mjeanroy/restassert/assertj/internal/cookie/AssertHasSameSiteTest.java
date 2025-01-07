/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.assertj.internal.cookie;

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.core.data.Cookie.SameSite;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;
import org.assertj.core.api.AssertionInfo;

class AssertHasSameSiteTest extends AbstractCookiesTest {

	@Override
	protected void run(AssertionInfo info, Cookie cookie) {
		cookies.assertHasSameSite(info, cookie, success().getSameSite());
	}

	@Override
	protected Cookie success() {
		return cookie(SameSite.NONE);
	}

	@Override
	protected Cookie failure() {
		return cookie(SameSite.STRICT);
	}

	@Override
	protected String message() {
		SameSite expected = success().getSameSite();
		SameSite actual = failure().getSameSite();
		return "Expecting cookie to have SameSite \"" + expected + "\" but was \"" + actual + "\"";
	}

	private Cookie cookie(SameSite sameSite) {
		return new MockCookieBuilder().setSameSite(sameSite).build();
	}
}
