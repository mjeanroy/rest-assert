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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * List of standards http headers names.
 */
public enum HttpHeader {

	/**
	 * Content type header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
	 */
	CONTENT_TYPE("Content-Type", true),

	/**
	 * Content Encoding header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
	 */
	CONTENT_ENCODING("Content-Encoding", true),

	/**
	 * Content Length header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13</a>
	 */
	CONTENT_LENGTH("Content-Length", false),

	/**
	 * Content Disposition header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1">http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1</a>
	 */
	CONTENT_DISPOSITION("Content-Disposition", true),

	/**
	 * Last Modified header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29</a>
	 */
	LAST_MODIFIED("Last-Modified", true),

	/**
	 * Expires header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21</a>
	 */
	EXPIRES("Expires", true),

	/**
	 * ETag header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19</a>
	 */
	ETAG("ETag", true),

	/**
	 * Location header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30</a>
	 */
	LOCATION("Location", true),

	/**
	 * Cache Control header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9</a>
	 */
	CACHE_CONTROL("Cache-Control", false),

	/**
	 * Cache Control header name.
	 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9</a>
	 */
	CONTENT_SECURITY_POLICY("Content-Security-Policy", false),

	/**
	 * Pragma header name.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32</a>
	 */
	PRAGMA("Pragma", false),

	/**
	 * X-XSS-Protection header name.
	 * @see <a href="https://www.w3.org/TR/2012/WD-CSP11-20121213/#relationship-to-x-xss-protection">https://www.w3.org/TR/2012/WD-CSP11-20121213/#relationship-to-x-xss-protection</a>
	 */
	X_XSS_PROTECTION("X-XSS-Protection", false),

	/**
	 * X-Content-Type-Options header name.
	 * @see <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">https://fetch.spec.whatwg.org/#x-content-type-options-header</a>
	 */
	X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options", false),

	/**
	 * X-Content-Type-Options header name.
	 * @see <a href="https://tools.ietf.org/html/rfc7034">https://tools.ietf.org/html/rfc7034</a>
	 */
	X_FRAME_OPTIONS("X-Frame-Options", false),

	/**
	 * Set-Cookie header name.
	 * @see <a href="https://tools.ietf.org/html/rfc6265">https://tools.ietf.org/html/rfc6265</a>
	 */
	SET_COOKIE("Set-Cookie", false),

	/**
	 * Access-Control-Allow-Origin name.
	 * @see <a href="https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-origin">https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-origin</a>
	 */
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin", true),

	/**
	 * Access-Control-Allow-Headers name.
	 * @see <a href="https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-headers">https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-headers</a>
	 */
	ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers", true),

	/**
	 * Access-Control-Allow-Headers name.
	 * @see <a href="https://www.w3.org/TR/cors/#access-control-expose-headers-response-header">https://www.w3.org/TR/cors/#access-control-expose-headers-response-header</a>
	 */
	ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers", true),

	/**
	 * Access-Control-Allow-Methods name.
	 * @see <a href="https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-methods">https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-methods</a>
	 */
	ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods", true),

	/**
	 * Access-Control-Allow-Credentials name.
	 * @see <a href="https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-credentials">https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-credentials</a>
	 */
	ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", true),

	/**
	 * Access-Control-Allow-Credentials name.
	 * @see <a href="https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-max-age">https://www.w3.org/TR/2008/WD-access-control-20080912/#access-control-allow-max-age</a>
	 */
	ACCESS_CONTROL_ALLOW_MAX_AGE("Access-Control-Allow-Max-Age", true),

	/**
	 * Strict-Transport-Security name.
	 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
	 */
	STRICT_TRANSPORT_SECURITY("Strict-Transport-Security", true);

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

	HttpHeader(String name, boolean single) {
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
