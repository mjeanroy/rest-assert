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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.data.MediaType;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HasMimeTypeAssertionTest {

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_mime_type() {
		MediaType mimeType = MediaType.parser().parse("application/json");
		HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(mimeType);
		HttpResponse rsp = createHttpResponse("application/json");

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_not_fail_if_header_is_set_with_expected_mime_types() {
		MediaType m1 = MediaType.parser().parse("application/json");
		MediaType m2 = MediaType.parser().parse("application/xml");
		HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(asList(m1, m2));
		HttpResponse rsp = createHttpResponse("application/json");

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	public void it_should_fail_if_header_is_not_set_with_expected_mime_type() {
		MediaType mimeType = MediaType.parser().parse("application/xml");
		HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(mimeType);
		HttpResponse rsp = createHttpResponse("application/json");

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have mime type application/xml but was application/json");
	}

	@Test
	public void it_should_fail_if_header_is_not_set_with_expected_mime_types() {
		MediaType m1 = MediaType.parser().parse("application/xml");
		MediaType m2 = MediaType.parser().parse("application/json");
		HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(asList(m1, m2));
		HttpResponse rsp = createHttpResponse("text/html");

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have mime type in [application/xml, application/json] but was text/html");
	}

	@Test
	public void it_should_fail_if_content_type_header_has_multiple_values() {
		MediaType mediaType  = MediaType.parser().parse("application/json");
		HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(mediaType);
		HttpResponse rsp = new HttpResponseBuilderImpl()
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
		MediaType mediaType = MediaType.parser().parse("application/json");
		HasMimeTypeAssertion assertion = new HasMimeTypeAssertion(mediaType);
		HttpResponse rsp = new HttpResponseBuilderImpl().build();

		AssertionResult result = assertion.handle(rsp);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
		assertThat(result.getError().toString()).isEqualTo("Expecting response to have header Content-Type");
	}

	@Test
	public void it_should_fail_if_mime_type_is_null() {
		assertThatThrownBy(hasMimeTypeAssertion((MediaType) null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Mime-Type value must be defined");
	}

	@Test
	public void it_should_fail_if_list_mime_type_is_null() {
		assertThatThrownBy(hasMimeTypeAssertion((Collection<MediaType>) null))
				.isExactlyInstanceOf(NullPointerException.class)
				.hasMessage("Mime-Type values must be defined");
	}

	@Test
	public void it_should_fail_if_list_mime_type_is_empty() {
		assertThatThrownBy(hasMimeTypeAssertion(Collections.<MediaType>emptyList()))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Mime-Type values must be defined");
	}

	private static HttpResponse createHttpResponse(String mimeType) {
		return new HttpResponseBuilderImpl().addHeader("Content-Type", mimeType + "; charset=utf-8").build();
	}

	private static ThrowingCallable hasMimeTypeAssertion(final Collection<MediaType> mediaTypes) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				new HasMimeTypeAssertion(mediaTypes);
			}
		};
	}

	private static ThrowingCallable hasMimeTypeAssertion(final MediaType mediaType) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				new HasMimeTypeAssertion(mediaType);
			}
		};
	}
}
