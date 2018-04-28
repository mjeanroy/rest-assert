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

package com.github.mjeanroy.restassert.test.commons;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DateTestUtilsTest {

	@Test
	public void it_should_parse_internet_format_date() {
		String dateAsString = "Wed, 15 Nov 1995 12:45:26 GMT";
		Date date = DateTestUtils.fromInternetMessageFormat(dateAsString);

		TimeZone tz = TimeZone.getDefault();
		int offsetInHour = tz.getOffset(date.getTime()) / 1000 / 60 / 60;

		assertThat(date)
			.isNotNull()
			.hasDayOfMonth(15)
			.hasMonth(11)
			.hasYear(1995)
			.hasHourOfDay(12 + offsetInHour)
			.hasMinute(45)
			.hasSecond(26);
	}
}
