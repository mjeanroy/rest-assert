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

package com.github.mjeanroy.restassert.assertj.api;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.core.internal.data.bindings.ning.NingHttpCookie.create;
import static com.github.mjeanroy.restassert.core.internal.data.bindings.ning.NingHttpResponse.create;

/**
 * Entry point for assertion methods for Async-Http
 * library (version < 2.0.0).
 */
public final class NingHttpAssertions {

	// Ensure non instantiation
	private NingHttpAssertions() {
	}

	/**
	 * Creates a new instance of {@link HttpResponseAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static HttpResponseAssert assertThat(Response actual) {
		return new HttpResponseAssert(toHttpResponse(actual));
	}

	/**
	 * Creates a new instance of {@link CookieAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static CookieAssert assertThat(com.ning.http.client.cookie.Cookie actual) {
		Cookie cookie = create(actual);
		return new CookieAssert(cookie);
	}

	/**
	 * Creates a new instance of {@link JsonAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static JsonAssert assertJsonThat(Response actual) {
		return JsonAssertions.assertJsonThat(toHttpResponse(actual));
	}

	private static HttpResponse toHttpResponse(Response actual) {
		return create(actual);
	}
}
