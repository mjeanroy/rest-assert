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

package com.github.mjeanroy.rest_assert.internal.json.comparators;

import com.github.mjeanroy.rest_assert.error.RestAssertError;
import com.github.mjeanroy.rest_assert.error.json.ShouldBeAnArray;
import com.github.mjeanroy.rest_assert.error.json.ShouldBeAnObject;
import com.github.mjeanroy.rest_assert.error.json.ShouldBeEntryOf;
import com.github.mjeanroy.rest_assert.error.json.ShouldBeEqualTo;
import com.github.mjeanroy.rest_assert.error.json.ShouldHaveEntry;
import com.github.mjeanroy.rest_assert.error.json.ShouldNotHaveEntry;
import com.github.mjeanroy.rest_assert.internal.json.JsonParser;
import com.github.mjeanroy.rest_assert.internal.json.JsonType;
import com.github.mjeanroy.rest_assert.tests.json.JsonArray;
import com.github.mjeanroy.rest_assert.tests.json.JsonObject;
import org.junit.Test;

import java.util.List;

import static com.github.mjeanroy.rest_assert.tests.json.JsonArray.jsonArray;
import static com.github.mjeanroy.rest_assert.tests.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.rest_assert.tests.json.JsonObject.jsonObject;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractJsonComparatorTest {

	protected abstract JsonParser jsonParser();

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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeAnArray.class);
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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeAnObject.class);
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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldHaveEntry.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(1)
				.contains("bar");
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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldNotHaveEntry.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(1)
				.contains("bar");
	}

	@Test
	public void it_should_fail_if_actual_entry_is_null_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", null)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar")
		);

		checkTypes(actual, expected, JsonType.NULL, JsonType.STRING);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_string_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar")
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 0)
		);

		checkTypes(actual, expected, JsonType.STRING, JsonType.NUMBER);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_boolean_and_expected_is_not() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", true)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 0)
		);

		checkTypes(actual, expected, JsonType.BOOLEAN, JsonType.NUMBER);
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

		checkTypes(actual, expected, JsonType.OBJECT, JsonType.NUMBER);
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

		checkTypes(actual, expected, JsonType.ARRAY, JsonType.NUMBER);
	}

	private void checkTypes(JsonObject actual, JsonObject expected, JsonType actualType, JsonType expectedType) {
		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEntryOf.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo", actualType.getFormattedName(), expectedType.getFormattedName());
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_numbers() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", 1.0)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", 2.0)
		);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo", 1.0, 2.0);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_strings() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", "bar1")
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", "bar2")
		);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo", "bar1", "bar2");
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_booleans() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", true)
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", false)
		);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo", true, false);
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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo.bar", true, false);
	}

	@Test
	public void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_values() {
		JsonObject actual = jsonObject(
				jsonEntry("foo", jsonArray("foo", 1.0, true))
		);

		JsonObject expected = jsonObject(
				jsonEntry("foo", jsonArray("bar", 2.0, false))
		);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(3);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo[0]", "foo", "bar");
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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("foo[0].bar", "foo", "bar");
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_numbers() {
		JsonArray actual = jsonArray("foo", 1.0);
		JsonArray expected = jsonArray("foo", 2.0);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("[1]", 1.0, 2.0);
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_strings() {
		JsonArray actual = jsonArray("foo", 1.0);
		JsonArray expected = jsonArray("bar", 1.0);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("[0]", "foo", "bar");
	}

	@Test
	public void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_booleans() {
		JsonArray actual = jsonArray("foo", true);
		JsonArray expected = jsonArray("foo", false);

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("[1]", true, false);
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

		JsonComparator comparator = new JsonComparator(jsonParser(), actual.toJson(), expected.toJson());
		List<RestAssertError> errors = comparator.compare();

		assertThat(errors)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error)
				.isNotNull()
				.isExactlyInstanceOf(ShouldBeEqualTo.class);

		assertThat(error.args())
				.isNotNull()
				.hasSize(3)
				.contains("[0].foo", "foo", "bar");
	}
}
