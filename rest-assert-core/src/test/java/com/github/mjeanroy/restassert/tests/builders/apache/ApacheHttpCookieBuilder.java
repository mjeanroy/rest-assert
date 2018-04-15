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

package com.github.mjeanroy.restassert.tests.builders.apache;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Build mock instance of {@link Cookie} class.
 */
public class ApacheHttpCookieBuilder {

	/**
	 * Cookie name.
	 */
	private String name;

	/**
	 * Cookie value.
	 */
	private String value;

	/**
	 * Cookie domain.
	 */
	private String domain;

	/**
	 * Cookie path.
	 */
	private String path;

	/**
	 * Cookie "secure" flag.
	 */
	private boolean secure;

	/**
	 * Create builder.
	 */
	public ApacheHttpCookieBuilder() {
		this.name = "cookie";
		this.value = "";
	}

	/**
	 * Set {@link #secure}.
	 *
	 * @param secure New {@link #secure}.
	 * @return Current builder.
	 */
	public ApacheHttpCookieBuilder setSecure(boolean secure) {
		this.secure = secure;
		return this;
	}

	/**
	 * Set {@link #domain}.
	 *
	 * @param domain New {@link #domain}.
	 * @return Current builder.
	 */
	public ApacheHttpCookieBuilder setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * Set {@link #name}.
	 *
	 * @param name New {@link #name}.
	 * @return Current builder.
	 */
	public ApacheHttpCookieBuilder setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set {@link #value}.
	 *
	 * @param value New {@link #value}.
	 * @return Current builder.
	 */
	public ApacheHttpCookieBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Set {@link #path}.
	 *
	 * @param path New {@link #path}.
	 * @return Current builder.
	 */
	public ApacheHttpCookieBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Create mock instance of {@link Cookie}.
	 *
	 * @return Mock instance.
	 */
	public Cookie build() {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setDomain(domain);
		cookie.setSecure(secure);
		cookie.setPath(path);
		return cookie;
	}
}
