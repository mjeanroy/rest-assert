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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XssProtectionParserTest {

	private XssProtectionParser parser;

	@BeforeEach
	void setUp() {
		parser = (XssProtectionParser) XssProtection.parser();
	}

	@Test
	void it_should_parse_0() {
		assertThat(parser.parse("0")).isEqualTo(XssProtection.disable());
		assertThat(parser.parse(" 0 ")).isEqualTo(XssProtection.disable());
	}

	@Test
	void it_should_parse_1() {
		assertThat(parser.parse("1")).isEqualTo(XssProtection.enable());
		assertThat(parser.parse(" 1 ")).isEqualTo(XssProtection.enable());
	}

	@Test
	void it_should_parse_1_with_block_mode() {
		assertThat(parser.parse("1; mode=block")).isEqualTo(XssProtection.enableModeBlock());
		assertThat(parser.parse("1;mode=block")).isEqualTo(XssProtection.enableModeBlock());
		assertThat(parser.parse(" 1 ; mode=block ")).isEqualTo(XssProtection.enableModeBlock());
	}

	@Test
	void it_should_parse_1_with_report_uri() {
		String uri = "https://google.com";
		assertThat(parser.parse("1; report=" + uri)).isEqualTo(XssProtection.enableModeReport(uri));
		assertThat(parser.parse("1;report=" + uri)).isEqualTo(XssProtection.enableModeReport(uri));
		assertThat(parser.parse(" 1 ; report=" + uri + " ")).isEqualTo(XssProtection.enableModeReport(uri));
		assertThat(parser.parse("1; REPORT=" + uri)).isEqualTo(XssProtection.enableModeReport(uri));
	}
}
