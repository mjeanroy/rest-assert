/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.assertj.internal.http.mimetype;

import com.github.mjeanroy.restassert.assertj.internal.HttpResponses;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHttpResponsesMimeTypeTest {

	final HttpResponses httpResponses = HttpResponses.instance();

	@Test
	public void should_pass_if_mime_type_is_ok() {
		HttpResponse httpResponse = newHttpResponse(getHeader());
		run(httpResponse);
	}

	@Test
	public void should_fail_if_header_is_not_available() {
		String expectedMimeType = getMimeType();
		String actualMimeType = expectedMimeType + "foo";

		Header expectedHeader = getHeader();
		String expectedName = expectedHeader.getName();
		String expectedValue = expectedHeader.getValue();
		String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
		Header header = header(expectedName, actualValue);

		HttpResponse httpResponse = newHttpResponse(header);

		try {
			run(httpResponse);
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError e) {
			assertThat(e.getMessage()).isEqualTo(String.format("Expecting response to have mime type \"%s\" but was \"%s\"", expectedMimeType, actualMimeType));
		}
	}

	protected abstract void run(HttpResponse httpResponse);

	protected abstract String getMimeType();

	private Header getHeader() {
		return header("Content-Type", getMimeType() + ";charset=UTF-8");
	}

	private static HttpResponse newHttpResponse(Header header) {
		return new HttpResponseBuilderImpl().addHeader(header).build();
	}
}
