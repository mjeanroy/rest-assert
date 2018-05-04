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

import static org.assertj.core.api.Assertions.assertThat;

public class NumbersTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_get_long_value_from_string() {
		assertThat(Numbers.toLong("1", "Test")).isEqualTo(1L);
	}

	@Test
	public void it_should_fail_if_string_value_is_not_a_valid_long_value() {
		String message = "My Custom Error Message";

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(message);
		assertThat(Numbers.toLong("test", message)).isEqualTo(1L);
	}

	@Test
	public void it_should_fail_with_null() {
		String message = "My Custom Error Message";

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);
		assertThat(Numbers.toLong(null, message)).isEqualTo(1L);
	}
}
