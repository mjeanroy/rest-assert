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

public class CacheControlParserTest {

	@Test
	public void it_should_parse_no_cache_header() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("no-cache");

		assertThat(cacheControl.getVisibility()).isNull();
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isTrue();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_create_no_store_header() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("no-store");

		assertThat(cacheControl.getVisibility()).isNull();
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isTrue();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_create_public_with_max_age_header() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("public, max-age=0");

		assertThat(cacheControl.getVisibility()).isEqualTo(CacheControl.Visibility.PUBLIC);
		assertThat(cacheControl.getMaxAge()).isZero();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_create_private_with_max_age_header() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("private, max-age=0");

		assertThat(cacheControl.getVisibility()).isEqualTo(CacheControl.Visibility.PRIVATE);
		assertThat(cacheControl.getMaxAge()).isZero();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_parse_no_cache_and_no_store() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("no-cache, no-store");

		assertThat(cacheControl.getVisibility()).isNull();
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isTrue();
		assertThat(cacheControl.isNoStore()).isTrue();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_parse_proxy_revalidate_directive() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("proxy-revalidate");

		assertThat(cacheControl.getVisibility()).isNull();
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isTrue();
	}

	@Test
	public void it_should_parse_must_revalidate_directive() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("must-revalidate");

		assertThat(cacheControl.getVisibility()).isNull();
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isTrue();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_parse_no_transform_directive() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("no-transform");

		assertThat(cacheControl.getVisibility()).isNull();
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isNull();
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isTrue();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}

	@Test
	public void it_should_parse_s_maxage_directive() {
		CacheControlParser parser = CacheControl.parser();
		CacheControl cacheControl = parser.parse("public, s-maxage=3600");

		assertThat(cacheControl.getVisibility()).isEqualTo(CacheControl.Visibility.PUBLIC);
		assertThat(cacheControl.getMaxAge()).isNull();
		assertThat(cacheControl.getSMaxAge()).isEqualTo(3600L);
		assertThat(cacheControl.isNoCache()).isFalse();
		assertThat(cacheControl.isNoStore()).isFalse();
		assertThat(cacheControl.isMustRevalidate()).isFalse();
		assertThat(cacheControl.isNoTransform()).isFalse();
		assertThat(cacheControl.isProxyRevalidate()).isFalse();
	}
}
