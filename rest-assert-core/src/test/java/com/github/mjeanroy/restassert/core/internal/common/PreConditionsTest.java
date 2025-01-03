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

package com.github.mjeanroy.restassert.core.internal.common;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PreConditionsTest {

	@Test
	void it_should_not_throw_npe_if_value_is_not_null() {
		String value = "foo";
		String result = PreConditions.notNull(value, "message");
		assertThat(result).isEqualTo(value);
	}

	@Test
	void it_should_throw_npe_if_value_is_null() {
		Object value = null;
		String message = "message";

		assertThatThrownBy(() -> PreConditions.notNull(value, message))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage(message);
	}

	@Test
	public void notBlank_should_throw_npe_if_string_is_null() {
		String value = null;
		String message = "Should not be null";

		assertThatThrownBy(() -> PreConditions.notBlank(value, message))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage(message);
	}

	@Test
	public void notBlank_should_throw_illegal_argument_exception_if_string_is_empty() {
		String value = "";
		String message = "Should not be null";

		assertThatThrownBy(() -> PreConditions.notBlank(value, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void notBlank_should_throw_illegal_argument_exception_if_string_is_blank() {
		String value = "    ";
		String message = "Should not be null";

		assertThatThrownBy(() -> PreConditions.notBlank(value, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void notBlank_should_return_argument_if_string_is_ok() {
		String obj = "Foo";
		String message = "Should not be null";
		assertThat(PreConditions.notBlank(obj, message)).isEqualTo(obj);
	}

	@Test
	public void isGreaterThan_should_fail_if_value_is_less_than_min() {
		int val = 0;
		int minValue = 1;
		String message = "Should be less than";

		assertThatThrownBy(() -> PreConditions.isGreaterThan(val, minValue, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void isGreaterThan_should_not_fail_if_value_is_equal_to_min() {
		int val = 0;
		String message = "foo";
		assertThat(PreConditions.isGreaterThan(val, val, message)).isEqualTo(val);
	}

	@Test
	public void isGreaterThan_should_not_fail_if_value_is_greater_than_min() {
		int val = 0;
		int minValue = val - 1;
		String message = "foo";
		assertThat(PreConditions.isGreaterThan(val, minValue, message)).isEqualTo(val);
	}

	@Test
	public void isInRang_should_fail_if_value_is_less_than_min() {
		int val = 0;
		int min = 1;
		int max = 5;
		String message = "Should be less than";

		assertThatThrownBy(() -> PreConditions.isInRange(val, min, max, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void isInRange_should_fail_if_value_is_greater_than_max() {
		int val = 10;
		int min = 1;
		int max = 5;
		String message = "Should be less than";

		assertThatThrownBy(() -> PreConditions.isInRange(val, min, max, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_equal_to_min() {
		int val = 0;
		int max = val + 1;
		String message = "foo";
		assertThat(PreConditions.isInRange(val, val, max, message)).isEqualTo(val);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_greater_than_min() {
		int val = 0;
		int min = val - 1;
		int max = val + 1;
		String message = "foo";
		assertThat(PreConditions.isInRange(val, min, max, message)).isEqualTo(val);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_equal_to_max() {
		int val = 0;
		int min = val - 1;
		String message = "foo";
		assertThat(PreConditions.isInRange(val, min, val, message)).isEqualTo(val);
	}

	@Test
	public void notEmpty_should_return_not_empty_iterable() {
		String message = "message";
		Iterable<String> inputs = singleton("foo");
		Iterable<String> result = PreConditions.notEmpty(inputs, message);
		assertThat(result).isSameAs(inputs);
	}

	@Test
	public void notEmpty_should_fail_with_null_iterable() {
		String message = "message";
		Iterable<Object> inputs = null;

		assertThatThrownBy(() -> PreConditions.notEmpty(inputs, message))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage(message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_iterable() {
		String message = "message";
		Iterable<Object> inputs = emptyList();

		assertThatThrownBy(() -> PreConditions.notEmpty(inputs, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void notEmpty_should_return_not_empty_collection() {
		String message = "message";
		Collection<String> inputs = singleton("foo");
		Collection<String> result = PreConditions.notEmpty(inputs, message);
		assertThat(result).isSameAs(inputs);
	}

	@Test
	public void notEmpty_should_fail_with_null_collection() {
		String message = "message";
		Collection<Object> inputs = null;

		assertThatThrownBy(() -> PreConditions.notEmpty(inputs, message))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage(message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_collection() {
		String message = "message";
		Collection<Object> inputs = emptyList();

		assertThatThrownBy(() -> PreConditions.notEmpty(inputs, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	@Test
	public void notEmpty_should_return_not_empty_string() {
		String message = "message";
		String input = "foo";
		String result = PreConditions.notEmpty(input, message);
		assertThat(result).isSameAs(input);
	}

	@Test
	public void notEmpty_should_fail_with_null_string() {
		String message = "message";
		String input = null;

		assertThatThrownBy(() -> PreConditions.notEmpty(input, message))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage(message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_string() {
		String message = "message";
		String input = "";

		assertThatThrownBy(() -> PreConditions.notEmpty(input, message))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}
}
