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

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class IsHeaderEqualToAssertionTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_value() {
		final String name = "foo";
		final String value = "bar";
		final IsHeaderEqualToAssertion assertion = new IsHeaderEqualToAssertion(name, value, false);
		final HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, value).build();

		final AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_multiple_value_header_contains_expected_value() {
		final String name = "foo";
		final String v1 = "bar";
		final String v2 = v1 + v1;
		final IsHeaderEqualToAssertion assertion = new IsHeaderEqualToAssertion(name, v1, false);
		final HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, v2).addHeader(name, v1).build();

		final AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_header_is_not_set() {
		final String name = "foo";
		final String value = "bar";
		final IsHeaderEqualToAssertion assertion = new IsHeaderEqualToAssertion(name, value, false);
		final HttpResponse rsp = new HttpResponseBuilderImpl().addHeader("bar", "bar").build();

		final AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo");
	}

	@Test
	public void it_should_fail_if_header_is_does_not_have_expected_value() {
		final String name = "foo";
		final String v1 = "bar";
		final String v2 = v1 + v1;
		final IsHeaderEqualToAssertion assertion = new IsHeaderEqualToAssertion(name, v1, false);
		final HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, v2).build();

		final AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo equal to bar but was barbar");
	}

	@Test
	public void it_should_fail_if_single_value_header_has_multiple_values() {
		final String name = "Content-Type";
		final String v1 = "application/json";
		final String v2 = "application/xml";
		final IsHeaderEqualToAssertion assertion = new IsHeaderEqualToAssertion(name, v1, false);
		final HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, v1).addHeader(name, v2).build();

		final AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to contains header Content-Type with a single value but found: [application/json, application/xml]");
	}

	@Test
	public void it_should_fail_if_header_name_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Header name cannot be blank");
		new IsHeaderEqualToAssertion(null, "value", false);
	}

	@Test
	public void it_should_fail_if_header_name_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Header name cannot be blank");
		new IsHeaderEqualToAssertion("", "value", false);
	}

	@Test
	public void it_should_fail_if_header_name_is_blank() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Header name cannot be blank");
		new IsHeaderEqualToAssertion("   ", "value", false);
	}

	@Test
	public void it_should_fail_if_header_value_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Header value must not be null");
		new IsHeaderEqualToAssertion("name", null, false);
	}
}
