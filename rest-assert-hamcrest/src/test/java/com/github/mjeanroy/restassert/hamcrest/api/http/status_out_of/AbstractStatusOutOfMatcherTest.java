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

package com.github.mjeanroy.restassert.hamcrest.api.http.status_out_of;

import com.github.mjeanroy.restassert.hamcrest.api.http.AbstractHttpMatcherTest;
import com.github.mjeanroy.restassert.test.data.Range;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;

import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractStatusOutOfMatcherTest extends AbstractHttpMatcherTest<Integer> {

	abstract Range range();

	@Override
	protected final Stream<Integer> testInputs() {
		Range range = range();
		return IntStream.range(0, 999).filter(i -> i < range.getStart() || i > range.getEnd()).boxed();
	}

	@Override
	protected final void doTest(Consumer<Integer> testFn) {
		Range range = range();
		int start = range.getStart();
		int end = range.getEnd();

		for (int i = start; i <= end; i++) {
			int status = i;
			String message = generateHamcrestErrorMessage(
				buildExpectationMessage(start, end),
				buildMismatchMessage(status)
			);

			assertFailure(message, () ->
				testFn.accept(status)
			);
		}
	}

	@Override
	protected final <T> void setupHttpResponse(
		HttpResponseBuilder<T> httpResponseBuilder,
		Integer status
	) {
		httpResponseBuilder.setStatus(status);
	}

	private static String buildExpectationMessage(int start, int end) {
		return String.format("Expecting status code to be out of %s and %s", start, end);
	}

	private static String buildMismatchMessage(int status) {
		return String.format("was %s", status);
	}
}
