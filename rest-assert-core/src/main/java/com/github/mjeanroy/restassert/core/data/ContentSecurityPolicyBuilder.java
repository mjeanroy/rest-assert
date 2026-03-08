/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

/// Builder used to create [ContentSecurityPolicy] instances.
public class ContentSecurityPolicyBuilder {
	/// List of value directives.
	private final Map<SourceDirective, Set<Source>> sources;

	/// Pattern used to validate [host value](https://www.w3.org/TR/CSP/#host_source).
	private static final Pattern PATTERN_HOST_VALUE = Pattern.compile(
		"^" +
			"(" + SCHEME_REGEX + "://)?" +
			"(" + HOST_NAME_REGEX + ")" +
			"(:" + HOST_PORT_REGEX + ")?" +
			"(" + HOST_PATH_REGEX + ")?" +
			"$",

		Pattern.CASE_INSENSITIVE
	);

	/// Create builder.
	ContentSecurityPolicyBuilder() {
		this.sources = new LinkedHashMap<>();
	}

	/// Add values for [`base-uri`](https://w3c.github.io/webappsec-csp/#directive-base-uri) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addBaseUri(Source src, Source... other) {
		return add(SourceDirective.BASE_URI, src, asList(other));
	}

	/// Add values for [`default-src`](https://w3c.github.io/webappsec-csp/#directive-default-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addDefaultSrc(Source src, Source... other) {
		return add(SourceDirective.DEFAULT_SRC, src, asList(other));
	}

	/// Add values for [`script-src`](https://w3c.github.io/webappsec-csp/#directive-script-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addScriptSrc(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC, src, asList(other));
	}

	/// Add values for [`script-src-elem`](https://w3c.github.io/webappsec-csp/#directive-script-src-elem) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addScriptSrcElem(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC_ELEM, src, asList(other));
	}

	/// Add values for [`script-src-attr`](https://w3c.github.io/webappsec-csp/#directive-script-src-attr) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addScriptSrcAttr(Source src, Source... other) {
		return add(SourceDirective.SCRIPT_SRC_ATTR, src, asList(other));
	}

	/// Add values for [`style-src`](https://w3c.github.io/webappsec-csp/#directive-style-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addStyleSrc(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC, src, asList(other));
	}

	/// Add values for [`style-src-elem`](https://w3c.github.io/webappsec-csp/#directive-style-src-elem) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addStyleSrcElem(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC_ELEM, src, asList(other));
	}

	/// Add values for [`style-src-attr`](https://w3c.github.io/webappsec-csp/#directive-style-src-attr) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addStyleSrcAttr(Source src, Source... other) {
		return add(SourceDirective.STYLE_SRC_ATTR, src, asList(other));
	}

	/// Add values for [`font-src`](https://w3c.github.io/webappsec-csp/#directive-font-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addFontSrc(Source src, Source... other) {
		return add(SourceDirective.FONT_SRC, src, asList(other));
	}

	/// Add values for [`object-src`](https://w3c.github.io/webappsec-csp/#directive-object-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addObjectSrc(Source src, Source... other) {
		return add(SourceDirective.OBJECT_SRC, src, asList(other));
	}

	/// Add values for [`media-src`](https://www.w3.org/TR/CSP/#directive-media-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addMediaSrc(Source src, Source... other) {
		return add(SourceDirective.MEDIA_SRC, src, asList(other));
	}

	/// Add values for [`img-src`](https://w3c.github.io/webappsec-csp/#directive-img-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addImgSrc(Source src, Source... other) {
		return add(SourceDirective.IMG_SRC, src, asList(other));
	}

	/// Add values for [`connect-src`](https://w3c.github.io/webappsec-csp/#directive-connect-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addConnectSrc(Source src, Source... other) {
		return add(SourceDirective.CONNECT_SRC, src, asList(other));
	}

	/// Add values for [`child-src`](https://w3c.github.io/webappsec-csp/#directive-child-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addChildSrc(Source src, Source... other) {
		return add(SourceDirective.CHILD_SRC, src, asList(other));
	}

	/// Add values for [`manifest-src`](https://www.w3.org/TR/CSP/#directive-child-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addManifestSrc(Source src, Source... other) {
		return add(SourceDirective.MANIFEST_SRC, src, asList(other));
	}

	/// Add values for [`frame-src`](https://w3c.github.io/webappsec-csp/#directive-frame-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addFrameSrc(Source src, Source... other) {
		return add(SourceDirective.FRAME_SRC, src, asList(other));
	}

	/// Add values for [`prefetch-src`](https://w3c.github.io/webappsec-csp/#directive-prefetch-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addPrefetchSrc(Source src, Source... other) {
		return add(SourceDirective.PREFETCH_SRC, src, asList(other));
	}

	/// Add values for [`worker-src`](https://w3c.github.io/webappsec-csp/#directive-worker-src) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addWorkerSrc(Source src, Source... other) {
		return add(SourceDirective.WORKER_SRC, src, asList(other));
	}

	/// Add values for [`form-action`](https://w3c.github.io/webappsec-csp/#directive-form-action) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addFormAction(Source src, Source... other) {
		return add(SourceDirective.FORM_ACTION, src, asList(other));
	}

	/// Add values for [`navigate-to`](https://w3c.github.io/webappsec-csp/#directive-navigate-to) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addNavigateTo(Source src, Source... other) {
		return add(SourceDirective.NAVIGATE_TO, src, asList(other));
	}

	/// Add values for [`frame-ancestors`](https://w3c.github.io/webappsec-csp/#directive-frame-ancestors) directive.
	///
	/// @param src Source value.
	/// @param other Optional other source values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addFrameAncestors(Source src, Source... other) {
		return add(SourceDirective.FRAME_ANCESTORS, src, asList(other), input ->
			PreConditions.match(input.getValue(), PATTERN_HOST_VALUE, "Source must be a valid host value")
		);
	}

	/// Add values for [`plugin-types`](https://w3c.github.io/webappsec-csp/#directive-plugin-types) directive.
	///
	/// @param mediaType Media-type value.
	/// @param other Optional other media-types values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addPluginTypes(String mediaType, String... other) {
		Source src = new SourceValue(mediaType);
		List<Source> otherSources = Arrays.stream(other).map(SourceValue::new).collect(Collectors.toList());
		return add(SourceDirective.PLUGIN_TYPES, src, otherSources);
	}

	/// Add [`disown-opener`](https://w3c.github.io/webappsec-csp/#directive-disown-opener) directive.
	///
	/// @return Current builder.
	public ContentSecurityPolicyBuilder addDisownOpener() {
		List<Source> sources = emptyList();
		return add(SourceDirective.DISOWN_OPENER, sources, null);
	}

	/// Add values for [`report-uri`](https://www.w3.org/TR/CSP/#directive-report-uri) directive.
	///
	/// @param uri Uri value.
	/// @param other Optional other uri values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	/// @throws IllegalArgumentException If at least one uri is not a valid uri value.
	public ContentSecurityPolicyBuilder addReportUri(String uri, String... other) {
		Source src = new UriSource(uri);
		List<Source> otherSources = Arrays.stream(other).map(UriSource::new).collect(Collectors.toList());
		return add(SourceDirective.REPORT_URI, src, otherSources);
	}

	/// Set token value for [`report-to`](https://w3c.github.io/webappsec-csp/#directive-report-to) directive.
	///
	/// @param token Token Value.
	/// @return Current builder.
	/// @throws NullPointerException If `token` is `null`.
	public ContentSecurityPolicyBuilder setReportTo(String token) {
		Source src = new SourceValue(token);
		List<Source> others = emptyList();
		return add(SourceDirective.REPORT_TO, src, others);
	}

	/// Add values for [`report-uri`](https://www.w3.org/TR/CSP/#directive-report-uri) directive.
	///
	/// @param uri Uri value.
	/// @param other Optional other uri values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addReportUri(URI uri, URI... other) {
		Source src = new UriSource(uri);
		List<Source> otherSources = Arrays.stream(other).map(UriSource::new).collect(Collectors.toList());
		return add(SourceDirective.REPORT_URI, src, otherSources);
	}

	/// Add values for [`sandbox`](https://w3c.github.io/webappsec-csp/#directive-sandbox) directive.
	///
	/// @param sandbox Sandbox value.
	/// @param other Optional other sandbox values.
	/// @return Current builder.
	/// @throws NullPointerException If at least one parameter is `null`.
	public ContentSecurityPolicyBuilder addSandbox(Sandbox sandbox, Sandbox... other) {
		return add(SourceDirective.SANDBOX, sandbox, asList(other));
	}

	/// Enable [`block-all-mixed-content`](https://w3c.github.io/webappsec-mixed-content) directive.
	///
	/// @return Current builder.
	public ContentSecurityPolicyBuilder blockAllMixedContent() {
		List<Source> sources = emptyList();
		return add(SourceDirective.BLOCK_ALL_MIXED_CONTENT, sources, null);
	}

	/// Enable [`upgrade-insecure-request`](https://w3c.github.io/webappsec-upgrade-insecure-requests) directive.
	///
	/// @return Current builder.
	public ContentSecurityPolicyBuilder upgradeInsecureRequest() {
		List<Source> sources = emptyList();
		return add(SourceDirective.UPGRADE_INSECURE_REQUEST, sources, null);
	}

	/// Enable [`block-all-mixed-content`](https://www.w3.org/TR/mixed-content/#strict-checking) directive.
	///
	/// @param resource Source directive.
	/// @param other Other, optional, source directives.
	/// @return Current builder.
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

	/// Create [ContentSecurityPolicy] instance.
	///
	/// @return New [ContentSecurityPolicy] instance.
	public ContentSecurityPolicy build() {
		return new ContentSecurityPolicy(sources);
	}

	/// Validator interface.
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
