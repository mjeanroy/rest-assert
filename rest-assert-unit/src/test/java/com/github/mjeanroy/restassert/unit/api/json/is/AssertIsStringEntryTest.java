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

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertIsStringEntry;

class AssertIsStringEntryTest {

	@Test
	void it_should_pass_with_a_string() {
		String entryPath = "name";
		run(entryPath);
		run("Custom Message", entryPath);
	}

	@Test
	void it_should_fail_with_null_string() {
		String entryPath = "gender";
		assertFailure(defaultMessage(entryPath, "null"), () -> run(entryPath));
		assertFailure("Custom Message", () -> run("Custom Message", entryPath));
	}

	@Test
	void it_should_fail_with_an_array() {
		String entryPath = "roles";
		assertFailure(defaultMessage(entryPath, "an array"), () -> run(entryPath));
		assertFailure("Custom Message", () -> run("Custom Message", entryPath));
	}

	@Test
	void it_should_fail_with_a_boolean() {
		String entryPath = "active";
		assertFailure(defaultMessage(entryPath, "a boolean"), () -> run(entryPath));
		assertFailure("Custom Message", () -> run("Custom Message", entryPath));
	}

	@Test
	void it_should_fail_with_a_number() {
		String entryPath = "id";
		assertFailure(defaultMessage(entryPath, "a number"), () -> run(entryPath));
		assertFailure("Custom Message", () -> run("Custom Message", entryPath));
	}

	@Test
	void it_should_fail_with_an_object() {
		String entryPath = "permissions";
		assertFailure(defaultMessage(entryPath, "an object"), () -> run(entryPath));
		assertFailure("Custom Message", () -> run("Custom Message", entryPath));
	}

	private static void run(String path) {
		assertIsStringEntry(actual(), path);
	}

	private static void run(String message, String json) {
		assertIsStringEntry(message, actual(), json);
	}

	private static String defaultMessage(String entry, String actualType) {
		return "Expecting json entry " + fmt(entry) + " to be a string but was " + actualType;
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
