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

import com.github.mjeanroy.restassert.core.data.CacheControl.Visibility;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheControlBuilderTest {

	@Test
	public void it_should_create_no_cache_header() {
		CacheControl expected = CacheControl.builder()
			.noCache()
			.build();

		assertThat(expected.getVisibility()).isNull();
		assertThat(expected.isNoStore()).isFalse();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isFalse();
		assertThat(expected.isMustRevalidate()).isFalse();
		assertThat(expected.isProxyRevalidate()).isFalse();
		assertThat(expected.getMaxAge()).isNull();
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("no-cache");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=null, " +
				"noCache=true, " +
				"noStore=false, " +
				"noTransform=false, " +
				"mustRevalidate=false, " +
				"proxyRevalidate=false, " +
				"maxAge=null, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_no_store_header() {
		CacheControl expected = CacheControl.builder()
			.noCache()
			.noStore()
			.build();

		assertThat(expected.getVisibility()).isNull();
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isFalse();
		assertThat(expected.isMustRevalidate()).isFalse();
		assertThat(expected.isProxyRevalidate()).isFalse();
		assertThat(expected.getMaxAge()).isNull();
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("no-cache, no-store");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=null, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=false, " +
				"mustRevalidate=false, " +
				"proxyRevalidate=false, " +
				"maxAge=null, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_public_with_max_age_header() {
		CacheControl expected = CacheControl.builder()
			.noCache()
			.noStore()
			.maxAge(3600)
			.build();

		assertThat(expected.getVisibility()).isNull();
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isFalse();
		assertThat(expected.isMustRevalidate()).isFalse();
		assertThat(expected.isProxyRevalidate()).isFalse();
		assertThat(expected.getMaxAge()).isEqualTo(3600L);
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("no-cache, no-store, max-age=3600");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=null, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=false, " +
				"mustRevalidate=false, " +
				"proxyRevalidate=false, " +
				"maxAge=3600, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_private_with_max_age_header() {
		CacheControl expected = CacheControl.builder()
			.visibility(Visibility.PRIVATE)
			.noCache()
			.noStore()
			.maxAge(3600)
			.build();

		assertThat(expected.getVisibility()).isEqualTo(Visibility.PRIVATE);
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isFalse();
		assertThat(expected.isMustRevalidate()).isFalse();
		assertThat(expected.isProxyRevalidate()).isFalse();
		assertThat(expected.getMaxAge()).isEqualTo(3600L);
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("private, no-cache, no-store, max-age=3600");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=PRIVATE, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=false, " +
				"mustRevalidate=false, " +
				"proxyRevalidate=false, " +
				"maxAge=3600, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_private_with_must_revalidate_header() {
		CacheControl expected = CacheControl.builder()
			.visibility(Visibility.PRIVATE)
			.noCache()
			.noStore()
			.mustRevalidate()
			.maxAge(3600)
			.build();

		assertThat(expected.getVisibility()).isEqualTo(Visibility.PRIVATE);
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isFalse();
		assertThat(expected.isMustRevalidate()).isTrue();
		assertThat(expected.isProxyRevalidate()).isFalse();
		assertThat(expected.getMaxAge()).isEqualTo(3600L);
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("private, no-cache, no-store, must-revalidate, max-age=3600");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=PRIVATE, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=false, " +
				"mustRevalidate=true, " +
				"proxyRevalidate=false, " +
				"maxAge=3600, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_private_with_proxy_revalidate_header() {
		CacheControl expected = CacheControl.builder()
			.visibility(Visibility.PRIVATE)
			.noCache()
			.noStore()
			.mustRevalidate()
			.proxyRevalidate()
			.maxAge(3600)
			.build();

		assertThat(expected.getVisibility()).isEqualTo(Visibility.PRIVATE);
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isFalse();
		assertThat(expected.isMustRevalidate()).isTrue();
		assertThat(expected.isProxyRevalidate()).isTrue();
		assertThat(expected.getMaxAge()).isEqualTo(3600L);
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("private, no-cache, no-store, must-revalidate, proxy-revalidate, max-age=3600");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=PRIVATE, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=false, " +
				"mustRevalidate=true, " +
				"proxyRevalidate=true, " +
				"maxAge=3600, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_private_with_no_transform_header() {
		CacheControl expected = CacheControl.builder()
			.visibility(Visibility.PRIVATE)
			.noCache()
			.noStore()
			.noTransform()
			.mustRevalidate()
			.proxyRevalidate()
			.maxAge(3600)
			.build();

		assertThat(expected.getVisibility()).isEqualTo(Visibility.PRIVATE);
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isTrue();
		assertThat(expected.isMustRevalidate()).isTrue();
		assertThat(expected.isProxyRevalidate()).isTrue();
		assertThat(expected.getMaxAge()).isEqualTo(3600L);
		assertThat(expected.getSMaxAge()).isNull();
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("private, no-cache, no-store, no-transform, must-revalidate, proxy-revalidate, max-age=3600");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=PRIVATE, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=true, " +
				"mustRevalidate=true, " +
				"proxyRevalidate=true, " +
				"maxAge=3600, " +
				"sMaxAge=null, " +
				"immutable=false" +
			"}"
		);
	}

	@Test
	public void it_should_create_private_with_s_max_age_header() {
		CacheControl expected = CacheControl.builder()
			.visibility(Visibility.PRIVATE)
			.noCache()
			.noStore()
			.noTransform()
			.mustRevalidate()
			.proxyRevalidate()
			.maxAge(3600)
			.sMaxAge(1000)
			.build();

		assertThat(expected.getVisibility()).isEqualTo(Visibility.PRIVATE);
		assertThat(expected.isNoStore()).isTrue();
		assertThat(expected.isNoCache()).isTrue();
		assertThat(expected.isNoTransform()).isTrue();
		assertThat(expected.isMustRevalidate()).isTrue();
		assertThat(expected.isProxyRevalidate()).isTrue();
		assertThat(expected.getMaxAge()).isEqualTo(3600L);
		assertThat(expected.getSMaxAge()).isEqualTo(1000L);
		assertThat(expected.isImmutable()).isFalse();
		assertThat(expected.serializeValue()).isEqualTo("private, no-cache, no-store, no-transform, must-revalidate, proxy-revalidate, max-age=3600, s-maxage=1000");
		assertThat(expected.toString()).isEqualTo(
			"CacheControl{" +
				"visibility=PRIVATE, " +
				"noCache=true, " +
				"noStore=true, " +
				"noTransform=true, " +
				"mustRevalidate=true, " +
				"proxyRevalidate=true, " +
				"maxAge=3600, " +
				"sMaxAge=1000, " +
				"immutable=false" +
			"}"
		);
	}
}
