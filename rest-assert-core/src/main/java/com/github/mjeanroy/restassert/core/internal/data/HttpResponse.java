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

import com.github.mjeanroy.restassert.core.internal.exceptions.NonParsableResponseBodyException;

import java.util.List;

/**
 * Http response contract.
 */
public interface HttpResponse {

	/**
	 * Get http status of http response.
	 *
	 * @return Http status.
	 */
	int getStatus();

	/**
	 * Check that http response contains header.
	 * Header name should be case insensitive.
	 *
	 * @param name Header name.
	 * @return True if http response contains header, false otherwise.
	 */
	boolean hasHeader(String name);

	/**
	 * Get (first) header value.
	 * If http response does not contain header, it returns
	 * null.
	 *
	 * @param name Header name.
	 * @return Header value, null if response does not contain header.
	 */
	List<String> getHeader(String name);

	/**
	 * Get response body.
	 *
	 * @return Response body.
	 * @throws NonParsableResponseBodyException If response body cannot be read.
	 */
	String getContent();

	/**
	 * Get the list of cookies (i.e cookie objects defined by {@code Set-Cookie} header.
	 *
	 * @return List of cookies, may be empty if {@code Set-Cookie} is missing.
	 */
	List<Cookie> getCookies();
}
