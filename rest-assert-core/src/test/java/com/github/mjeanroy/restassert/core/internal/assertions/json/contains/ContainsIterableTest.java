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

package com.github.mjeanroy.restassert.core.internal.assertions.json.contains;

import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertSuccessResult;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

class ContainsIterableTest {

	private JsonAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	void it_should_check_if_json_contains_entries() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String actual = jsonObject.toJson();

		assertSuccessResult(assertions.contains(actual, singleton("id")));
		assertSuccessResult(assertions.contains(actual, singleton("$.id")));
		assertSuccessResult(assertions.contains(actual, singleton("name")));
		assertSuccessResult(assertions.contains(actual, singleton("$.name")));
		assertSuccessResult(assertions.contains(actual, asList("id", "name")));
	}

	@Test
	void it_should_fail_if_json_is_null_or_empty() {
		test_failure(null, "id");
		test_failure("", "id");
	}

	@Test
	void it_should_fail_if_json_does_contains_entries() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1)
		);

		String actual = jsonObject.toJson();
		test_failure(actual, "name");
		test_failure(actual, "$.name");
	}

	private void test_failure(String actual, String key) {
		assertFailureResult(
			assertions.contains(actual, singleton(key)),
			String.format("Expecting json to contain entry %s", key)
		);
	}
}
