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

import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

/**
 * Values of valid XSS protection value.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
 */
public enum XssProtection implements HeaderValue {

	/**
	 * Disables the XSS Protections offered by the user-agent.
	 */
	DISABLE("0") {
		@Override
		boolean match(String value) {
			return value.equals(this.value);
		}
	},

	/**
	 * Enables the XSS Protections.
	 */
	ENABLE("1") {
		@Override
		boolean match(String value) {
			return value.equals(this.value);
		}
	},

	/**
	 * Enables XSS protections and instructs the user-agent to block the response in the
	 * event that script has been inserted from user input, instead of sanitizing.
	 */
	ENABLE_BLOCK("1; mode=block") {
		@Override
		boolean match(String value) {
			String[] parts = this.value.split(";", 2);
			if (parts.length != 2) {
				return false;
			}

			String mode = parts[0].trim().toLowerCase();
			String option = parts[1].trim().toLowerCase();
			return mode.equals("1") && option.equals("mode=block");
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
	public static XssProtectionParser parser() {
		return PARSER;
	}

	/**
	 * The value value.
	 */
	final String value;

	XssProtection(String value) {
		this.value = value;
	}

	@Override
	public String serializeValue() {
		return value;
	}

	/**
	 * Check if value raw value match specified value.
	 *
	 * @param value Raw value.
	 * @return {@code true} if {@code value} match specified value, {@code false} otherwise.
	 */
	abstract boolean match(String value);
}
