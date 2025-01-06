/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.RequireSriFor;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Sandbox;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceValue;
import com.github.mjeanroy.restassert.core.internal.common.PreConditions;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.HOST_NAME_REGEX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.HOST_PATH_REGEX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.HOST_PORT_REGEX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SCHEME_REGEX;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * DefaultCookieBuilder used to create {@link ContentSecurityPolicy} instances.
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-base-uri">https://w3c.github.io/webappsec-csp/#directive-base-uri</a>
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-default-src">https://w3c.github.io/webappsec-csp/#directive-default-src</a>
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-script-src">https://w3c.github.io/webappsec-csp/#directive-script-src</a>
	 */
	public ContentSecurityPolicyBuilder addScriptSrc(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code script-src-elem} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-script-src-elem">https://w3c.github.io/webappsec-csp/#directive-script-src-elem</a>
	 */
	public ContentSecurityPolicyBuilder addScriptSrcElem(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC_ELEM, src, asList(other));
	}

	/**
	 * Add values for {@code script-src-attr} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-script-src-attr">https://w3c.github.io/webappsec-csp/#directive-script-src-attr</a>
	 */
	public ContentSecurityPolicyBuilder addScriptSrcAttr(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC_ATTR, src, asList(other));
	}

	/**
	 * Add values for {@code style-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-style-src">https://w3c.github.io/webappsec-csp/#directive-style-src</a>
	 */
	public ContentSecurityPolicyBuilder addStyleSrc(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code style-src-elem} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-style-src-elem">https://w3c.github.io/webappsec-csp/#directive-style-src-elem</a>
	 */
	public ContentSecurityPolicyBuilder addStyleSrcElem(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC_ELEM, src, asList(other));
	}

	/**
	 * Add values for {@code style-src-attr} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-style-src-attr">https://w3c.github.io/webappsec-csp/#directive-style-src-attr</a>
	 */
	public ContentSecurityPolicyBuilder addStyleSrcAttr(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC_ATTR, src, asList(other));
	}

	/**
	 * Add values for {@code font-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-font-src">https://w3c.github.io/webappsec-csp/#directive-font-src</a>
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-object-src">https://w3c.github.io/webappsec-csp/#directive-object-src</a>
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-img-src">https://w3c.github.io/webappsec-csp/#directive-img-src</a>
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-connect-src">https://w3c.github.io/webappsec-csp/#directive-connect-src</a>
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
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-child-src">https://w3c.github.io/webappsec-csp/#directive-child-src</a>
	 */
	public ContentSecurityPolicyBuilder addChildSrc(Source src, Source... other) {
		return add(SourceDirective.CHILD_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code manifest-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://www.w3.org/TR/CSP/#directive-child-src">https://www.w3.org/TR/CSP/#directive-child-src</a>
	 */
	public ContentSecurityPolicyBuilder addManifestSrc(Source src, Source... other) {
		return add(SourceDirective.MANIFEST_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code frame-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-frame-src">https://w3c.github.io/webappsec-csp/#directive-frame-src</a>
	 */
	public ContentSecurityPolicyBuilder addFrameSrc(Source src, Source... other) {
		return add(SourceDirective.FRAME_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code prefetch-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-prefetch-src">https://w3c.github.io/webappsec-csp/#directive-prefetch-src</a>
	 */
	public ContentSecurityPolicyBuilder addPrefetchSrc(Source src, Source... other) {
		return add(SourceDirective.PREFETCH_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code worker-src} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-worker-src">https://w3c.github.io/webappsec-csp/#directive-worker-src</a>
	 */
	public ContentSecurityPolicyBuilder addWorkerSrc(Source src, Source... other) {
		return add(SourceDirective.WORKER_SRC, src, asList(other));
	}

	/**
	 * Add values for {@code form-action} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-form-action">https://w3c.github.io/webappsec-csp/#directive-form-action</a>
	 */
	public ContentSecurityPolicyBuilder addFormAction(Source src, Source... other) {
		return add(SourceDirective.FORM_ACTION, src, asList(other));
	}

	/**
	 * Add values for {@code navigate-to} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-navigate-to">https://w3c.github.io/webappsec-csp/#directive-navigate-to</a>
	 */
	public ContentSecurityPolicyBuilder addNavigateTo(Source src, Source... other) {
		return add(SourceDirective.NAVIGATE_TO, src, asList(other));
	}

	/**
	 * Add values for {@code frame-ancestors} directive.
	 *
	 * @param src Source value.
	 * @param other Optional other source values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-frame-ancestors">https://w3c.github.io/webappsec-csp/#directive-frame-ancestors</a>
	 */
	public ContentSecurityPolicyBuilder addFrameAncestors(Source src, Source... other) {
		return add(SourceDirective.FRAME_ANCESTORS, src, asList(other), input ->
			PreConditions.match(input.getValue(), PATTERN_HOST_VALUE, "Source must be a valid host value")
		);
	}

	/**
	 * Add values for {@code plugin-types} directive.
	 *
	 * @param mediaType Media-type value.
	 * @param other Optional other media-types values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-plugin-types">https://w3c.github.io/webappsec-csp/#directive-plugin-types</a>
	 */
	public ContentSecurityPolicyBuilder addPluginTypes(String mediaType, String... other) {
		Source src = new SourceValue(mediaType);
		List<Source> otherSources = Arrays.stream(other).map(SourceValue::new).collect(Collectors.toList());
		return add(SourceDirective.PLUGIN_TYPES, src, otherSources);
	}

	/**
	 * Add {@code disown-opener} directive.
	 *
	 * @return Current builder.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-disown-opener">https://w3c.github.io/webappsec-csp/#directive-disown-opener</a>
	 */
	public ContentSecurityPolicyBuilder addDisownOpener() {
		List<Source> sources = emptyList();
		return add(SourceDirective.DISOWN_OPENER, sources, null);
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
		List<Source> otherSources = Arrays.stream(other).map(UriSource::new).collect(Collectors.toList());
		return add(SourceDirective.REPORT_URI, src, otherSources);
	}

	/**
	 * Set token value for {@code report-to} directive.
	 *
	 * @param token Token Value.
	 * @return Current builder.
	 * @throws NullPointerException If {@code token} is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-report-to">https://w3c.github.io/webappsec-csp/#directive-report-to</a>
	 */
	public ContentSecurityPolicyBuilder setReportTo(String token) {
		Source src = new SourceValue(token);
		List<Source> others = emptyList();
		return add(SourceDirective.REPORT_TO, src, others);
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
		List<Source> otherSources = Arrays.stream(other).map(UriSource::new).collect(Collectors.toList());
		return add(SourceDirective.REPORT_URI, src, otherSources);
	}

	/**
	 * Add values for {@code sandbox} directive.
	 *
	 * @param sandbox Sandbox value.
	 * @param other Optional other sandbox values.
	 * @return Current builder.
	 * @throws NullPointerException If at least one parameter is {@code null}.
	 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-sandbox">https://w3c.github.io/webappsec-csp/#directive-sandbox</a>
	 */
	public ContentSecurityPolicyBuilder addSandbox(Sandbox sandbox, Sandbox... other) {
		return add(SourceDirective.SANDBOX, sandbox, asList(other));
	}

	/**
	 * Enable {@code block-all-mixed-content} directive.
	 *
	 * @return Current builder.
	 * @see <a href="https://w3c.github.io/webappsec-mixed-content">https://w3c.github.io/webappsec-mixed-content</a>
	 */
	public ContentSecurityPolicyBuilder blockAllMixedContent() {
		List<Source> sources = emptyList();
		return add(SourceDirective.BLOCK_ALL_MIXED_CONTENT, sources, null);
	}

	/**
	 * Enable {@code upgrade-insecure-request} directive.
	 *
	 * @return Current builder.
	 * @see <a href="https://w3c.github.io/webappsec-upgrade-insecure-requests">https://w3c.github.io/webappsec-upgrade-insecure-requests</a>
	 */
	public ContentSecurityPolicyBuilder upgradeInsecureRequest() {
		List<Source> sources = emptyList();
		return add(SourceDirective.UPGRADE_INSECURE_REQUEST, sources, null);
	}

	/**
	 * Enable {@code block-all-mixed-content} directive.
	 *
	 * @param resource Source directive.
	 * @param other Other, optional, source directives.
	 * @return Current builder.
	 * @see <a href="https://www.w3.org/TR/mixed-content/#strict-checking">https://www.w3.org/TR/mixed-content/#strict-checking</a>
	 */
	public ContentSecurityPolicyBuilder addRequireSriFor(RequireSriFor resource, RequireSriFor... other) {
		return add(SourceDirective.REQUIRE_SRI_FOR, resource, asList(other));
	}

	private ContentSecurityPolicyBuilder add(SourceDirective directive, Source src, List<? extends Source> other) {
		return add(directive, src, other, null);
	}

	private ContentSecurityPolicyBuilder add(SourceDirective directive, Source src, List<? extends Source> other, SourceValidator validator) {
		List<Source> inputs = new ArrayList<>(other.size() + 1);

		if (src != null) {
			inputs.add(src);
		}

		inputs.addAll(other);

		return add(directive, inputs, validator);
	}

	private ContentSecurityPolicyBuilder add(SourceDirective directive, List<Source> src, SourceValidator validator) {
		Set<Source> list = sources.computeIfAbsent(directive, k -> new LinkedHashSet<>());

		if (validator != null) {
			for (Source o : src) {
				validator.validate(o);
			}
		}

		list.addAll(src);

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
