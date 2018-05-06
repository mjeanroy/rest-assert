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

import com.github.mjeanroy.restassert.core.data.MediaType;

/**
 * List of standards mime types:
 *
 * <ul>
 *   <li>Media Types format is standardized: <a href="http://www.w3.org/Protocols/rfc1341/4_Content-Type.html">http://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a></li>
 *   <li>List of standard mime types: <a href="http://www.iana.org/assignments/media-types/media-types.xhtml">http://www.iana.org/assignments/media-types/media-types.xhtml</a></li>
 * </ul>
 *
 * @see <a href="http://www.w3.org/Protocols/rfc1341/4_Content-Type.html">http://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
 * @see <a href="http://www.iana.org/assignments/media-types/media-types.xhtml">http://www.iana.org/assignments/media-types/media-types.xhtml</a>
 */
public final class MimeTypes {

	// Ensure non instantiation
	private MimeTypes() {
	}

	/**
	 * Text media type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc2046#section-4.1">https://tools.ietf.org/html/rfc2046#section-4.1</a>
	 */
	public static final MediaType TEXT_PLAIN = MediaType.text("plain");

	/**
	 * CSV Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc4180">https://tools.ietf.org/html/rfc4180</a>
	 */
	public static final MediaType CSV = MediaType.text("csv");

	/**
	 * PDF Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3778">https://tools.ietf.org/html/rfc3778</a>
	 */
	public static final MediaType PDF = MediaType.application("pdf");

	/**
	 * HTML Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc2854">https://tools.ietf.org/html/rfc2854</a>
	 */
	public static final MediaType TEXT_HTML = MediaType.text("html");

	/**
	 * XHTML Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3236">https://tools.ietf.org/html/rfc3236</a>
	 */
	public static final MediaType XHTML = MediaType.application("xhtml+xml");

	/**
	 * CSS Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc2318">https://tools.ietf.org/html/rfc2318</a>
	 */
	public static final MediaType CSS = MediaType.text("css");

	/**
	 * JSON Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc4627">https://tools.ietf.org/html/rfc3023</a>
	 */
	public static final MediaType JSON = MediaType.application("json");

	/**
	 * XML Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3023">https://tools.ietf.org/html/rfc3023</a>
	 */
	public static final MediaType APPLICATION_XML = MediaType.application("xml");

	/**
	 * XML Media Type.
	 * Alternative for "application/xml" media type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3023">https://tools.ietf.org/html/rfc3023</a>
	 */
	public static final MediaType TEXT_XML = MediaType.text("xml");

	/**
	 * JavaScript Media Type.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc4329">https://tools.ietf.org/html/rfc4329</a>
	 */
	public static final MediaType APPLICATION_JAVASCRIPT = MediaType.application("javascript");

	/**
	 * JavaScript Media Type.
	 * Obsolete alternative for "application/javascript" media type.
	 * This alternative may be used by old web server.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc4329"></a>https://tools.ietf.org/html/rfc4329</a>
	 */
	public static final MediaType TEXT_JAVASCRIPT = MediaType.text("javascript");
}
