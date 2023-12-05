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

package com.github.mjeanroy.restassert.hamcrest.api.http.header_equal_to;

import com.github.mjeanroy.restassert.hamcrest.api.http.AbstractHttpMatcherTest;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractHeaderEqualToMatcherTest extends AbstractHttpMatcherTest<Header> {

	abstract Header header();

	@Override
	protected final Stream<Header> testInputs() {
		return Stream.of(header());
	}

	@Override
	protected final void doTest(Consumer<Header> testFn) {
		Header expectedHeader = header();
		String expectedName = expectedHeader.getName();
		String expectedValue = expectedHeader.getValue();

		String actualValue = failValue();
		Header actualHeader = Header.header(expectedName, actualValue);

		String message = generateHamcrestErrorMessage(
			buildExpectationMessage(expectedName, expectedValue),
			buildMismatchMessage(actualValue)
		);

		assertFailure(message, () ->
			testFn.accept(actualHeader)
		);
	}

	@Override
	protected final <T> void setupHttpResponse(
		HttpResponseBuilder<T> httpResponseBuilder,
		Header header
	) {
		httpResponseBuilder.addHeader(header);
	}

	String failValue() {
		return header().getValue() + "foo";
	}

	private static String buildExpectationMessage(String expectedName, String expectedValue) {
		return String.format("Expecting response to have header %s equal to %s", expectedName, expectedValue);
	}

	private static String buildMismatchMessage(String actualValue) {
		return String.format("was %s", actualValue);
	}
}
