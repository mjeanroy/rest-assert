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

package com.github.mjeanroy.restassert.tests;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Static Test Utilities.
 */
public final class TestUtils {

	// Ensure non instantiation.
	private TestUtils() {
	}

	/**
	 * Create date using UTC TimeZone.
	 *
	 * @param year The year.
	 * @param month The month, starting at zero.
	 * @param dayOfMonth The day of month.
	 * @param hourOfDay The hours of day.
	 * @param minute The minute.
	 * @param seconds The seconds.
	 * @return The UTC Date.
	 */
	public static Date createUtcDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int seconds) {
		TimeZone utc = TimeZone.getTimeZone("UTC");
		Calendar calendar = new GregorianCalendar(utc);
		calendar.setLenient(false);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, seconds);

		return calendar.getTime();
	}
}
