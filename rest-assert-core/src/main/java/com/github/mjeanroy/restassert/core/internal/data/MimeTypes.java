/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

/// List of standards mime types:
/// - Media Types format is standardized in [RFC 1341](http://www.w3.org/Protocols/rfc1341/4_Content-Type.html).
/// - List of standard mime types can be found in the [IANA specification](http://www.iana.org/assignments/media-types/media-types.xhtml).
public final class MimeTypes {

	// Ensure non instantiation
	private MimeTypes() {
	}

	/// Text media type ([RFC 2046](https://tools.ietf.org/html/rfc2046#section-4.1));
	public static final MediaType TEXT_PLAIN = MediaType.text("plain");

	/// CSV Media Type ([RFC 4180](https://tools.ietf.org/html/rfc4180));
	public static final MediaType CSV = MediaType.text("csv");

	/// PDF Media Type ([RFC 3778](https://tools.ietf.org/html/rfc3778));
	public static final MediaType PDF = MediaType.application("pdf");

	/// HTML Media Type ([RFC 2854](https://tools.ietf.org/html/rfc2854));
	public static final MediaType TEXT_HTML = MediaType.text("html");

	/// XHTML Media Type ([RFC 3236](https://tools.ietf.org/html/rfc3236)).
	public static final MediaType XHTML = MediaType.application("xhtml+xml");

	/// CSS Media Type ([RFC 2318](https://tools.ietf.org/html/rfc2318)).
	public static final MediaType CSS = MediaType.text("css");

	/// JSON Media Type ([RFC 4627](https://tools.ietf.org/html/rfc3023)).
	public static final MediaType JSON = MediaType.application("json");

	/// XML Media Type ([RFC 3023](https://tools.ietf.org/html/rfc3023)).
	public static final MediaType APPLICATION_XML = MediaType.application("xml");

	/// XML Media Type ([RFC 3023](https://tools.ietf.org/html/rfc3023)).
	///
	/// Alternative for `application/xml` media type.
	public static final MediaType TEXT_XML = MediaType.text("xml");

	/// JavaScript Media Type ([RFC 4329](https://tools.ietf.org/html/rfc4329)).
	public static final MediaType APPLICATION_JAVASCRIPT = MediaType.application("javascript");

	/// JavaScript Media Type ([RFC 4329](https://tools.ietf.org/html/rfc4329)).
	///
	/// Obsolete alternative for `application/javascript` media type.
	/// This alternative may be used by old web server.
	public static final MediaType TEXT_JAVASCRIPT = MediaType.text("javascript");
}
