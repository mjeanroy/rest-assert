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

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.Cookie.SameSite;

import java.util.Date;

/**
 * Create mock instance of {@link Cookie} class.
 */
public class MockCookieBuilder extends AbstractCookieBuilder<Cookie, MockCookieBuilder> implements CookieBuilder<Cookie> {

	/**
	 * Cookie SameSite flag.
	 */
	private SameSite sameSite;

	/**
	 * Cookies expires Date.
	 */
	private Date expires;

	/**
	 * Create default builder.
	 */
	public MockCookieBuilder() {
		this.sameSite = SameSite.LAX;
	}

	/**
	 * Set {@link #sameSite}.
	 *
	 * @param sameSite New {@link #sameSite}.
	 * @return Current builder.
	 */
	public MockCookieBuilder setSameSite(SameSite sameSite) {
		this.sameSite = sameSite;
		return this;
	}

	/**
	 * Set {@link #sameSite}.
	 *
	 * @param sameSite New {@link #sameSite}.
	 * @return Current builder.
	 */
	public MockCookieBuilder setSameSite(String sameSite) {
		this.sameSite = SameSite.parse(sameSite);
		return this;
	}

	/**
	 * Set {@link #expires}.
	 *
	 * @param expires New {@link #expires}.
	 * @return Current builder.
	 */
	public MockCookieBuilder setExpires(Date expires) {
		this.expires = expires == null ? null : new Date(expires.getTime());
		return this;
	}

	/**
	 * Set {@link #expires} using given timestamp.
	 *
	 * @param time New {@link #expires} time.
	 * @return Current builder.
	 */
	public MockCookieBuilder setExpires(long time) {
		return setExpires(new Date(time));
	}

	/**
	 * Build mock of {@link Cookie} class.
	 *
	 * @return Mock instance.
	 */
	public Cookie build() {
		return new MockCookie(
			getName(),
			getValue(),
			getDomain(),
			getPath(),
			isHttpOnly(),
			isSecure(),
			sameSite,
			getMaxAge(),
			expires
		);
	}

	private static final class MockCookie implements Cookie {

		private final String name;
		private final String value;
		private final String domain;
		private final String path;
		private final boolean httpOnly;
		private final boolean secured;
		private final SameSite sameSite;
		private final long maxAge;
		private final Date expires;

		private MockCookie(
			String name,
			String value,
			String domain,
			String path,
			boolean httpOnly,
			boolean secured,
			SameSite sameSite,
			long maxAge,
			Date expires
		) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.httpOnly = httpOnly;
			this.secured = secured;
			this.sameSite = sameSite;
			this.maxAge = maxAge;
			this.expires = expires;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public String getDomain() {
			return domain;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public boolean isSecured() {
			return secured;
		}

		@Override
		public boolean isHttpOnly() {
			return httpOnly;
		}

		@Override
		public SameSite getSameSite() {
			return sameSite;
		}

		@Override
		public Long getMaxAge() {
			return maxAge;
		}

		@Override
		public Date getExpires() {
			return expires;
		}

		@Override
		public String toString() {
			return "Cookie{name: " + name + "}";
		}
	}
}
