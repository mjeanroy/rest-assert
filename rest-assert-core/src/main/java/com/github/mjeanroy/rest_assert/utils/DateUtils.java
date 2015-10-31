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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Arrays.asList;

/**
 * Static Date Utilities.
 */
public final class DateUtils {

	private DateUtils() {
	}

	/**
	 * Date format pattern used to parse HTTP date headers in RFC 1123 format.
	 * This should be the default patterns to use.
	 */
	private static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

	/**
	 * Date format pattern used to parse HTTP date headers in RFC 1036 format.
	 * This pattern is deprecated but may appear because of old web server.
	 */
	private static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";

	/**
	 * Date format pattern used to parse HTTP date headers in ANSI C format.
	 * This pattern is deprecated but may appear because of old web server.
	 */
	private static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

	/**
	 * List of patterns to use to convert http date as string to date object.
	 */
	private static final List<String> PATTERNS = Collections.unmodifiableList(asList(
		PATTERN_RFC1123,
		PATTERN_RFC1036,
		PATTERN_ASCTIME
	));

	/**
	 * Single Quote Character.
	 */
	private static final String SINGLE_QUOTE = "'";

	/**
	 * Parse HTTP date to get a real Date object.
	 * This function will use three patterns:
	 * - First is pattern defined by RFC 1123 (most standard).
	 * - Second is pattern defined by RFC 1036.
	 * - Third is ANSI C's asctime() format.
	 * These patterns should still be supported, as stated here:
	 * https://tools.ietf.org/html/rfc7231#section-7.1.1.1
	 *
	 * @param date Date to parse.
	 * @return Dare object, null if date cannot be parsed.
	 */
	public static Date parseHttpDate(String date) {
		if (date.length() > 1 && date.startsWith(SINGLE_QUOTE) && date.endsWith(SINGLE_QUOTE)) {
			date = date.substring(1, date.length() - 1);
		}

		for (String pattern : PATTERNS) {
			DateFormat df = createDateFormat(pattern);
			try {
				return df.parse(date);
			}
			catch (ParseException ex) {
				// try next...
			}
		}

		return null;
	}

	/**
	 * Format HTTP Date using most common pattern (RFC 1123).
	 *
	 * @param date Date to format.
	 * @return String representation.
	 */
	public static String formatHttpDate(Date date) {
		DateFormat df = createDateFormat(PATTERN_RFC1123);
		return df.format(date);
	}

	private static DateFormat createDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern, Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df;
	}
}
