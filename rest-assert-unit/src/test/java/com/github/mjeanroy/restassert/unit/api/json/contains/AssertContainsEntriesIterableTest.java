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

package com.github.mjeanroy.restassert.unit.api.json.contains;

import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import com.github.mjeanroy.restassert.test.json.JSONTestUtils;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertContainsEntries;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

class AssertContainsEntriesIterableTest {

	@Test
	void it_should_pass_if_json_contains_entries() {
		String json = createJson();

		assertContainsEntries(json, singleton(JsonAssertions.jsonEntry("id", 1)));
		assertContainsEntries(json, singleton(JsonAssertions.jsonEntry("name", "John Doe")));

		assertContainsEntries(json, asList(
			JsonAssertions.jsonEntry("id", 1),
			JsonAssertions.jsonEntry("name", "John Doe")
		));
	}

	@Test
	void it_should_fail() {
		String json = createJson();
		String message = "Expecting json entry id to be equal to 2 but was 1";
		assertFailure(message, () -> assertContainsEntries(json, singleton(JsonAssertions.jsonEntry("id", 2))));
	}

	@Test
	void it_should_fail_with_custom_message() {
		String json = createJson();
		String message = "error";
		assertFailure(message, () -> assertContainsEntries(message, json, singleton(JsonAssertions.jsonEntry("id", 2))));
	}

	private String createJson() {
		return toJSON(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);
	}
}
