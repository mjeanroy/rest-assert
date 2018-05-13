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

package com.github.mjeanroy.restassert.unit.api.http.ok3.headers.headerequalto;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.GZIP_CONTENT_ENCODING;

import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.unit.api.http.OkHttpAssert;
import okhttp3.Response;

public class AssertIsGzippedTest extends AbstractOkHttpHeaderEqualToTest {

	private static final Header HEADER = GZIP_CONTENT_ENCODING;
	private static final String FAILED_VALUE = "deflate";

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected void invoke(Response actual) {
		OkHttpAssert.assertIsGzipped(actual);
	}

	@Override
	protected void invoke(String message, Response actual) {
		OkHttpAssert.assertIsGzipped(message, actual);
	}

	@Override
	protected String failValue() {
		return FAILED_VALUE;
	}
}
