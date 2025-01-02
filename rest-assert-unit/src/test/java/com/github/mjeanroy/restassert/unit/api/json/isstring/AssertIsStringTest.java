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

package com.github.mjeanroy.restassert.unit.api.json.isstring;

import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertIsString;

class AssertIsStringTest {

	@Test
	void it_should_pass_with_string() {
		assertIsString("\"\"");
		assertIsString("Custom Message", "\"\"");

		assertIsString("\"Hello World\"");
		assertIsString("Custom Message", "\"Hello World\"");
	}

	@Test
	void it_should_fail_with_null_string() {
		String message = "Expecting json to be a string but was null";
		assertFailure(message, () -> assertIsString("null"));
	}

	@Test
	void it_should_fail_with_a_boolean() {
		String message = "Expecting json to be a string but was a boolean";
		assertFailure(message, () -> assertIsString("true"));
		assertFailure(message, () -> assertIsString("false"));
	}

	@Test
	void it_should_fail_with_a_number() {
		String message = "Expecting json to be a string but was a number";
		assertFailure(message, () -> assertIsString("0"));
		assertFailure(message, () -> assertIsString("1.1"));
	}

	@Test
	void it_should_fail_with_an_array() {
		String message = "Expecting json to be a string but was an array";
		assertFailure(message, () -> assertIsString("[0,1,2]"));
	}

	@Test
	void it_should_fail_with_an_object() {
		String message = "Expecting json to be a string but was an object";
		assertFailure(message, () -> assertIsString("{}"));
	}

	@Test
	void it_should_fail_with_a_custom_message() {
		String message = "Message";
		assertFailure(message, () -> assertIsString(message, "null"));
	}
}
