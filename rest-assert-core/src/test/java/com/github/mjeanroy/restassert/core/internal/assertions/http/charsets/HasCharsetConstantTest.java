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

package com.github.mjeanroy.restassert.core.internal.assertions.http.charsets;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HasCharsetConstantTest extends AbstractHttpResponseAssertionsCharsetTest {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.hasCharset(response, CHARSET);
	}

	@Override
	String expectedCharset() {
		return CHARSET.displayName().toLowerCase();
	}

	@Test
	void it_should_fail_if_response_does_not_have_content_type() {
		// GIVEN
		HttpResponse rsp = new HttpResponseBuilderImpl().build();

		// WHEN
		AssertionResult result = assertions.hasCharset(rsp, CHARSET);

		// THEN
		checkError(
			result,
			"Expecting response to have header \"Content-Type\""
		);
	}

	@Test
	void it_should_fail_if_response_has_content_type_without_charset() {
		// GIVEN
		HttpResponse rsp = new HttpResponseBuilderImpl()
			.addHeader("Content-Type", "application/json")
			.build();

		// WHEN
		AssertionResult result = assertions.hasCharset(rsp, CHARSET);

		// THEN
		checkError(result, "Expecting response to have defined charset");
	}
}
