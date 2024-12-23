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

package com.github.mjeanroy.restassert.unit.api.http.header_does_not_have;

import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.unit.api.http.AbstractHttpTest;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.randomString;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractDoesNotHaveHttpHeaderTest extends AbstractHttpTest<Header> {

	abstract Header header();

	@Override
	protected Stream<Header> testInputs() {
		return Stream.of(
			Header.header(randomString(), randomString())
		);
	}

	@Override
	protected void runTestFailure(String msg, Consumer<Header> testFn) {
		Header header = header();
		String message = msg == null ? defaultErrorMessage(header) : msg;
		assertFailure(message, () ->
			testFn.accept(header)
		);
	}

	@Override
	protected <T> void setupHttpResponse(HttpResponseBuilder<T> builder, Header header) {
		builder.addHeader(header);
	}

	private static String defaultErrorMessage(Header expectedHeader) {
		return "Expecting response not to have header " + fmt(expectedHeader.getName());
	}
}
