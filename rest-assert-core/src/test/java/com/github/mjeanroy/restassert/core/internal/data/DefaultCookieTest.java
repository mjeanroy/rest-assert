/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

import com.github.mjeanroy.restassert.core.internal.data.Cookie.SameSite;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static com.github.mjeanroy.restassert.tests.TestUtils.createUtcDate;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultCookieTest {

	@Test
	void it_should_implement_equals() {
		EqualsVerifier.forClass(DefaultCookie.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		String name = "X-Auth-Token";
		String value = "b145d7b2-d76c-4aa4-93d4-5f853f94b333";
		String domain = "localhost";
		String path = "/";
		boolean secure = true;
		boolean httpOnly = true;
		long maxAge = 0L;
		SameSite sameSite = SameSite.LAX;
		Date expires = null;

		DefaultCookie cookie = new DefaultCookie(name, value, domain, path, secure, httpOnly, sameSite, maxAge, expires);

		assertThat(cookie.toString()).isEqualTo(
			"DefaultCookie{" +
				"name=X-Auth-Token, " +
				"value=b145d7b2-d76c-4aa4-93d4-5f853f94b333, " +
				"domain=localhost, " +
				"path=/, " +
				"secure=true, " +
				"httpOnly=true, " +
				"sameSite=LAX, " +
				"maxAge=0, " +
				"expires=null" +
				"}"
		);
	}

	@Test
	void it_should_create_cookie_with_a_clone_of_expires_dates() {
		String name = "X-Auth-Token";
		String value = "b145d7b2-d76c-4aa4-93d4-5f853f94b333";
		String domain = "localhost";
		String path = "/";
		boolean secure = true;
		boolean httpOnly = true;
		long maxAge = 0L;
		SameSite sameSite = SameSite.LAX;
		Date expires = createUtcDate(2016, Calendar.APRIL, 21, 18, 21, 35);
		DefaultCookie cookie = new DefaultCookie(name, value, domain, path, secure, httpOnly, sameSite, maxAge, expires);
		assertThat(cookie.getExpires()).isNotNull().isNotSameAs(expires).isEqualTo(expires);
	}
}
