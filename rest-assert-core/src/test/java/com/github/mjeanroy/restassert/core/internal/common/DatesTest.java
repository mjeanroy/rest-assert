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

package com.github.mjeanroy.restassert.core.internal.common;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DatesTest {

	@Test
	void it_should_parse_rfc_1123() {
		String date = "Sun, 06 Nov 1994 08:49:37 GMT";

		Date result = Dates.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	void it_should_parse_rfc_1036() {
		String date = "Sunday, 06-Nov-94 08:49:37 GMT";

		Date result = Dates.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	void it_should_parse_ansi_c_format() {
		String date = "Sun Nov  6 08:49:37 1994";

		Date result = Dates.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	void it_should_parse_without_single_quotes() {
		String date = "'Sun, 06 Nov 1994 08:49:37 GMT'";

		Date result = Dates.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	void it_should_throw_exception_if_pattern_is_not_known() {
		String date = "foo bar";

		assertThatThrownBy(() -> Dates.parseHttpDate(date))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("HTTP Date must respect standard formats: EEE, dd MMM yyyy HH:mm:ss zzz, EEE, dd-MMM-yy HH:mm:ss zzz or EEE MMM d HH:mm:ss yyyy");
	}

	@Test
	void it_should_format_date() {
		Date date = new Date();
		date.setTime(784111777000L);

		assertThat(Dates.formatHttpDate(date)).isEqualTo("Sun, 06 Nov 1994 08:49:37 GMT");
	}
}
