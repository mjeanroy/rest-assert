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

class HasHeaderAssertionTest {

	@Test
	void it_should_not_fail_if_header_is_set() {
		String name = "foo";
		HasHeaderAssertion assertion = new HasHeaderAssertion(name);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader(name, "bar").build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_fail_if_header_is_not_set() {
		String name = "foo";
		HasHeaderAssertion assertion = new HasHeaderAssertion(name);
		HttpResponse rsp = new HttpResponseBuilderImpl().addHeader("bar", "bar").build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).hasToString(
			"Expecting response to have header \"foo\""
		);
	}

	@Test
	void it_should_fail_if_header_name_is_null() {
		assertThatThrownBy(() -> new HasHeaderAssertion(null))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("Header name cannot be blank");
	}

	@Test
	void it_should_fail_if_header_name_is_empty() {
		assertThatThrownBy(() -> new HasHeaderAssertion(""))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Header name cannot be blank");
	}

	@Test
	void it_should_fail_if_header_name_is_blank() {
		assertThatThrownBy(() -> new HasHeaderAssertion("   "))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Header name cannot be blank");
	}
}
