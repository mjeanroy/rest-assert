/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HasCharsetAssertionTest {

	@Test
	void it_should_not_fail_if_header_is_set_with_expected_charset() {
		String charset = "utf-8";
		HasCharsetAssertion assertion = new HasCharsetAssertion(charset);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader("Content-Type", "application/json; charset=utf-8").build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_fail_if_header_is_not_set_with_expected_charset() {
		String charset = "utf-8";
		HasCharsetAssertion assertion = new HasCharsetAssertion(charset);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader("Content-Type", "application/json; charset=utf-16").build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).hasToString(
			"Expecting response to have charset \"utf-8\" but was \"utf-16\""
		);
	}

	@Test
	void it_should_fail_if_content_type_header_has_multiple_values() {
		HasCharsetAssertion assertion = new HasCharsetAssertion("utf-8");
		HttpResponse rsp = new HttpResponseBuilderImpl()
			.addHeader("Content-Type", "application/json; charset=utf-8")
			.addHeader("Content-Type", "application/xml; charset=utf-8")
			.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).hasToString(
			"Expecting response to contains header \"Content-Type\" with a single value but found: [\"application/json; charset=utf-8\", \"application/xml; charset=utf-8\"]"
		);
	}

	@Test
	void it_should_fail_if_charset_is_not_set() {
		HasCharsetAssertion assertion = new HasCharsetAssertion("utf-8");
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader("Content-Type", "application/json").build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have defined charset");
	}

	@Test
	void it_should_fail_if_header_is_not_set() {
		HasCharsetAssertion assertion = new HasCharsetAssertion("utf-8");
		HttpResponse rsp = new HttpResponseBuilderImpl().build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).hasToString("Expecting response to have header \"Content-Type\"");
	}

	@Test
	void it_should_fail_if_charset_is_null() {
		assertThatThrownBy(() -> new HasCharsetAssertion(null))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("Charset value must be defined");
	}

	@Test
	void it_should_fail_if_charset_is_empty() {
		assertThatThrownBy(() -> new HasCharsetAssertion(""))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Charset value must be defined");
	}

	@Test
	void it_should_fail_if_header_name_is_blank() {
		assertThatThrownBy(() -> new HasCharsetAssertion("    "))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Charset value must be defined");
	}
}
