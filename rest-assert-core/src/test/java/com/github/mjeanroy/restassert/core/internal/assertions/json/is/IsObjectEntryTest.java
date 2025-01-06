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

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static org.assertj.core.api.Assertions.assertThat;

class IsObjectEntryTest {

	@Test
	void it_should_succeed_with_an_object() {
		it_should_succeed(actual(), "permissions");
	}

	@Test
	void it_should_fail_with_a_number() {
		it_should_fail(actual(), "id", "a number");
	}

	@Test
	void it_should_fail_with_a_boolean() {
		it_should_fail(actual(), "active", "a boolean");
	}

	@Test
	void it_should_fail_with_null() {
		it_should_fail(actual(), "gender", "null");
	}

	@Test
	void it_should_fail_with_array() {
		it_should_fail(actual(), "roles", "an array");
	}

	@Test
	void it_should_fail_with_a_string() {
		it_should_fail(actual(), "name", "a string");
	}

	private static void it_should_succeed(String json, String path) {
		AssertionResult result = run(json, path);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
		assertThat(result.getError()).isNull();
	}

	private static void it_should_fail(String json, String path, String actualType) {
		AssertionResult result = run(json, path);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).isNotNull();
		assertThat(result.getError().buildMessage()).isEqualTo(
			"Expecting json entry " + fmt(path) + " to be an object but was " + actualType
		);
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

	private static AssertionResult run(String json, String path) {
		return JsonAssertions.instance().isObjectEntry(json, path);
	}
}
