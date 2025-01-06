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

package com.github.mjeanroy.restassert.core.internal.json.comparators;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParser;
import com.github.mjeanroy.restassert.test.json.JSONArray;
import com.github.mjeanroy.restassert.test.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public abstract class AbstractJsonComparatorTest {

	private JsonComparator comparator;

	@BeforeEach
	void setUp() {
		comparator = new DefaultJsonComparator(jsonParser());
	}

	protected abstract JsonParser jsonParser();

	@Test
	void it_should_create_comparator_with_arguments() throws Exception {
		JsonParser parser = mock(JsonParser.class);
		JsonComparator comparator = new DefaultJsonComparator(parser);
		assertThat(readField(comparator, "parser", true)).isSameAs(parser);
	}

	@Test
	void it_should_fail_if_actual_should_be_an_array() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", "bar")
		);

		JSONArray expected = jsonArray(
			jsonObject(
				jsonEntry("foo", "bar")
			)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json to be an array but was an object"
		);
	}

	@Test
	void it_should_fail_if_actual_should_be_an_object() {
		JSONArray actual = jsonArray(
			jsonObject(
				jsonEntry("foo", "bar")
			)
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", "bar")
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json to be an object but was an array"
		);
	}

	@Test
	void it_should_fail_if_actual_does_not_contain_expected_entry() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", "bar")
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", "bar"),
			jsonEntry("bar", "foo")
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json to contain entry \"bar\""
		);
	}

	@Test
	void it_should_fail_if_actual_contain_an_unexpected_entry() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", "bar"),
			jsonEntry("bar", "foo")
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", "bar")
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json not to contain entry \"bar\""
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_null_and_expected_is_not() {
		JSONObject actual = jsonObject(
			jsonEntry("foo")
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", "bar")
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be a string but was null"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_string_and_expected_is_not() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", "bar")
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be a number but was a string"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_boolean_and_expected_is_not() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", true)
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be a number but was a boolean"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_object_and_expected_is_not() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", jsonObject(
				jsonEntry("hello", "world")
			))
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be a number but was an object"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_array_and_expected_is_not() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", jsonArray(
				jsonObject(
					jsonEntry("hello", "world")
				)
			))
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be a number but was an array"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_array_and_expected_is_array_with_different_size() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", jsonArray(1, 2, 3))
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", jsonArray(1, 2))
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json array \"foo\" to have size 2 but was 3"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_numbers() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", 1.0)
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", 2.0)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be equal to 2.0 but was 1.0"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_strings() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", "bar1")
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", "bar2")
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be equal to \"bar2\" but was \"bar1\""
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_booleans() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", true)
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", false)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo\" to be equal to false but was true"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_nested_object() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", jsonObject(
				jsonEntry("bar", true)
			))
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", jsonObject(
				jsonEntry("bar", false)
			))
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo.bar\" to be equal to false but was true"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_values() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", jsonArray().add("foo").add(1.0).add(true))
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", jsonArray().add("bar").add(1.0).add(true))
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo[0]\" to be equal to \"bar\" but was \"foo\""
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_objects() {
		JSONObject actual = jsonObject(
			jsonEntry("foo", jsonArray(
				jsonObject(
					jsonEntry("bar", "foo")
				)
			))
		);

		JSONObject expected = jsonObject(
			jsonEntry("foo", jsonArray(
				jsonObject(
					jsonEntry("bar", "bar")
				)
			))
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"foo[0].bar\" to be equal to \"bar\" but was \"foo\""
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_numbers() {
		JSONArray actual = jsonArray().add("foo").add(1.0);
		JSONArray expected = jsonArray().add("foo").add(2.0);
		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"[1]\" to be equal to 2.0 but was 1.0"
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_strings() {
		JSONArray actual = jsonArray().add("foo").add(1.0);
		JSONArray expected = jsonArray().add("bar").add(1.0);
		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"[0]\" to be equal to \"bar\" but was \"foo\""
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_booleans() {
		JSONArray actual = jsonArray().add("foo").add(true);
		JSONArray expected = jsonArray().add("foo").add(false);
		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"[1]\" to be equal to false but was true"
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_objects() {
		JSONArray actual = jsonArray(
			jsonObject(
				jsonEntry("foo", "foo")
			)
		);

		JSONArray expected = jsonArray(
			jsonObject(
				jsonEntry("foo", "bar")
			)
		);

		checkComparison(
			actual.toJSON(),
			expected.toJSON(),
			"Expecting json entry \"[0].foo\" to be equal to \"bar\" but was \"foo\""
		);
	}

	private void checkComparison(String actual, String expected, String expectedErrorMessage) {
		List<RestAssertError> errors = comparator.compare(actual, expected);
		assertThat(errors).hasSize(1);

		RestAssertError error = errors.get(0);
		assertThat(error).isNotNull();
		assertThat(error.buildMessage()).isEqualTo(expectedErrorMessage);
	}
}
