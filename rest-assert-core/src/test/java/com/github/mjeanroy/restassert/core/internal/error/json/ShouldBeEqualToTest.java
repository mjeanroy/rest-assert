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

package com.github.mjeanroy.restassert.core.internal.error.json;

import com.github.mjeanroy.restassert.test.json.JSONTestUtils;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryEqualTo.shouldHaveEntryEqualTo;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldBeEqualToTest {

	@Test
	void it_should_format_error_message() {
		String json = toJSON(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe")
		);

		String entry = "id";
		int actualValue = 1;
		int expectedValue = 2;
		ShouldHaveEntryEqualTo shouldBeEqualTo = shouldHaveEntryEqualTo(
			json,
			entry,
			actualValue,
			expectedValue
		);

		assertThat(shouldBeEqualTo).isNotNull();
		assertThat(shouldBeEqualTo.json()).isEqualTo(json);
		assertThat(shouldBeEqualTo.entryName()).isEqualTo(entry);

		assertThat(shouldBeEqualTo.message()).isEqualTo("Expecting json entry %s to be equal to %s but was %s");
		assertThat(shouldBeEqualTo.args()).hasSize(3).containsExactly(entry, expectedValue, actualValue);
		assertThat(shouldBeEqualTo.buildMessage()).isEqualTo("Expecting json entry " + fmt(entry) + " to be equal to 2 but was 1");
		assertThat(shouldBeEqualTo.toString()).isEqualTo(shouldBeEqualTo.buildMessage());
	}
}
