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

/**
 * Build {@link CacheControl} instance.
 */
public class CacheControlBuilder {

	/**
	 * Visibility directive (i.e {@code public} or {@code private}.
	 */
	private Visibility visibility;

	/**
	 * Flag for {@code no-store} directive.
	 */
	private boolean noStore;

	/**
	 * Flag for {@code no-cache} directive.
	 */
	private boolean noCache;

	/**
	 * Flag for {@code no-transform} directive.
	 */
	private boolean noTransform;

	/**
	 * Flag for {@code must-revalidate} directive.
	 */
	private boolean mustRevalidate;

	/**
	 * Flag for {@code proxy-revalidate} directive.
	 */
	private boolean proxyRevalidate;

	/**
	 * Flag for {@code max-age} directive.
	 */
	private Long maxAge;

	/**
	 * Flag for {@code s-maxage} directive.
	 */
	private Long sMaxAge;

	/**
	 * Flag for {@code immutable} directive.
	 */
	private boolean immutable;

	/**
	 * Create the builder with default values initialized.
	 */
	CacheControlBuilder() {
		this.noCache = false;
		this.noStore = false;
		this.noTransform = false;
		this.mustRevalidate = false;
		this.proxyRevalidate = false;
		this.immutable = false;
	}

	/**
	 * Update {@code "public"} or {@code "private"} directive.
	 *
	 * @param visibility New visibility.
	 * @return Current builder.
	 */
	public CacheControlBuilder visibility(Visibility visibility) {
		this.visibility = visibility;
		return this;
	}

	/**
	 * Enable {@code "no-cache"} directive.
	 *
	 * @return Current builder.
	 */
	public CacheControlBuilder noCache() {
		this.noCache = true;
		return this;
	}

	/**
	 * Enable {@code "no-transform"} directive.
	 *
	 * @return Current builder.
	 */
	public CacheControlBuilder noTransform() {
		this.noTransform = true;
		return this;
	}

	/**
	 * Enable {@code "must-revalidate"} directive.
	 *
	 * @return Current builder.
	 */
	public CacheControlBuilder mustRevalidate() {
		this.mustRevalidate = true;
		return this;
	}

	/**
	 * Enable {@code "proxy-revalidate"} directive.
	 *
	 * @return Current builder.
	 */
	public CacheControlBuilder proxyRevalidate() {
		this.proxyRevalidate = true;
		return this;
	}

	/**
	 * Enable {@code "no-store"} directive.
	 *
	 * @return Current builder.
	 */
	public CacheControlBuilder noStore() {
		this.noStore = true;
		return this;
	}

	/**
	 * Update {@code "max-age"} directive.
	 *
	 * @param maxAge Enable {@code "max-age"} directive and update its value.
	 * @return Current builder.
	 */
	public CacheControlBuilder maxAge(long maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Update {@code s-maxage} directive.
	 *
	 * @param sMaxAge Enable {@code s-maxage} directive and update its value.
	 * @return Current builder.
	 */
	public CacheControlBuilder sMaxAge(long sMaxAge) {
		this.sMaxAge = sMaxAge;
		return this;
	}

	/**
	 * Enable {@code "immutable"} directive.
	 *
	 * @return Current builder.
	 */
	public CacheControlBuilder immutable() {
		this.immutable = true;
		return this;
	}

	/**
	 * Create new {@link CacheControl} value object.
	 *
	 * @return Cache-Control value.
	 */
	public CacheControl build() {
		return new CacheControl(
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
	}
}
