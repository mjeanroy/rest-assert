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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class IsDateHeaderEqualToAssertionTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_value() {
		final String name = "foo";
		final String value = "Thu, 05 May 2016 19:29:03 GMT";

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		cal.set(Calendar.HOUR_OF_DAY, 19);
		cal.set(Calendar.MINUTE, 29);
		cal.set(Calendar.SECOND, 3);

		final Date date = cal.getTime();
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, value)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_multiple_value_header_contains_expected_value() {
		final String name = "foo";
		final String v1 = "Thu, 05 May 2016 19:29:03 GMT";
		final String v2 = "Thu, 05 May 2016 19:30:03 GMT";

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		cal.set(Calendar.HOUR_OF_DAY, 19);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 3);

		final Date date = cal.getTime();
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, v1)
				.addHeader(name, v2)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_header_is_not_set() {
		final String name = "foo";

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		cal.set(Calendar.HOUR_OF_DAY, 19);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 3);

		final Date date = cal.getTime();
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl().build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo");
	}

	@Test
	public void it_should_fail_if_header_is_does_not_have_expected_value() {
		final String name = "foo";
		final String value = "Thu, 05 May 2016 19:29:03 GMT";

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		cal.set(Calendar.HOUR_OF_DAY, 19);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 3);

		final Date date = cal.getTime();
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, value)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header foo equal to Thu, 05 May 2016 19:30:03 GMT but was Thu, 05 May 2016 19:29:03 GMT");
	}

	@Test
	public void it_should_fail_if_single_value_header_has_multiple_values() {
		final String name = "Last-Modified";
		final String v1 = "Thu, 05 May 2016 19:29:03 GMT";
		final String v2 = "Thu, 05 May 2016 19:30:03 GMT";

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		cal.set(Calendar.HOUR_OF_DAY, 19);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 3);

		final Date date = cal.getTime();
		final IsDateHeaderEqualToAssertion assertion = new IsDateHeaderEqualToAssertion(name, date);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader(name, v1)
				.addHeader(name, v2)
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to contains header Last-Modified with a single value but found: [Thu, 05 May 2016 19:29:03 GMT, Thu, 05 May 2016 19:30:03 GMT]");
	}

	@Test
	public void it_should_fail_if_header_name_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Header name cannot be blank");
		new IsDateHeaderEqualToAssertion(null, new Date());
	}

	@Test
	public void it_should_fail_if_header_name_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Header name cannot be blank");
		new IsDateHeaderEqualToAssertion("", new Date());
	}

	@Test
	public void it_should_fail_if_header_name_is_blank() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Header name cannot be blank");
		new IsDateHeaderEqualToAssertion("   ", new Date());
	}

	@Test
	public void it_should_fail_if_header_value_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Header value must not be null");
		new IsDateHeaderEqualToAssertion("name", null);
	}
}
