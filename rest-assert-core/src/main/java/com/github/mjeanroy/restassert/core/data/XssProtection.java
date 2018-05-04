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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.internal.data.AbstractHeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

/**
 * Values of valid XSS protection header.
 */
public enum XssProtection implements HeaderValue {

	/**
	 * Disables the XSS Protections offered by the user-agent.
	 */
	DISABLE("0") {
		@Override
		boolean match(String value) {
			return value.equals(header);
		}
	},

	/**
	 * Enables the XSS Protections.
	 */
	ENABLE("1") {
		@Override
		boolean match(String value) {
			return value.equals(header);
		}
	},

	/**
	 * Enables XSS protections and instructs the user-agent to block the response in the
	 * event that script has been inserted from user input, instead of sanitizing.
	 */
	ENABLE_BLOCK("1; mode=block") {
		@Override
		boolean match(String value) {
			return value.equals(header);
		}
	};

	/**
	 * The parser instance.
	 */
	private static final XssProtectionParser PARSER = new XssProtectionParser();

	/**
	 * Get parser for {@link XssProtection} instances.
	 *
	 * @return The parser.
	 */
	public static HeaderParser parser() {
		return PARSER;
	}

	/**
	 * The header value.
	 */
	final String header;

	XssProtection(String header) {
		this.header = header;
	}

	@Override
	public String serializeValue() {
		return header;
	}

	/**
	 * Check if header raw value match specified value.
	 *
	 * @param value Raw value.
	 * @return {@code true} if {@code value} match specified value, {@code false} otherwise.
	 */
	abstract boolean match(String value);

	/**
	 * Parser for {@link XssProtection} header.
	 */
	private static class XssProtectionParser extends AbstractHeaderParser {

		@Override
		protected HeaderValue doParse(String value) {
			String rawValue = value.toLowerCase();
			for (XssProtection x : XssProtection.values()) {
				if (x.match(rawValue)) {
					return x;
				}
			}

			return null;
		}
	}
}
