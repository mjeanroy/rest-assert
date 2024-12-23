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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonTypeTest {

	@Test
	void it_should_check_type_null() {
		assertThat(JsonType.NULL.is("null")).isTrue();

		assertThat(JsonType.NULL.is(null)).isFalse();
		assertThat(JsonType.NULL.is("")).isFalse();
		assertThat(JsonType.NULL.is("0")).isFalse();
	}

	@Test
	void it_should_check_type_string() {
		assertThat(JsonType.STRING.is("\"hello\"")).isTrue();
		assertThat(JsonType.STRING.is("\"\"")).isTrue();

		assertThat(JsonType.STRING.is(null)).isFalse();
		assertThat(JsonType.STRING.is("")).isFalse();
		assertThat(JsonType.STRING.is("\"")).isFalse();
		assertThat(JsonType.STRING.is("'hello'")).isFalse();
		assertThat(JsonType.STRING.is("null")).isFalse();
		assertThat(JsonType.STRING.is("0")).isFalse();
	}

	@Test
	void it_should_check_type_number() {
		for (int i = 0; i < 10; i++) {
			assertThat(JsonType.NUMBER.is(String.valueOf(i))).isTrue();
			assertThat(JsonType.NUMBER.is("-" + i)).isTrue();
		}

		assertThat(JsonType.NUMBER.is("10")).isTrue();
		assertThat(JsonType.NUMBER.is("10.1")).isTrue();
		assertThat(JsonType.NUMBER.is("-10")).isTrue();
		assertThat(JsonType.NUMBER.is("-10.1")).isTrue();

		assertThat(JsonType.NUMBER.is(null)).isFalse();
		assertThat(JsonType.NUMBER.is("")).isFalse();
		assertThat(JsonType.NUMBER.is("--10.1")).isFalse();
		assertThat(JsonType.NUMBER.is("10.1.1")).isFalse();
		assertThat(JsonType.NUMBER.is("10,1")).isFalse();
		assertThat(JsonType.NUMBER.is("foo")).isFalse();
	}

	@Test
	void it_should_check_type_boolean() {
		assertThat(JsonType.BOOLEAN.is("true")).isTrue();
		assertThat(JsonType.BOOLEAN.is("false")).isTrue();

		assertThat(JsonType.BOOLEAN.is(null)).isFalse();
		assertThat(JsonType.BOOLEAN.is("")).isFalse();
		assertThat(JsonType.BOOLEAN.is("null")).isFalse();
		assertThat(JsonType.BOOLEAN.is("0")).isFalse();
	}

	@Test
	void it_should_check_type_array() {
		assertThat(JsonType.ARRAY.is("[]")).isTrue();
		assertThat(JsonType.ARRAY.is("[1, 2, 3]")).isTrue();

		assertThat(JsonType.ARRAY.is(null)).isFalse();
		assertThat(JsonType.ARRAY.is("")).isFalse();
		assertThat(JsonType.ARRAY.is("null")).isFalse();
		assertThat(JsonType.ARRAY.is("{}")).isFalse();
	}

	@Test
	void it_should_check_type_object() {
		assertThat(JsonType.OBJECT.is("{}")).isTrue();
		assertThat(JsonType.OBJECT.is("{\"id\": 1}")).isTrue();

		assertThat(JsonType.OBJECT.is(null)).isFalse();
		assertThat(JsonType.OBJECT.is("")).isFalse();
		assertThat(JsonType.OBJECT.is("null")).isFalse();
		assertThat(JsonType.OBJECT.is("[]")).isFalse();
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
