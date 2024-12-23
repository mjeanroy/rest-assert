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

import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldNotHaveHeader.shouldNotHaveHeader;
import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldNotHaveHeaderTest {

	@Test
	void it_should_format_error_message() {
		String unexpectedHeaderName = "X-Requested-With";
		ShouldNotHaveHeader shouldHaveStatus = shouldNotHaveHeader(unexpectedHeaderName);

		assertThat(shouldHaveStatus).isNotNull();
		assertThat(shouldHaveStatus.message()).isEqualTo("Expecting response not to have header %s");
		assertThat(shouldHaveStatus.args()).hasSize(1).containsExactly(unexpectedHeaderName);
		assertThat(shouldHaveStatus.buildMessage()).isEqualTo("Expecting response not to have header " + fmt(unexpectedHeaderName));
		assertThat(shouldHaveStatus.toString()).isEqualTo("Expecting response not to have header " + fmt(unexpectedHeaderName));
	}
}
