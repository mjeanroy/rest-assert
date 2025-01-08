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

package com.github.mjeanroy.restassert.assertj.api.json.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.github.mjeanroy.restassert.assertj.api.JsonAssertions.assertThatJson;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.api.Assertions.assertThat;

class IsObjectTest {

	@ParameterizedTest
	@ValueSource(strings = { "{}", "{ }", "{\"id\": 1}" })
	void it_should_pass_with_an_object(String json) {
		it_should_succeed(json);
	}

	@Test
	void it_should_fail_with_null_string() {
		it_should_fail("null", "null");
	}

	@ParameterizedTest
	@ValueSource(strings = { "0", "0.5", "-0.5" })
	void it_should_fail_with_number(String json) {
		it_should_fail(json, "a number");
	}

	@ParameterizedTest
	@ValueSource(strings = { "false", "true" })
	void it_should_fail_with_a_boolean(String json) {
		it_should_fail(json, "a boolean");
	}

	@ParameterizedTest
	@ValueSource(strings = { "\"\"", "\"Hello World\"" })
	void it_should_fail_with_a_string(String json) {
		it_should_fail(json, "a string");
	}

	@ParameterizedTest
	@ValueSource(strings = { "[]", "[ ]", "[0,1,2]" })
	void it_should_fail_with_an_array(String json) {
		it_should_fail(json, "an array");
	}

	private static void it_should_succeed(String json) {
		run(json);
	}

	private static void it_should_fail(String json, String actualType) {
		try {
			run(json);
			failBecauseExpectedAssertionErrorWasNotThrown();
		}
		catch (AssertionError e) {
			assertThat(e.getMessage()).isEqualTo(
				"Expecting json to be an object but was " + actualType
			);
		}
	}

	private static void run(String json) {
		assertThatJson(json).isObject();
	}
}
