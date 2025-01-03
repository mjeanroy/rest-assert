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

package com.github.mjeanroy.restassert.core.internal.assertions.json.isstring;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IsNumberTest {

	@Test
	void it_should_succeed_with_a_number() {
		it_should_succeed("0");
		it_should_succeed("1");
		it_should_succeed("0.5");
	}

	@Test
	void it_should_fail_with_a_string() {
		it_should_fail("\"\"", "Expecting json to be a number but was a string");
		it_should_fail("\"Message\"", "Expecting json to be a number but was a string");
	}

	@Test
	void it_should_fail_with_a_boolean() {
		it_should_fail("false", "Expecting json to be a number but was a boolean");
		it_should_fail("true", "Expecting json to be a number but was a boolean");
	}

	@Test
	void it_should_fail_with_null() {
		it_should_fail("null", "Expecting json to be a number but was null");
	}

	@Test
	void it_should_fail_with_array() {
		it_should_fail("[]", "Expecting json to be a number but was an array");
		it_should_fail("[ ]", "Expecting json to be a number but was an array");
		it_should_fail("[0,1,2]", "Expecting json to be a number but was an array");
	}

	@Test
	void it_should_fail_with_object() {
		it_should_fail("{}", "Expecting json to be a number but was an object");
		it_should_fail("{ }", "Expecting json to be a number but was an object");
		it_should_fail("{\"id\":1}", "Expecting json to be a number but was an object");
	}

	private static void it_should_succeed(String json) {
		AssertionResult result = run(json);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
		assertThat(result.getError()).isNull();
	}

	private static void it_should_fail(String json, String expectedError) {
		AssertionResult result = run(json);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).isNotNull();
		assertThat(result.getError().buildMessage()).isEqualTo(expectedError);
	}

	private static AssertionResult run(String json) {
		return JsonAssertions.instance().isNumber(json);
	}
}
