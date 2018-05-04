/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.tests;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public abstract class AssertionUtils {

	public static void assertSuccessResult(AssertionResult result) {
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
		assertThat(result.getError()).isNull();
	}

	public static void assertFailureResult(AssertionResult result, Class<?> klassError, String pattern, Object[] args) {
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();

		RestAssertError error = result.getError();
		assertThat(error)
				.isNotNull()
				.isInstanceOf(klassError);

		assertThat(error.args())
				.isNotNull()
				.hasSameSizeAs(args)
				.contains(args);

		String expectedMessage = format(pattern, args);
		assertThat(error.buildMessage())
				.isNotNull()
				.isNotEmpty()
				.isEqualTo(expectedMessage);
	}

	public static void assertFailure(String message, Function test) {
		try {
			test.apply();
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo(message);
		}
	}

	public static void failBecauseExpectedAssertionErrorWasNotThrown() {
		fail("Exception was not thrown");
	}
}
