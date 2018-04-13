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

package com.github.mjeanroy.restassert.internal.assertions.http.cookie;

import com.github.mjeanroy.restassert.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.internal.data.Cookie;
import com.github.mjeanroy.restassert.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.mocks.CookieMockBuilder;
import com.github.mjeanroy.restassert.tests.mocks.HttpResponseMockBuilderImpl;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractHasCookieTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_expected_cookie() {
		Cookie cookie = newCookie();
		AssertionResult result = invoke(newResponse(cookie));
		checkSuccess(result);
	}

	@Test
	public void it_should_fail_without_any_cookies() {
		HttpResponse rsp = new HttpResponseMockBuilderImpl().build();
		AssertionResult result = invoke(rsp);
		verifyError(result);
	}

	@Test
	public void it_should_fail_without_expected_cookies() {
		HttpResponse rsp = new HttpResponseMockBuilderImpl()
				.addCookie(new CookieMockBuilder()
						.setName("foo1")
						.setValue("bar1")
						.build())
				.addCookie(new CookieMockBuilder()
						.setName("foo2")
						.setValue("bar2")
						.build())
				.build();

		AssertionResult result = invoke(rsp);

		verifyError(result);
	}

	protected abstract Cookie newCookie();

	protected abstract void verifyError(AssertionResult result);

	private HttpResponse newResponse(Cookie cookie, Cookie... cookies) {
		return new HttpResponseMockBuilderImpl()
				.addCookie(cookie, cookies)
				.build();
	}
}
