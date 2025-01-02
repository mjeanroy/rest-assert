/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.json;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonTypeTest {

	@Test
	void it_should_match_null() {
		assertThat(JsonType.NULL.match(null)).isTrue();

		assertThat(JsonType.NULL.match(false)).isFalse();
		assertThat(JsonType.NULL.match(true)).isFalse();
		assertThat(JsonType.NULL.match(emptyList())).isFalse();
		assertThat(JsonType.NULL.match(emptyMap())).isFalse();
		assertThat(JsonType.NULL.match(0)).isFalse();
		assertThat(JsonType.NULL.match(0D)).isFalse();
		assertThat(JsonType.NULL.match("")).isFalse();
		assertThat(JsonType.NULL.match("value")).isFalse();
	}

	@Test
	void it_should_match_string() {
		assertThat(JsonType.STRING.match("")).isTrue();
		assertThat(JsonType.STRING.match("value")).isTrue();

		assertThat(JsonType.STRING.match(null)).isFalse();
		assertThat(JsonType.STRING.match(false)).isFalse();
		assertThat(JsonType.STRING.match(true)).isFalse();
		assertThat(JsonType.STRING.match(emptyList())).isFalse();
		assertThat(JsonType.STRING.match(emptyMap())).isFalse();
		assertThat(JsonType.STRING.match(0)).isFalse();
		assertThat(JsonType.STRING.match(0D)).isFalse();
	}

	@Test
	void it_should_match_number() {
		assertThat(JsonType.NUMBER.match(0)).isTrue();
		assertThat(JsonType.NUMBER.match(0D)).isTrue();

		assertThat(JsonType.NUMBER.match("")).isFalse();
		assertThat(JsonType.NUMBER.match("value")).isFalse();
		assertThat(JsonType.NUMBER.match(null)).isFalse();
		assertThat(JsonType.NUMBER.match(false)).isFalse();
		assertThat(JsonType.NUMBER.match(true)).isFalse();
		assertThat(JsonType.NUMBER.match(emptyList())).isFalse();
		assertThat(JsonType.NUMBER.match(emptyMap())).isFalse();
	}

	@Test
	void it_should_match_boolean() {
		assertThat(JsonType.BOOLEAN.match(false)).isTrue();
		assertThat(JsonType.BOOLEAN.match(true)).isTrue();

		assertThat(JsonType.BOOLEAN.match(0)).isFalse();
		assertThat(JsonType.BOOLEAN.match(0D)).isFalse();
		assertThat(JsonType.BOOLEAN.match("")).isFalse();
		assertThat(JsonType.BOOLEAN.match("value")).isFalse();
		assertThat(JsonType.BOOLEAN.match(null)).isFalse();
		assertThat(JsonType.BOOLEAN.match(emptyList())).isFalse();
		assertThat(JsonType.BOOLEAN.match(emptyMap())).isFalse();
	}

	@Test
	void it_should_match_array() {
		assertThat(JsonType.ARRAY.match(emptyList())).isTrue();

		assertThat(JsonType.ARRAY.match(false)).isFalse();
		assertThat(JsonType.ARRAY.match(true)).isFalse();
		assertThat(JsonType.ARRAY.match(0)).isFalse();
		assertThat(JsonType.ARRAY.match(0D)).isFalse();
		assertThat(JsonType.ARRAY.match("")).isFalse();
		assertThat(JsonType.ARRAY.match("value")).isFalse();
		assertThat(JsonType.ARRAY.match(null)).isFalse();
		assertThat(JsonType.ARRAY.match(emptyMap())).isFalse();
	}

	@Test
	void it_should_match_object() {
		assertThat(JsonType.OBJECT.match(emptyMap())).isTrue();

		assertThat(JsonType.OBJECT.match(emptyList())).isFalse();
		assertThat(JsonType.OBJECT.match(false)).isFalse();
		assertThat(JsonType.OBJECT.match(true)).isFalse();
		assertThat(JsonType.OBJECT.match(0)).isFalse();
		assertThat(JsonType.OBJECT.match(0D)).isFalse();
		assertThat(JsonType.OBJECT.match("")).isFalse();
		assertThat(JsonType.OBJECT.match("value")).isFalse();
		assertThat(JsonType.OBJECT.match(null)).isFalse();
	}

	@Test
	void it_should_return_type_null() {
		assertThat(JsonType.parseType(null))
				.isNotNull()
				.isEqualTo(JsonType.NULL);
	}

	@Test
	void it_should_return_type_number_with_integer() {
		assertThat(JsonType.parseType(10))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	void it_should_return_type_number_with_long() {
		assertThat(JsonType.parseType(100000L))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	void it_should_return_type_number_with_float() {
		assertThat(JsonType.parseType(1.5))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	void it_should_return_type_number_with_double() {
		assertThat(JsonType.parseType(1.5D))
				.isNotNull()
				.isEqualTo(JsonType.NUMBER);
	}

	@Test
	void it_should_return_type_boolean_with_false() {
		assertThat(JsonType.parseType(false))
				.isNotNull()
				.isEqualTo(JsonType.BOOLEAN);
	}

	@Test
	void it_should_return_type_boolean_with_true() {
		assertThat(JsonType.parseType(true))
				.isNotNull()
				.isEqualTo(JsonType.BOOLEAN);
	}

	@Test
	void it_should_return_type_string_with_string() {
		assertThat(JsonType.parseType("foo"))
				.isNotNull()
				.isEqualTo(JsonType.STRING);
	}

	@Test
	void it_should_return_type_string_with_empty_string() {
		assertThat(JsonType.parseType(""))
				.isNotNull()
				.isEqualTo(JsonType.STRING);
	}

	@Test
	void it_should_return_type_object_with_map() {
		Map<String, Object> map = new HashMap<>();
		map.put("foo", "bar");

		assertThat(JsonType.parseType(map))
				.isNotNull()
				.isEqualTo(JsonType.OBJECT);
	}

	@Test
	void it_should_return_type_array_with_collection() {
		Collection<String> list = asList("foo", "bar");
		assertThat(JsonType.parseType(list))
				.isNotNull()
				.isEqualTo(JsonType.ARRAY);
	}

	@Test
	void it_should_return_type_array_with_array() {
		String[] array = new String[]{
				"foo",
				"bar"
		};

		assertThat(JsonType.parseType(array))
				.isNotNull()
				.isEqualTo(JsonType.ARRAY);
	}

	@Test
	void it_should_fail_with_unknown_object() {
		Foo foo = new Foo();

		assertThatThrownBy(() -> JsonType.parseType(foo))
				.isExactlyInstanceOf(UnsupportedOperationException.class)
				.hasMessage("Json type of object Foo{} cannot be found");
	}

	private static class Foo {
		@Override
		public String toString() {
			return "Foo{}";
		}
	}
}
