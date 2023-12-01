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

package com.github.mjeanroy.restassert.hamcrest.api.json.contains;

import com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils;
import com.github.mjeanroy.restassert.test.commons.StringTestUtils;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import com.github.mjeanroy.restassert.tests.Function;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.mjeanroy.restassert.hamcrest.api.json.JsonMatchers.contains;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static java.util.Arrays.asList;

class ContainsTest {

	@Test
	void it_should_pass_if_json_contains_entries() {
		String json = createJson();
		MatcherAssert.assertThat(json, contains("id"));
		MatcherAssert.assertThat(json, contains("name"));
		MatcherAssert.assertThat(json, contains("id", "name"));
	}

	@Test
	void it_should_fail_using_single_entry() {
		final String json = createJson();
		final String entryName = "foo";
		final String message = HamcrestTestUtils.generateHamcrestErrorMessage(
				buildExpectationMessage(entryName),
				buildMismatchMessage()
		);

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				MatcherAssert.assertThat(json, contains(entryName));
			}
		});
	}

	@Test
	void it_should_fail_using_multiple_entry() {
		final String json = createJson();
		final String entryName1 = "foo";
		final String entryName2 = "bar";
		final String entryName3 = "id";
		final String message = HamcrestTestUtils.generateHamcrestErrorMessage(
				buildExpectationMessage(asList(entryName1, entryName2)),
				buildMismatchMessage()
		);

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				MatcherAssert.assertThat(json, contains(entryName1, entryName2, entryName3));
			}
		});
	}

	private static String createJson() {
		JsonObject jsonObject = jsonObject(
				jsonEntry("id", 1),
				jsonEntry("name", "John Doe")
		);

		return jsonObject.toJson();
	}

	private static String buildExpectationMessage(String entry) {
		return String.format("Expecting json to contain entry %s", entry);
	}

	private static String buildExpectationMessage(List<String> missingEntries) {
		List<String> outputLines = new ArrayList<>(missingEntries.size());

		for (String missingEntry : missingEntries) {
			String outputLine = "- " + buildExpectationMessage(missingEntry);
			if (!outputLines.isEmpty()) {
				outputLine = "          " + outputLine;
			}

			outputLines.add(outputLine);
		}

		return StringTestUtils.join("," + System.lineSeparator(), outputLines);
	}

	private static String buildMismatchMessage() {
		return "was not";
	}
}
