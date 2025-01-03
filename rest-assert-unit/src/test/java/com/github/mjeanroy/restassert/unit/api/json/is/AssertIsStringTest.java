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

package com.github.mjeanroy.restassert.unit.api.json.is;

import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertIsString;

class AssertIsStringTest {

	@Test
	void it_should_pass_with_string() {
		run("\"\"");
		run("\"Hello World\"");
		run("Custom Message", "\"Hello World\"");
	}

	@Test
	void it_should_fail_with_null_string() {
		String message = defaultMessage("null");
		assertFailure(message, () -> run("null"));
	}

	@Test
	void it_should_fail_with_a_boolean() {
		String message = defaultMessage("a boolean");
		assertFailure(message, () -> run("true"));
		assertFailure(message, () -> run("false"));
	}

	@Test
	void it_should_fail_with_a_number() {
		String message = defaultMessage("a number");
		assertFailure(message, () -> run("0"));
		assertFailure(message, () -> run("1.1"));
	}

	@Test
	void it_should_fail_with_an_array() {
		String message = defaultMessage("an array");
		assertFailure(message, () -> run("[0,1,2]"));
	}

	@Test
	void it_should_fail_with_an_object() {
		String message = defaultMessage("an object");
		assertFailure(message, () -> run("{}"));
	}

	@Test
	void it_should_fail_with_a_custom_message() {
		String message = "Message";
		assertFailure(message, () -> run(message, "null"));
	}

	private static void run(String json) {
		assertIsString(json);
	}

	private static void run(String message, String json) {
		assertIsString(message, json);
	}

	private static String defaultMessage(String actualType) {
		return "Expecting json to be a string but was " + actualType;
	}
}
