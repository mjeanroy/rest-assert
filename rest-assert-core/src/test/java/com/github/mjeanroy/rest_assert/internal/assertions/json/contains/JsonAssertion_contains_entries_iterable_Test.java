/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.assertions.json.contains;

import com.github.mjeanroy.rest_assert.error.CompositeError;
import com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions;
import com.github.mjeanroy.rest_assert.tests.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertSuccessResult;
import static com.github.mjeanroy.rest_assert.tests.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.rest_assert.tests.json.JsonObject.jsonObject;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

public class JsonAssertion_contains_entries_iterable_Test {

	protected JsonAssertions assertions;

	@Before
	public void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	public void it_should_check_if_json_contains_entries() throws Exception {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String actual = jsonObject.toJson();

		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("id", 1))));
		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("name", "John Doe"))));
		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("$.id", 1))));
		assertSuccessResult(assertions.containsEntries(actual, asList(JsonAssertions.jsonEntry("$.name", "John Doe"))));

		assertSuccessResult(assertions.containsEntries(actual, asList(
			JsonAssertions.jsonEntry("id", 1),
			JsonAssertions.jsonEntry("name", "John Doe")
		)));
	}

	@Test
	public void it_should_fail_if_json_does_contains_entries() throws Exception {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String actual = jsonObject.toJson();

		assertFailureResult(
			assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("name", "Jane Doe"))),
			CompositeError.class,
			"Expecting json entry %s to be equal to %s but was %s", new Object[]{"name", "Jane Doe", "John Doe"}
		);

		assertFailureResult(
			assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("$.name", "Jane Doe"))),
			CompositeError.class,
			"Expecting json entry %s to be equal to %s but was %s", new Object[]{"$.name", "Jane Doe", "John Doe"}
		);
	}
}