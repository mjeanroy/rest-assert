package com.github.mjeanroy.restassert.unit.api.cookie.apache;

import com.github.mjeanroy.restassert.tests.builders.apache.ApacheHttpCookieBuilder;
import com.github.mjeanroy.restassert.unit.api.cookie.ApacheHttpCookieAssert;
import org.apache.http.cookie.Cookie;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertHasMaxAge {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_fail_because_of_unsupported_operation() {
		Cookie cookie = new ApacheHttpCookieBuilder().build();

		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("org.apache.http.cookie.Cookie does not support #getMaxAge(), please use #getExpires() instead.");

		ApacheHttpCookieAssert.assertHasMaxAge(cookie, 0L);
	}

	@Test
	public void it_should_fail_with_custom_message_because_of_unsupported_operation() {
		Cookie cookie = new ApacheHttpCookieBuilder().build();

		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("org.apache.http.cookie.Cookie does not support #getMaxAge(), please use #getExpires() instead.");

		ApacheHttpCookieAssert.assertHasMaxAge("message", cookie, 0L);
	}
}
