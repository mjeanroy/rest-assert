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

package com.github.mjeanroy.restassert.assertj.internal.http.headers.headerequalto;

import com.github.mjeanroy.restassert.assertj.internal.HttpResponses;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHttpResponsesHeaderEqualToTest {

	final HttpResponses httpResponses = HttpResponses.instance();

	@Test
	public void should_pass_if_header_is_ok() {
		// GIVEN
		final Header header = getHeader();
		final HttpResponse httpResponse = newHttpResponse(header);

		// WHEN
		invoke(httpResponse);

		// THEN
	}

	@Test
	public void should_fail_if_header_is_not_available() {
		// GIVEN
		final Header expectedHeader = getHeader();
		final String expectedName = expectedHeader.getName();
		final String expectedValue = expectedHeader.getValue();
		final String actualValue = failValue();
		final Header header = header(expectedName, actualValue);
		final HttpResponse httpResponse = newHttpResponse(header);

		try {
			// WHEN
			invoke(httpResponse);
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError e) {
			// THEN
			assertThat(e.getMessage())
					.isNotNull()
					.isNotEmpty()
					.isEqualTo(format("Expecting response to have header \"%s\" equal to \"%s\" but was \"%s\"", expectedName, expectedValue, actualValue));
		}
	}

	/**
	 * Get expected header.
	 *
	 * @return Expected header.
	 */
	protected abstract Header getHeader();

	/**
	 * Create fail value (i.e should not match expected header value).
	 *
	 * @return Fail value.
	 */
	String failValue() {
		return getHeader().getValue() + "foo";
	}

	/**
	 * Create fake http response with expected header.
	 *
	 * @param header Expected header.
	 * @return Fake http response.
	 */
	private HttpResponse newHttpResponse(Header header) {
		return new HttpResponseBuilderImpl().addHeader(header).build();
	}

	/**
	 * Invoke test with {@code httpResponse}.
	 *
	 * @param httpResponse Http response.
	 */
	protected abstract void invoke(HttpResponse httpResponse);
}
