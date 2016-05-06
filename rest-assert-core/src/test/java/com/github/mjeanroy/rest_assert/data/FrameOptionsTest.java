/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.data;

import com.github.mjeanroy.rest_assert.data.FrameOptions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FrameOptionsTest {

	@Test
	public void it_should_match_deny() {
		assertThat(FrameOptions.DENY.match("deny")).isTrue();
		assertThat(FrameOptions.DENY.match("DENY")).isTrue();

		assertThat(FrameOptions.DENY.match("sameorigin")).isFalse();
		assertThat(FrameOptions.DENY.match("SAMEORIGIN")).isFalse();
		assertThat(FrameOptions.DENY.match("allow-from domain.com")).isFalse();
		assertThat(FrameOptions.DENY.match("ALLOW-FROM domain.com")).isFalse();
	}

	@Test
	public void it_should_match_same_origin() {
		assertThat(FrameOptions.SAME_ORIGIN.match("sameorigin")).isTrue();
		assertThat(FrameOptions.SAME_ORIGIN.match("SAMEORIGIN")).isTrue();

		assertThat(FrameOptions.SAME_ORIGIN.match("deny")).isFalse();
		assertThat(FrameOptions.SAME_ORIGIN.match("DENY")).isFalse();
		assertThat(FrameOptions.SAME_ORIGIN.match("allow-from domain.com")).isFalse();
		assertThat(FrameOptions.SAME_ORIGIN.match("ALLOW-FROM domain.com")).isFalse();
	}

	@Test
	public void it_should_match_allow_from() {
		assertThat(FrameOptions.ALLOW_FROM.match("allow-from domain.com")).isTrue();
		assertThat(FrameOptions.ALLOW_FROM.match("ALLOW-FROM domain.com")).isTrue();

		assertThat(FrameOptions.ALLOW_FROM.match("deny")).isFalse();
		assertThat(FrameOptions.ALLOW_FROM.match("DENY")).isFalse();
		assertThat(FrameOptions.ALLOW_FROM.match("sameorigin")).isFalse();
		assertThat(FrameOptions.ALLOW_FROM.match("SAMEORIGIN")).isFalse();
	}
}
