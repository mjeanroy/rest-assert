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

package com.github.mjeanroy.restassert.core.internal.data;

import java.util.Date;

/**
 * Cookie object: this interface defines what fields may
 * appear in the Set-Cookie header.
 */
public interface Cookie {

	/**
	 * Cookie name.
	 *
	 * @return Name.
	 */
	String getName();

	/**
	 * Cookie value.
	 *
	 * @return Value.
	 */
	String getValue();

	/**
	 * Cookie domain.
	 *
	 * @return Domain.
	 */
	String getDomain();

	/**
	 * Cookie path.
	 *
	 * @return path.
	 */
	String getPath();

	/**
	 * Check if cookie is flagged as secured.
	 *
	 * @return True if cookie is secured, false otherwise.
	 */
	boolean isSecured();

	/**
	 * Check if cookie is flagged as http only.
	 *
	 * @return True if cookie is "http only", false otherwise.
	 */
	boolean isHttpOnly();

	/**
	 * Get cookie max age.
	 * If {@code null} is returned, it means that the max-age field was not defined
	 * in the Set-Cookie header.
	 *
	 * @return Max age (in seconds).
	 */
	Long getMaxAge();

	/**
	 * Get cookie expires date (i.e date defined by expires directive).
	 *
	 * If {@code null} is returned, it means that the expires field was not defined
	 * in the Set-Cookie header.
	 *
	 * @return Expires date.
	 */
	Date getExpires();
}
