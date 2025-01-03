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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusOutOfAssertionTest {

	@Test
	void it_should_not_fail_if_status_match() {
		int start = 200;
		int end = 299;
		StatusOutOfAssertion assertion = new StatusOutOfAssertion(start, end);

		for (int i = 0; i <= 999; i++) {
			if (i < start || i > end) {
				HttpResponse rsp = new HttpResponseBuilderImpl().setStatus(i).build();
				AssertionResult result = assertion.handle(rsp);
				assertThat(result).isNotNull();
				assertThat(result.isSuccess()).isTrue();
				assertThat(result.isFailure()).isFalse();
			}
		}
	}

	@Test
	void it_should_fail_if_status_does_not_match() {
		int start = 200;
		int end = 299;
		StatusOutOfAssertion assertion = new StatusOutOfAssertion(start, end);

		for (int i = start; i <= end; i++) {
			HttpResponse rsp = new HttpResponseBuilderImpl().setStatus(i).build();

			AssertionResult result = assertion.handle(rsp);

			assertThat(result).isNotNull();
			assertThat(result.isSuccess()).isFalse();
			assertThat(result.isFailure()).isTrue();

			String message = String.format("Expecting status code to be out of 200 and 299 but was %s", i);
			assertThat(result.getError().toString()).isEqualTo(message);
		}
	}

	@Test
	void it_should_fail_if_status_start_is_negative() {
		assertThatThrownBy(() -> new StatusOutOfAssertion(-1, 200))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Http status code must be positive");
	}

	@Test
	void it_should_fail_if_status_end_is_negative() {
		assertThatThrownBy(() -> new StatusOutOfAssertion(200, -1))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Http status code must be positive");
	}

	@Test
	void it_should_fail_if_status_start_is_greater_than_end() {
		assertThatThrownBy(() -> new StatusOutOfAssertion(201, 200))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Lower bound must be strictly less than upper bound");
	}
}
