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

package com.github.mjeanroy.restassert.core.internal.error.http;

import org.junit.Test;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader.shouldHaveHeader;
import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShouldHaveHeaderTest {

	@Test
	public void it_should_format_error_message() {
		String headerName = "foo";
		ShouldHaveHeader shouldHaveHeader = shouldHaveHeader(headerName);
		assertThat(shouldHaveHeader).isNotNull();
		assertThat(shouldHaveHeader.toString()).isEqualTo("Expecting response to have header foo");
	}

	@Test
	public void it_should_format_error_message_with_expected_value() {
		String headerName = "foo";
		String headerValue = "bar";
		String actualValue = "quix";
		ShouldHaveHeader shouldHaveHeader = shouldHaveHeaderWithValue(headerName, headerValue, actualValue);
		assertThat(shouldHaveHeader).isNotNull();
		assertThat(shouldHaveHeader.toString()).isEqualTo("Expecting response to have header foo equal to bar but was quix");
	}

	@Test
	public void it_should_format_error_message_with_collection_of_one_value() {
		String headerName = "foo";
		String headerValue = "bar";
		String actualValue = "quix";
		ShouldHaveHeader shouldHaveHeader = shouldHaveHeaderWithValue(headerName, headerValue, singletonList(actualValue));
		assertThat(shouldHaveHeader).isNotNull();
		assertThat(shouldHaveHeader.toString()).isEqualTo("Expecting response to have header foo equal to bar but was quix");
	}

	@Test
	public void it_should_format_error_message_with_collection_of_values() {
		String headerName = "foo";
		String headerValue = "bar";
		String v1 = "foo";
		String v2 = "bar";
		ShouldHaveHeader shouldHaveHeader = shouldHaveHeaderWithValue(headerName, headerValue, asList(v1, v2));
		assertThat(shouldHaveHeader).isNotNull();
		assertThat(shouldHaveHeader.toString()).isEqualTo("Expecting response to have header foo equal to bar but contains only [foo, bar]");
	}
}
