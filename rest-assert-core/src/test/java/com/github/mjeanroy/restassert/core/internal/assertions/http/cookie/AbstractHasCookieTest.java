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

package com.github.mjeanroy.restassert.core.internal.assertions.http.cookie;

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractHasCookieTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	void it_should_pass_with_expected_cookie() {
		Cookie cookie = newCookie();
		AssertionResult result = run(newResponse(cookie));
		checkSuccess(result);
	}

	@Test
	void it_should_fail_without_any_cookies() {
		HttpResponse rsp = new HttpResponseBuilderImpl().build();
		AssertionResult result = run(rsp);
		verifyError(result);
	}

	@Test
	void it_should_fail_without_expected_cookies() {
		HttpResponse rsp = new HttpResponseBuilderImpl()
			.addCookie("foo1", "bar1")
			.addCookie("foo2", "bar2")
			.build();

		AssertionResult result = run(rsp);

		verifyError(result);
	}

	@Test
	void it_should_fail_if_response_is_null() {
		AssertionResult result = run(null);
		checkError(result, "Expecting HTTP Response not to be null");
	}

	abstract Cookie newCookie();

	abstract void verifyError(AssertionResult result);

	private HttpResponse newResponse(Cookie cookie, Cookie... cookies) {
		return new HttpResponseBuilderImpl().addCookie(cookie, cookies).build();
	}
}
