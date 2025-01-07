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

package com.github.mjeanroy.restassert.core.internal.assertions.json.contains;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import com.github.mjeanroy.restassert.core.data.JsonEntry;
import com.github.mjeanroy.restassert.test.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertSuccessResult;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

class ContainsEntriesIterableTest {

	private JsonAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	void it_should_check_if_json_contains_entries() {
		String actual = toJSON(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("id", 1))));
		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("name", "John Doe"))));
		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("$.id", 1))));
		assertSuccessResult(assertions.containsEntries(actual, singletonList(JsonAssertions.jsonEntry("$.name", "John Doe"))));

		assertSuccessResult(
			assertions.containsEntries(actual, asList(
				JsonAssertions.jsonEntry("id", 1),
				JsonAssertions.jsonEntry("name", "John Doe")
			))
		);
	}

	@Test
	void it_should_fail_if_json_is_null_or_empty() {
		JsonEntry entry = JsonAssertions.jsonEntry("name", "Jane Doe");
		test_failure_on_missing_entry((String) null, entry);
		test_failure_on_missing_entry("", entry);
	}

	@Test
	void it_should_fail_if_json_does_contains_entries() {
		JSONObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		test_failure_on_value_mismatch(jsonObject, JsonAssertions.jsonEntry("name", "Jane Doe"));
		test_failure_on_value_mismatch(jsonObject, JsonAssertions.jsonEntry("$.name", "Jane Doe"));
		test_failure_on_missing_entry(jsonObject, JsonAssertions.jsonEntry("foo", "Jane Doe"));
		test_failure_on_missing_entry(jsonObject, JsonAssertions.jsonEntry("$.foo", "Jane Doe"));
	}

	private void test_failure_on_value_mismatch(JSONObject actual, JsonEntry entry) {
		AssertionResult result = assertions.containsEntries(
			actual.toJSON(),
			singleton(entry)
		);

		String key = entry.getKey();

		assertFailureResult(
			result,
			String.format("Expecting json entry %s to be equal to %s but was %s", key, entry.getValue(), actual.getValue(key))
		);
	}

	private void test_failure_on_missing_entry(JSONObject actual, JsonEntry entry) {
		test_failure_on_missing_entry(actual.toJSON(), entry);
	}

	private void test_failure_on_missing_entry(String actual, JsonEntry entry) {
		AssertionResult result = assertions.containsEntries(
			actual,
			singleton(entry)
		);

		assertFailureResult(
			result,
			String.format("Expecting json to contain entry %s", entry.getKey())
		);
	}
}
