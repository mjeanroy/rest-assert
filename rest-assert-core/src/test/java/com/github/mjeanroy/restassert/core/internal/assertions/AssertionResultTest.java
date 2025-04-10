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

package com.github.mjeanroy.restassert.core.internal.assertions;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.tests.builders.RestAssertErrorBuilder;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AssertionResultTest {

	@Test
	void it_should_be_a_success_result() {
		AssertionResult result = success();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
		assertThat(result.getError()).isNull();
	}

	@Test
	void it_should_be_a_failure() {
		RestAssertError error = new RestAssertErrorBuilder().build();
		AssertionResult result = failure(error);
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError()).isEqualTo(error);
	}

	@Test
	void it_should_fail_if_error_object_is_null_with_a_failure() {
		assertThatThrownBy(() -> failure(null))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("Error object must not be null");
	}
}
