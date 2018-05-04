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

package com.github.mjeanroy.restassert.core.internal.assertions.json.contains;

import com.github.mjeanroy.restassert.core.internal.error.CompositeError;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertSuccessResult;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

public class ContainsEntriesIterableTest {

	private JsonAssertions assertions;

	@Before
	public void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	public void it_should_check_if_json_contains_entries() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String actual = jsonObject.toJson();

		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("id", 1))));
		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("name", "John Doe"))));
		assertSuccessResult(assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("$.id", 1))));
		assertSuccessResult(assertions.containsEntries(actual, singletonList(JsonAssertions.jsonEntry("$.name", "John Doe"))));

		assertSuccessResult(assertions.containsEntries(actual, asList(
			JsonAssertions.jsonEntry("id", 1),
			JsonAssertions.jsonEntry("name", "John Doe")
		)));
	}

	@Test
	public void it_should_fail_if_json_does_contains_entries() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String actual = jsonObject.toJson();

		AssertionResult r1 = assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("name", "Jane Doe")));
		assertFailureResult(r1, CompositeError.class, "Expecting json entry %s to be equal to %s but was %s", new Object[]{"name", "Jane Doe", "John Doe"});

		AssertionResult r2 = assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("$.name", "Jane Doe")));
		assertFailureResult(r2, CompositeError.class, "Expecting json entry %s to be equal to %s but was %s", new Object[]{"$.name", "Jane Doe", "John Doe"});

		AssertionResult r3 = assertions.containsEntries(actual, singleton(JsonAssertions.jsonEntry("foo", "Jane Doe")));
		assertFailureResult(r3, CompositeError.class, "Expecting json to contain entry %s", new Object[]{"foo"});
	}
}
