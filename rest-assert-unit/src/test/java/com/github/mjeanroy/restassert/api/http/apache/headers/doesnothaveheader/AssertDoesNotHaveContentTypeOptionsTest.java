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

package com.github.mjeanroy.restassert.api.http.apache.headers.doesnothaveheader;

import com.github.mjeanroy.restassert.api.http.ApacheHttpAssert;
import com.github.mjeanroy.restassert.tests.models.Header;
import org.apache.http.HttpResponse;

import static com.github.mjeanroy.restassert.tests.models.Header.header;

public class AssertDoesNotHaveContentTypeOptionsTest extends AbstractApacheDoesNotHaveHttpHeaderTest {

	@Override
	protected Header getHeader() {
		return header("X-Content-Type-Options", "nosniff");
	}

	@Override
	protected void invoke(HttpResponse actual) {
		ApacheHttpAssert.assertDoesNotHaveContentTypeOptions(actual);
	}

	@Override
	protected void invoke(String message, HttpResponse actual) {
		ApacheHttpAssert.assertDoesNotHaveContentTypeOptions(message, actual);
	}
}
