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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.github.mjeanroy.restassert.core.data.ContentType.contentType;
import static com.github.mjeanroy.restassert.core.data.MediaType.application;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ContentTypeTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_create_content_type_with_charsets() {
		ContentType contentType = contentType(application("json"), StandardCharsets.UTF_8);
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");

		assertThat(contentType.getParameters()).extracting("name", "value").containsOnly(tuple("charset", "utf-8"));
		assertThat(contentType.getParameter("charset")).isNotNull();
		assertThat(contentType.getParameter("charset").getName()).isEqualTo("charset");
		assertThat(contentType.getParameter("charset").getValue()).isEqualTo("utf-8");

		assertThat(contentType.getCharset()).isNotNull();
		assertThat(contentType.getCharset()).isEqualTo("utf-8");

		assertThat(contentType.serializeValue()).isEqualTo("application/json; charset=utf-8");
		assertThat(contentType.toString()).isEqualTo(
			"ContentType{" +
				"mediaType=MediaType{type=application, subtype=json}, " +
				"parameters={charset=Parameter{name=charset, value=utf-8}}" +
			"}"
		);
	}

	@Test
	public void it_should_create_content_type_without_charset() {
		ContentType contentType = contentType(application("json"));
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");

		assertThat(contentType.getParameters()).isEmpty();
		assertThat(contentType.getParameter("charset")).isNull();
		assertThat(contentType.getCharset()).isNull();

		assertThat(contentType.serializeValue()).isEqualTo("application/json");
		assertThat(contentType.toString()).isEqualTo(
			"ContentType{" +
				"mediaType=MediaType{type=application, subtype=json}, " +
				"parameters={}" +
			"}"
		);
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(ContentType.class).verify();
	}

	@Test
	public void it_should_fail_if_media_type_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Media Type must not be null");

		new ContentType(null, Collections.<String, Parameter>emptyMap());
	}
}
