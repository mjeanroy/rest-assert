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

/**
 * Builder used to create instance of {@link StrictTransportSecurity} object.
 */
public class StrictTransportSecurityBuilder {

	/**
	 * {@code max-age} directive.
	 * The max-age value is required to create value.
	 */
	private long maxAge;

	/**
	 * {@code includeSubDomains} directive.
	 */
	private boolean includeSubDomains;

	/**
	 * {@code preload} directive.
	 */
	private boolean preload;

	/**
	 * Empty constructor, only used internally.
	 */
	StrictTransportSecurityBuilder() {
	}

	/**
	 * Create builder.
	 *
	 * @param maxAge Max-Age value.
	 * @see <a href="https://tools.ietf.org/html/rfc6797#page-16">https://tools.ietf.org/html/rfc6797#page-16</a>
	 */
	StrictTransportSecurityBuilder(long maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * Update {@code maxage} directive.
	 *
	 * @param maxAge The max age value.
	 * @return Current builder.
	 * @see <a href="https://tools.ietf.org/html/rfc6797#page-16">https://tools.ietf.org/html/rfc6797#page-16</a>
	 */
	public StrictTransportSecurityBuilder maxAge(long maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Enable {@code includeSubDomains} directive.
	 *
	 * @return Current builder.
	 * @see <a href="https://tools.ietf.org/html/rfc6797#section-6.1.2">https://tools.ietf.org/html/rfc6797#section-6.1.2</a>
	 */
	public StrictTransportSecurityBuilder includeSubDomains() {
		this.includeSubDomains = true;
		return this;
	}

	/**
	 * Enable {@code preload} directive.
	 *
	 * @return Current builder.
	 * @see <a href="https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security">https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security</a>
	 */
	public StrictTransportSecurityBuilder preload() {
		this.preload = true;
		return this;
	}

	/**
	 * Create {@code Strict-Transport-Security} value.
	 *
	 * @return Header.
	 */
	public StrictTransportSecurity build() {
		return new StrictTransportSecurity(maxAge, includeSubDomains, preload);
	}
}
