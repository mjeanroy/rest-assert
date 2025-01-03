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

package com.github.mjeanroy.restassert.core.internal.error;

import com.github.mjeanroy.restassert.tests.builders.RestAssertErrorBuilder;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.error.CompositeError.composeErrors;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class CompositeErrorTest {

	@Test
	void it_should_compose_errors_messages() {
		RestAssertError error1 = createErrorWithExpectation("foo");
		RestAssertError error2 = createErrorWithExpectation("bar %s %s", 1, 2);
		RestAssertError error3 = createErrorWithExpectation("foobar %s", "hello");

		CompositeError error = composeErrors(asList(error1, error2, error3));

		assertThat(error).isNotNull();
		assertThat(error.args()).hasSize(3).contains(1, 2, "hello");

		assertThat(error.message()).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"foo,",
				"bar %s %s,",
				"foobar %s"
			))
		);

		assertThat(error.buildMessage()).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"foo,",
				"bar 1 2,",
				"foobar hello"
			))
		);
	}

	@Test
	void it_should_compose_errors_expectations() {
		RestAssertError error1 = createErrorWithExpectation("m1");
		RestAssertError error2 = createErrorWithExpectation("m2: %s %s", 1, 2);
		RestAssertError error3 = createErrorWithExpectation("m3: %s", "hello");

		CompositeError error = composeErrors(asList(error1, error2, error3));
		Message expectation = error.getExpectation();

		assertThat(expectation).isNotNull();
		assertThat(expectation.getArgs()).hasSize(3).contains(1, 2, "hello");

		assertThat(expectation.getMessage()).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"→ m1",
				"→ m2: %s %s",
				"→ m3: %s"
			))
		);

		assertThat(expectation.formatMessage()).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"→ m1",
				"→ m2: 1 2",
				"→ m3: " + fmt("hello")
			))
		);
	}

	@Test
	void it_should_compose_errors_mismatch() {
		RestAssertError error1 = createErrorWithMismatch("m1");
		RestAssertError error2 = createErrorWithMismatch("m2: %s %s", 1, 2);
		RestAssertError error3 = createErrorWithMismatch("m3: %s", "hello");

		CompositeError error = composeErrors(asList(error1, error2, error3));
		Message mismatch = error.getMismatch();

		assertThat(mismatch).isNotNull();
		assertThat(mismatch.getArgs()).hasSize(3).contains(1, 2, "hello");

		assertThat(mismatch.getMessage()).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"→ m1",
				"→ m2: %s %s",
				"→ m3: %s"
			))
		);

		assertThat(mismatch.formatMessage()).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"→ m1",
				"→ m2: 1 2",
				"→ m3: " + fmt("hello")
			))
		);
	}

	private static RestAssertError createErrorWithExpectation(String message, Object... args) {
		return new RestAssertErrorBuilder().setExpectation(message, args).build();
	}

	private static RestAssertError createErrorWithMismatch(String message, Object... args) {
		return new RestAssertErrorBuilder().setMismatch(message, args).build();
	}
}
