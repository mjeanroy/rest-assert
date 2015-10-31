/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.utils;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilsTest {

	@Test
	public void it_should_parse_rfc_1123() {
		String date = "Sun, 06 Nov 1994 08:49:37 GMT";

		Date result = DateUtils.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	public void it_should_parse_rfc_1036() {
		String date = "Sunday, 06-Nov-94 08:49:37 GMT";

		Date result = DateUtils.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	public void it_should_parse_ansi_c_format() {
		String date = "Sun Nov  6 08:49:37 1994";

		Date result = DateUtils.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}

	@Test
	public void it_should_parse_without_single_quotes() {
		String date = "'Sun, 06 Nov 1994 08:49:37 GMT'";

		Date result = DateUtils.parseHttpDate(date);

		assertThat(result)
			.isNotNull()
			.hasTime(784111777000L);
	}
}
