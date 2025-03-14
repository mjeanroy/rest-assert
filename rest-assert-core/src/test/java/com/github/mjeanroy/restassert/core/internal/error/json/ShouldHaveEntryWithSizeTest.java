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

import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryWithSize.shouldHaveEntryWithSize;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldHaveEntryWithSizeTest {

	@Test
	void it_should_format_error_message() {
		String json = toJSON(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe"),
			jsonEntry("nicknames", jsonArray())
		);

		String entry = "nicknames";
		int actualSize = 0;
		int expectedSize = 5;
		ShouldHaveEntryWithSize shouldHaveEntryWithSize = shouldHaveEntryWithSize(
			json,
			entry,
			actualSize,
			expectedSize
		);

		assertThat(shouldHaveEntryWithSize).isNotNull();
		assertThat(shouldHaveEntryWithSize.json()).isEqualTo(json);
		assertThat(shouldHaveEntryWithSize.entryName()).isEqualTo(entry);

		assertThat(shouldHaveEntryWithSize.message()).isEqualTo("Expecting json array %s to have size %s but was %s");
		assertThat(shouldHaveEntryWithSize.args()).hasSize(3).containsExactly(entry, expectedSize, actualSize);
		assertThat(shouldHaveEntryWithSize.buildMessage()).isEqualTo("Expecting json array " + fmt(entry) + " to have size 5 but was 0");
		assertThat(shouldHaveEntryWithSize.toString()).isEqualTo(shouldHaveEntryWithSize.buildMessage());
	}
}
