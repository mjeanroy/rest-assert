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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StatusBetweenAssertionTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_not_fail_if_status_match() {
		final int start = 200;
		final int end = 299;
		final StatusBetweenAssertion assertion = new StatusBetweenAssertion(start, end);

		for (int i = start; i <= end; i++) {
			final HttpResponse rsp = new HttpResponseBuilderImpl().setStatus(i).build();
			final AssertionResult result = assertion.handle(rsp);
			assertThat(result).isNotNull();
			assertThat(result.isSuccess()).isTrue();
			assertThat(result.isFailure()).isFalse();
		}
	}

	@Test
	public void it_should_fail_if_status_does_not_match() {
		final int start = 200;
		final int end = 299;
		final StatusBetweenAssertion assertion = new StatusBetweenAssertion(start, end);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.setStatus(400)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting status code to be between 200 and 299 but was 400");
	}

	@Test
	public void it_should_fail_if_status_start_is_negative() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Http status code must be positive");
		new StatusBetweenAssertion(-1, 200);
	}

	@Test
	public void it_should_fail_if_status_end_is_negative() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Http status code must be positive");
		new StatusBetweenAssertion(200, -1);
	}

	@Test
	public void it_should_fail_if_status_start_is_greater_than_end() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Lower bound must be strictly less than upper bound");
		new StatusBetweenAssertion(201, 200);
	}
}