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

package com.github.mjeanroy.restassert.assertj.api.http.charsets;

import com.github.mjeanroy.restassert.assertj.api.HttpResponseAssert;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.assertj.api.HttpResponseAssertions.assertThat;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractHttpResponsesCharsetTest {

	@Test
	void should_pass() {
		HttpResponse httpResponse = newHttpResponse(getCharset());
		run(assertThat(httpResponse));
	}

	@Test
	void should_fail_if_mime_type_does_not_match() {
		String expectedCharset = getCharset();
		String actualCharset = expectedCharset + "foo";
		HttpResponse httpResponse = newHttpResponse(actualCharset);

		try {
			run(assertThat(httpResponse));
			failBecauseExpectedAssertionErrorWasNotThrown();
		}
		catch (AssertionError e) {
			assertThat(e.getMessage()).isEqualTo(
				String.format("Expecting response to have charset \"%s\" but was \"%s\"", expectedCharset, actualCharset)
			);
		}
	}

	abstract String getCharset();

	abstract void run(HttpResponseAssert assertion);

	private static HttpResponse newHttpResponse(String charset) {
		String contentType = format("application/json;charset=%s", charset);
		return new HttpResponseBuilderImpl().addHeader("Content-Type", contentType).build();
	}
}
