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

package com.github.mjeanroy.restassert.core.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrictTransportSecurityParserTest {

	private StrictTransportSecurityParser parser;

	@BeforeEach
	void setUp() {
		parser = (StrictTransportSecurityParser) StrictTransportSecurity.parser();
	}

	@Test
	void it_should_parse_header_with_max_age() {
		StrictTransportSecurity sts = parser.parse("max-age=3600");

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isFalse();
		assertThat(sts.isPreload()).isFalse();
	}

	@Test
	void it_should_parse_header_with_max_age_and_preload() {
		StrictTransportSecurity sts = parser.parse("max-age=3600; preload");

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isFalse();
		assertThat(sts.isPreload()).isTrue();
	}

	@Test
	void it_should_parse_header_with_max_age_and_include_sub_domain() {
		StrictTransportSecurity sts = parser.parse("max-age=3600; includeSubDomains");

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isFalse();
	}

	@Test
	void it_should_parse_header_with_max_age_and_include_sub_domain_and_preload() {
		StrictTransportSecurity sts = parser.parse("max-age=3600; includeSubDomains; preload");

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isTrue();
	}

	@Test
	void it_should_parse_with_case_insensitive_header() {
		StrictTransportSecurity sts = parser.parse("MAX-AGE=3600;  INCLUDESUBDOMAINS; PRELOAD");

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isTrue();
	}
}
