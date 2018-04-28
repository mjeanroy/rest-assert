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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class JsonArrayTest {

	@Test
	public void it_should_create_array_of_strings() {
		String v1 = "foo";
		String v2 = "bar";
		String v3 = "baz";
		JsonArray array = JsonArray.jsonArray(v1, v2, v3);

		assertThat(array.toJson()).isEqualTo("[\"foo\", \"bar\", \"baz\"]");
		assertThat(array.getValues()).isEqualTo(asList(v1, v2, v3));
	}

	@Test
	public void it_should_create_array_of_numbers() {
		int v1 = 1;
		int v2 = 2;
		int v3 = 3;
		JsonArray array = JsonArray.jsonArray(v1, v2, v3);

		assertThat(array.toJson()).isEqualTo("[1, 2, 3]");
		assertThat(array.getValues()).isEqualTo(asList(v1, v2, v3));
	}

	@Test
	public void it_should_create_array_of_booleans() {
		boolean v1 = true;
		boolean v2 = false;
		boolean v3 = true;
		JsonArray array = JsonArray.jsonArray(v1, v2, v3);

		assertThat(array.toJson()).isEqualTo("[true, false, true]");
		assertThat(array.getValues()).isEqualTo(asList(v1, v2, v3));
	}

	@Test
	public void it_should_create_array_of_json() {
		String json1 =
			"{" +
				"\"id\" : 1, " +
				"\"name\": \"John Doe\"" +
			"}";

		String json2 =
			"{" +
				"\"id\" : 2, " +
				"\"name\": \"Jane Doe\"" +
			"}";

		JsonValue o1 = newJsonObject(json1);
		JsonValue o2 = newJsonObject(json2);

		JsonArray array = JsonArray.jsonArray(o1, o2);

		assertThat(array.toJson()).isEqualTo(
			"[" +
				"{\"id\" : 1, \"name\": \"John Doe\"}, " +
				"{\"id\" : 2, \"name\": \"Jane Doe\"}" +
			"]"
		);

		assertThat(array.getValues()).isEqualTo(asList(o1, o2));
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(JsonArray.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		JsonArray array = JsonArray.jsonArray(true, "foo", 1);
		assertThat(array.toString()).isEqualTo("JsonArray{values=[true, foo, 1]}");
	}

	private static JsonValue newJsonObject(String json) {
		JsonValue object = mock(JsonValue.class);
		when(object.toJson()).thenReturn(json);
		return object;
	}
}
