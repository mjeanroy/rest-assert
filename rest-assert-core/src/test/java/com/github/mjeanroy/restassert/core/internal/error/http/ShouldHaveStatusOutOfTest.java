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

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveStatusOutOf.shouldHaveStatusOutOf;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldHaveStatusOutOfTest {

	@Test
	void it_should_format_error_message() {
		int actualStatus = 200;
		int start = 200;
		int end = 299;
		ShouldHaveStatusOutOf shouldHaveStatusOutOf = shouldHaveStatusOutOf(actualStatus, start, end);

		assertThat(shouldHaveStatusOutOf).isNotNull();
		assertThat(shouldHaveStatusOutOf.message()).isEqualTo("Expecting status code to be out of %s and %s but was %s");
		assertThat(shouldHaveStatusOutOf.args()).hasSize(3).containsExactly(start, end, actualStatus);
		assertThat(shouldHaveStatusOutOf.buildMessage()).isEqualTo("Expecting status code to be out of 200 and 299 but was 200");
		assertThat(shouldHaveStatusOutOf.toString()).isEqualTo("Expecting status code to be out of 200 and 299 but was 200");
	}
}
