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
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class FrameOptionsTest {

	@Test
	public void it_should_create_deny_header() {
		FrameOptions frameOptions = FrameOptions.deny();

		assertThat(frameOptions.getDirective()).isEqualTo(FrameOptions.Directive.DENY);
		assertThat(frameOptions.getOptions()).isEmpty();
		assertThat(frameOptions.serializeValue()).isEqualTo("DENY");
		assertThat(frameOptions.toString()).isEqualTo(
			"FrameOptions{" +
				"directive=DENY, " +
				"options=[]" +
			"}"
		);
	}

	@Test
	public void it_should_create_same_origin_header() {
		FrameOptions frameOptions = FrameOptions.sameOrigin();

		assertThat(frameOptions.getDirective()).isEqualTo(FrameOptions.Directive.SAME_ORIGIN);
		assertThat(frameOptions.getOptions()).isEmpty();
		assertThat(frameOptions.serializeValue()).isEqualTo("SAMEORIGIN");
		assertThat(frameOptions.toString()).isEqualTo(
			"FrameOptions{" +
				"directive=SAME_ORIGIN, " +
				"options=[]" +
				"}"
		);
	}

	@Test
	public void it_should_create_allow_from_header() {
		String uri = "https://example.com";
		FrameOptions frameOptions = FrameOptions.allowFrom(uri);

		assertThat(frameOptions.getDirective()).isEqualTo(FrameOptions.Directive.ALLOW_FROM);
		assertThat(frameOptions.getOptions()).containsOnly(uri);
		assertThat(frameOptions.serializeValue()).isEqualTo("ALLOW-FROM " + uri);
		assertThat(frameOptions.toString()).isEqualTo(
			"FrameOptions{" +
				"directive=ALLOW_FROM, " +
				"options=[https://example.com]" +
				"}"
		);
	}

	@Test
	public void it_should_create_allow_from_header_using_uri_object() {
		URI uri = URI.create("https://example.com");
		FrameOptions frameOptions = FrameOptions.allowFrom(uri);

		assertThat(frameOptions.getDirective()).isEqualTo(FrameOptions.Directive.ALLOW_FROM);
		assertThat(frameOptions.getOptions()).containsOnly(uri.toString());
		assertThat(frameOptions.serializeValue()).isEqualTo("ALLOW-FROM " + uri);
		assertThat(frameOptions.toString()).isEqualTo(
			"FrameOptions{" +
				"directive=ALLOW_FROM, " +
				"options=[https://example.com]" +
			"}"
		);
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(FrameOptions.class).verify();
	}
}
