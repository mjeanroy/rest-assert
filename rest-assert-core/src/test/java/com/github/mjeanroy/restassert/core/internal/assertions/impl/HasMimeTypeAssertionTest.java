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

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class HasMimeTypeAssertionTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_mime_type() {
		final String mimeType = "application/json";
		final HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(mimeType);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_mime_types() {
		final String m1 = "application/json";
		final String m2 = "application/xml";
		final HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(asList(m1, m2));
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_header_is_not_set_with_expected_mime_type() {
		final String mimeType = "application/xml";
		final HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(mimeType);
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have mime type application/xml but was application/json");
	}

	@Test
	public void it_should_fail_if_header_is_not_set_with_expected_mime_types() {
		final String m1 = "application/xml";
		final String m2 = "application/json";
		final HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(asList(m1, m2));
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader("Content-Type", "text/html; charset=utf-8")
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have mime type in [application/xml, application/json] but was text/html");
	}

	@Test
	public void it_should_fail_if_content_type_header_has_multiple_values() {
		final HasMimeTypeAssertion assertion = new HasMimeTypeAssertion("application/json");
		final HttpResponse rsp = new HttpResponseBuilderImpl()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.addHeader("Content-Type", "application/xml; charset=utf-8")
				.build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to contains header Content-Type with a single value but found: [application/json; charset=utf-8, application/xml; charset=utf-8]");
	}

	@Test
	public void it_should_fail_if_header_is_not_set() {
		final HasMimeTypeAssertion assertion = new HasMimeTypeAssertion("application/json");
		final HttpResponse rsp = new HttpResponseBuilderImpl().build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header Content-Type");
	}

	@Test
	public void it_should_fail_if_mime_type_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Mime-Type value must be defined");
		new HasMimeTypeAssertion((String) null);
	}

	@Test
	public void it_should_fail_if_list_mime_type_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Mime-Type values must be defined");
		new HasMimeTypeAssertion((Iterable<String>) null);
	}

	@Test
	public void it_should_fail_if_list_mime_type_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Mime-Type values must be defined");
		new HasMimeTypeAssertion(Collections.<String>emptyList());
	}

	@Test
	public void it_should_fail_if_mime_type_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Mime-Type value must be defined");
		new HasMimeTypeAssertion("");
	}

	@Test
	public void it_should_fail_if_header_name_is_blank() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Mime-Type value must be defined");
		new HasMimeTypeAssertion("    ");
	}
}
