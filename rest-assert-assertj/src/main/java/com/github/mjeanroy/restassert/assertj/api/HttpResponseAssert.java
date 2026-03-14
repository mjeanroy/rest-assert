/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.data.HttpHeader;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import org.assertj.core.api.ListAssert;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.trimToNull;

/**
 * Assertion methods for {@link HttpResponse}.
 * <br>
 * To create an instance of this class, invoke {@link HttpResponseAssertions#assertThat(HttpResponse)}.
 *
 * <br><br>
 *
 * Various wrappers exists to create {@link HttpResponse} from HTTP Clients:
 * <ul>
 *   <li>{@link OkHttpAssertions#assertThat(okhttp3.Response)}</li>
 *   <li>{@link GoogleHttpAssertions#assertThat(com.google.api.client.http.HttpResponse)}</li>
 *   <li>{@link AsyncHttpAssertions#assertThat(org.asynchttpclient.Response)}</li>
 *   <li>{@link ApacheHttpAssertions#assertThat(org.apache.http.HttpResponse)}</li>
 *   <li>{@link JunitServersHttpAssertions#assertThat(com.github.mjeanroy.junit.servers.client.HttpResponse)}</li>
 *   <li>{@link SpringMockMvcHttpAssertions#assertThat(ResultActions)}</li>
 *   <li>{@link NingHttpAssertions#assertThat(com.ning.http.client.Response)}</li>
 * </ul>
 *
 * @see OkHttpAssertions#assertThat(okhttp3.Response)
 * @see GoogleHttpAssertions#assertThat(com.google.api.client.http.HttpResponse)
 * @see AsyncHttpAssertions#assertThat(org.asynchttpclient.Response)
 * @see ApacheHttpAssertions#assertThat(org.apache.http.HttpResponse)
 * @see JunitServersHttpAssertions#assertThat(com.github.mjeanroy.junit.servers.client.HttpResponse)
 * @see SpringMockMvcHttpAssertions#assertThat(ResultActions)
 * @see NingHttpAssertions#assertThat(com.ning.http.client.Response)
 */
public class HttpResponseAssert extends AbstractHttpResponseAssert<HttpResponseAssert> {

	/**
	 * Create new assertion instance.
	 *
	 * @param actual HTTP Response.
	 */
	public HttpResponseAssert(HttpResponse actual) {
		super(actual, HttpResponseAssert.class);
	}

	/**
	 * Extract body and returns JSON assertion object, such as:
	 *
	 * <pre><code>
	 *   assertThat(response).isOk().extractingJsonBody().isEqualTo(
	 *     readFile("/json/expected.json")
	 *   );
	 * </code></pre>
	 *
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
	 * @return New {@link ListAssert ListAssert&lt;Cookie&gt;} assertion.
	 */
	public ListAssert<Cookie> extractingCookies() {
		isNotNull();
		return new ListAssert<>(
			actual.getCookies()
		);
	}

	/**
	 * Extract cookies returns new assertion with header list as value
	 * under test.
	 *
	 * For example:
	 *
	 * <pre><code>
	 *   assertThat(response).isOk().extractingHeaders()
	 *     .extracting(HttpHeader::getName, HttpHeader::getValue)
	 *     .contains(
	 *       tuple("X-Xss-Protection", "1; mode=block"),
	 *       tuple("X-Frame-Options", "deny")
	 *     );
	 * </code></pre>
	 *
	 * @return New {@link ListAssert ListAssert&lt;Cookie&gt;} assertion.
	 */
	public ListAssert<HttpHeader> extractingHeaders() {
		isNotNull();
		return new ListAssert<>(
			actual.getHeaders()
		);
	}
}
