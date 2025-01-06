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

package com.github.mjeanroy.restassert.core.internal.json.parsers;

import com.github.mjeanroy.restassert.test.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@SuppressWarnings("unchecked")
public abstract class AbstractJsonParserTest {

	@Test
	void it_should_parse_null() {
		assertThat(parser().parse("null")).isEqualTo(null);
	}

	@Test
	void it_should_parse_string() {
		assertThat(parser().parse("\"Hello World\"")).isEqualTo("Hello World");
	}

	@Test
	void it_should_parse_boolean() {
		assertThat(parser().parse("false")).isEqualTo(false);
		assertThat(parser().parse("true")).isEqualTo(true);
	}

	@Test
	void it_should_parse_number() {
		assertThat(parser().parse("1")).isEqualTo(1D);
		assertThat(parser().parse("1.1")).isEqualTo(1.1D);
	}

	@Test
	void it_should_parse_object() {
		String actual = toJSON(
			jsonEntry("str", "bar"),
			jsonEntry("nb", 1.0),
			jsonEntry("bool", true)
		);

		Object result = parser().parse(actual);

		assertThat(result).isInstanceOf(Map.class);
		assertThat(((Map<String, Object>) result)).hasSize(3);
	}

	@Test
	void it_should_parse_array() {
		String actual = jsonArray("Hello World").toJSON();
		Object result = parser().parse(actual);

		assertThat(result).isInstanceOf(List.class);
		assertThat(((List<Object>) result)).hasSize(1);
	}

	@Test
	void it_should_parse_with_object() {
		String actual = toJSON(
			jsonEntry("str", "bar"),
			jsonEntry("nb", 1.0),
			jsonEntry("bool", true)
		);

		Object result = parser().parse(actual);
		assertThat(result).isInstanceOf(Map.class);
	}

	@Test
	void it_should_parse_with_array() {
		String actual = jsonArray(1, 2, 3).toJSON();
		Object result = parser().parse(actual);
		assertThat(result).isInstanceOf(Iterable.class);
	}

	@Test
	void it_should_parse_json_object() {
		String actual = toJSON(
			jsonEntry("str", "bar"),
			jsonEntry("nb", 1.0),
			jsonEntry("bool", true)
		);

		Map<String, Object> map = parser().parseObject(actual);

		assertThat(map).hasSize(3).contains(
			entry("str", "bar"),
			entry("nb", 1.0),
			entry("bool", true)
		);
	}

	@Test
	void it_should_parse_complex_object() {
		String actual = toJSON(
			jsonEntry("foo", jsonObject(
					jsonEntry("id", 1.0),
					jsonEntry("name", "bar")
				)
			)
		);

		Map<String, Object> map = parser().parseObject(actual);

		assertThat(map).hasSize(1).containsKey("foo");
		assertThat(map.get("foo")).isInstanceOf(Map.class);

		Map<String, Object> nestedObject = (Map<String, Object>) map.get("foo");
		assertThat(nestedObject).hasSize(2).contains(
			entry("id", 1.0),
			entry("name", "bar")
		);
	}

	@Test
	void it_should_parse_json_array() {
		String actual = jsonArray().add("foo").add(1.0).add(true).toJSON();
		List<Object> list = parser().parseArray(actual);
		assertThat(list).hasSize(3).contains(
			"foo", 1.0, true
		);
	}

	@Test
	void it_should_parse_json_array_of_objects() {
		JSONArray jsonArray = jsonArray(
			jsonObject(
				jsonEntry("id", 1.0),
				jsonEntry("name", "foo")
			),
			jsonObject(
				jsonEntry("id", 2.0),
				jsonEntry("name", "bar")
			)
		);

		List<Object> list = parser().parseArray(jsonArray.toJSON());

		assertThat(list).hasSize(2);
		assertThat(list.get(0)).isInstanceOf(Map.class);
		assertThat(list.get(1)).isInstanceOf(Map.class);

		Map<String, Object> obj1 = (Map<String, Object>) list.get(0);
		assertThat(obj1).hasSize(2).contains(
			entry("id", 1.0),
			entry("name", "foo")
		);

		Map<String, Object> obj2 = (Map<String, Object>) list.get(1);
		assertThat(obj2).hasSize(2).contains(
			entry("id", 2.0),
			entry("name", "bar")
		);
	}

	abstract JsonParser parser();
}
