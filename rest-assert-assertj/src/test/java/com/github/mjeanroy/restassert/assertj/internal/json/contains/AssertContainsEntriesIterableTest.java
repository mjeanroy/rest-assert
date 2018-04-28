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

package com.github.mjeanroy.restassert.assertj.internal.json.contains;

import com.github.mjeanroy.restassert.assertj.api.JsonAssertions;
import com.github.mjeanroy.restassert.assertj.internal.Jsons;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.assertj.core.api.AssertionInfo;
import org.junit.Test;

import static com.github.mjeanroy.restassert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertContainsEntriesIterableTest {

	private final Jsons jsons = Jsons.instance();

	@Test
	public void it_should_pass_if_json_contains_entries() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String json = jsonObject.toJson();

		jsons.assertContainsEntries(someInfo(), json, singleton(JsonAssertions.jsonEntry("id", 1)));
		jsons.assertContainsEntries(someInfo(), json, singleton(JsonAssertions.jsonEntry("name", "John Doe")));
		jsons.assertContainsEntries(someInfo(), json, singleton(JsonAssertions.jsonEntry("$.id", 1)));
		jsons.assertContainsEntries(someInfo(), json, singleton(JsonAssertions.jsonEntry("$.name", "John Doe")));
		jsons.assertContainsEntries(someInfo(), json, asList(
			JsonAssertions.jsonEntry("id", 1),
			JsonAssertions.jsonEntry("name", "John Doe")
		));
	}

	@Test
	public void it_should_fail_if_json_does_not_contains_entry() {
		final AssertionInfo info = someInfo();
		final JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1)
		);

		final String json = jsonObject.toJson();

		try {
			jsons.assertContainsEntries(info, json, singleton(JsonAssertions.jsonEntry("id", 2)));
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError e) {
			String expectedMessage = "Expecting json entry \"id\" to be equal to 2 but was 1";

			assertThat(e.getMessage())
					.isNotNull()
					.isNotEmpty()
					.isEqualTo(expectedMessage);
		}
	}
}
