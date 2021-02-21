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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class JsonEntryTest {

	@Test
	public void it_should_format_null() {
		String key = "foo";
		String value = null;
		JsonEntry e = JsonEntry.jsonEntry(key, value);

		assertThat(e.getKey()).isEqualTo(key);
		assertThat(e.getValue()).isEqualTo(value);
		assertThat(e.toJson()).isEqualTo("\"foo\" : null");
	}

	@Test
	public void it_should_format_string() {
		String key = "foo";
		String value = "bar";
		JsonEntry e = JsonEntry.jsonEntry(key, value);

		assertThat(e.getKey()).isEqualTo(key);
		assertThat(e.getValue()).isEqualTo(value);
		assertThat(e.toJson()).isEqualTo("\"foo\" : \"bar\"");
	}

	@Test
	public void it_should_format_numbers() {
		String key = "foo";
		double value = 2;
		JsonEntry e = JsonEntry.jsonEntry(key, value);

		assertThat(e.getKey()).isEqualTo(key);
		assertThat(e.getValue()).isEqualTo(value);
		assertThat(e.toJson()).isEqualTo("\"foo\" : 2.0");
	}

	@Test
	public void it_should_format_boolean() {
		String key = "foo";
		boolean value = true;
		JsonEntry e = JsonEntry.jsonEntry(key, value);

		assertThat(e.getKey()).isEqualTo(key);
		assertThat(e.getValue()).isEqualTo(value);
		assertThat(e.toJson()).isEqualTo("\"foo\" : true");
	}

	@Test
	public void it_should_format_json_value() {
		String key = "foo";
		JsonValue value = mock(JsonValue.class);
		when(value.toJson()).thenReturn("{}");

		JsonEntry e = JsonEntry.jsonEntry(key, value);

		assertThat(e.getKey()).isEqualTo(key);
		assertThat(e.getValue()).isEqualTo(value);
		assertThat(e.toJson()).isEqualTo("\"foo\" : {}");
	}

	@Test
	public void it_should_escape_key_name() {
		String key = "foo \"bar\"";
		String value = "bar";
		JsonEntry e = JsonEntry.jsonEntry(key, value);

		assertThat(e.getKey()).isEqualTo(key);
		assertThat(e.getValue()).isEqualTo(value);
		assertThat(e.toJson()).isEqualTo("\"foo \\\"bar\\\"\" : \"bar\"");
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(JsonEntry.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		String key = "foo";
		String value = "bar";
		JsonEntry e = JsonEntry.jsonEntry(key, value);
		assertThat(e.toString()).isEqualTo("JsonEntry{key=foo, value=bar}");
	}
}
