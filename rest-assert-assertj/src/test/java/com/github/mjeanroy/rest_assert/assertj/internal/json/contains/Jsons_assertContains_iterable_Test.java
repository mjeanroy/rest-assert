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

package com.github.mjeanroy.rest_assert.assertj.internal.json.contains;

import com.github.mjeanroy.rest_assert.assertj.internal.Jsons;
import com.github.mjeanroy.rest_assert.tests.json.JsonObject;
import org.assertj.core.api.AssertionInfo;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.rest_assert.tests.fixtures.JsonFixtures.jsonSuccess;
import static com.github.mjeanroy.rest_assert.tests.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.rest_assert.tests.json.JsonObject.jsonObject;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class Jsons_assertContains_iterable_Test {

	protected Jsons jsons = Jsons.instance();

	@Test
	public void it_should_pass_if_json_contains_entries() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String json = jsonObject.toJson();

		jsons.assertContains(someInfo(), json, singleton("id"));
		jsons.assertContains(someInfo(), json, singleton("name"));
		jsons.assertContains(someInfo(), json, singleton("$.id"));
		jsons.assertContains(someInfo(), json, singleton("$.name"));
		jsons.assertContains(someInfo(), json, asList("id", "name"));
	}

	@Test
	public void it_should_fail_if_json_does_not_contains_entry() {
		final AssertionInfo info = someInfo();
		final JsonObject jsonObject = jsonObject(
			jsonEntry("id", 1)
		);

		final String json = jsonObject.toJson();

		try {
			jsons.assertContains(info, json, singleton("name"));
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError e) {
			String expectedMessage = "Expecting json to contain entry \"name\"";

			assertThat(e.getMessage())
					.isNotNull()
					.isNotEmpty()
					.isEqualTo(expectedMessage);
		}
	}

	protected String actual() {
		return jsonSuccess();
	}
}
