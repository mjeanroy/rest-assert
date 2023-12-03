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

package com.github.mjeanroy.restassert.unit.api.http;

import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.randomString;

public abstract class AbstractHttpTest<TEST_INPUT> {

	@ParameterizedTest
	@ArgumentsSource(HttpAsserterArgumentProvider.class)
	<T> void it_should_pass(HttpAsserter<T> httpAssert) {
		testInputs().forEach((testInput -> {
			runTest(
				httpAssert,
				newHttpResponse(httpAssert, testInput)
			);

			runTest(
				httpAssert,
				randomMessage(),
				newHttpResponse(httpAssert, testInput)
			);
		}));
	}

	@ParameterizedTest
	@ArgumentsSource(HttpAsserterArgumentProvider.class)
	<T> void it_should_fail(HttpAsserter<T> httpAssert) {
		runTestFailure(null, (charset) ->
			runTest(
				httpAssert,
				newHttpResponse(httpAssert, charset)
			)
		);
	}

	@ParameterizedTest
	@ArgumentsSource(HttpAsserterArgumentProvider.class)
	<T> void it_should_fail_with_custom_message(HttpAsserter<T> httpAssert) {
		String message = randomMessage();
		runTestFailure(message, (charset) ->
			runTest(
				httpAssert,
				message,
				newHttpResponse(httpAssert, charset)
			)
		);
	}

	protected abstract Stream<TEST_INPUT> testInputs();

	protected abstract void runTestFailure(
		String msg,
		Consumer<TEST_INPUT> testFn
	);

	protected abstract <T> void runTest(
		HttpAsserter<T> httpAssert,
		T actual
	);

	protected abstract <T> void runTest(
		HttpAsserter<T> httpAssert,
		String message,
		T actual
	);

	private String randomMessage() {
		return getClass().getSimpleName() + "::" + randomString();
	}

	protected final <T> T newHttpResponse(HttpAsserter<T> httpAssert, TEST_INPUT input) {
		HttpResponseBuilder<T> builder = httpAssert.httpResponseBuilder();

		if (input != null) {
			setupHttpResponse(builder, input);
		}

		return builder.build();
	}

	protected abstract <T> void setupHttpResponse(
		HttpResponseBuilder<T> builder,
		TEST_INPUT input
	);
}
