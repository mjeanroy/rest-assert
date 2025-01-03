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

package com.github.mjeanroy.restassert.unit.api.http.status_between;

import com.github.mjeanroy.restassert.test.data.Range;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.unit.api.http.AbstractHttpTest;

import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractHttpStatusBetweenTest extends AbstractHttpTest<Integer> {

	abstract Range range();

	@Override
	protected Stream<Integer> testInputs() {
		Range range = range();
		return IntStream.range(range.getStart(), range.getEnd() + 1).boxed();
	}

	@Override
	protected void runTestFailure(String msg, Consumer<Integer> testFn) {
		Range rang = range();
		int inclusiveStart = rang.getStart();
		int inclusiveEnd = rang.getEnd();

		for (int i = 100; i <= 599; i++) {
			if (i >= inclusiveStart && i <= inclusiveEnd) {
				continue;
			}

			int status = i;
			String message = msg == null ? defaultErrorMessage(inclusiveStart, inclusiveEnd, status) : msg;
			assertFailure(message, () ->
				testFn.accept(status)
			);
		}
	}

	@Override
	protected final <T> void setupHttpResponse(HttpResponseBuilder<T> builder, Integer status) {
		builder.setStatus(status);
	}

	private static String defaultErrorMessage(int start, int end, int status) {
		return String.format("Expecting status code to be between %s and %s but was %s", start, end, status);
	}
}
