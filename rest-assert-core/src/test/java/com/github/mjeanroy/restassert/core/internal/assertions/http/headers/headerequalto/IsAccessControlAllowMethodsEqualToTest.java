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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.headerequalto;

import com.github.mjeanroy.restassert.core.data.RequestMethod;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;

import static com.github.mjeanroy.restassert.test.data.Header.header;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static java.util.Arrays.asList;

class IsAccessControlAllowMethodsEqualToTest extends AbstractHttpHeaderEqualToTest {

	private static final String V1 = "GET";
	private static final String V2 = "POST";
	private static final String V3 = "PUT";
	private static final String VALUE = String.join(", ", asList(V1, V2, V3));

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isAccessControlAllowMethodsEqualTo(response,
			RequestMethod.valueOf(V1),
			RequestMethod.valueOf(V2),
			RequestMethod.valueOf(V3)
		);
	}

	@Override
	Header getHeader() {
		return header(ACCESS_CONTROL_ALLOW_METHODS.getName(), VALUE);
	}

	@Override
	boolean allowMultipleValues() {
		return false;
	}
}
