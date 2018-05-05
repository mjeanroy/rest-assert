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

public class FrameOptionsParserTest {

	@Test
	public void it_should_match_deny() {
		FrameOptions header = FrameOptions.parser().parse("deny");
		assertThat(header.getDirective()).isEqualTo(FrameOptions.Directive.DENY);
		assertThat(header.getOptions()).isEmpty();
	}

	@Test
	public void it_should_match_deny_case_insensitive() {
		FrameOptions header = FrameOptions.parser().parse("DENY");
		assertThat(header.getDirective()).isEqualTo(FrameOptions.Directive.DENY);
		assertThat(header.getOptions()).isEmpty();
	}

	@Test
	public void it_should_match_same_origin() {
		FrameOptions header = FrameOptions.parser().parse("sameorigin");
		assertThat(header.getDirective()).isEqualTo(FrameOptions.Directive.SAME_ORIGIN);
		assertThat(header.getOptions()).isEmpty();
	}

	@Test
	public void it_should_match_same_origin_case_insensitive() {
		FrameOptions header = FrameOptions.parser().parse("SAMEORIGIN");
		assertThat(header.getDirective()).isEqualTo(FrameOptions.Directive.SAME_ORIGIN);
		assertThat(header.getOptions()).isEmpty();
	}

	@Test
	public void it_should_match_allow_from() {
		String uri = "https://example.com";
		FrameOptions header = FrameOptions.parser().parse("allow-from " + uri);
		assertThat(header.getDirective()).isEqualTo(FrameOptions.Directive.ALLOW_FROM);
		assertThat(header.getOptions()).hasSize(1).containsOnly("https://example.com");
	}

	@Test
	public void it_should_match_allow_from_case_insensitive() {
		String uri = "https://example.com";
		FrameOptions header = FrameOptions.parser().parse("ALLOW-FROM " + uri);
		assertThat(header.getDirective()).isEqualTo(FrameOptions.Directive.ALLOW_FROM);
		assertThat(header.getOptions()).hasSize(1).containsOnly("https://example.com");
	}
}
