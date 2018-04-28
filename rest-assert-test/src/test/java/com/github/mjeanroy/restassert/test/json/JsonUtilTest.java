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

package com.github.mjeanroy.restassert.test.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class JsonUtilTest {

	@Test
	public void it_should_return_null_with_null() {
		assertThat(JsonUtil.formatValue(null)).isNull();
	}

	@Test
	public void it_should_format_string() {
		assertThat(JsonUtil.formatValue("foo")).isEqualTo("\"foo\"");
	}

	@Test
	public void it_should_format_string_and_escape_double_quotes() {
		assertThat(JsonUtil.formatValue("Hello \"Mr\"")).isEqualTo("\"Hello \\\"Mr\\\"\"");
	}

	@Test
	public void it_should_format_json_value_using_toJson() {
		String output = "{}";
		JsonValue jsonValue = mock(JsonValue.class);
		when(jsonValue.toJson()).thenReturn(output);

		String result = JsonUtil.formatValue(jsonValue);
		assertThat(result).isEqualTo(output);
		verify(jsonValue).toJson();
	}

	@Test
	public void it_should_return_to_string_as_default() {
		assertThat(JsonUtil.formatValue(new TestBean())).isEqualTo("{}");
	}

	@Test
	public void it_should_escape_value() {
		assertThat(JsonUtil.jsonEscape(null)).isNull();
		assertThat(JsonUtil.jsonEscape("")).isEmpty();
		assertThat(JsonUtil.jsonEscape("foo")).isEqualTo("foo");
		assertThat(JsonUtil.jsonEscape("\"foo\"")).isEqualTo("\\\"foo\\\"");
	}

	private static class TestBean {
		@Override
		public String toString() {
			return "{}";
		}
	}
}
