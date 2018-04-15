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

package com.github.mjeanroy.restassert.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;

public final class CookieSerializer {

	public static String serialize(Cookie cookie) {
		StringBuilder sb = new StringBuilder();
		sb.append(cookie.getName()).append("=").append(cookie.getValue());

		String domain = cookie.getDomain();
		String path = cookie.getPath();
		boolean secure = cookie.isSecured();
		boolean httpOnly = cookie.isHttpOnly();
		Long maxAge = cookie.getMaxAge();
		Date expires = cookie.getExpires();

		if (domain != null) {
			sb.append("; Domain=").append(domain);
		}

		if (path != null) {
			sb.append("; Path=").append(path);
		}

		if (secure) {
			sb.append("; secure");
		}

		if (httpOnly) {
			sb.append("; HttpOnly");
		}

		if (maxAge != null) {
			sb.append("; max-age=").append(maxAge);
		}

		if (expires != null) {
			DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US);
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			sb.append("; expires=").append(df.format(expires));
		}

		return sb.toString();
	}
}
