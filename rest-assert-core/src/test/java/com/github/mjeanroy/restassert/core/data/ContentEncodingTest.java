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

import com.github.mjeanroy.restassert.core.data.ContentEncoding.Directive;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentEncodingTest {

	@Test
	public void it_should_create_gzip_content_encoding() {
		ContentEncoding contentEncoding = ContentEncoding.gzip();
		assertThat(contentEncoding.getDirectives()).isEqualTo(singletonList(Directive.GZIP));
		assertThat(contentEncoding.serializeValue()).isEqualTo("gzip");
		assertThat(contentEncoding.toString()).isEqualTo(
			"ContentEncoding{" +
				"directives=[GZIP]" +
			"}"
		);
	}

	@Test
	public void it_should_create_deflate_content_encoding() {
		ContentEncoding contentEncoding = ContentEncoding.deflate();
		assertThat(contentEncoding.getDirectives()).isEqualTo(singletonList(Directive.DEFLATE));
		assertThat(contentEncoding.serializeValue()).isEqualTo("deflate");
		assertThat(contentEncoding.toString()).isEqualTo(
			"ContentEncoding{" +
				"directives=[DEFLATE]" +
			"}"
		);
	}

	@Test
	public void it_should_create_br_content_encoding() {
		ContentEncoding contentEncoding = ContentEncoding.br();
		assertThat(contentEncoding.getDirectives()).isEqualTo(singletonList(Directive.BR));
		assertThat(contentEncoding.serializeValue()).isEqualTo("br");
		assertThat(contentEncoding.toString()).isEqualTo(
			"ContentEncoding{" +
				"directives=[BR]" +
			"}"
		);
	}

	@Test
	public void it_should_create_content_encoding_with_several_transformations() {
		ContentEncoding contentEncoding = new ContentEncoding(asList(
			Directive.COMPRESS,
			Directive.IDENTITY
		));

		assertThat(contentEncoding.getDirectives()).hasSize(2).containsExactly(Directive.COMPRESS, Directive.IDENTITY);
		assertThat(contentEncoding.serializeValue()).isEqualTo("compress, identity");
		assertThat(contentEncoding.toString()).isEqualTo(
			"ContentEncoding{" +
				"directives=[COMPRESS, IDENTITY]" +
			"}"
		);
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(ContentEncoding.class).verify();
	}
}
