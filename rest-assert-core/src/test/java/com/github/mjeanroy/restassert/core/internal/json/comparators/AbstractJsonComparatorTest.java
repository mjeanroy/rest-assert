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

package com.github.mjeanroy.restassert.core.internal.json.comparators;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeAnArray;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeAnObject;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeEntryOf;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryEqualTo;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntry;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryWithSize;
import com.github.mjeanroy.restassert.core.internal.error.json.ShouldNotHaveEntry;
import com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParser;
import com.github.mjeanroy.restassert.core.internal.json.JsonType;
import com.github.mjeanroy.restassert.test.json.JsonArray;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.test.json.JsonArray.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public abstract class AbstractJsonComparatorTest {

	private JsonComparator comparator;

	@Before
	public void setUp() {
		comparator = new DefaultJsonComparator(jsonParser());
	}

	protected abstract JsonParser jsonParser();

	@Test
	public void it_should_create_comparator_with_arguments() throws Exception {
		JsonParser parser = mock(JsonParser.class);
		JsonComparator comparator = new DefaultJsonComparator(parser);
		assertThat(readField(comparator, "parser", true)).isSameAs(parser);
	}

	@Test
	public void it_should_fail_if_actual_should_be_an_array() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar")
		);

		JsonArray expected = jsonArray(
				jsonObject(
						jsonEntry("foo", "bar")
				)
		);

		checkComparison(actual.toJson(), expected.toJson(), ShouldBeAnArray.class);
	}

	@Test
	public void it_should_fail_if_actual_should_be_an_object() {
		JsonArray actual = jsonArray(
				jsonObject(
						jsonEntry("foo", "bar")
				)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar")
		);

		checkComparison(actual.toJson(), expected.toJson(), ShouldBeAnObject.class);
	}

	@Test
	public void it_should_fail_if_actual_does_not_contain_expected_entry() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar")
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar"),
				jsonEntry("bar", "foo")
		);

		checkComparison(actual.toJson(), expected.toJson(), ShouldHaveEntry.class, "bar");
	}

	@Test
	public void it_should_fail_if_actual_contain_an_unexpected_entry() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar"),
				jsonEntry("bar", "foo")
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar")
		);

		checkComparison(actual.toJson(), expected.toJson(), ShouldNotHaveEntry.class, "bar");
	}

	@Test
	public void it_should_fail_if_actual_entry_is_null_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", null)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar")
		);

		checkShouldBeEntryOf(actual, expected, JsonType.NULL, JsonType.STRING);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_string_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar")
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 0)
		);

		checkShouldBeEntryOf(actual, expected, JsonType.STRING, JsonType.NUMBER);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_boolean_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", true)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 0)
		);

		checkShouldBeEntryOf(actual, expected, JsonType.BOOLEAN, JsonType.NUMBER);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_object_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", jsonObject(
						jsonEntry("hello", "world")
				))
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 0)
		);

		checkShouldBeEntryOf(actual, expected, JsonType.OBJECT, JsonType.NUMBER);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_array_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", jsonArray(
						jsonObject(
								jsonEntry("hello", "world")
						)
				))
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 0)
		);

		checkShouldBeEntryOf(actual, expected, JsonType.ARRAY, JsonType.NUMBER);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_array_and_expected_is_array_with_different_size() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", jsonArray(1, 2, 3))
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", jsonArray(1, 2))
		);

		checkComparison(actual.toJson(), expected.toJson(), ShouldHaveEntryWithSize.class, "foo", 3, 2);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_numbers() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", 1.0)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 2.0)
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "foo", 1.0, 2.0);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_strings() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar1")
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar2")
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "foo", "bar1", "bar2");
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_booleans() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", true)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", false)
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "foo", true, false);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_nested_object() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", jsonObject(
						jsonEntry("bar", true)
				))
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", jsonObject(
						jsonEntry("bar", false)
				))
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "foo.bar", true, false);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_values() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", jsonArray("foo", 1.0, true))
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", jsonArray("bar", 1.0, true))
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "foo[0]", "foo", "bar");
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_objects() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", jsonArray(
						jsonObject(
								jsonEntry("bar", "foo")
						)
				))
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", jsonArray(
						jsonObject(
								jsonEntry("bar", "bar")
						)
				))
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "foo[0].bar", "foo", "bar");
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_numbers() {
		JsonArray actual = jsonArray("foo", 1.0);
		JsonArray expected = jsonArray("foo", 2.0);
		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "[1]", 1.0, 2.0);
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_strings() {
		JsonArray actual = jsonArray("foo", 1.0);
		JsonArray expected = jsonArray("bar", 1.0);
		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "[0]", "foo", "bar");
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_booleans() {
		JsonArray actual = jsonArray("foo", true);
		JsonArray expected = jsonArray("foo", false);
		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "[1]", true, false);
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_objects() {
		JsonArray actual = jsonArray(
				jsonObject(
						jsonEntry("foo", "foo")
				)
		);

		JsonArray expected = jsonArray(
				jsonObject(
						jsonEntry("foo", "bar")
				)
		);

		checkShouldBeEqualTo(actual.toJson(), expected.toJson(), "[0].foo", "foo", "bar");
	}

	private void checkShouldBeEqualTo(String actual, String expected, Object... args) {
		checkComparison(actual, expected, ShouldHaveEntryEqualTo.class, args);
	}

	private void checkShouldBeEntryOf(JsonObject actual, JsonObject expected, JsonType actualType, JsonType expectedType) {
		checkComparison(actual.toJson(), expected.toJson(), ShouldBeEntryOf.class, "foo", actualType.getFormattedName(), expectedType.getFormattedName());
	}

	private void checkComparison(String actual, String expected, Class<?> errorKlass, Object... args) {
		List<RestAssertError> errors = comparator.compare(actual, expected);

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(errorKlass);

		assertThat(error.args())
				.isNotNull()
				.hasSize(args.length)
				.contains(args);
	}
}
