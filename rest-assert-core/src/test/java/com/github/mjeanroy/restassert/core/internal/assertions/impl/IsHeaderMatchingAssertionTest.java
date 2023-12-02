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
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderValue;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HeaderParserBuilder;
import com.github.mjeanroy.restassert.tests.builders.HeaderValueBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IsHeaderMatchingAssertionTest {

	@Test
	void it_should_not_fail_if_header_is_set_with_expected_value() {
		String name = "foo";
		String value = "bar";
		HttpHeaderValue expected = new HeaderValueBuilder().setValue(value).build();
		HttpHeaderParser<?> parser = new HeaderParserBuilder().add(value, expected).build();

		IsHeaderMatchingAssertion assertion = new IsHeaderMatchingAssertion(name, expected, parser);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, value).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_not_fail_if_multiple_value_header_contains_expected_value() {
		String name = "foo";
		String value = "bar";
		HttpHeaderValue expected = new HeaderValueBuilder().setValue(value).build();
		HttpHeaderParser<?> parser = new HeaderParserBuilder().add(value, expected).build();

		IsHeaderMatchingAssertion assertion = new IsHeaderMatchingAssertion(name, expected, parser);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, value + value).addHeader(name, value).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_fail_if_header_is_not_set() {
		String name = "foo";
		String value = "bar";
		HttpHeaderValue expected = new HeaderValueBuilder().setValue(value).build();
		HttpHeaderParser<?> parser = new HeaderParserBuilder().add(value, expected).build();

		IsHeaderMatchingAssertion assertion = new IsHeaderMatchingAssertion(name, expected, parser);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader("bar", "bar").build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo");
	}

	@Test
	void it_should_fail_if_header_is_does_not_have_expected_value() {
		String name = "foo";
		String value = "bar";
		HttpHeaderValue expected = new HeaderValueBuilder().setValue(value).build();
		HttpHeaderParser<?> parser = new HeaderParserBuilder().add(value, expected).build();

		IsHeaderMatchingAssertion assertion = new IsHeaderMatchingAssertion(name, expected, parser);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, value + value).build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo equal to bar but was barbar");
	}

	@Test
	void it_should_fail_if_single_value_header_has_multiple_values() {
		String name = "Content-Type";
		String value = "bar";
		HttpHeaderValue expected = new HeaderValueBuilder().setValue(value).build();
		HttpHeaderParser<?> parser = new HeaderParserBuilder().add(value, expected).build();

		IsHeaderMatchingAssertion assertion = new IsHeaderMatchingAssertion(name, expected, parser);
		HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, "application/json")
				.addHeader(name, "application/xml")
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to contains header Content-Type with a single value but found: [application/json, application/xml]");
	}

	@Test
	void it_should_fail_if_header_name_is_null() {
		assertThatThrownBy(() -> new IsHeaderMatchingAssertion(null, new HeaderValueBuilder().build(), new HeaderParserBuilder().build()))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Header name cannot be blank");
	}

	@Test
	void it_should_fail_if_header_name_is_empty() {
		assertThatThrownBy(() -> new IsHeaderMatchingAssertion("", new HeaderValueBuilder().build(), new HeaderParserBuilder().build()))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Header name cannot be blank");
	}

	@Test
	void it_should_fail_if_header_name_is_blank() {
		assertThatThrownBy(() -> new IsHeaderMatchingAssertion("   ", new HeaderValueBuilder().build(), new HeaderParserBuilder().build()))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Header name cannot be blank");
	}

	@Test
	void it_should_fail_if_header_value_is_null() {
		assertThatThrownBy(() -> new IsHeaderMatchingAssertion("name", null, new HeaderParserBuilder().build()))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Header expected value must not be null");
	}

	@Test
	void it_should_fail_if_header_parser_is_null() {
		assertThatThrownBy(() -> new IsHeaderMatchingAssertion("name", new HeaderValueBuilder().build(), null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Header parser must not be null");
	}
}
