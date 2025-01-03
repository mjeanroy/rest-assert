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

package com.github.mjeanroy.restassert.core.internal.assertions.json.is;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class IsStringTest {

	@ParameterizedTest
	@ValueSource(strings = { "\"\"", "\"Hello World\"" })
	void it_should_succeed_with_a_string(String json) {
		it_should_succeed(json);
	}

	@ParameterizedTest
	@ValueSource(strings = { "0", "1", "1.1", "-1.5" })
	void it_should_fail_with_a_number(String json) {
		it_should_fail(json, "a number");
	}

	@ParameterizedTest
	@ValueSource(strings = { "false", "true" })
	void it_should_fail_with_a_boolean(String json) {
		it_should_fail(json, "a boolean");
	}

	@Test
	void it_should_fail_with_null() {
		it_should_fail("null", "null");
	}

	@ParameterizedTest
	@ValueSource(strings = { "[]", "[ ]", "[1,2,3]" })
	void it_should_fail_with_array(String json) {
		it_should_fail(json, "an array");
	}

	@ParameterizedTest
	@ValueSource(strings = { "{}", "{ }", "{\"id\":1}" })
	void it_should_fail_with_object(String json) {
		it_should_fail(json, "an object");
	}

	private static void it_should_succeed(String json) {
		AssertionResult result = run(json);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
		assertThat(result.getError()).isNull();
	}

	private static void it_should_fail(String json, String actualType) {
		AssertionResult result = run(json);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).isNotNull();
		assertThat(result.getError().buildMessage()).isEqualTo(
			"Expecting json to be a string but was " + actualType
		);
	}

	private static AssertionResult run(String json) {
		return JsonAssertions.instance().isString(json);
	}
}
