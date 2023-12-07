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

import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractHttpMatcherTest<TEST_INPUT> {

	@ParameterizedTest
	@ArgumentsSource(HttpMatcherArgumentProvider.class)
	<T> void it_should_pass(HttpMatcher<T> httpMatcher) {
		testInputs().forEach((testInput) -> {
			run(httpMatcher, newHttpResponse(httpMatcher, testInput));
		});
	}

	@ParameterizedTest
	@ArgumentsSource(HttpMatcherArgumentProvider.class)
	<T> void it_should_fail(HttpMatcher<T> httpMatcher) {
		doTest((input) ->
			run(httpMatcher, newHttpResponse(httpMatcher, input))
		);
	}

	protected abstract <T> void run(HttpMatcher<T> httpMatcher, T actual);

	protected abstract Stream<TEST_INPUT> testInputs();

	protected abstract <T> void setupHttpResponse(HttpResponseBuilder<T> httpResponseBuilder, TEST_INPUT testInput);

	protected abstract void doTest(Consumer<TEST_INPUT> testFn);

	private <T> T newHttpResponse(HttpMatcher<T> httpMatcher, TEST_INPUT testInput) {
		HttpResponseBuilder<T> builder = httpMatcher.httpResponseBuilder();

		if (testInput != null) {
			setupHttpResponse(builder, testInput);
		}

		return builder.build();
	}
}
