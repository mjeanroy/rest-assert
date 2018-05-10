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

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;

import static com.github.mjeanroy.restassert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.X_CONTENT_TYPE_OPTIONS;

public class AssertIsContentTypeOptionsEqualToStringTest extends AbstractHttpResponsesHeaderEqualToTest {

	private static final Header HEADER = X_CONTENT_TYPE_OPTIONS;
	private static final String VALUE = X_CONTENT_TYPE_OPTIONS.getValue();

	@Override
	protected void invoke(HttpResponse httpResponse) {
		httpResponses.assertIsContentTypeOptionsEqualTo(someInfo(), httpResponse, VALUE);
	}

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	public void should_fail_if_header_is_not_available() {
		// Can't provide a failed value since only "nosniff" is authorized.
	}
}
