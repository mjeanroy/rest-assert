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

package com.github.mjeanroy.restassert.core.internal.assertions.http.cookie;

import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
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
		HttpResponse rsp = new HttpResponseBuilderImpl().build();
		AssertionResult result = invoke(rsp);
		verifyError(result);
	}

	@Test
	public void it_should_fail_without_expected_cookies() {
		HttpResponse rsp = new HttpResponseBuilderImpl()
				.addCookie(new CookieBuilder()
						.setName("foo1")
						.setValue("bar1")
						.build())
				.addCookie(new CookieBuilder()
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
		return new HttpResponseBuilderImpl()
				.addCookie(cookie, cookies)
				.build();
	}
}
