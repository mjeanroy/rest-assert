/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheControlTest {

	@Test
	public void it_should_create_no_cache_header() {
		CacheControl cacheControl = new CacheControl.Builder()
			.noCache()
			.build();

		assertThat(cacheControl.toString()).isEqualTo("no-cache");
		assertThat(cacheControl.value()).isEqualTo("no-cache");
		assertThat(cacheControl.match("no-cache")).isTrue();
	}

	@Test
	public void it_should_create_no_store_header() {
		CacheControl cacheControl = new CacheControl.Builder()
			.noStore()
			.build();

		assertThat(cacheControl.toString()).isEqualTo("no-store");
		assertThat(cacheControl.value()).isEqualTo("no-store");
		assertThat(cacheControl.match("no-store")).isTrue();
	}

	@Test
	public void it_should_create_public_with_max_age_header() {
		CacheControl cacheControl = new CacheControl.Builder()
			.visibility(CacheControl.Visibility.PUBLIC)
			.maxAge(0)
			.build();

		assertThat(cacheControl.toString()).isEqualTo("public, max-age=0");
		assertThat(cacheControl.value()).isEqualTo("public, max-age=0");
		assertThat(cacheControl.match("public, max-age=0")).isTrue();
	}

	@Test
	public void it_should_create_private_with_max_age_header() {
		CacheControl cacheControl = new CacheControl.Builder()
			.visibility(CacheControl.Visibility.PRIVATE)
			.maxAge(3600)
			.build();

		assertThat(cacheControl.toString()).isEqualTo("private, max-age=3600");
		assertThat(cacheControl.value()).isEqualTo("private, max-age=3600");
		assertThat(cacheControl.match("private, max-age=3600")).isTrue();
	}

	@Test
	public void it_should_match_header_value_even_in_the_wrong_order() {
		CacheControl cacheControl = new CacheControl.Builder()
			.noCache()
			.noStore()
			.build();

		assertThat(cacheControl.value()).isEqualTo("no-cache, no-store");
		assertThat(cacheControl.match("no-store, no-cache")).isTrue();
	}

	@Test
	public void it_should_implement_equals() {
		CacheControl c1 = new CacheControl.Builder().build();
		CacheControl c2 = new CacheControl.Builder().build();
		CacheControl c3 = new CacheControl.Builder().build();
		CacheControl c4 = new CacheControl.Builder()
			.maxAge(0)
			.build();

		assertThat(c1.equals(c2)).isTrue();
		assertThat(c1.equals(c4)).isFalse();
		assertThat(c4.equals(c1)).isFalse();
		assertThat(c1.equals(null)).isFalse();

		// Reflective
		assertThat(c1.equals(c1)).isTrue();

		// Symmetric
		assertThat(c1.equals(c2)).isTrue();
		assertThat(c2.equals(c1)).isTrue();

		// Transitive
		assertThat(c1.equals(c2)).isTrue();
		assertThat(c2.equals(c3)).isTrue();
		assertThat(c1.equals(c3)).isTrue();
	}

	@Test
	public void it_should_implement_hash_code() {
		CacheControl c1 = new CacheControl.Builder().build();
		CacheControl c2 = new CacheControl.Builder().build();
		assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
	}

	@Test
	public void it_should_parse_no_cache_directive() {
		CacheControl c = new CacheControl.Builder()
			.noCache()
			.build();

		assertThat(c.value()).isEqualTo("no-cache");
		assertThat(c.match("no-cache")).isTrue();
	}

	@Test
	public void it_should_parse_no_store_directive() {
		CacheControl c1 = new CacheControl.Builder()
			.noStore()
			.build();

		assertThat(c1.value()).isEqualTo("no-store");
		assertThat(c1.match("no-store")).isTrue();
	}

	@Test
	public void it_should_parse_proxy_revalidate_directive() {
		CacheControl c1 = new CacheControl.Builder()
			.proxyRevalidate()
			.build();

		assertThat(c1.value()).isEqualTo("proxy-revalidate");
		assertThat(c1.match("proxy-revalidate")).isTrue();
	}

	@Test
	public void it_should_parse_must_revalidate_directive() {
		CacheControl c1 = new CacheControl.Builder()
			.mustRevalidate()
			.build();

		assertThat(c1.value()).isEqualTo("must-revalidate");
		assertThat(c1.match("must-revalidate")).isTrue();
	}

	@Test
	public void it_should_parse_no_transform_directive() {
		CacheControl c1 = new CacheControl.Builder()
			.noTransform()
			.build();

		assertThat(c1.value()).isEqualTo("no-transform");
		assertThat(c1.match("no-transform")).isTrue();
	}

	@Test
	public void it_should_parse_s_maxage_directive() {
		CacheControl c1 = new CacheControl.Builder()
			.sMaxAge(3600)
			.visibility(CacheControl.Visibility.PUBLIC)
			.build();

		assertThat(c1.value()).isEqualTo("public, s-maxage=3600");
		assertThat(c1.match("public, s-maxage=3600")).isTrue();
	}
}
