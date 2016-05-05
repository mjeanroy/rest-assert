/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.assertions;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.utils.Predicate;

import static com.github.mjeanroy.rest_assert.utils.Utils.notNull;

/**
 /**
 * Predicate used to check if a cookie match expected name
 * and value.
 */
class CookieNameValuePredicate extends CookieNamePredicate implements Predicate<Cookie> {
	/**
	 * Expected value.
	 */
	private final String value;

	/**
	 * Create predicate.
	 *
	 * @param name Expected name.
	 * @param value Expected value.
	 * @throws NullPointerException If {@code name} or {@code value} is {@code null}.
	 */
	CookieNameValuePredicate(String name, String value) {
		super(name);
		this.value = notNull(value, "Cookie value must not be null");
	}

	@Override
	public boolean apply(Cookie cookie) {
		return super.apply(cookie) && value.equals(cookie.getValue());
	}
}
