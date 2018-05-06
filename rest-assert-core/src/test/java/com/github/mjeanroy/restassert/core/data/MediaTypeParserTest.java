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

public class MediaTypeParserTest {

	@Test
	public void it_should_parse_media_type() {
		MediaTypeParser parser = MediaType.parser();
		MediaType mediaType = parser.parse("text/html");
		assertThat(mediaType.getType()).isEqualTo("text");
		assertThat(mediaType.getSubtype()).isEqualTo("html");
		assertThat(mediaType.serializeValue()).isEqualTo("text/html");
	}

	@Test
	public void it_should_parse_uppercase_media_type() {
		MediaTypeParser parser = MediaType.parser();
		MediaType mediaType = parser.parse("APPLICATION/JSON");
		assertThat(mediaType.getType()).isEqualTo("application");
		assertThat(mediaType.getSubtype()).isEqualTo("json");
		assertThat(mediaType.serializeValue()).isEqualTo("application/json");
	}

	@Test
	public void it_should_parse_media_type_with_space() {
		MediaTypeParser parser = MediaType.parser();
		MediaType mediaType = parser.parse(" application / xml ");
		assertThat(mediaType.getType()).isEqualTo("application");
		assertThat(mediaType.getSubtype()).isEqualTo("xml");
		assertThat(mediaType.serializeValue()).isEqualTo("application/xml");
	}
}
