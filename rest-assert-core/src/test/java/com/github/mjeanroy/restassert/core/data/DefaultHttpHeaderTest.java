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

package com.github.mjeanroy.restassert.core.data;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultHttpHeaderTest {

	@Test
	void it_should_create_header() {
		String name = "x-xss-protection";
		String value = "1; mode=block";
		HttpHeader httpHeader = new DefaultHttpHeader(name, singletonList(value));

		assertThat(httpHeader.getName()).isEqualTo("X-Xss-Protection");
		assertThat(httpHeader.getValue()).isEqualTo("1; mode=block");
		assertThat(httpHeader.getValues()).hasSize(1).containsOnly("1; mode=block");
	}

	@Test
	void it_should_create_multi_value_header() {
		String name = "x-xss-protection";
		List<String> values = Arrays.asList("0", "1; mode=block");
		HttpHeader httpHeader = new DefaultHttpHeader(name, values);

		assertThat(httpHeader.getName()).isEqualTo("X-Xss-Protection");
		assertThat(httpHeader.getValue()).isEqualTo("0,1; mode=block");
		assertThat(httpHeader.getValues()).hasSize(2).containsExactlyElementsOf(values);
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(DefaultHttpHeader.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		String name = "x-xss-protection";
		String value = "1; mode=block";
		HttpHeader httpHeader = new DefaultHttpHeader(name, singletonList(value));
		assertThat(httpHeader).hasToString(
			"DefaultHttpHeader{" +
				"name=X-Xss-Protection, " +
				"values=[1; mode=block]" +
			"}"
		);
	}
}
