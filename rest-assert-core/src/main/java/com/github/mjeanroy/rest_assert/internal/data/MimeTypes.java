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

package com.github.mjeanroy.rest_assert.internal.data;

/**
 * List of standards mime types.
 *
 * Media Types format is standardized:
 * http://www.w3.org/Protocols/rfc1341/4_Content-Type.html
 *
 * List of standard mime types:
 * http://www.iana.org/assignments/media-types/media-types.xhtml
 */
public final class MimeTypes {

	// Ensure non instantiation
	private MimeTypes() {
	}

	/**
	 * Text media type.
	 * https://tools.ietf.org/html/rfc2046#section-4.1
	 */
	public static final String TEXT_PLAIN = "text/plain";

	/**
	 * CSV Media Type.
	 * https://tools.ietf.org/html/rfc4180
	 */
	public static final String CSV = "text/csv";

	/**
	 * PDF Media Type.
	 * https://tools.ietf.org/html/rfc3778
	 */
	public static final String PDF = "application/pdf";

	/**
	 * HTML Media Type.
	 * https://tools.ietf.org/html/rfc2854
	 */
	public static final String TEXT_HTML = "text/html";

	/**
	 * XHTML Media Type.
	 * https://tools.ietf.org/html/rfc3236
	 */
	public static final String XHTML = "application/xhtml+xml";

	/**
	 * CSS Media Type.
	 * https://tools.ietf.org/html/rfc2318
	 */
	public static final String CSS = "text/css";

	/**
	 * JSON Media Type.
	 * https://tools.ietf.org/html/rfc4627
	 */
	public static final String JSON = "application/json";

	/**
	 * XML Media Type.
	 * https://tools.ietf.org/html/rfc3023
	 */
	public static final String APPLICATION_XML = "application/xml";

	/**
	 * XML Media Type.
	 * Alternative for "application/xml" media type.
	 * https://tools.ietf.org/html/rfc3023
	 */
	public static final String TEXT_XML = "text/xml";

	/**
	 * JavaScript Media Type.
	 * https://tools.ietf.org/html/rfc4329
	 */
	public static final String APPLICATION_JAVASCRIPT = "application/javascript";

	/**
	 * JavaScript Media Type.
	 * https://tools.ietf.org/html/rfc4329
	 * Obsolete alternative for "application/javascript" media type.
	 * This alternative may be used by old web server.
	 */
	public static final String TEXT_JAVASCRIPT = "text/javascript";
}
