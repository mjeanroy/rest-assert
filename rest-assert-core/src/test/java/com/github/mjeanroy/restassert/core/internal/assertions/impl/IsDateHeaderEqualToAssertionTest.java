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
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import java.util.Date;

import static com.github.mjeanroy.restassert.tests.TestUtils.createUtcDate;
import static java.util.Calendar.MAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IsDateHeaderEqualToAssertionTest {

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_value() {
		final String name = "foo";
		final String value = "Thu, 05 May 2016 19:29:03 GMT";
		final Date date = createUtcDate(2016, MAY, 5, 19, 29, 3);
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, value)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_multiple_value_header_contains_expected_value() {
		final String name = "foo";
		final String v1 = "Thu, 05 May 2016 19:29:03 GMT";
		final String v2 = "Thu, 05 May 2016 19:30:03 GMT";
		final Date date = createUtcDate(2016, MAY, 5, 19, 30, 3);
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, v1)
				.addHeader(name, v2)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_header_is_not_set() {
		final String name = "foo";
		final Date date = createUtcDate(2016, MAY, 5, 19, 30, 3);
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl().build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo");
	}

	@Test
	public void it_should_fail_if_header_is_does_not_have_expected_value() {
		final String name = "foo";
		final String value = "Thu, 05 May 2016 19:29:03 GMT";
		final Date date = createUtcDate(2016, MAY, 5, 19, 30, 3);
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, value)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo equal to Thu, 05 May 2016 19:30:03 GMT but was Thu, 05 May 2016 19:29:03 GMT");
	}

	@Test
	public void it_should_fail_if_single_value_header_has_multiple_values() {
		final String name = "Last-Modified";
		final String v1 = "Thu, 05 May 2016 19:29:03 GMT";
		final String v2 = "Thu, 05 May 2016 19:30:03 GMT";
		final Date date = createUtcDate(2016, MAY, 5, 19, 30, 3);
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, v1)
				.addHeader(name, v2)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to contains header Last-Modified with a single value but found: [Thu, 05 May 2016 19:29:03 GMT, Thu, 05 May 2016 19:30:03 GMT]");
	}

	@Test
	public void it_should_fail_if_header_name_is_null() {
		assertThatThrownBy(isDateHeaderEqualToAssertion(null, new Date()))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Header name cannot be blank");
	}

	@Test
	public void it_should_fail_if_header_name_is_empty() {
		assertThatThrownBy(isDateHeaderEqualToAssertion("", new Date()))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Header name cannot be blank");
	}

	@Test
	public void it_should_fail_if_header_name_is_blank() {
		assertThatThrownBy(isDateHeaderEqualToAssertion("   ", new Date()))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Header name cannot be blank");
	}

	@Test
	public void it_should_fail_if_header_value_is_null() {
		assertThatThrownBy(isDateHeaderEqualToAssertion("name", null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Header value must not be null");
	}

	private static ThrowingCallable isDateHeaderEqualToAssertion(final String name, final Date value) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				new IsDateHeaderEqualToAssertion(name, value);
			}
		};
	}
}
