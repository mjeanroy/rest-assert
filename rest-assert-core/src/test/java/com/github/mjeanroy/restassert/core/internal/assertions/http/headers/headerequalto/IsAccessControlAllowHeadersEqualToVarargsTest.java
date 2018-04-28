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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.headerequalto;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.join;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static java.util.Arrays.asList;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;

public class IsAccessControlAllowHeadersEqualToVarargsTest extends AbstractHttpHeaderEqualToTest {

	private static final String V1 = "X-Requested-With";
	private static final String V2 = "X-H1";
	private static final String V3 = "X-H2";
	private static final String VALUE = join(asList(V1, V2, V3), ", ");

	@Override
	protected Header getHeader() {
		return header(ACCESS_CONTROL_ALLOW_HEADERS.getName(), VALUE);
	}

	@Override
	protected AssertionResult invoke(HttpResponse response) {
		return assertions.isAccessControlAllowHeadersEqualTo(response, V1, V2, V3);
	}

	@Override
	protected boolean allowMultipleValues() {
		return false;
	}
}
