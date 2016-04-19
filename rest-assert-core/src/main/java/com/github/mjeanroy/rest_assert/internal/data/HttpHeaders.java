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

/**
 * List of standards http headers names.
 */
public final class HttpHeaders {

	// Ensure non instantiation
	private HttpHeaders() {
	}

	/**
	 * Content type header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17
	 */
	public static final String CONTENT_TYPE = "Content-Type";

	/**
	 * Content Encoding header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11
	 */
	public static final String CONTENT_ENCODING = "Content-Encoding";

	/**
	 * Content Disposition header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1
	 */
	public static final String CONTENT_DISPOSITION = "Content-Disposition";

	/**
	 * Last Modified header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29
	 */
	public static final String LAST_MODIFIED = "Last-Modified";

	/**
	 * Expires header name.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21
	 */
	public static final String EXPIRES = "Expires";

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

	/**
	 * X-XSS-Protection header name.
	 * https://www.w3.org/TR/2012/WD-CSP11-20121213/#relationship-to-x-xss-protection
	 */
	public static final String X_XSS_PROTECTION = "X-XSS-Protection";

	/**
	 * X-Content-Type-Options header name.
	 * https://fetch.spec.whatwg.org/#x-content-type-options-header
	 */
	public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

	/**
	 * X-Content-Type-Options header name.
	 * https://tools.ietf.org/html/rfc7034
	 */
	public static final String X_FRAME_OPTIONS = "X-Frame-Options";

	public static interface HeaderValue {
		/**
		 * Get header value (as it should appears in HTTP header).
		 *
		 * @return Header value.
		 */
		String value();

		/**
		 * Check that actual header value match this header value (in most cases,
		 * a simple call to equals should be enough).
		 *
		 * @param actualValue Actual header value.
		 * @return {@code true} if actual header value match, {@code false} otherwise.
		 */
		boolean match(String actualValue);
	}

	/**
	 * Values of valid XSS protection header.
	 */
	public static enum XssProtection implements HeaderValue {

		/**
		 * Disables the XSS Protections offered by the user-agent.
		 */
		DISABLE("0"),

		/**
		 * Enables the XSS Protections.
		 */
		ENABLE("1"),

		/**
		 * Enables XSS protections and instructs the user-agent to block the response in the
		 * event that script has been inserted from user input, instead of sanitizing.
		 */
		ENABLE_BLOCK("1; mode=block");

		private final String header;

		XssProtection(String header) {
			this.header = header;
		}

		@Override
		public String value() {
			return header;
		}

		@Override
		public boolean match(String actualValue) {
			return header.equals(actualValue);
		}
	}

	/**
	 * Values of valid X-Content-Type-Options header.
	 */
	public static enum ContentTypeOptions implements HeaderValue {

		/**
		 * The only defined value, "nosniff", prevents Internet Explorer and Google Chrome
		 * from MIME-sniffing a response away from the declared content-type.
		 */
		NO_SNIFF("nosniff");

		private final String header;

		private ContentTypeOptions(String header) {
			this.header = header;
		}

		@Override
		public String value() {
			return header;
		}

		@Override
		public boolean match(String actualValue) {
			return header.equals(actualValue);
		}
	}

	/**
	 * Values of valid X-Frame-Options header.
	 */
	public static enum FrameOptions implements HeaderValue {

		/**
		 * A browser receiving content with this header MUST NOT display
		 * this content in any frame.
		 */
		DENY("deny"),

		/**
		 * A browser receiving content with this header MUST NOT display
		 * this content in any frame from a page of different origin than
		 * the content itself.
		 *
		 * If a browser or plugin can not reliably determine whether the
		 * origin of the content and the frame have the same origin, this
		 * MUST be treated as "DENY".
		 */
		SAME_ORIGIN("sameorigin"),

		/**
		 * A browser receiving content with this header MUST NOT display
		 * this content in any frame from a page of different origin than
		 * the listed origin.  While this can expose the page to risks by
		 * the trusted origin, in some cases it may be necessary to use
		 * content from other domains.
		 */
		ALLOW_FROM("allow-from") {
			@Override
			public boolean match(String actualValue) {
				return actualValue.startsWith(value());
			}
		};

		private final String header;

		private FrameOptions(String header) {
			this.header = header;
		}

		@Override
		public String value() {
			return header;
		}

		@Override
		public boolean match(String actualValue) {
			return header.equals(actualValue);
		}
	}
}
