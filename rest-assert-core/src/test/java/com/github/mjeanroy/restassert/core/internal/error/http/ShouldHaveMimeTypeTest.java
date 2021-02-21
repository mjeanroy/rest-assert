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

package com.github.mjeanroy.restassert.core.internal.error.http;

import org.junit.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveMimeType.shouldHaveMimeType;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShouldHaveMimeTypeTest {

	@Test
	public void it_should_format_error_message() {
		String expectedMimeType = "application/json";
		String actualMimeType = "application/xml";
		ShouldHaveMimeType shouldHaveMimeType = shouldHaveMimeType(expectedMimeType, actualMimeType);

		assertThat(shouldHaveMimeType).isNotNull();
		assertThat(shouldHaveMimeType.message()).isEqualTo("Expecting response to have mime type %s but was %s");
		assertThat(shouldHaveMimeType.args()).hasSize(2).containsExactly(expectedMimeType, actualMimeType);
		assertThat(shouldHaveMimeType.buildMessage()).isEqualTo("Expecting response to have mime type application/json but was application/xml");
		assertThat(shouldHaveMimeType.toString()).isEqualTo("Expecting response to have mime type application/json but was application/xml");
	}

	@Test
	public void it_should_format_error_message_with_list() {
		List<String> expectedMimeType = asList("application/json", "application/javascript");
		String actualMimeType = "application/xml";
		ShouldHaveMimeType shouldHaveMimeType = shouldHaveMimeType(expectedMimeType, actualMimeType);

		assertThat(shouldHaveMimeType).isNotNull();
		assertThat(shouldHaveMimeType.message()).isEqualTo("Expecting response to have mime type in %s but was %s");
		assertThat(shouldHaveMimeType.args()).hasSize(2).containsExactly(expectedMimeType, actualMimeType);
		assertThat(shouldHaveMimeType.buildMessage()).isEqualTo("Expecting response to have mime type in [application/json, application/javascript] but was application/xml");
		assertThat(shouldHaveMimeType.toString()).isEqualTo("Expecting response to have mime type in [application/json, application/javascript] but was application/xml");
	}
}
