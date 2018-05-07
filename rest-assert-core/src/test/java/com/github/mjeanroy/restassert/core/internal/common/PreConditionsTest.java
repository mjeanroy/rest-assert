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

package com.github.mjeanroy.restassert.core.internal.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.isGreaterThan;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.isInRange;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notEmpty;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class PreConditionsTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_not_throw_npe_if_value_is_not_null() {
		String value = "foo";
		String result = notNull(value, "message");
		assertThat(result).isEqualTo(value);
	}

	@Test
	public void it_should_throw_npe_if_value_is_null() {
		String message = "message";

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);

		notNull(null, "message");
	}

	@Test
	public void notBlank_should_throw_npe_if_string_is_null() {
		String message = "Should not be null";

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);

		notBlank(null, message);
	}

	@Test
	public void notBlank_should_throw_illegal_argument_exception_if_string_is_empty() {
		String message = "Should not be null";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		notBlank("", message);
	}

	@Test
	public void notBlank_should_throw_illegal_argument_exception_if_string_is_blank() {
		String message = "Should not be null";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		notBlank("    ", message);
	}

	@Test
	public void notBlank_should_return_argument_if_string_is_ok() {
		String obj = "Foo";
		assertThat(notBlank(obj, "Should not be null")).isEqualTo(obj);
	}

	@Test
	public void isGreaterThan_should_fail_if_value_is_less_than_min() {
		String message = "Should be less than";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		isGreaterThan(0, 1, message);
	}

	@Test
	public void isGreaterThan_should_not_fail_if_value_is_equal_to_min() {
		int val = 0;
		assertThat(isGreaterThan(val, val, "foo")).isEqualTo(val);
	}

	@Test
	public void isGreaterThan_should_not_fail_if_value_is_greater_than_min() {
		int val = 0;
		assertThat(isGreaterThan(val, val - 1, "foo")).isEqualTo(val);
	}

	@Test
	public void isInRang_should_fail_if_value_is_less_than_min() {
		String message = "Should be less than";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		isInRange(0, 1, 5, message);
	}

	@Test
	public void isInRange_should_fail_if_value_is_greater_than_max() {
		String message = "Should be less than";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		isInRange(10, 1, 5, message);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_equal_to_min() {
		int val = 0;
		assertThat(isInRange(val, val, val + 1, "foo")).isEqualTo(val);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_greater_than_min() {
		int val = 0;
		assertThat(isInRange(val, val - 1, val + 1, "foo")).isEqualTo(val);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_equal_to_max() {
		int val = 0;
		assertThat(isInRange(val, val - 1, val, "foo")).isEqualTo(val);
	}

	@Test
	public void notEmpty_should_return_not_empty_iterable() {
		String message = "message";
		Iterable<String> inputs = singleton("foo");
		Iterable<String> result = notEmpty(inputs, message);
		assertThat(result).isSameAs(inputs);
	}

	@Test
	public void notEmpty_should_fail_with_null_iterable() {
		String message = "message";
		Iterable<Object> inputs = null;

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);

		notEmpty(inputs, message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_iterable() {
		String message = "message";
		Iterable<Object> inputs = emptyList();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		notEmpty(inputs, message);
	}

	@Test
	public void notEmpty_should_return_not_empty_collection() {
		String message = "message";
		Collection<String> inputs = singleton("foo");
		Collection<String> result = notEmpty(inputs, message);
		assertThat(result).isSameAs(inputs);
	}

	@Test
	public void notEmpty_should_fail_with_null_collection() {
		String message = "message";
		Collection<Object> inputs = null;

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);

		notEmpty(inputs, message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_collection() {
		String message = "message";
		Collection<Object> inputs = emptyList();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		notEmpty(inputs, message);
	}

	@Test
	public void notEmpty_should_return_not_empty_string() {
		String message = "message";
		String input = "foo";
		String result = notEmpty(input, message);
		assertThat(result).isSameAs(input);
	}

	@Test
	public void notEmpty_should_fail_with_null_string() {
		String message = "message";
		String input = null;

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);

		notEmpty(input, message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_string() {
		String message = "message";
		String input = "";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);

		notEmpty(input, message);
	}
}
