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

import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractDoesNotHaveCookieTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	void it_should_pass_without_any_cookies() {
		AssertionResult result = run(newResponse(null));
		checkSuccess(result);
	}

	@Test
	void it_should_pass_without_cookie() {
		HttpResponse response = newResponse(new MockCookieBuilder()
			.setName("foo")
			.setValue("bar")
			.build());

		AssertionResult result = run(response);
		checkSuccess(result);
	}

	@Test
	void it_should_fail_with_cookies() {
		HttpResponse rsp = newResponse(newCookie());
		AssertionResult result = run(rsp);
		verifyError(result);
	}

	abstract Cookie newCookie();

	abstract void verifyError(AssertionResult result);

	private HttpResponse newResponse(Cookie cookie) {
		HttpResponseBuilderImpl builder = new HttpResponseBuilderImpl();
		if (cookie != null) {
			builder.addCookie(cookie);
		}

		return builder.build();
	}
}
