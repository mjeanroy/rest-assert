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

import com.github.mjeanroy.restassert.core.internal.common.PreConditions;
import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderValue;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

/// Content-Security-Policy Header (a.k.a CSP):
/// - [W3C Specification](https://w3c.github.io/webappsec-csp/)
/// - [MDN Documentation](https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Security-Policy)
public final class ContentSecurityPolicy implements HttpHeaderValue {

	/// The parser instance.
	private static final ContentSecurityPolicyParser PARSER = new ContentSecurityPolicyParser();

	/// Get parser for [ContentSecurityPolicy] instances.
	///
	/// @return The parser.
	public static HttpHeaderParser<ContentSecurityPolicy> parser() {
		return PARSER;
	}

	/// Create new builder for [ContentSecurityPolicy].
	///
	/// @return The builder.
	public static ContentSecurityPolicyBuilder builder() {
		return new ContentSecurityPolicyBuilder();
	}

	/// List of value directives.
	private final Map<SourceDirective, Set<Source>> directives;

	/// Create CSP value object.
	///
	/// @param directives Header directives.
	ContentSecurityPolicy(Map<SourceDirective, Set<Source>> directives) {
		// Make a deep copy.
		Map<SourceDirective, Set<Source>> clone = new LinkedHashMap<>();

		for (Entry<SourceDirective, Set<Source>> entry : directives.entrySet()) {
			Set<Source> sources = new LinkedHashSet<>(entry.getValue());
			SourceDirective directive = entry.getKey();
			clone.put(directive, unmodifiableSet(sources));
		}

		this.directives = unmodifiableMap(clone);
	}

	/// Get [#directives]
	///
	/// @return Returns [#directives]
	public Map<SourceDirective, Set<Source>> getDirectives() {
		return directives;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof ContentSecurityPolicy) {
			ContentSecurityPolicy csp = (ContentSecurityPolicy) o;
			return Objects.equals(directives, csp.directives);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(directives);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("directives", directives)
			.build();
	}

	@Override
	public String serializeValue() {
		return directives.entrySet().stream()
			.map((input) -> {
				SourceDirective directive = input.getKey();
				Set<Source> sources = input.getValue();
				String name = directive.getName();
				String value = sources.stream()
					.map(Source::getValue)
					.collect(Collectors.joining(" "));

				int capacity = name.length() + value.length() + 1;
				StringBuilder sb = new StringBuilder(capacity);

				sb.append(name);
				if (!value.isEmpty()) {
					sb.append(" ").append(value);
				}

				return sb.toString();
			})
			.collect(Collectors.joining("; "));
	}

	/// List of CSP directive.
	public enum SourceDirective {
		/// Handle [`base-uri`](https://w3c.github.io/webappsec-csp/#directive-base-uri) directive.
		BASE_URI("base-uri") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addBaseUri(new SourceValue(value));
			}
		},

		/// Handle [`default-src`](https://w3c.github.io/webappsec-csp/#directive-default-src) directive.
		DEFAULT_SRC("default-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addDefaultSrc(new SourceValue(value));
			}
		},

		/// Handle [`script-src`](https://w3c.github.io/webappsec-csp/#directive-script-src) directive.
		SCRIPT_SRC("script-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addScriptSrc(new SourceValue(value));
			}
		},

		/// Handle [`script-src-elem`](https://w3c.github.io/webappsec-csp/#directive-script-src-elem) directive.
		SCRIPT_SRC_ELEM("script-src-elem") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addScriptSrcElem(new SourceValue(value));
			}
		},

		/// Handle [`script-src-attr`](https://w3c.github.io/webappsec-csp/#directive-script-src-attr) directive.
		SCRIPT_SRC_ATTR("script-src-attr") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addScriptSrcAttr(new SourceValue(value));
			}
		},

		/// Handle [`style-src`](https://w3c.github.io/webappsec-csp/#directive-style-src) directive.
		STYLE_SRC("style-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addStyleSrc(new SourceValue(value));
			}
		},

		/// Handle [`style-src-elem`](https://w3c.github.io/webappsec-csp/#directive-style-src-elem) directive.
		STYLE_SRC_ELEM("style-src-elem") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addStyleSrcElem(new SourceValue(value));
			}
		},

		/// Handle [`style-src`](https://w3c.github.io/webappsec-csp/#directive-style-src-attr) directive.
		STYLE_SRC_ATTR("style-src-attr") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addStyleSrcAttr(new SourceValue(value));
			}
		},

		/// Handle [`object-src`](https://w3c.github.io/webappsec-csp/#directive-object-src) directive.
		OBJECT_SRC("object-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addObjectSrc(new SourceValue(value));
			}
		},

		/// Handle [`media-src`](https://w3c.github.io/webappsec-csp/#directive-media-src) directive.
		MEDIA_SRC("media-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addMediaSrc(new SourceValue(value));
			}
		},

		/// Handle [`img-src`](https://w3c.github.io/webappsec-csp/#directive-img-src) directive.
		IMG_SRC("img-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addImgSrc(new SourceValue(value));
			}
		},

		/// Handle [`font-src`](https://w3c.github.io/webappsec-csp/#directive-font-src) directive.
		FONT_SRC("font-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFontSrc(new SourceValue(value));
			}
		},

		/// Handle [`connect-src`](https://w3c.github.io/webappsec-csp/#directive-connect-src) directive.
		CONNECT_SRC("connect-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addConnectSrc(new SourceValue(value));
			}
		},

		/// Handle [`child-src`](https://w3c.github.io/webappsec-csp/#directive-child-src) directive.
		CHILD_SRC("child-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addChildSrc(new SourceValue(value));
			}
		},

		/// Handle [`manifest-src`](https://w3c.github.io/webappsec-csp/#directive-manifest-src) directive.
		MANIFEST_SRC("manifest-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addManifestSrc(new SourceValue(value));
			}
		},

		/// Handle [`frame-src`](https://w3c.github.io/webappsec-csp/#directive-frame-src) directive.
		FRAME_SRC("frame-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFrameSrc(new SourceValue(value));
			}
		},

		/// Handle [`prefetch-src`](https://w3c.github.io/webappsec-csp/#directive-prefetch-src) directive.
		PREFETCH_SRC("prefetch-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addPrefetchSrc(new SourceValue(value));
			}
		},

		/// Handle [`worker-src`](https://w3c.github.io/webappsec-csp/#directive-worker-src) directive.
		WORKER_SRC("worker-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addWorkerSrc(new SourceValue(value));
			}
		},

		/// Handle [`form-action`](https://w3c.github.io/webappsec-csp/#directive-form-action) directive.
		FORM_ACTION("form-action") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFormAction(new SourceValue(value));
			}
		},

		/// Handle [`navigate-to`](https://w3c.github.io/webappsec-csp/#directive-navigate-to) directive.
		NAVIGATE_TO("navigate-to") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addNavigateTo(new SourceValue(value));
			}
		},

		/// Handle [`frame-ancestors`](https://w3c.github.io/webappsec-csp/#directive-frame-ancestors) directive.
		FRAME_ANCESTORS("frame-ancestors") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFrameAncestors(new SourceValue(value));
			}
		},

		/// Handle [`plugin-types`](https://w3c.github.io/webappsec-csp/#directive-plugin-types) directive.
		PLUGIN_TYPES("plugin-types") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addPluginTypes(value);
			}
		},

		/// Handle [`report-uri`](https://w3c.github.io/webappsec-csp/#directive-report-uri) directive.
		REPORT_URI("report-uri") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addReportUri(value);
			}
		},

		/// Handle [`report-to`](https://w3c.github.io/webappsec-csp/#directive-report-to) directive.
		REPORT_TO("report-to") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.setReportTo(value);
			}
		},

		/// Handle [`sandbox`](https://w3c.github.io/webappsec-csp/#directive-sandbox) directive.
		SANDBOX("sandbox") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addSandbox(Sandbox.byValue(value));
			}
		},

		/// Handle [`disown-opener`](https://w3c.github.io/webappsec-csp/#directive-disown-opener) directive.
		DISOWN_OPENER("disown-opener") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addDisownOpener();
			}
		},

		/// Handle [`require-sri-for`](https://w3c.github.io/webappsec-subresource-integrity).
		///
		/// See also [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/require-sri-for) for additional
		/// details.
		REQUIRE_SRI_FOR("require-sri-for") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addRequireSriFor(RequireSriFor.byValue(value));
			}
		},

		/// Handle [`upgrade-insecure-request`](https://w3c.github.io/webappsec-upgrade-insecure-requests/).
		///
		/// See also [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/upgrade-insecure-requests) for
		/// additional details.
		UPGRADE_INSECURE_REQUEST("upgrade-insecure-request") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.upgradeInsecureRequest();
			}
		},

		/// Handle [`block-all-mixed-content`](https://w3c.github.io/webappsec-mixed-content/).
		///
		/// See also [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/block-all-mixed-content) for
		/// additional details.
		BLOCK_ALL_MIXED_CONTENT("block-all-mixed-content") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.blockAllMixedContent();
			}
		};

		/// Name of directive.
		/// This name is the directive label in CSP value.
		private final String name;

		SourceDirective(String name) {
			this.name = name;
		}

		/// Get [#name].
		///
		/// @return Returns [#name].
		String getName() {
			return name;
		}

		/// Parse value directive value.
		///
		/// @param headerValue Directive value.
		/// @param builder Current builder.
		void parse(String headerValue, ContentSecurityPolicyBuilder builder) {
			String[] values = headerValue.split(" ");
			for (String value : values) {
				doParse(value, builder);
			}
		}

		/// Append `value` to appropriate section.
		///
		/// @param value Value.
		/// @param builder DefaultCookieBuilder.
		abstract void doParse(String value, ContentSecurityPolicyBuilder builder);

		/// Map of directives indexed by name.
		private static final Map<String, SourceDirective> map = stream(SourceDirective.values()).collect(
			Collectors.toMap(
				(input) -> input.getName().toLowerCase(),
				Function.identity()
			)
		);

		/// Get [SourceDirective] by name (search is **case-insensitive**).
		///
		/// @param name Name.
		/// @return Directive, may be `null` if name does not exist.
		static SourceDirective byName(String name) {
			return map.get(name.toLowerCase());
		}
	}

	/// Source item.
	/// The value must follow [Content-Security-Policy RFC](https://www.w3.org/TR/CSP/#source_list).
	public interface Source {
		/// Get source value.
		///
		/// @return The source value.
		String getValue();
	}

	/// Template for [Source] implementation.
	abstract static class AbstractSourceValue implements Source {
		@Override
		public String toString() {
			return getValue();
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}

			if (o instanceof AbstractSourceValue) {
				AbstractSourceValue s = (AbstractSourceValue) o;
				return getValue().equals(s.getValue());
			}

			return false;
		}

		@Override
		public int hashCode() {
			return getValue().hashCode();
		}
	}

	/// Simple source item.
	static class SourceValue extends AbstractSourceValue implements Source {
		/// Source item value.
		private final String value;

		/// Create source.
		///
		/// @param value Source value.
		/// @throws NullPointerException If `value` is `null`.
		/// @throws IllegalArgumentException If `value` is empty or blank.
		SourceValue(String value) {
			this.value = PreConditions.notBlank(value, "Source value must be defined");
		}

		@Override
		public String getValue() {
			return value;
		}
	}

	/// Host source, defined by:
	/// - Optional scheme.
	/// - Host name (required).
	/// - Optional port.
	/// - Optional path.
	///
	///  See [CSP Host Source Specification](https://www.w3.org/TR/CSP/#host_source) for additional details.
	private static class Host extends AbstractSourceValue implements Source {
		/// Host scheme, may be `null`.
		private final String scheme;

		/// Host name.
		private final String host;

		/// Host port, may be `null`.
		private final String port;

		/// Host path, may be `null`.
		private final String path;

		/// Create host.
		///
		/// @param scheme Host scheme.
		/// @param host Host name.
		/// @param port Host port.
		/// @param path Host path.
		/// @throws NullPointerException If `host` is `null`.
		private Host(String scheme, String host, String port, String path) {
			this.scheme = scheme;
			this.host = PreConditions.notBlank(host, "Host must be defined");
			this.port = port;
			this.path = path;
		}

		@Override
		public String getValue() {
			StringBuilder val = new StringBuilder();

			// Append the scheme if it is defined.
			if (scheme != null) {
				val.append(scheme).append("://");
			}

			val.append(host);

			// Append the port if it is defined.
			if (port != null) {
				val.append(":").append(port);
			}

			// Then, append the path if it is defined
			if (path != null) {
				val.append(path);
			}

			return val.toString();
		}
	}

	static final String SCHEME_REGEX = "[a-z][a-z0-9+\\-.]*";
	static final String HOST_NAME_REGEX = "(\\*\\.)?([a-z0-9\\-])+(\\.[a-z0-9\\-]+)*";
	static final String HOST_PORT_REGEX = "([0-9]+)|\\*";
	static final String HOST_PATH_REGEX = "([^?#]*)";

	/// Pattern used to validate scheme value ([RFC 3986](https://tools.ietf.org/html/rfc3986#section-3.1)).
	private static final Pattern PATTERN_SCHEME = Pattern.compile("^" + SCHEME_REGEX + "$", Pattern.CASE_INSENSITIVE);

	/// Pattern used to validate base64 value ([CSP Base 64 value](https://www.w3.org/TR/CSP/#base64_value)).
	private static final Pattern PATTERN_BASE64 = Pattern.compile("^[a-z0-9+/]+={2}$", Pattern.CASE_INSENSITIVE);

	/// Pattern used to validate host name ([CSP Host](https://www.w3.org/TR/CSP/#host_part)).
	private static final Pattern PATTERN_HOST_NAME = Pattern.compile("^" + HOST_NAME_REGEX + "$", Pattern.CASE_INSENSITIVE);

	/// Pattern used to validate host port ([CSP Host](https://www.w3.org/TR/CSP/#host_part)).
	private static final Pattern PATTERN_PORT = Pattern.compile("^" + HOST_PORT_REGEX + "$", Pattern.CASE_INSENSITIVE);

	/// Pattern used to validate a path value ([RFC 3986](https://tools.ietf.org/html/rfc3986#section-3.3)).
	private static final Pattern PATTERN_PATH = Pattern.compile(HOST_PATH_REGEX, Pattern.CASE_INSENSITIVE);

	/// The `'self'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source SELF = new SourceValue("'self'");

	/// The `'none'` Source ([CSP Sources](https://www.w3.org/TR/CSP/#source_list)).
	private static final Source NONE = new SourceValue("'none'");

	/// The `'unsafe-eval'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source UNSAFE_EVAL = new SourceValue("'unsafe-eval'");

	/// The `'unsafe-hashes'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source UNSAFE_HASHES = new SourceValue("'unsafe-hashes'");

	/// The `'strict-dynamic'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source STRICT_DYNAMIC = new SourceValue("'strict-dynamic'");

	/// The `'report-sample'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source REPORT_SAMPLE = new SourceValue("'report-sample'");

	/// The `'unsafe-inline'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source UNSAFE_INLINE = new SourceValue("'unsafe-inline'");

	/// The `http` scheme ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source HTTP = scheme("http");

	/// The `'https'` scheme ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source HTTPS = scheme("https");

	/// The `'data'` schele ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	private static final Source DATA = scheme("data");

	/// The "all host" (`*`) value keyword ([CSP Host](https://www.w3.org/TR/CSP/#host_part).
	private static final Source ALL_HOST = new SourceValue("*");

	/// Get the `'self'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return Self Source.
	public static Source self() {
		return SELF;
	}

	/// Get the `'none'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return None Source.
	public static Source none() {
		return NONE;
	}

	/// Get the `'unsafe-eval'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return Unsafe-Eval Source.
	public static Source unsafeEval() {
		return UNSAFE_EVAL;
	}

	/// Get the `'unsafe-hashed'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return The Unsafe-Hashes Source.
	public static Source unsafeHashes() {
		return UNSAFE_HASHES;
	}

	/// Get the `'unsafe-inline'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return The unsafe-inline Source.
	public static Source unsafeInline() {
		return UNSAFE_INLINE;
	}

	/// Get the `'strict-dynamic'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return The Strict-Dynamic Source.
	public static Source strictDynamic() {
		return STRICT_DYNAMIC;
	}

	/// Get the `'report-sample'` keyword ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return The Report-Sample Source.
	public static Source reportSample() {
		return REPORT_SAMPLE;
	}

	/// Get the star matching all host ([CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source)).
	///
	/// @return The "ALL HOST" Source.
	public static Source allHosts() {
		return ALL_HOST;
	}

	/// Build a new scheme source.
	/// Note that the last `:` character will be appended.
	///
	/// See [CSP Keyword Source](https://www.w3.org/TR/CSP/#keyword_source) for additional
	/// details.
	///
	/// @param scheme Scheme value.
	/// @return The source item.
	/// @throws NullPointerException If `scheme` is `null`.
	/// @throws IllegalArgumentException If `scheme` does not match Scheme Pattern.
	public static Source scheme(String scheme) {
		PreConditions.notNull(scheme, "Scheme must not be null");
		PreConditions.match(scheme, PATTERN_SCHEME, String.format("Scheme %s is not a valid scheme", scheme));
		return new SourceValue(scheme + ":");
	}

	/// Build a new [nonce source](https://www.w3.org/TR/CSP/#nonce_source).
	///
	/// @param base64 Base64 Value.
	/// @return The source item.
	/// @throws NullPointerException If `base64` is `null`.
	/// @throws IllegalArgumentException If `base64` does not match [Base 64 Pattern][#PATTERN_BASE64].
	public static Source nonce(String base64) {
		PreConditions.notNull(base64, "Base64 value must not be null");
		PreConditions.match(base64, PATTERN_BASE64, String.format("%s is not a valid base64 value", base64));
		return new SourceValue("'nonce-" + base64 + "'");
	}

	/// Get [HTTP scheme](https://www.w3.org/TR/CSP/#scheme_part).
	/// This is a shortcut for [#scheme(String)] method.
	///
	/// @return The source item.
	public static Source http() {
		return HTTP;
	}

	/// Get [HTTPS scheme](https://www.w3.org/TR/CSP/#scheme_part).
	/// This is a shortcut for [#scheme(String)] method.
	///
	/// @return The source item.
	public static Source https() {
		return HTTPS;
	}

	/// Get [Data scheme](https://www.w3.org/TR/CSP/#scheme_part).
	/// This is a shortcut for [#scheme(String)] method.
	///
	/// @return The source item.
	public static Source data() {
		return DATA;
	}

	private static Source algo(String algo, String base64) {
		PreConditions.notNull(base64, "Base64 value must not be null");
		PreConditions.match(base64, PATTERN_BASE64, String.format("%s is not a valid base64 value", base64));
		return new SourceValue("'" + algo + "-" + base64 + "'");
	}

	/// Build new [SHA-256 hash source](https://www.w3.org/TR/CSP/#hash_value).
	///
	/// @param base64 The hash value as base 64.
	/// @return Source item.
	/// @throws NullPointerException If `base64` is `null`.
	/// @throws IllegalArgumentException If `base64` is not a valid base64 value.
	public static Source sha256(String base64) {
		return algo("sha256", base64);
	}

	/// Build new [SHA-384 hash source](https://www.w3.org/TR/CSP/#hash_value).
	///
	/// @param base64 The hash value as base 64.
	/// @return Source item.
	/// @throws NullPointerException If `base64` is `null`.
	/// @throws IllegalArgumentException If `base64` is not a valid base64 value.
	public static Source sha384(String base64) {
		return algo("sha384", base64);
	}

	/// Build new [SHA-512 hash source](https://www.w3.org/TR/CSP/#hash_value).
	///
	/// @param base64 The hash value as base 64.
	/// @return Source item.
	/// @throws NullPointerException If `base64` is `null`.
	/// @throws IllegalArgumentException If `base64` is not a valid base64 value.
	public static Source sha512(String base64) {
		return algo("sha512", base64);
	}

	/// Build new [host source](https://www.w3.org/TR/CSP/#host_source).
	///
	/// @param scheme Host scheme, may be `null`.
	/// @param host Host name.
	/// @param port Port value, may be `null` or `*`.
	/// @param path Path value, may be `null`.
	/// @return Source item.
	/// @throws NullPointerException If `host` is `null`.
	/// @throws IllegalArgumentException If `scheme` is not a valid scheme value.
	/// @throws IllegalArgumentException If `host` is not a valid host name.
	/// @throws IllegalArgumentException If `port` is not a valid port value.
	/// @throws IllegalArgumentException If `path` is not a valid path value.
	public static Source host(String scheme, String host, String port, String path) {
		PreConditions.notBlank(host, "Host name must be defined");
		PreConditions.match(host, PATTERN_HOST_NAME, "Host %s is not valid");

		if (scheme != null) {
			scheme = PreConditions.match(scheme, PATTERN_SCHEME, String.format("Scheme %s is not a valid scheme", scheme));
		}

		if (port != null && !port.equals("*")) {
			port = PreConditions.match(port, PATTERN_PORT, String.format("Port %s should only contains integers", port));
		}

		if (path != null && !path.isEmpty()) {
			path = PreConditions.match(path, PATTERN_PATH, String.format("Path %s seems not valid", path));
		}

		return new Host(scheme, host, port, path);
	}

	/// Build new [host source](https://www.w3.org/TR/CSP/#host_source).
	///
	/// @param url Host url, must not be `null`.
	/// @return Source item.
	/// @throws NullPointerException If `url` is null.
	public static Source host(URL url) {
		PreConditions.notNull(url, "Host url must not be null");

		// If port is less than zero, then it means it is not set.
		int port = url.getPort();
		String portValue = port >= 0 ? String.valueOf(port) : null;

		return new Host(url.getProtocol(), url.getHost(), portValue, url.getPath());
	}

	/// Set of allowed [sandbox value](https://www.w3.org/TR/CSP/#sandbox-usage).
	public enum Sandbox implements Source {
		/// The `'allow-scripts'` sandbox value of a CSP.
		ALLOW_SCRIPTS("allow-scripts"),

		/// The `'allow-same-origin'` sandbox value of a CSP.
		ALLOW_SAME_ORIGIN("allow-same-origin"),

		/// The `'allow-forms'` sandbox value of a CSP.
		ALLOW_FORMS("allow-forms"),

		/// The `'allow-pointer-lock'` sandbox value of a CSP.
		ALLOW_POINTER_LOCK("allow-pointer-lock"),

		/// The `'allow-top-navigation'` sandbox value of a CSP.
		ALLOW_POPUPS("allow-popups"),

		/// The `'allow-top-navigation'` sandbox value of a CSP.
		ALLOW_TOP_NAVIGATION("allow-top-navigation");

		/// Label as specified by RFC.
		private final String value;

		Sandbox(String value) {
			this.value = value;
		}

		/// Label as specified by RFC.
		///
		/// @return Sandbox value appearing in CSP value.
		@Override
		public String getValue() {
			return value;
		}

		private static final Map<String, Sandbox> map = stream(Sandbox.values()).collect(
			Collectors.toMap(
				(input) -> input.getValue().toLowerCase(),
				Function.identity()
			)
		);

		/// Get sandbox item from value.
		///
		/// @param value Value.
		/// @return Sandbox item, may be `null` if `value` does not exist.
		static Sandbox byValue(String value) {
			return map.get(value.toLowerCase());
		}
	}

	/// Set of allowed [required-sri-for value](https://w3c.github.io/webappsec-subresource-integrity)).
	///
	/// See also [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/require-sri-for) for
	/// additional details.
	public enum RequireSriFor implements Source {
		/// The script value for the SRI attribute.
		SCRIPT("script"),

		/// The style value for the SRI attribute.
		STYLE("style");

		/// Label as specified by [RFC](https://w3c.github.io/webappsec-subresource-integrity/#opt-in-require-sri-for).
		private final String value;

		RequireSriFor(String value) {
			this.value = value;
		}

		/// Get [#value]
		///
		/// @return Returns [#value]
		@Override
		public String getValue() {
			return value;
		}

		private static final Map<String, RequireSriFor> map = stream(RequireSriFor.values()).collect(
			Collectors.toMap(
				(input) -> input.getValue().toLowerCase(),
				Function.identity()
			)
		);

		/// Get [RequireSriFor] item from value.
		///
		/// @param value Value.
		/// @return The item, may be `null` if `value` does not exist.
		static RequireSriFor byValue(String value) {
			return map.get(value.toLowerCase());
		}
	}
}
