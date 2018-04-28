/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.tests.Function;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.junit.Test;

import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertContainsEntries;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;

public class AssertContainsEntriesTest {

	@Test
	public void it_should_pass_if_json_contains_entries() {
		String json = createJson();

		assertContainsEntries(json, JsonAssertions.jsonEntry("id", 1));
		assertContainsEntries(json, JsonAssertions.jsonEntry("name", "John Doe"));

		assertContainsEntries(json,
			JsonAssertions.jsonEntry("id", 1),
			JsonAssertions.jsonEntry("name", "John Doe")
		);
	}

	@Test
	public void it_should_fail() {
		final String json = createJson();
		final String message = "Expecting json entry id to be equal to 2 but was 1";

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				assertContainsEntries(json, JsonAssertions.jsonEntry("id", 2));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message() {
		final String json = createJson();
		final String message = "error";

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				assertContainsEntries(message, json, JsonAssertions.jsonEntry("id", 2));
			}
		});
	}

	private String createJson() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		return jsonObject.toJson();
	}
}
