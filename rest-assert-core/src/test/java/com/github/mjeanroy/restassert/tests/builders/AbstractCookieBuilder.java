/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.tests.builders;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.randomString;

public abstract class AbstractCookieBuilder<T, SELF extends CookieBuilder<T>> implements CookieBuilder<T> {

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
	 * Cookie "http-only" flag.
	 */
	private boolean httpOnly;

	/**
	 * Cookie "secure" flag.
	 */
	private boolean secure;

	/**
	 * Cookie max-age value.
	 */
	private long maxAge;

	protected AbstractCookieBuilder() {
		this.name = randomString();
		this.value = randomString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setName(String name) {
		this.name = name;
		return (SELF) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setValue(String value) {
		this.value = value;
		return (SELF) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setDomain(String domain) {
		this.domain = domain;
		return (SELF) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setPath(String path) {
		this.path = path;
		return (SELF) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setMaxAge(long maxAge) {
		this.maxAge = maxAge;
		return (SELF) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
		return (SELF) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final SELF setSecure(boolean secured) {
		this.secure = secured;
		return (SELF) this;
	}

	protected final String getName() {
		return name;
	}

	protected final String getValue() {
		return value;
	}

	protected final String getDomain() {
		return domain;
	}

	protected final String getPath() {
		return path;
	}

	protected final long getMaxAge() {
		return maxAge;
	}

	protected final boolean isHttpOnly() {
		return httpOnly;
	}

	protected final boolean isSecure() {
		return secure;
	}
}
