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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ContentTypeParserTest {

	@Test
	public void it_should_parse_content_type_without_charsets() {
		ContentTypeParser parser = ContentType.parser();
		ContentType contentType = parser.parse("application/json");
		assertThat(contentType).isNotNull();
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");
		assertThat(contentType.getParameters()).isEmpty();
		assertThat(contentType.serializeValue()).isEqualTo("application/json");
	}

	@Test
	public void it_should_create_content_type_with_charset() {
		ContentTypeParser parser = ContentType.parser();
		ContentType contentType = parser.parse("application/json; charset=utf-8");
		assertThat(contentType).isNotNull();
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");

		assertThat(contentType.getParameters())
			.hasSize(1)
			.extracting("name", "value")
			.containsOnly(tuple("charset", "utf-8"));

		assertThat(contentType.serializeValue()).isEqualTo("application/json; charset=utf-8");
	}

	@Test
	public void it_should_create_content_type_with_single_quoted_charset() {
		ContentTypeParser parser = ContentType.parser();
		ContentType contentType = parser.parse("application/json; charset='utf-8'");
		assertThat(contentType).isNotNull();
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");

		assertThat(contentType.getParameters())
			.hasSize(1)
			.extracting("name", "value")
			.containsOnly(tuple("charset", "utf-8"));

		assertThat(contentType.serializeValue()).isEqualTo("application/json; charset=utf-8");
	}

	@Test
	public void it_should_create_content_type_with_double_quoted_charset() {
		ContentTypeParser parser = ContentType.parser();
		ContentType contentType = parser.parse("application/json; charset=\"utf-8\"");
		assertThat(contentType).isNotNull();
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");

		assertThat(contentType.getParameters()).extracting("name", "value").containsOnly(tuple("charset", "utf-8"));
		assertThat(contentType.getParameter("charset").getValue()).isEqualTo("utf-8");
		assertThat(contentType.getCharset()).isEqualTo("utf-8");

		assertThat(contentType.serializeValue()).isEqualTo("application/json; charset=utf-8");
	}

	@Test
	public void it_should_create_content_type_with_more_than_one_parameter() {
		ContentTypeParser parser = ContentType.parser();
		ContentType contentType = parser.parse("application/json; foo=bar; charset=\"utf-8\"");
		assertThat(contentType).isNotNull();
		assertThat(contentType.getMediaType().getType()).isEqualTo("application");
		assertThat(contentType.getMediaType().getSubtype()).isEqualTo("json");

		assertThat(contentType.getParameters()).extracting("name", "value").containsOnly(tuple("foo", "bar"), tuple("charset", "utf-8"));
		assertThat(contentType.getParameter("foo").getValue()).isEqualTo("bar");
		assertThat(contentType.getParameter("charset").getValue()).isEqualTo("utf-8");
		assertThat(contentType.getCharset()).isEqualTo("utf-8");

		assertThat(contentType.serializeValue()).isEqualTo("application/json; foo=bar; charset=utf-8");
	}
}
