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

import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.AbstractSourceValue;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Sandbox;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceValue;
import com.github.mjeanroy.restassert.core.internal.common.Collections;
import com.github.mjeanroy.restassert.core.internal.common.PreConditions;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.HOST_NAME_REGEX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.HOST_PATH_REGEX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.HOST_PORT_REGEX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SCHEME_REGEX;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.map;
import static java.util.Arrays.asList;

/**
 * Builder used to create {@link ContentSecurityPolicy} instances.
 */
public class ContentSecurityPolicyBuilder {
	/**
	 * List of value directives.
	 */
	private final Map<SourceDirective, Set<Source>> sources;

	/**
	 * Pattern used to validate host value.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#host_source">https://www.w3.org/TR/CSP/#host_source</a>
	 */
	private static final Pattern PATTERN_HOST_VALUE = Pattern.compile(
		"^" +
			"(" + SCHEME_REGEX + "://)?" +
			"(" + HOST_NAME_REGEX + ")" +
			"(:" + HOST_PORT_REGEX + ")?" +
			"(" + HOST_PATH_REGEX + ")?" +
			"$",

		Pattern.CASE_INSENSITIVE
	);

	/**
	 * Create builder.
	 */
	ContentSecurityPolicyBuilder() {
		this.sources = new LinkedHashMap<>();
	}

	/**
	 * Add values for {@code base-uri} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-base-uri">https://www.w3.org/TR/CSP/#directive-base-uri</a>
	 */
	public ContentSecurityPolicyBuilder addBaseUri(Source src, Source... other) {
		return add(SourceDirective.BASE_URI, src, asList(other));
	}

	/**
	 * Add values for {@code default-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-default-src">https://www.w3.org/TR/CSP/#directive-default-src</a>
	 */
	public ContentSecurityPolicyBuilder addDefaultSrc(Source src, Source... other) {
		return add(SourceDirective.DEFAULT_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code script-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-script-src">https://www.w3.org/TR/CSP/#directive-script-src</a>
	 */
	public ContentSecurityPolicyBuilder addScriptSrc(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code style-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-style-src">https://www.w3.org/TR/CSP/#directive-style-src</a>
	 */
	public ContentSecurityPolicyBuilder addStyleSrc(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code font-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-font-src">https://www.w3.org/TR/CSP/#directive-font-src</a>
	 */
	public ContentSecurityPolicyBuilder addFontSrc(Source src, Source... other) {
		return add(SourceDirective.FONT_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code object-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-object-src">https://www.w3.org/TR/CSP/#directive-object-src</a>
	 */
	public ContentSecurityPolicyBuilder addObjectSrc(Source src, Source... other) {
		return add(SourceDirective.OBJECT_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code media-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-media-src">https://www.w3.org/TR/CSP/#directive-media-src</a>
	 */
	public ContentSecurityPolicyBuilder addMediaSrc(Source src, Source... other) {
		return add(SourceDirective.MEDIA_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code img-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-img-src">https://www.w3.org/TR/CSP/#directive-img-src</a>
	 */
	public ContentSecurityPolicyBuilder addImgSrc(Source src, Source... other) {
		return add(SourceDirective.IMG_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code connect-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-connect-src">https://www.w3.org/TR/CSP/#directive-connect-src</a>
	 */
	public ContentSecurityPolicyBuilder addConnectSrc(Source src, Source... other) {
		return add(SourceDirective.CONNECT_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code child-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-child-src">https://www.w3.org/TR/CSP/#directive-child-src</a>
	 */
	public ContentSecurityPolicyBuilder addChildSrc(Source src, Source... other) {
		return add(SourceDirective.CHILD_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code form-actions} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-form-action">https://www.w3.org/TR/CSP/#directive-form-action</a>
	 */
	public ContentSecurityPolicyBuilder addFormAction(Source src, Source... other) {
		return add(SourceDirective.FORM_ACTION, src, asList(other));
	}

	/**
	 * Add values for {@code frame-ancestors} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-frame-ancestors">https://www.w3.org/TR/CSP/#directive-frame-ancestors</a>
	 */
	public ContentSecurityPolicyBuilder addFrameAncestors(Source src, Source... other) {
		return add(SourceDirective.FRAME_ANCESTORS, src, asList(other), new SourceValidator() {
			@Override
			public void validate(Source src) {
				PreConditions.match(src.getValue(), PATTERN_HOST_VALUE, "Source must be a valid host value");
			}
		});
	}

	/**
	 * Add values for {@code frame-ancestors} directive.
	 *
	 * @param mediaType Media-type value.
	 * @param other Optional other media-types values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-plugin-types">https://www.w3.org/TR/CSP/#directive-plugin-types</a>
	 */
	public ContentSecurityPolicyBuilder addPluginTypes(String mediaType, String... other) {
		Source src = new SourceValue(mediaType);
		List<Source> otherSources = map(other, new Collections.Mapper<String, Source>() {
			@Override
			public Source apply(String input) {
				return new SourceValue(input);
			}
		});

		return add(SourceDirective.PLUGIN_TYPES, src, otherSources);
	}

	/**
	 * Add values for {@code report-uri} directive.
	 *
	 * @param uri Uri value.
	 * @param other Optional other uri values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @throws IllegalArgumentException If at least one uri is not a valid uri value.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-report-uri">https://www.w3.org/TR/CSP/#directive-report-uri</a>
	 */
	public ContentSecurityPolicyBuilder addReportUri(String uri, String... other) {
		Source src = new UriSource(uri);
		List<Source> otherSources = map(other, new Collections.Mapper<String, Source>() {
			@Override
			public Source apply(String input) {
				return new UriSource(input);
			}
		});

		return add(SourceDirective.REPORT_URI, src, otherSources);
	}

	/**
	 * Add values for {@code report-uri} directive.
	 *
	 * @param uri Uri value.
	 * @param other Optional other uri values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-report-uri">https://www.w3.org/TR/CSP/#directive-report-uri</a>
	 */
	public ContentSecurityPolicyBuilder addReportUri(URI uri, URI... other) {
		Source src = new UriSource(uri);
		List<Source> otherSources = map(other, new Collections.Mapper<URI, Source>() {
			@Override
			public Source apply(URI input) {
				return new UriSource(input);
			}
		});

		return add(SourceDirective.REPORT_URI, src, otherSources);
	}

	/**
	 * Add values for {@code sandbox} directive.
	 *
	 * @param sandbox Sandbox value.
	 * @param other Optional other sandbox values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-sandbox">https://www.w3.org/TR/CSP/#directive-sandbox</a>
	 */
	public ContentSecurityPolicyBuilder addSandbox(Sandbox sandbox, Sandbox... other) {
		@SuppressWarnings("unchecked")
		List<Source> otherSources = (List) asList(other);
		return add(SourceDirective.SANDBOX, sandbox, otherSources);
	}

	/**
	 * Enable {@code block-all-mixed-content} directive.
	 *
	 * @return Current builder.
	 * @see <a href="https://www.w3.org/TR/mixed-content/#strict-checking">https://www.w3.org/TR/mixed-content/#strict-checking</a>
	 */
	public ContentSecurityPolicyBuilder blockAllMixedContent() {
		return add(SourceDirective.BLOCK_ALL_MIXED_CONTENT, null, java.util.Collections.<Source>emptyList());
	}

	private ContentSecurityPolicyBuilder add(SourceDirective directive, Source src, List<Source> other) {
		return add(directive, src, other, NO_OP_VALIDATOR);
	}

	private ContentSecurityPolicyBuilder add(SourceDirective directive, Source src, List<Source> other, SourceValidator validator) {
		Set<Source> list = sources.get(directive);
		if (list == null) {
			list = new LinkedHashSet<>();
			sources.put(directive, list);
		}

		// First check validity before adding to the list.
		if (src != null) {
			validator.validate(src);
		}

		for (Source o : other) {
			validator.validate(o);
		}

		if (src != null) {
			list.add(src);
		}

		list.addAll(other);

		return this;
	}

	/**
	 * Create {@link ContentSecurityPolicy} instance.
	 *
	 * @return New {@link ContentSecurityPolicy} instance.
	 */
	public ContentSecurityPolicy build() {
		return new ContentSecurityPolicy(sources);
	}

	/**
	 * Validator interface.
	 */
	private interface SourceValidator {
		void validate(Source src);
	}

	/**
	 * Validator used as a fallback.
	 */
	private static final SourceValidator NO_OP_VALIDATOR = new SourceValidator() {
		@Override
		public void validate(Source src) {
		}
	};

	private static class UriSource extends AbstractSourceValue implements Source {
		private final URI uri;

		private UriSource(URI uri) {
			this.uri = PreConditions.notNull(uri, "Uri value must not be null");
		}

		private UriSource(String uri) {
			this(URI.create(PreConditions.notBlank(uri, "Uri value must be defined")));
		}

		@Override
		public String getValue() {
			return uri.toString();
		}
	}
}
