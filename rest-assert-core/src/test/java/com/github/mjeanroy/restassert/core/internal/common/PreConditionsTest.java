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

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class PreConditionsTest {

	@Test
	public void it_should_not_throw_npe_if_value_is_not_null() {
		final String value = "foo";
		final String result = PreConditions.notNull(value, "message");
		assertThat(result).isEqualTo(value);
	}

	@Test
	public void it_should_throw_npe_if_value_is_null() {
		final Object value = null;
		final String message = "message";

		assertThatThrownBy(notNull(value, message))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	public void notBlank_should_throw_npe_if_string_is_null() {
		final String value = null;
		final String message = "Should not be null";

		assertThatThrownBy(notBlank(value, message))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	public void notBlank_should_throw_illegal_argument_exception_if_string_is_empty() {
		final String value = "";
		final String message = "Should not be null";

		assertThatThrownBy(notBlank(value, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void notBlank_should_throw_illegal_argument_exception_if_string_is_blank() {
		final String value = "    ";
		final String message = "Should not be null";

		assertThatThrownBy(notBlank(value, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void notBlank_should_return_argument_if_string_is_ok() {
		final String obj = "Foo";
		final String message = "Should not be null";
		assertThat(PreConditions.notBlank(obj, message)).isEqualTo(obj);
	}

	@Test
	public void isGreaterThan_should_fail_if_value_is_less_than_min() {
		final int val = 0;
		final int minValue = 1;
		final String message = "Should be less than";

		assertThatThrownBy(isGreaterThan(val, minValue, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void isGreaterThan_should_not_fail_if_value_is_equal_to_min() {
		final int val = 0;
		final String message = "foo";
		assertThat(PreConditions.isGreaterThan(val, val, message)).isEqualTo(val);
	}

	@Test
	public void isGreaterThan_should_not_fail_if_value_is_greater_than_min() {
		final int val = 0;
		final int minValue = val - 1;
		final String message = "foo";
		assertThat(PreConditions.isGreaterThan(val, minValue, message)).isEqualTo(val);
	}

	@Test
	public void isInRang_should_fail_if_value_is_less_than_min() {
		final int val = 0;
		final int min = 1;
		final int max = 5;
		final String message = "Should be less than";

		assertThatThrownBy(isInRange(val, min, max, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void isInRange_should_fail_if_value_is_greater_than_max() {
		final int val = 10;
		final int min = 1;
		final int max = 5;
		final String message = "Should be less than";

		assertThatThrownBy(isInRange(val, min, max, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_equal_to_min() {
		final int val = 0;
		final int max = val + 1;
		final String message = "foo";
		assertThat(PreConditions.isInRange(val, val, max, message)).isEqualTo(val);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_greater_than_min() {
		final int val = 0;
		final int min = val - 1;
		final int max = val + 1;
		final String message = "foo";
		assertThat(PreConditions.isInRange(val, min, max, message)).isEqualTo(val);
	}

	@Test
	public void isInRange_should_not_fail_if_value_is_equal_to_max() {
		final int val = 0;
		final int min = val - 1;
		final String message = "foo";
		assertThat(PreConditions.isInRange(val, min, val, message)).isEqualTo(val);
	}

	@Test
	public void notEmpty_should_return_not_empty_iterable() {
		final String message = "message";
		final Iterable<String> inputs = singleton("foo");
		final Iterable<String> result = PreConditions.notEmpty(inputs, message);
		assertThat(result).isSameAs(inputs);
	}

	@Test
	public void notEmpty_should_fail_with_null_iterable() {
		final String message = "message";
		final Iterable<Object> inputs = null;

		assertThatThrownBy(notEmpty(inputs, message))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_iterable() {
		String message = "message";
		Iterable<Object> inputs = emptyList();

		assertThatThrownBy(notEmpty(inputs, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void notEmpty_should_return_not_empty_collection() {
		final String message = "message";
		final Collection<String> inputs = singleton("foo");
		final Collection<String> result = PreConditions.notEmpty(inputs, message);
		assertThat(result).isSameAs(inputs);
	}

	@Test
	public void notEmpty_should_fail_with_null_collection() {
		final String message = "message";
		final Collection<Object> inputs = null;

		assertThatThrownBy(notEmpty(inputs, message))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_collection() {
		final String message = "message";
		final Collection<Object> inputs = emptyList();

		assertThatThrownBy(notEmpty(inputs, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	public void notEmpty_should_return_not_empty_string() {
		final String message = "message";
		final String input = "foo";
		final String result = PreConditions.notEmpty(input, message);
		assertThat(result).isSameAs(input);
	}

	@Test
	public void notEmpty_should_fail_with_null_string() {
		final String message = "message";
		final String input = null;

		assertThatThrownBy(notEmpty(input, message))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	public void notEmpty_should_fail_with_empty_string() {
		final String message = "message";
		final String input = "";

		assertThatThrownBy(notEmpty(input, message))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	private static ThrowingCallable notNull(final Object value, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.notNull(value, message);
			}
		};
	}

	private static ThrowingCallable notBlank(final String value, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.notBlank(value, message);
			}
		};
	}

	private static ThrowingCallable isGreaterThan(final int val, final int min, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.isGreaterThan(val, min, message);
			}
		};
	}

	private static ThrowingCallable isInRange(final int val, final int min, final int max, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.isInRange(val, min, max, message);
			}
		};
	}

	private static ThrowingCallable notEmpty(final Iterable<Object> inputs, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.notEmpty(inputs, message);
			}
		};
	}

	private static ThrowingCallable notEmpty(final Collection<Object> inputs, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.notEmpty(inputs, message);
			}
		};
	}

	private static ThrowingCallable notEmpty(final String input, final String message) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				PreConditions.notEmpty(input, message);
			}
		};
	}
}
