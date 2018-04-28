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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Static utilities.
 */
public final class DateTestUtils {

	/**
	 * Pattern used to format / read a date in the internet format
	 * message defined by RFC 5322 (http://tools.ietf.org/html/rfc5322).
	 */
	private static final String IMF_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

	// Ensure non instantiation.
	private DateTestUtils() {
	}

	/**
	 * Translate date formatted as the Internet Message Format (specified by
	 * RFC 5322, http://tools.ietf.org/html/rfc5322) to a {@link Date} instance.
	 *
	 * @param date Date value.
	 * @return The date instance.
	 */
	public static Date fromInternetMessageFormat(String date) {
		DateFormat df = new SimpleDateFormat(IMF_FORMAT, Locale.US);
		try {
			return df.parse(date);
		} catch (ParseException ex) {
			throw new AssertionError(ex);
		}
	}
}
