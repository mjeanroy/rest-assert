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

package com.github.mjeanroy.restassert.core.internal.error.json;

import com.github.mjeanroy.restassert.core.internal.json.JsonType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShouldBeTypeOfTest {

	@Test
	void it_should_format_error_message() {
		JsonType actualType = JsonType.NULL;
		JsonType expectedType = JsonType.NUMBER;
		ShouldBeTypeOf shouldBeTypeOf = ShouldBeTypeOf.shouldBeTypeOf(expectedType, actualType);

		assertThat(shouldBeTypeOf).isNotNull();
		assertThat(shouldBeTypeOf.message()).isEqualTo("Expecting json to be a number but was null");
		assertThat(shouldBeTypeOf.args()).hasSize(0);
		assertThat(shouldBeTypeOf.buildMessage()).isEqualTo("Expecting json to be a number but was null");
		assertThat(shouldBeTypeOf.toString()).isEqualTo("Expecting json to be a number but was null");
		assertThat(shouldBeTypeOf.entryName()).isEmpty();
	}
}
