/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2021 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
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
import com.github.mjeanroy.restassert.test.json.JsonArray;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.test.json.JsonArray.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
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
		JsonObject actual = jsonObject(
			jsonEntry("foo", "bar")
		);

		JsonArray expected = jsonArray(
			jsonObject(
				jsonEntry("foo", "bar")
			)
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json to be an array but was an object"
		);
	}

	@Test
	void it_should_fail_if_actual_should_be_an_object() {
		JsonArray actual = jsonArray(
			jsonObject(
				jsonEntry("foo", "bar")
			)
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", "bar")
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json to be an object but was an array"
		);
	}

	@Test
	void it_should_fail_if_actual_does_not_contain_expected_entry() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", "bar")
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", "bar"),
			jsonEntry("bar", "foo")
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json to contain entry \"bar\""
		);
	}

	@Test
	void it_should_fail_if_actual_contain_an_unexpected_entry() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", "bar"),
			jsonEntry("bar", "foo")
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", "bar")
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json not to contain entry \"bar\""
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_null_and_expected_is_not() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", null)
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", "bar")
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be a string but was null"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_string_and_expected_is_not() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", "bar")
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be a number but was a string"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_boolean_and_expected_is_not() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", true)
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be a number but was a boolean"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_object_and_expected_is_not() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", jsonObject(
				jsonEntry("hello", "world")
			))
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", 0)
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be a number but was an object"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_array_and_expected_is_not() {
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

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be a number but was an array"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_array_and_expected_is_array_with_different_size() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", jsonArray(1, 2, 3))
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", jsonArray(1, 2))
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json array \"foo\" to have size 2 but was 3"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_numbers() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", 1.0)
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", 2.0)
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be equal to 2.0 but was 1.0"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_strings() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", "bar1")
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", "bar2")
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be equal to \"bar2\" but was \"bar1\""
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_booleans() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", true)
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", false)
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo\" to be equal to false but was true"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_nested_object() {
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

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo.bar\" to be equal to false but was true"
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_values() {
		JsonObject actual = jsonObject(
			jsonEntry("foo", jsonArray("foo", 1.0, true))
		);

		JsonObject expected = jsonObject(
			jsonEntry("foo", jsonArray("bar", 1.0, true))
		);

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo[0]\" to be equal to \"bar\" but was \"foo\""
		);
	}

	@Test
	void it_should_fail_if_actual_entry_is_not_equal_to_expected_entry_with_array_objects() {
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

		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"foo[0].bar\" to be equal to \"bar\" but was \"foo\""
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_numbers() {
		JsonArray actual = jsonArray("foo", 1.0);
		JsonArray expected = jsonArray("foo", 2.0);
		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"[1]\" to be equal to 2.0 but was 1.0"
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_strings() {
		JsonArray actual = jsonArray("foo", 1.0);
		JsonArray expected = jsonArray("bar", 1.0);
		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"[0]\" to be equal to \"bar\" but was \"foo\""
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_booleans() {
		JsonArray actual = jsonArray("foo", true);
		JsonArray expected = jsonArray("foo", false);
		checkComparison(
			actual.toJson(),
			expected.toJson(),
			"Expecting json entry \"[1]\" to be equal to false but was true"
		);
	}

	@Test
	void it_should_fail_if_array_entry_is_not_equal_to_expected_array_entry_with_objects() {
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

		checkComparison(
			actual.toJson(),
			expected.toJson(),
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
