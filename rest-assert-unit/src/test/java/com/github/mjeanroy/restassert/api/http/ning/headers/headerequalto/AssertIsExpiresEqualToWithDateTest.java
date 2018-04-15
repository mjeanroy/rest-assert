/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.api.http.ning.headers.headerequalto;

import com.github.mjeanroy.restassert.api.http.NingHttpAssert;
import com.github.mjeanroy.restassert.tests.models.Header;
import com.ning.http.client.Response;

import java.util.Date;

import static com.github.mjeanroy.restassert.tests.Dates.fromInternetMessageFormat;
import static com.github.mjeanroy.restassert.tests.models.Header.header;

public class AssertIsExpiresEqualToWithDateTest extends AbstractGoogleHttpHeaderEqualToTest {

	private static final String VALUE = "Tue, 15 Nov 1994 12:45:26 GMT";

	@Override
	protected Header getHeader() {
		return header("Expires", VALUE);
	}

	@Override
	protected void invoke(Response actual) {
		Date date = fromInternetMessageFormat(VALUE);
		NingHttpAssert.assertIsExpiresEqualTo(actual, date);
	}

	@Override
	protected void invoke(String message, Response actual) {
		Date date = fromInternetMessageFormat(VALUE);
		NingHttpAssert.assertIsExpiresEqualTo(message, actual, date);
	}

	@Override
	protected String failValue() {
		return "Wed, 15 Nov 1995 12:45:26 GMT";
	}
}
