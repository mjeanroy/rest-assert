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

package com.github.mjeanroy.restassert.core.internal.json.parsers;

import com.github.mjeanroy.restassert.core.internal.json.JsonException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.mjeanroy.restassert.core.internal.json.parsers.Jackson2JsonParser.jackson2Parser;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Jackson2JsonParserTest extends AbstractJsonParserTest {

	private static JsonParser parser;

	@BeforeClass
	public static void setUp() {
		parser = jackson2Parser();
	}

	@Override
	protected JsonParser parser() {
		return parser;
	}

	@Test
	public void it_should_wrap_checked_exception() {
		String json = "[ Invalid JSON ]";
		assertThatThrownBy(parse(json)).isExactlyInstanceOf(JsonException.class);
	}

	private static ThrowingCallable parse(final String json) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				parser.parse(json);
			}
		};
	}
}
