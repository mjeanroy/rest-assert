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

package com.github.mjeanroy.restassert.hamcrest.api.http.header_does_not_have;

import com.github.mjeanroy.restassert.hamcrest.api.http.AbstractHttpMatcherTest;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.randomString;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractDoesNotHaveHttpHeaderMatcherTest extends AbstractHttpMatcherTest<Header> {

	abstract Header header();

	@Override
	protected Stream<Header> testInputs() {
		return Stream.of(randomCookie());
	}

	@Override
	protected final void doTest(Consumer<Header> testFn) {
		Header header = header();

		String message = generateHamcrestErrorMessage(
			buildExpectationMessage(header),
			buildMismatchMessage()
		);

		assertFailure(message, () ->
			testFn.accept(header)
		);
	}

	@Override
	protected final <T> void setupHttpResponse(
		HttpResponseBuilder<T> httpResponseBuilder,
		Header header
	) {
		httpResponseBuilder.addHeader(header);
	}

	private static Header randomCookie() {
		return Header.header(randomString(), randomString());
	}

	private static String buildExpectationMessage(Header expectedHeader) {
		return "Expecting response not to have header " + fmt(expectedHeader.getName());
	}

	private static String buildMismatchMessage() {
		return "was";
	}
}
