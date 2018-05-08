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

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.LAST_MODIFIED;

public class IsLastModifiedEqualToTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = LAST_MODIFIED;
	private static final String VALUE = HEADER.getValue();
	private static final String NAME = HEADER.getName();
	private static final String FAILED_VALUE = "Wed, 15 Nov 1995 12:45:26 GMT";

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected AssertionResult invoke(HttpResponse response) {
		return assertions.isLastModifiedEqualTo(response, VALUE);
	}

	@Override
	protected String failValue() {
		return FAILED_VALUE;
	}

	@Override
	protected boolean allowMultipleValues() {
		return false;
	}

	@Test
	public void it_should_support_obsolete_rfc_850_format() {
		// GIVEN
		invokeTest("Wednesday, 15-Nov-95 12:45:26 GMT");
	}

	@Test
	public void it_should_support_obsolete_asctime_format() {
		invokeTest("Wed Nov 15 12:45:26 1995");
	}

	private void invokeTest(String value) {
		// GIVEN
		final HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, value).build();

		// WHEN
		final AssertionResult result = assertions.isLastModifiedEqualTo(response, value);

		// THEN
		checkSuccess(result);
	}
}
