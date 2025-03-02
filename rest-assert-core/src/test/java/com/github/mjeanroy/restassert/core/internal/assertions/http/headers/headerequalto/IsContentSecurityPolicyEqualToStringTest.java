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

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.CONTENT_SECURITY_POLICY;

class IsContentSecurityPolicyEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = CONTENT_SECURITY_POLICY;
	private static final String NAME = HEADER.getName();
	private static final String VALUE = HEADER.getValue();
	private static final String FAILED_VALUE = "default-src 'none'; script-src 'self' 'unsafe-inline'";

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isContentSecurityPolicyEqualTo(response, VALUE);
	}

	@Override
	Header getHeader() {
		return HEADER;
	}

	@Override
	boolean allowMultipleValues() {
		return true;
	}

	@Override
	String failValue() {
		return FAILED_VALUE;
	}

	@Test
	void it_should_handle_different_directive_order() {
		String actual = "script-src 'self' 'unsafe-inline'; default-src 'none'";
		String expected = "default-src 'none'; script-src 'self' 'unsafe-inline'";
		doTest(actual, expected);
	}

	@Test
	void it_should_handle_different_source_value_order() {
		String actual = "default-src 'none'; script-src 'unsafe-inline' 'self'";
		String expected = "default-src 'none'; script-src 'self' 'unsafe-inline'";
		doTest(actual, expected);
	}

	@Test
	void it_should_handle_case_insensitive_comparison() {
		String actual = "DEFAULT-SRC 'none'; SCRIPT-SRC 'self' 'unsafe-inline'";
		String expected = "default-src 'none'; script-src 'self' 'unsafe-inline'";
		doTest(actual, expected);
	}

	private static void doTest(String actual, String expected) {
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();
		AssertionResult result = assertions.isContentSecurityPolicyEqualTo(response, expected);
		checkSuccess(result);
	}
}
