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

package com.github.mjeanroy.restassert.core.data;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_create_parameter() {
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
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Parameter.class).verify();
	}

	@Test
	public void it_should_not_create_parameter_with_null_name() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Parameter name must be defined");

		Parameter.parameter(null, "bar");
	}

	@Test
	public void it_should_not_create_parameter_with_empty_name() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Parameter name must be defined");

		Parameter.parameter("", "bar");
	}

	@Test
	public void it_should_not_create_parameter_with_null_value() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Parameter value must be defined");

		Parameter.parameter("foo", null);
	}
}
