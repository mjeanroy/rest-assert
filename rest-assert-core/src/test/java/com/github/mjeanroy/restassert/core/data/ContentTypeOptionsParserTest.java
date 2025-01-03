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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.internal.exceptions.InvalidHeaderValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeOptionsParserTest {

	private ContentTypeOptionsParser parser;

	@BeforeEach
	void setUp() {
		parser = (ContentTypeOptionsParser) ContentTypeOptions.parser();
	}

	@Test
	void it_should_parse_no_sniff_value() {
		assertThat(parser.parse("nosniff")).isEqualTo(ContentTypeOptions.NO_SNIFF);
		assertThat(parser.parse("NOSNIFF")).isEqualTo(ContentTypeOptions.NO_SNIFF);
		assertThat(parser.parse(" nosniff ")).isEqualTo(ContentTypeOptions.NO_SNIFF);
	}

	@Test
	void it_should_failed_to_parse_invalid_value() {
		assertThatThrownBy(() -> parser.parse("foo"))
			.isExactlyInstanceOf(InvalidHeaderValue.class)
			.hasMessage("X-Content-Type-Options value 'foo' is not a valid one.");
	}
}
