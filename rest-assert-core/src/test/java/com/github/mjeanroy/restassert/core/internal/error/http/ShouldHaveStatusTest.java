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

package com.github.mjeanroy.restassert.core.internal.error.http;

import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveStatus.shouldHaveStatus;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldHaveStatusTest {

	@Test
	void it_should_format_error_message() {
		int expectedStatus = 200;
		int actualStatus = 400;
		ShouldHaveStatus shouldHaveStatus = shouldHaveStatus(actualStatus, expectedStatus);

		assertThat(shouldHaveStatus).isNotNull();
		assertThat(shouldHaveStatus.message()).isEqualTo("Expecting status code to be %s but was %s");
		assertThat(shouldHaveStatus.args()).hasSize(2).containsExactly(expectedStatus, actualStatus);
		assertThat(shouldHaveStatus.buildMessage()).isEqualTo("Expecting status code to be 200 but was 400");
		assertThat(shouldHaveStatus.toString()).isEqualTo("Expecting status code to be 200 but was 400");
	}
}
