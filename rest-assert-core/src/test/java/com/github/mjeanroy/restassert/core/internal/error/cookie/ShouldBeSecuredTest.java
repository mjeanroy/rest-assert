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

package com.github.mjeanroy.restassert.core.internal.error.cookie;

import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldBeSecured.shouldBeSecured;
import static com.github.mjeanroy.restassert.core.internal.error.cookie.ShouldBeSecured.shouldNotBeSecured;
import static org.assertj.core.api.Assertions.assertThat;

class ShouldBeSecuredTest {

	@Test
	void it_should_format_error_message() {
		ShouldBeSecured shouldBeSecured = shouldBeSecured();

		assertThat(shouldBeSecured).isNotNull();
		assertThat(shouldBeSecured.message()).isEqualTo("Expecting cookie to be secured");
		assertThat(shouldBeSecured.args()).isNotNull().isEmpty();
		assertThat(shouldBeSecured.buildMessage()).isEqualTo("Expecting cookie to be secured");
		assertThat(shouldBeSecured.toString()).isEqualTo("Expecting cookie to be secured");
	}

	@Test
	void it_should_format_error_message_with_negation() {
		ShouldBeSecured shouldNotBeSecured = shouldNotBeSecured();

		assertThat(shouldNotBeSecured).isNotNull();
		assertThat(shouldNotBeSecured.message()).isEqualTo("Expecting cookie not to be secured");
		assertThat(shouldNotBeSecured.args()).isNotNull().isEmpty();
		assertThat(shouldNotBeSecured.buildMessage()).isEqualTo("Expecting cookie not to be secured");
		assertThat(shouldNotBeSecured.toString()).isEqualTo("Expecting cookie not to be secured");
	}
}
