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

package com.github.mjeanroy.restassert.test.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class JsonObjectTest {

	@Test
	public void it_should_create_empty_object() {
		JsonObject object = JsonObject.jsonObject();
		assertThat(object.getEntries()).isNotNull().isEmpty();
		assertThat(object.toJson()).isEqualTo("{}");
	}

	@Test
	public void it_should_create_object_with_entries() {
		JsonObject object = JsonObject.jsonObject(
			JsonEntry.jsonEntry("id", 1),
			JsonEntry.jsonEntry("name", "John Doe")
		);

		assertThat(object.getEntries())
			.hasSize(2)
			.extracting("key", "value")
			.contains(
				tuple("id", 1),
				tuple("name", "John Doe")
			);

		assertThat(object.toJson()).isEqualTo(
			"{" +
				"\"id\" : 1, " +
				"\"name\" : \"John Doe\"" +
			"}"
		);
	}

	@Test
	public void it_should_create_object_and_escape_double_quotes() {
		JsonObject object = JsonObject.jsonObject(
			JsonEntry.jsonEntry("\"id\"", 1),
			JsonEntry.jsonEntry("\"name\"", "\"John Doe\"")
		);

		assertThat(object.getEntries())
			.hasSize(2)
			.extracting("key", "value")
			.contains(
				tuple("\"id\"", 1),
				tuple("\"name\"", "\"John Doe\"")
			);

		assertThat(object.toJson()).isEqualTo(
			"{" +
				"\"\\\"id\\\"\" : 1, " +
				"\"\\\"name\\\"\" : \"\\\"John Doe\\\"\"" +
				"}"
		);
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(JsonObject.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		JsonObject object = JsonObject.jsonObject(
			JsonEntry.jsonEntry("id", 1),
			JsonEntry.jsonEntry("name", "John Doe")
		);

		assertThat(object.toString()).isEqualTo(
			"JsonObject{" +
				"entries={" +
					"id=JsonEntry{key=id, value=1}, " +
					"name=JsonEntry{key=name, value=John Doe}" +
				"}" +
			"}"
		);
	}
}
