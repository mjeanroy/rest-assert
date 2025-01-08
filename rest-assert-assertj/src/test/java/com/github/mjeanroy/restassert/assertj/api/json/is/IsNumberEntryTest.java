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

import static com.github.mjeanroy.restassert.assertj.api.JsonAssertions.assertThatJson;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.api.Assertions.assertThat;

class IsNumberEntryTest {

	@Test
	void it_should_pass_with_a_number() {
		it_should_succeed("id");
	}

	@Test
	void it_should_fail_with_null_string() {
		it_should_fail("gender", "null");
	}

	@Test
	void it_should_fail_with_a_boolean() {
		it_should_fail("active", "a boolean");
	}

	@Test
	void it_should_fail_with_an_array() {
		it_should_fail("roles", "an array");
	}

	@Test
	void it_should_fail_with_a_string() {
		it_should_fail("name", "a string");
	}

	@Test
	void it_should_fail_with_an_object() {
		it_should_fail("permissions", "an object");
	}

	private static void it_should_succeed(String path) {
		run(path);
	}

	private static void it_should_fail(String path, String actualType) {
		try {
			run(path);
			failBecauseExpectedAssertionErrorWasNotThrown();
		}
		catch (AssertionError e) {
			assertThat(e.getMessage()).isEqualTo(
				"Expecting json entry " + fmt(path) + " to be a number but was " + actualType
			);
		}
	}

	private static void run(String path) {
		assertThatJson(actual()).isNumberEntry(path);
	}

	private static String actual() {
		return toJSON(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe"),
			jsonEntry("active", true),
			jsonEntry("gender"),
			jsonEntry("roles", jsonArray("ADMIN")),
			jsonEntry("permissions", jsonObject())
		);
	}
}
