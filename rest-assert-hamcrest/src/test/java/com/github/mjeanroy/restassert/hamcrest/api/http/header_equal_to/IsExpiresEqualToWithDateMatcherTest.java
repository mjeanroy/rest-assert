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

package com.github.mjeanroy.restassert.hamcrest.api.http.header_equal_to;

import com.github.mjeanroy.restassert.hamcrest.api.http.HttpMatcher;
import com.github.mjeanroy.restassert.test.data.Header;
import org.hamcrest.MatcherAssert;

import java.util.Date;

import static com.github.mjeanroy.restassert.test.commons.DateTestUtils.fromInternetMessageFormat;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.EXPIRES;

class IsExpiresEqualToWithDateMatcherTest extends AbstractHeaderEqualToMatcherTest {

	private static final String VALUE = EXPIRES.getValue();
	private static final String FAILED_VALUE = "Wed, 15 Nov 1995 12:45:26 GMT";

	@Override
	protected <T> void run(HttpMatcher<T> httpMatcher, T actual) {
		Date date = fromInternetMessageFormat(VALUE);
		MatcherAssert.assertThat(actual, httpMatcher.isExpiresEqualTo(date));
	}

	@Override
	Header header() {
		return EXPIRES;
	}

	@Override
	String failValue() {
		return FAILED_VALUE;
	}
}
