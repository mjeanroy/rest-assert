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

package com.github.mjeanroy.rest_assert.internal.json;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.github.mjeanroy.rest_assert.internal.json.JsonType.parseType;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonTypeTest {

	@Test
	public void it_should_return_type_null() {
		assertThat(parseType(null))
				.isNotNull()
				.isEqualTo(JsonType.NULL);
	}

	@Test
	public void it_should_return_type_number_with_integer() {
		assertThat(parseType(10))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	public void it_should_return_type_number_with_long() {
		assertThat(parseType(100000L))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	public void it_should_return_type_number_with_float() {
		assertThat(parseType(1.5))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	public void it_should_return_type_number_with_double() {
		assertThat(parseType(1.5D))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	public void it_should_return_type_boolean_with_false() {
		assertThat(parseType(false))
				.isNotNull()
				.isEqualTo(JsonType.BOOLEAN);
	}

	@Test
	public void it_should_return_type_boolean_with_true() {
		assertThat(parseType(true))
				.isNotNull()
				.isEqualTo(JsonType.BOOLEAN);
	}

	@Test
	public void it_should_return_type_string_with_string() {
		assertThat(parseType("foo"))
				.isNotNull()
				.isEqualTo(JsonType.STRING);
	}

	@Test
	public void it_should_return_type_string_with_empty_string() {
		assertThat(parseType(""))
				.isNotNull()
				.isEqualTo(JsonType.STRING);
	}

	@Test
	public void it_should_return_type_object_with_map() {
		Map<String, Object> map = new HashMap<>();
		map.put("foo", "bar");

		assertThat(parseType(map))
				.isNotNull()
				.isEqualTo(JsonType.OBJECT);
	}

	@Test
	public void it_should_return_type_array_with_collection() {
		Collection<String> list = asList("foo", "bar");
		assertThat(parseType(list))
				.isNotNull()
				.isEqualTo(JsonType.ARRAY);
	}

	@Test
	public void it_should_return_type_array_with_array() {
		String[] array = new String[]{
				"foo",
				"bar"
		};

		assertThat(parseType(array))
				.isNotNull()
				.isEqualTo(JsonType.ARRAY);
	}
}
