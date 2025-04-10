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

package com.github.mjeanroy.restassert.hamcrest.api.json.contains;

import com.github.mjeanroy.restassert.test.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static java.util.Arrays.asList;

abstract class AbstractJsonContainsTest {

	final JSONObject input = jsonObject(
		jsonEntry("id", 1),
		jsonEntry("name", "John Doe")
	);

	@Test
	void it_should_pass() {
		test_pass();
	}

	@Test
	final void it_should_fail() {
		test_fail();
	}

	abstract void test_pass();

	abstract void test_fail();

	static String buildExpectationMessage(String entry) {
		return "Expecting json to contain entry " + fmt(entry);
	}

	static String buildExpectationMessage(String... missingEntries) {
		return buildExpectationMessage(asList(missingEntries));
	}

	private static String buildExpectationMessage(List<String> missingEntries) {
		List<String> outputLines = new ArrayList<>(missingEntries.size());

		for (String missingEntry : missingEntries) {
			String outputLine = "→ " + buildExpectationMessage(missingEntry);
			if (!outputLines.isEmpty()) {
				outputLine = "          " + outputLine;
			}

			outputLines.add(outputLine);
		}

		return String.join(System.lineSeparator(), outputLines);
	}

	static String buildMismatchMessage() {
		return "was not";
	}
}
