/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.api.http.headers;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.rest_assert.tests.TestData.newHttpResponseWithHeader;
import static com.github.mjeanroy.rest_assert.tests.models.Header.header;
import static java.lang.String.format;

import org.junit.Test;

import com.github.mjeanroy.rest_assert.api.AbstractAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.Function;
import com.github.mjeanroy.rest_assert.tests.models.Header;

public abstract class AbstractHttpHeaderTest extends AbstractAssertTest<HttpResponse> {

	@Test
	public void it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newResponse(header));
		invoke("foo", newResponse(header));
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header() {
		final Header expectedHeader = getHeader();
		final Header header = header(expectedHeader.getValue(), expectedHeader.getName());
		final String message = format("Expecting response to have header %s", expectedHeader.getName());

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(newResponse(header));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		final Header expectedHeader = getHeader();
		final Header header = header(expectedHeader.getValue(), expectedHeader.getName());
		final String message = "foo";

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invoke(message, newResponse(header));
			}
		});
	}

	protected HttpResponse newResponse(Header header) {
		return newHttpResponseWithHeader(header);
	}

	protected abstract Header getHeader();
}