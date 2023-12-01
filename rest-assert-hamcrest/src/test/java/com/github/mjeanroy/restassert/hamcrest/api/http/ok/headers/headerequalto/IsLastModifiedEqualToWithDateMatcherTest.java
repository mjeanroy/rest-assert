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

package com.github.mjeanroy.restassert.hamcrest.api.http.ok.headers.headerequalto;

import com.github.mjeanroy.restassert.test.data.Header;
import okhttp3.Response;
import org.hamcrest.MatcherAssert;

import java.util.Date;

import static com.github.mjeanroy.restassert.hamcrest.api.http.OkHttpResponseMatchers.isLastModifiedEqualTo;
import static com.github.mjeanroy.restassert.test.commons.DateTestUtils.fromInternetMessageFormat;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.LAST_MODIFIED;

class IsLastModifiedEqualToWithDateMatcherTest extends AbstractOkHttpResponseHeaderEqualToMatcherTest {

	private static final String VALUE = LAST_MODIFIED.getValue();
	private static final String FAILED_VALUE = "Wed, 15 Nov 1995 12:45:26 GMT";

	@Override
	protected Header getHeader() {
		return LAST_MODIFIED;
	}

	@Override
	protected String failValue() {
		return FAILED_VALUE;
	}

	@Override
	protected void run(Response actual) {
		Date date = fromInternetMessageFormat(VALUE);
		MatcherAssert.assertThat(actual, isLastModifiedEqualTo(date));
	}
}
