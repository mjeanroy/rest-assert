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

package com.github.mjeanroy.restassert.unit.api.http.charsets;

import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.unit.api.http.AbstractHttpTest;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.randomString;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractHttpCharsetTest extends AbstractHttpTest<String> {

	abstract String charset();

	@Override
	protected final Stream<String> testInputs() {
		return Stream.of(charset());
	}

	@Override
	protected final void runTestFailure(String msg, Consumer<String> testFn) {
		String expectedCharset = charset();
		String actualCharset = expectedCharset + "-" + randomString();
		String message = msg == null ? defaultErrorMessage(expectedCharset, actualCharset) : msg;
		assertFailure(message, () ->
			testFn.accept(actualCharset)
		);
	}

	@Override
	protected final <T> void setupHttpResponse(HttpResponseBuilder<T> builder, String charset) {
		String contentType = String.format("application/json;charset=%s", charset);
		builder.addHeader("Content-Type", contentType);
	}

	private static String defaultErrorMessage(String expectedCharset, String actualCharset) {
		return "Expecting response to have charset " + fmt(expectedCharset) + " but was " + fmt(actualCharset);
	}
}
