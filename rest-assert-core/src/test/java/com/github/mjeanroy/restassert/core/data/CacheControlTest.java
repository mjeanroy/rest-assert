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
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheControlTest {

	@Test
	public void it_should_create_no_cache_header() {
		Visibility visibility = null;
		boolean noStore = false;
		boolean noCache = true;
		boolean noTransform = false;
		boolean mustRevalidate = false;
		boolean proxyRevalidate = false;
		Long maxAge = null;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = null;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = false;
		boolean mustRevalidate = false;
		boolean proxyRevalidate = false;
		Long maxAge = null;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = null;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = false;
		boolean mustRevalidate = false;
		boolean proxyRevalidate = false;
		Long maxAge = 3600L;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = Visibility.PRIVATE;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = false;
		boolean mustRevalidate = false;
		boolean proxyRevalidate = false;
		Long maxAge = 3600L;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = Visibility.PRIVATE;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = false;
		boolean mustRevalidate = true;
		boolean proxyRevalidate = false;
		Long maxAge = 3600L;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = Visibility.PRIVATE;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = false;
		boolean mustRevalidate = true;
		boolean proxyRevalidate = true;
		Long maxAge = 3600L;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = Visibility.PRIVATE;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = true;
		boolean mustRevalidate = true;
		boolean proxyRevalidate = true;
		Long maxAge = 3600L;
		Long sMaxAge = null;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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
		Visibility visibility = Visibility.PRIVATE;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = true;
		boolean mustRevalidate = true;
		boolean proxyRevalidate = true;
		Long maxAge = 3600L;
		Long sMaxAge = 1000L;
		boolean immutable = false;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
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

	@Test
	public void it_should_create_immutable_header() {
		Visibility visibility = Visibility.PRIVATE;
		boolean noStore = true;
		boolean noCache = true;
		boolean noTransform = true;
		boolean mustRevalidate = true;
		boolean proxyRevalidate = true;
		Long maxAge = 3600L;
		Long sMaxAge = 1000L;
		boolean immutable = true;
		CacheControl expected = new CacheControl(
			visibility,
			noStore,
			noCache,
			noTransform,
			mustRevalidate,
			proxyRevalidate,
			maxAge,
			sMaxAge,
			immutable
		);

		assertThat(expected.getVisibility()).isEqualTo(visibility);
		assertThat(expected.isNoStore()).isEqualTo(noStore);
		assertThat(expected.isNoCache()).isEqualTo(noCache);
		assertThat(expected.isNoTransform()).isEqualTo(noTransform);
		assertThat(expected.isMustRevalidate()).isEqualTo(mustRevalidate);
		assertThat(expected.isProxyRevalidate()).isEqualTo(proxyRevalidate);
		assertThat(expected.getMaxAge()).isEqualTo(maxAge);
		assertThat(expected.getSMaxAge()).isEqualTo(sMaxAge);
		assertThat(expected.isImmutable()).isEqualTo(immutable);
		assertThat(expected.serializeValue()).isEqualTo("private, no-cache, no-store, no-transform, must-revalidate, proxy-revalidate, max-age=3600, s-maxage=1000, immutable");
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
				"immutable=true" +
			"}"
		);
	}

	@Test
	public void it_should_implement_equals() {
		EqualsVerifier.forClass(CacheControl.class).verify();
	}
}
