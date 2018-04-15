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

package com.github.mjeanroy.restassert.unit.api.cookie.apache;

import com.github.mjeanroy.restassert.tests.builders.apache.ApacheHttpCookieBuilder;
import org.apache.http.cookie.Cookie;

import static com.github.mjeanroy.restassert.unit.api.cookie.ApacheHttpCookieAssert.assertHasDomain;

public class AssertHasDomainTest extends AbstractApacheHttpCookieTest {

	@Override
	protected void invoke(Cookie actual) {
		assertHasDomain(actual, success().getDomain());
	}

	@Override
	protected void invoke(String message, Cookie actual) {
		assertHasDomain(message, actual, success().getDomain());
	}

	@Override
	protected Cookie success() {
		return cookie("foo");
	}

	@Override
	protected Cookie failure() {
		final String expectedDomain = success().getDomain();
		final String actualDomain = expectedDomain + "foo";
		return cookie(actualDomain);
	}

	@Override
	protected String pattern() {
		return "Expecting cookie to have domain %s but was %s";
	}

	@Override
	protected Object[] placeholders() {
		final String expectedDomain = success().getDomain();
		final String actualDomain = failure().getDomain();
		return new Object[]{
				expectedDomain, actualDomain
		};
	}

	private Cookie cookie(String domain) {
		return new ApacheHttpCookieBuilder()
				.setDomain(domain)
				.build();
	}
}
