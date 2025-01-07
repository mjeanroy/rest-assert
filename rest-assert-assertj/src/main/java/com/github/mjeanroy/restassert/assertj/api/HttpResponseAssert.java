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

package com.github.mjeanroy.restassert.assertj.api;

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import org.assertj.core.api.ListAssert;

import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.trimToNull;

public class HttpResponseAssert extends AbstractHttpResponseAssert<HttpResponseAssert> {

	public HttpResponseAssert(HttpResponse actual) {
		super(actual, HttpResponseAssert.class);
	}

	/**
	 * Extract body and returns JSON assertion object, such as:
	 *
	 * @code {
	 *   assertThat(response).isOk().extractingJsonBody().isEqualTo(
	 *     readFile("/json/expected.json")
	 *   );
	 * }
	 * @return The {@link JsonAssert JSON assertion} object.
	 */
	public JsonAssert extractingJsonBody() {
		isNotNull();
		return new JsonAssert(
			actual.getContent()
		);
	}

	/**
	 * Extract cookie by its name and returns new assertion with given cookie as value
	 * under test.
	 *
	 * For example:
	 *
	 * <pre><code>
	 *   assertThat(response).isOk().extractingCookie("JSESSIONID")
	 *     .isSecured()
	 *     .isHttpOnly()
	 *     .hasSameSite(SameSite.STRICT);
	 * </code></pre>
	 *
	 * @param name Cookie name.
	 * @return New {@link CookieAssert} assertion.
	 */
	public CookieAssert extractingCookie(String name) {
		String trimmedName = notNull(trimToNull(name), "Cookie name must not be null");
		isNotNull();
		hasCookie(trimmedName);

		Cookie cookie = actual.getCookies().stream()
			.filter(c -> Objects.equals(trimToNull(c.getName()), trimmedName))
			.findFirst()
			.orElse(null);

		return new CookieAssert(cookie);
	}

	/**
	 * Extract cookies returns new assertion with cookie list as value
	 * under test.
	 *
	 * For example:
	 *
	 * <pre><code>
	 *   assertThat(response).isOk().extractingCookies().extracting(Cookie::getName).contains(
	 *     "JSESSIONID"
	 *   );
	 * </code></pre>
	 *
	 * @return New {@link ListAssert ListAssert<Cookie>} assertion.
	 */
	public ListAssert<Cookie> extractingCookies() {
		isNotNull();
		return new ListAssert<>(
			actual.getCookies()
		);
	}
}
