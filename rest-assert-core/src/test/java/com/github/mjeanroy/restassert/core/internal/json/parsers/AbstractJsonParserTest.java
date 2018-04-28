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

package com.github.mjeanroy.restassert.core.internal.json.parsers;

import com.github.mjeanroy.restassert.test.json.JsonArray;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.restassert.test.json.JsonArray.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public abstract class AbstractJsonParserTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_fail_to_parse_non_object_non_array() {
		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("Parser support object or array conversion only");

		parser().parse("null");
	}

	@Test
	public void it_should_parse_with_object() {
		JsonObject jsonObject = jsonObject(
				jsonEntry("str", "bar"),
				jsonEntry("nb", 1.0),
				jsonEntry("bool", true)
		);

		JsonParser parser = spy(parser());
		Object result = parser.parse(jsonObject.toJson());

		verify(parser).parseObject(jsonObject.toJson());
		assertThat(result)
				.isNotNull()
				.isInstanceOf(Map.class);
	}

	@Test
	public void it_should_parse_with_array() {
		JsonArray jsonArray = jsonArray(1, 2, 3);

		JsonParser parser = spy(parser());
		Object result = parser.parse(jsonArray.toJson());

		verify(parser).parseArray(jsonArray.toJson());
		assertThat(result)
				.isNotNull()
				.isInstanceOf(Iterable.class);
	}

	@Test
	public void it_should_parse_json_object() {
		JsonObject jsonObject = jsonObject(
				jsonEntry("str", "bar"),
				jsonEntry("nb", 1.0),
				jsonEntry("bool", true)
		);

		Map<String, Object> map = parser().parseObject(jsonObject.toJson());

		assertThat(map)
				.isNotNull()
				.isNotEmpty()
				.hasSize(3)
				.contains(
						entry("str", "bar"),
						entry("nb", 1.0),
						entry("bool", true)
				);
	}

	@Test
	public void it_should_parse_complex_object() {
		JsonObject jsonObject = jsonObject(
				jsonEntry("foo", jsonObject(
						jsonEntry("id", 1.0),
						jsonEntry("name", "bar")
				))
		);

		Map<String, Object> map = parser().parseObject(jsonObject.toJson());

		assertThat(map)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1)
				.containsKey("foo");

		assertThat(map.get("foo"))
				.isNotNull()
				.isInstanceOf(Map.class);

		Map<String, Object> nestedObject = (Map<String, Object>) map.get("foo");
		assertThat(nestedObject)
				.isNotNull()
				.hasSize(2)
				.contains(
						entry("id", 1.0),
						entry("name", "bar")
				);
	}

	@Test
	public void it_should_parse_json_array() {
		JsonArray jsonArray = jsonArray(
				"foo", 1.0, true
		);

		List<Object> list = parser().parseArray(jsonArray.toJson());

		assertThat(list)
				.isNotNull()
				.isNotEmpty()
				.hasSize(3)
				.contains("foo", 1.0, true);
	}

	@Test
	public void it_should_parse_json_array_of_objects() {
		JsonArray jsonArray = jsonArray(
				jsonObject(
						jsonEntry("id", 1.0),
						jsonEntry("name", "foo")
				),
				jsonObject(
						jsonEntry("id", 2.0),
						jsonEntry("name", "bar")
				)
		);

		List<Object> list = parser().parseArray(jsonArray.toJson());

		assertThat(list)
				.isNotNull()
				.isNotEmpty()
				.hasSize(2);

		assertThat(list.get(0))
				.isNotNull()
				.isInstanceOf(Map.class);

		assertThat(list.get(1))
				.isNotNull()
				.isInstanceOf(Map.class);

		Map<String, Object> obj1 = (Map<String, Object>) list.get(0);
		assertThat(obj1)
				.hasSize(2)
				.contains(
						entry("id", 1.0),
						entry("name", "foo")
				);

		Map<String, Object> obj2 = (Map<String, Object>) list.get(1);
		assertThat(obj2)
				.hasSize(2)
				.contains(
						entry("id", 2.0),
						entry("name", "bar")
				);
	}

	protected abstract JsonParser parser();
}
