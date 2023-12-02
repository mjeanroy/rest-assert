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

package com.github.mjeanroy.restassert.hamcrest.api.http;

import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpResponseHeaderEqualToMatcherTest<T> extends AbstractHttpResponseMatcherTest<T> {

	@Test
	void it_should_pass_with_expected_header() {
		Header header = getHeader();
		run(newHttpResponse(header));
	}

	@Test
	void it_should_fail_with_if_response_does_not_contain_header() {
		doTest((header) -> run(newHttpResponse(header)));
	}

	private void doTest(Consumer<Header> testFn) {
		Header expectedHeader = getHeader();
		String expectedName = expectedHeader.getName();
		String expectedValue = expectedHeader.getValue();
		String actualValue = failValue();
		Header header = header(expectedName, actualValue);
		String message = generateHamcrestErrorMessage(
			buildExpectationMessage(expectedName, expectedValue),
			buildMismatchMessage(actualValue)
		);

		assertFailure(message, () -> testFn.accept(header));
	}

	protected abstract Header getHeader();

	protected String failValue() {
		return getHeader().getValue() + "foo";
	}

	private T newHttpResponse(Header header) {
		return getBuilder().addHeader(header).build();
	}

	private static String buildExpectationMessage(String expectedName, String expectedValue) {
		return String.format("Expecting response to have header %s equal to %s", expectedName, expectedValue);
	}

	private static String buildMismatchMessage(String actualValue) {
		return String.format("was %s", actualValue);
	}
}
