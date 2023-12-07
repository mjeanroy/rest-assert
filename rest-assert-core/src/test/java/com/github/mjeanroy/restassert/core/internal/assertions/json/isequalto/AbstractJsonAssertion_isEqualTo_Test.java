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

package com.github.mjeanroy.restassert.core.internal.assertions.json.isequalto;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import com.github.mjeanroy.restassert.core.internal.error.CompositeError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertSuccessResult;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonSuccess;
import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractJsonAssertion_isEqualTo_Test<T> {

	static JsonAssertions assertions;

	@BeforeAll
	static void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	void it_should_pass() {
		String actual = actual();
		T expected = successObject();
		AssertionResult result = run(actual, expected);
		assertSuccessResult(result);
	}

	@Test
	void it_should_fail_with_actual_json_equal_to_null() {
		String actual = null;
		T expected = successObject();
		AssertionResult result = run(actual, expected);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).isNotNull();
		assertThat(result.getError().buildMessage()).isEqualTo("Expecting json not to be null");
	}

	@Test
	void it_should_fail() {
		String actual = actual();
		T expected = failureObject();
		AssertionResult result = run(actual, expected);

		String expectedPattern = String.join(System.lineSeparator(), Arrays.asList(
			"Expecting json entry %s to be equal to %s but was %s,",
			"Expecting json entry %s to be equal to %s but was %s,",
			"Expecting json entry %s to be equal to %s but was %s,",
			"Expecting json entry %s to be equal to %s but was %s,",
			"Expecting json entry %s to be equal to %s but was %s,",
			"Expecting json entry %s to be equal to %s but was %s"
		));

		Object[] args = new Object[] {
				"str", "bar", "foo",
				"nb", 2.0, 1.0,
				"bool", false, true,
				"array[0]", 1.1, 1.0,
				"array[1]", 2.1, 2.0,
				"array[2]", 3.1, 3.0
		};

		assertFailureResult(result, CompositeError.class, expectedPattern, args);
	}

	abstract AssertionResult run(String actual, T expected);

	abstract T successObject();

	abstract T failureObject();

	private static String actual() {
		return jsonSuccess();
	}
}
