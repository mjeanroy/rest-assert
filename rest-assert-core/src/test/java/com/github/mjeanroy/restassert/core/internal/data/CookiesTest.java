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

import com.github.mjeanroy.restassert.core.internal.data.Cookie.SameSite;
import com.github.mjeanroy.restassert.tests.builders.CookieBuilder;
import com.github.mjeanroy.restassert.tests.junit.TimeZoneRule;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static com.github.mjeanroy.restassert.core.internal.data.Cookies.newCookie;
import static com.github.mjeanroy.restassert.core.internal.data.Cookies.parse;
import static com.github.mjeanroy.restassert.tests.TestUtils.createUtcDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("ConstantConditions")
public class CookiesTest {

	@ClassRule
	public static TimeZoneRule tzRule = new TimeZoneRule("UTC");

	@Test
	public void it_should_create_cookie() {
		final String name = "foo";
		final String value = "bar";
		final String path = "/";
		final String domain = "domain.com";
		final boolean secure = true;
		final boolean httpOnly = true;
		final SameSite sameSite = SameSite.STRICT;
		final long maxAge = 10;
		final Date expires = null;

		final Cookie cookie = newCookie(name, value, domain, path, secure, httpOnly, sameSite, maxAge, expires);

		assertThat(cookie.getName()).isEqualTo(name);
		assertThat(cookie.getValue()).isEqualTo(value);
		assertThat(cookie.getPath()).isEqualTo(path);
		assertThat(cookie.getDomain()).isEqualTo(domain);
		assertThat(cookie.isSecured()).isEqualTo(secure);
		assertThat(cookie.isHttpOnly()).isEqualTo(httpOnly);
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.STRICT);
		assertThat(cookie.getMaxAge()).isEqualTo(maxAge);
	}

	@Test
	public void it_should_implement_to_string() {
		final String name = "name";
		final String value = "value";
		final String domain = "domain.com";
		final String path = "/";
		final boolean secure = true;
		final boolean httpOnly = true;
		final SameSite sameSite = SameSite.LAX;
		final long maxAge = 3600L;
		final Date expires = createUtcDate(2016, Calendar.APRIL, 21, 18, 21, 35);

		final Cookie cookie = newCookie(name, value, domain, path, secure, httpOnly, sameSite, maxAge, expires);

		assertThat(cookie.toString()).isEqualTo(
				"DefaultCookie{" +
						"name=name, " +
						"value=value, " +
						"domain=domain.com, " +
						"path=/, " +
						"secure=true, " +
						"httpOnly=true, " +
						"sameSite=LAX, " +
						"maxAge=3600, " +
						"expires=Thu Apr 21 18:21:35 UTC 2016" +
				"}"
		);
	}

	@Test
	public void it_should_not_parse_empty_set_cookie_header() {
		assertThatThrownBy(parseCookie("  "))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Header Set-Cookie must be defined");
}

	@Test
	public void it_should_parse_set_cookie() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; expires=Fri, 06 May 2016 16:19:20 -0000; secure; HttpOnly";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo("user_session");
		assertThat(cookie.getValue()).isEqualTo("foobar==");
		assertThat(cookie.getDomain()).isEqualTo("github.com");
		assertThat(cookie.getPath()).isEqualTo("/");
		assertThat(cookie.isSecured()).isTrue();
		assertThat(cookie.isHttpOnly()).isTrue();
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.LAX);
	}

	@Test
	public void it_should_parse_set_cookie_without_secure_flag() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; expires=Fri, 06 May 2016 16:19:20 -0000; HttpOnly";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.isSecured()).isFalse();
	}

	@Test
	public void it_should_parse_set_cookie_without_http_only_flag() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; expires=Fri, 06 May 2016 16:19:20 -0000";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.isHttpOnly()).isFalse();
	}

	@Test
	public void it_should_parse_set_cookie_with_max_age() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; max-age=3600; secure; HttpOnly";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getMaxAge()).isEqualTo(3600L);
	}

	@Test
	public void it_should_parse_set_cookie_with_max_age_equal_to_zero() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; max-age=0; secure; HttpOnly";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getMaxAge()).isZero();
	}

	@Test
	public void it_should_parse_set_cookie_with_max_age_less_than_zero() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; max-age=-3600; secure; HttpOnly";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getMaxAge()).isEqualTo(-3600L);
	}

	@Test
	public void it_should_parse_set_cookie_with_same_site() {
		final String setCookie = "user_session=foobar==; domain=github.com; path=/; max-age=3600; secure; HttpOnly; SameSite=Strict";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getSameSite()).isEqualTo(SameSite.STRICT);
	}

	@Test
	public void it_should_fail_if_max_age_is_a_float() {
		final String value = "user_session=foobar==; domain=github.com; path=/; max-age=-3600.5; secure; HttpOnly";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Max-Age is not a valid number");
	}

	@Test
	public void it_should_fail_if_max_age_is_not_a_number() {
		final String value = "user_session=foobar==; domain=github.com; path=/; max-age=-3600ab; secure; HttpOnly";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Max-Age is not a valid number");
	}

	@Test
	public void it_should_fail_if_cookie_does_not_have_a_name() {
		final String value = "=foobar; domain=github.com; path=/; max-age=-3600; secure; HttpOnly";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Set-Cookie header must have a name");
	}

	@Test
	public void it_should_not_parse_set_cookie_if_it_does_not_have_a_value() {
		final String value = "user_session";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Set-Cookie header must have a value");
	}

	@Test
	public void it_should_parse_set_cookie_with_only_name_and_value() {
		final String setCookie = "user_session=foobar==";
		final Cookie cookie = Cookies.parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo("user_session");
		assertThat(cookie.getValue()).isEqualTo("foobar==");
	}

	@Test
	public void it_should_parse_expires_date() {
		final String setCookie = "name=value; expires=Thu, 21 Apr 2016 18:21:35 +0000";
		final Cookie cookie = parse(setCookie);

		assertThat(cookie).isNotNull();
		assertThat(cookie.getExpires()).isNotNull();

		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		cal.setTime(cookie.getExpires());
		assertThat(cal.get(Calendar.YEAR)).isEqualTo(2016);
		assertThat(cal.get(Calendar.MONTH)).isEqualTo(Calendar.APRIL);
		assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(21);
		assertThat(cal.get(Calendar.HOUR_OF_DAY)).isEqualTo(18);
		assertThat(cal.get(Calendar.MINUTE)).isEqualTo(21);
		assertThat(cal.get(Calendar.SECOND)).isEqualTo(35);
		assertThat(cal.get(Calendar.MILLISECOND)).isZero();
	}

	@Test
	public void it_should_parse_expires_date_and_convert_year_between_70_and_99() {
		for (int i = 70; i <= 99; i++) {
			final String setCookie = String.format("name=value; expires=Thu, 21 Apr %s 18:21:35 +0000", i);
			final Cookie cookie = parse(setCookie);

			assertThat(cookie).isNotNull();
			assertThat(cookie.getExpires())
					.isNotNull()
					.hasYear(1900 + i);
		}
	}

	@Test
	public void it_should_parse_expires_date_and_convert_year_between_0_and_69() {
		for (int i = 0; i <= 69; i++) {
			final String year = i < 10 ? ("0" + i) : String.valueOf(i);
			final String setCookie = String.format("name=value; expires=Thu, 21 Apr %s 18:21:35 +0000", year);
			final Cookie cookie = parse(setCookie);

			assertThat(cookie).isNotNull();
			assertThat(cookie.getExpires()).isNotNull().hasYear(2000 + i);
		}
	}

	@Test
	public void it_should_fail_if_year_is_less_than_1601() {
		final String value = "name=value; expires=Thu, 21 Apr 1600 18:21:35 +0000";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expires year must be greater than 1601");
	}

	@Test
	public void it_should_fail_if_day_is_less_than_1() {
		final String value = "name=value; expires=Thu, 0 Apr 2016 18:21:35 +0000";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expires day cannot be less than 1 or greater than 31");
	}

	@Test
	public void it_should_fail_if_day_is_greater_than_31() {
		final String value = "name=value; expires=Thu, 32 Apr 2016 18:21:35 +0000";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expires day cannot be less than 1 or greater than 31");
	}

	@Test
	public void it_should_fail_if_hours_is_greater_than_23() {
		final String value = "name=value; expires=Thu, 10 Apr 2016 24:21:35 +0000";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expires hour cannot be less than 0 or greater than 23");
	}

	@Test
	public void it_should_fail_if_minutes_is_greater_than_59() {
		final String value = "name=value; expires=Thu, 10 Apr 2016 18:60:35 +0000";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expires minutes cannot be less than 0 or greater than 59");
	}

	@Test
	public void it_should_fail_if_seconds_is_greater_than_59() {
		final String value = "name=value; expires=Thu, 10 Apr 2016 18:30:60 +0000";
		assertThatThrownBy(parseCookie(value))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expires second cannot be less than 0 or greater than 59");
	}

	@Test
	public void it_should_compare_cookies() {
		assertThat(Cookies.equals(null, null)).isTrue();
		assertThat(Cookies.equals(null, new CookieBuilder().build())).isFalse();
		assertThat(Cookies.equals(new CookieBuilder().build(), null)).isFalse();

		final String domain = "domain.com";
		final String path = "path";
		final Cookie c1 = new CookieBuilder()
				.setName("foo")
				.setValue("bar")
				.setDomain(domain)
				.setPath(path)
				.build();

		final Cookie c2 = new CookieBuilder()
				.setName("foo")
				.setValue("bar")
				.setDomain(domain)
				.build();

		assertThat(Cookies.equals(c1, c1)).isTrue();
		assertThat(Cookies.equals(c1, c2)).isFalse();
	}

	private static ThrowingCallable parseCookie(final String value) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				Cookies.parse(value);
			}
		};
	}
}
