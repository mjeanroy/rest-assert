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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParameterTest {

	@Test
	void it_should_create_parameter() {
		Parameter parameter = Parameter.parameter("foo", "bar");
		assertThat(parameter.getName()).isEqualTo("foo");
		assertThat(parameter.getValue()).isEqualTo("bar");
		assertThat(parameter.serializeValue()).isEqualTo("foo=bar");
		assertThat(parameter.toString()).isEqualTo(
			"Parameter{" +
				"name=foo, " +
				"value=bar" +
				"}"
		);
	}

	@Test
	void it_should_parse_parameter() {
		Parameter parameter = Parameter.parse("foo=bar");
		assertThat(parameter.getName()).isEqualTo("foo");
		assertThat(parameter.getValue()).isEqualTo("bar");
		assertThat(parameter.serializeValue()).isEqualTo("foo=bar");
		assertThat(parameter.toString()).isEqualTo(
			"Parameter{" +
				"name=foo, " +
				"value=bar" +
				"}"
		);
	}

	@Test
	void it_should_parse_parameter_with_uppercase_name() {
		Parameter parameter = Parameter.parse("FOO=bar");
		assertThat(parameter.getName()).isEqualTo("foo");
		assertThat(parameter.getValue()).isEqualTo("bar");
		assertThat(parameter.serializeValue()).isEqualTo("foo=bar");
		assertThat(parameter.toString()).isEqualTo(
			"Parameter{" +
				"name=foo, " +
				"value=bar" +
				"}"
		);
	}

	@Test
	void it_should_parse_with_spaces() {
		Parameter parameter = Parameter.parse("foo = bar");
		assertThat(parameter.getName()).isEqualTo("foo");
		assertThat(parameter.getValue()).isEqualTo("bar");
		assertThat(parameter.serializeValue()).isEqualTo("foo=bar");
		assertThat(parameter.toString()).isEqualTo(
			"Parameter{" +
				"name=foo, " +
				"value=bar" +
				"}"
		);
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Parameter.class).verify();
	}

	@Test
	void it_should_not_create_parameter_with_null_name() {
		assertThatThrownBy(() -> Parameter.parameter(null, "bar"))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("Parameter name must be defined");
	}

	@Test
	void it_should_not_create_parameter_with_empty_name() {
		assertThatThrownBy(() -> Parameter.parameter("", "bar"))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("Parameter name must be defined");
	}

	@Test
	void it_should_not_create_parameter_with_null_value() {
		assertThatThrownBy(() -> Parameter.parameter("foo", null))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("Parameter value must be defined");
	}

	@Test
	void it_should_not_parse_parameter_with_null_raw_value() {
		assertThatThrownBy(() -> Parameter.parse(null))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("Parameter raw value must be defined");
	}
}
