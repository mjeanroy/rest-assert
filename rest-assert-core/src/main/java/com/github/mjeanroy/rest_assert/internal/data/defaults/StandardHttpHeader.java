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

package com.github.mjeanroy.rest_assert.internal.data.defaults;

/**
 * List of standards http headers names.
 */
public final class StandardHttpHeader {

	// Ensure non instantiation
	private StandardHttpHeader() {
	}

	/**
	 * Content type header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17
	 */
	public static final String CONTENT_TYPE = "Content-Type";

	/**
	 * ETag header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19
	 */
	public static final String ETAG = "ETag";

	/**
	 * Location header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30
	 */
	public static final String LOCATION = "Location";

	/**
	 * Cache Control header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9
	 */
	public static final String CACHE_CONTROL = "Cache-Control";
}
