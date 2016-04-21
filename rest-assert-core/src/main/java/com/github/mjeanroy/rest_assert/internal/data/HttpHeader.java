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

package com.github.mjeanroy.rest_assert.internal.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * List of standards http headers names.
 */
public enum HttpHeader {

	/**
	 * Content type header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17
	 */
	CONTENT_TYPE("Content-Type", true),

	/**
	 * Content Encoding header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11
	 */
	CONTENT_ENCODING("Content-Encoding", false),

	/**
	 * Content Length header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13
	 */
	CONTENT_LENGTH("Content-Length", false),

	/**
	 * Content Disposition header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1
	 */
	CONTENT_DISPOSITION("Content-Disposition", true),

	/**
	 * Last Modified header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29
	 */
	LAST_MODIFIED("Last-Modified", true),

	/**
	 * Expires header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21
	 */
	EXPIRES("Expires", true),

	/**
	 * ETag header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19
	 */
	ETAG("ETag", true),

	/**
	 * Location header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30
	 */
	LOCATION("Location", true),

	/**
	 * Cache Control header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9
	 */
	CACHE_CONTROL("Cache-Control", false),

	/**
	 * Pragma header name.
	 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32
	 */
	PRAGMA("Pragma", false),

	/**
	 * X-XSS-Protection header name.
	 * https://www.w3.org/TR/2012/WD-CSP11-20121213/#relationship-to-x-xss-protection
	 */
	X_XSS_PROTECTION("X-XSS-Protection", false),

	/**
	 * X-Content-Type-Options header name.
	 * https://fetch.spec.whatwg.org/#x-content-type-options-header
	 */
	X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options", false),

	/**
	 * X-Content-Type-Options header name.
	 * https://tools.ietf.org/html/rfc7034
	 */
	X_FRAME_OPTIONS("X-Frame-Options", false);

	/**
	 * Name of header.
	 */
	private final String name;

	/**
	 * Flag to check if this header allow multiple values.
	 * Multiple values is specified by W3C, see: https://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2.
	 *
	 * Short answer:
	 *
	 * Multiple message-header fields with the same field-name MAY be present in a message if and only if
	 * the entire field-value for that header field is defined as a comma-separated list [i.e., #(values)].
	 * It MUST be possible to combine the multiple header fields into one "field-name: field-value" pair,
	 * without changing the semantics of the message, by appending each subsequent field-value to the first,
	 * each separated by a comma.
	 */
	private final boolean single;

	private HttpHeader(String name, boolean single) {
		this.name = name;
		this.single = single;
	}

	/**
	 * Get {@link #name}.
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get {@link #single}.
	 *
	 * @return {@link #single}
	 */
	public boolean isSingle() {
		return single;
	}

	// Keep headers in an immutable map for fast lookup.
	private static final Map<String, HttpHeader> MAP;

	static {
		Map<String, HttpHeader> tmp = new HashMap<>();
		for (HttpHeader httpHeader : HttpHeader.values()) {
			tmp.put(httpHeader.getName().toLowerCase(), httpHeader);
		}

		MAP = Collections.unmodifiableMap(tmp);
	}

	/**
	 * Find header by its name.
	 *
	 * @param name Name of header.
	 * @return Header, null if it is not defined in the enum set.
	 */
	public static HttpHeader find(String name) {
		return MAP.get(name.toLowerCase());
	}

}
