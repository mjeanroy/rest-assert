/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

/**
 * Content-Security-Policy Header.
 *
 * @see <a href="https://w3c.github.io/webappsec-csp/">https://w3c.github.io/webappsec-csp/</a>
 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Security-Policy">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Security-Policy</a>
 */
public final class ContentSecurityPolicy implements HttpHeaderValue {

	/**
	 * The parser instance.
	 */
	private static final ContentSecurityPolicyParser PARSER = new ContentSecurityPolicyParser();

	/**
	 * Get parser for {@link ContentSecurityPolicy} instances.
	 *
	 * @return The parser.
	 */
	public static HttpHeaderParser<ContentSecurityPolicy> parser() {
		return PARSER;
	}

	/**
	 * Create new builder for {@link CacheControl}.
	 *
	 * @return The builder.
	 */
	public static ContentSecurityPolicyBuilder builder() {
		return new ContentSecurityPolicyBuilder();
	}

	/**
	 * List of value directives.
	 */
	private final Map<SourceDirective, Set<Source>> directives;

	/**
	 * Create CSP value object.
	 *
	 * @param directives Header directives.
	 */
	ContentSecurityPolicy(Map<SourceDirective, Set<Source>> directives) {
		// Make a deep copy.
		Map<SourceDirective, Set<Source>> clone = new LinkedHashMap<>();

		for (Entry<SourceDirective, Set<Source>> entry : directives.entrySet()) {
			LinkedHashSet<Source> sources = new LinkedHashSet<>(entry.getValue());
			SourceDirective directive = entry.getKey();
			clone.put(directive, unmodifiableSet(sources));
		}

		this.directives = unmodifiableMap(clone);
	}

	/**
	 * Get {@link #directives}
	 *
	 * @return {@link #directives}
	 */
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

	/**
	 * List of CSP directive.
	 */
	enum SourceDirective {
		/**
		 * Handle {@code base-uri} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-base-uri">https://w3c.github.io/webappsec-csp/#directive-base-uri</a>
		 */
		BASE_URI("base-uri") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addBaseUri(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code default-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-default-src">https://w3c.github.io/webappsec-csp/#directive-default-src</a>
		 */
		DEFAULT_SRC("default-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addDefaultSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code script-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-script-src">https://w3c.github.io/webappsec-csp/#directive-script-src</a>
		 */
		SCRIPT_SRC("script-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addScriptSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code script-src-elem} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-script-src-elem">https://w3c.github.io/webappsec-csp/#directive-script-src-elem</a>
		 */
		SCRIPT_SRC_ELEM("script-src-elem") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addScriptSrcElem(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code script-src-attr} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-script-src-attr">https://w3c.github.io/webappsec-csp/#directive-script-src-attr</a>
		 */
		SCRIPT_SRC_ATTR("script-src-attr") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addScriptSrcAttr(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code style-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-style-src">https://w3c.github.io/webappsec-csp/#directive-style-src</a>
		 */
		STYLE_SRC("style-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addStyleSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code style-src-elem} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-style-src-elem">https://w3c.github.io/webappsec-csp/#directive-style-src-elem</a>
		 */
		STYLE_SRC_ELEM("style-src-elem") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addStyleSrcElem(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code style-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-style-src-attr">https://w3c.github.io/webappsec-csp/#directive-style-src-attr</a>
		 */
		STYLE_SRC_ATTR("style-src-attr") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addStyleSrcAttr(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code object-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-object-src">https://w3c.github.io/webappsec-csp/#directive-object-src</a>
		 */
		OBJECT_SRC("object-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addObjectSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code media-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-media-src">https://w3c.github.io/webappsec-csp/#directive-media-src</a>
		 */
		MEDIA_SRC("media-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addMediaSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code img-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-img-src">https://w3c.github.io/webappsec-csp/#directive-img-src</a>
		 */
		IMG_SRC("img-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addImgSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code font-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-font-src">https://w3c.github.io/webappsec-csp/#directive-font-src</a>
		 */
		FONT_SRC("font-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFontSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code connect-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-connect-src">https://w3c.github.io/webappsec-csp/#directive-connect-src</a>
		 */
		CONNECT_SRC("connect-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addConnectSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code child-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-child-src">https://w3c.github.io/webappsec-csp/#directive-child-src</a>
		 */
		CHILD_SRC("child-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addChildSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code manifest-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-manifest-src">https://w3c.github.io/webappsec-csp/#directive-manifest-src</a>
		 */
		MANIFEST_SRC("manifest-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addManifestSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code frame-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-frame-src">https://w3c.github.io/webappsec-csp/#directive-frame-src</a>
		 */
		FRAME_SRC("frame-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFrameSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code prefetch-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-prefetch-src">https://w3c.github.io/webappsec-csp/#directive-prefetch-src</a>
		 */
		PREFETCH_SRC("prefetch-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addPrefetchSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code worker-src} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-worker-src">https://w3c.github.io/webappsec-csp/#directive-worker-src</a>
		 */
		WORKER_SRC("worker-src") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addWorkerSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code form-action} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-form-action">https://w3c.github.io/webappsec-csp/#directive-form-action</a>
		 */
		FORM_ACTION("form-action") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFormAction(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code navigate-to} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-navigate-to">https://w3c.github.io/webappsec-csp/#directive-navigate-to</a>
		 */
		NAVIGATE_TO("navigate-to") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addNavigateTo(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code frame-ancestors} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-frame-ancestors">https://w3c.github.io/webappsec-csp/#directive-frame-ancestors</a>
		 */
		FRAME_ANCESTORS("frame-ancestors") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addFrameAncestors(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code plugin-types} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-plugin-types">https://w3c.github.io/webappsec-csp/#directive-plugin-types</a>
		 */
		PLUGIN_TYPES("plugin-types") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addPluginTypes(value);
			}
		},

		/**
		 * Handle {@code report-uri} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-report-uri">https://w3c.github.io/webappsec-csp/#directive-report-uri</a>
		 */
		REPORT_URI("report-uri") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addReportUri(value);
			}
		},

		/**
		 * Handle {@code report-to} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-report-to">https://w3c.github.io/webappsec-csp/#directive-report-to</a>
		 */
		REPORT_TO("report-to") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.setReportTo(value);
			}
		},

		/**
		 * Handle {@code sandbox} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-sandbox">https://w3c.github.io/webappsec-csp/#directive-sandbox</a>
		 */
		SANDBOX("sandbox") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addSandbox(Sandbox.byValue(value));
			}
		},

		/**
		 * Handle {@code report-uri} directive.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-csp/#directive-disown-opener">https://w3c.github.io/webappsec-csp/#directive-disown-opener</a>
		 */
		DISOWN_OPENER("disown-opener") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addDisownOpener();
			}
		},

		/**
		 * Handle {@code require-sri-for}.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-subresource-integrity">https://w3c.github.io/webappsec-subresource-integrity</a>
		 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/require-sri-for">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/require-sri-for</a>
		 */
		REQUIRE_SRI_FOR("require-sri-for") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.addRequireSriFor(RequireSriFor.byValue(value));
			}
		},

		/**
		 * Handle {@code upgrade-insecure-request}.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-upgrade-insecure-requests/">https://w3c.github.io/webappsec-upgrade-insecure-requests/</a>
		 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/upgrade-insecure-requests">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/upgrade-insecure-requests</a>
		 */
		UPGRADE_INSECURE_REQUEST("upgrade-insecure-request") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.upgradeInsecureRequest();
			}
		},

		/**
		 * Handle {@code block-all-mixed-content}.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-mixed-content/">https://w3c.github.io/webappsec-mixed-content/</a>
		 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/block-all-mixed-content">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/block-all-mixed-content</a>
		 */
		BLOCK_ALL_MIXED_CONTENT("block-all-mixed-content") {
			@Override
			void doParse(String value, ContentSecurityPolicyBuilder builder) {
				builder.blockAllMixedContent();
			}
		};

		/**
		 * Name of directive.
		 * This name is the directive label in CSP value.
		 */
		private final String name;

		SourceDirective(String name) {
			this.name = name;
		}

		/**
		 * Get {@link #name}.
		 *
		 * @return {@link #name}.
		 */
		String getName() {
			return name;
		}

		/**
		 * Parse value directive value.
		 *
		 * @param headerValue Directive value.
		 * @param builder Current builder.
		 */
		void parse(String headerValue, ContentSecurityPolicyBuilder builder) {
			String[] values = headerValue.split(" ");
			for (String value : values) {
				doParse(value, builder);
			}
		}

		/**
		 * Append {@code value} to appropriate section.
		 *
		 * @param value Value.
		 * @param builder DefaultCookieBuilder.
		 */
		abstract void doParse(String value, ContentSecurityPolicyBuilder builder);

		/**
		 * Map of directives indexed by name.
		 */
		private static final Map<String, SourceDirective> map = stream(SourceDirective.values()).collect(
			Collectors.toMap(
				(input) -> input.getName().toLowerCase(),
				Function.identity()
			)
		);

		/**
		 * Get {@link SourceDirective} by name (search is case-insensitive).
		 *
		 * @param name Name.
		 * @return Directive, may be {@code null} if name does not exist.
		 */
		static SourceDirective byName(String name) {
			return map.get(name.toLowerCase());
		}
	}

	/**
	 * Source item.
	 * The value must follow Content-Security-Policy RFC.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#source_list">https://www.w3.org/TR/CSP/#source_list</a>
	 */
	public interface Source {
		String getValue();
	}

	/**
	 * Template for {@link Source} implementation.
	 */
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

	/**
	 * Simple source item.
	 */
	static class SourceValue extends AbstractSourceValue implements Source {
		/**
		 * Source item value.
		 */
		private final String value;

		/**
		 * Create source.
		 *
		 * @param value Source value.
		 * @throws NullPointerException If {@code value} is {@code null}.
		 * @throws IllegalArgumentException If {@code value} is empty or blank.
		 */
		SourceValue(String value) {
			this.value = PreConditions.notBlank(value, "Source value must be defined");
		}

		@Override
		public String getValue() {
			return value;
		}
	}

	/**
	 * Host source, defined by:
	 * <ul>
	 * <li>Optional scheme.</li>
	 * <li>Host name (required).</li>
	 * <li>Optional port.</li>
	 * <li>Optional path.</li>
	 * </ul>
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#host_source">https://www.w3.org/TR/CSP/#host_source</a>
	 */
	private static class Host extends AbstractSourceValue implements Source {
		/**
		 * Host scheme, may be {@code null}.
		 */
		private final String scheme;

		/**
		 * Host name.
		 */
		private final String host;

		/**
		 * Host port, may be {@code null}.
		 */
		private final String port;

		/**
		 * Host path, may be {@code null}.
		 */
		private final String path;

		/**
		 * Create host.
		 *
		 * @param scheme Host scheme.
		 * @param host Host name.
		 * @param port Host port.
		 * @param path Host path.
		 * @throws NullPointerException If {@code host} is {@code null}.
		 */
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

	/**
	 * Pattern used to validate scheme value.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.1">https://tools.ietf.org/html/rfc3986#section-3.1</a>
	 */
	private static final Pattern PATTERN_SCHEME = Pattern.compile("^" + SCHEME_REGEX + "$", Pattern.CASE_INSENSITIVE);

	/**
	 * Pattern used to validate base64 value.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#base64_value">https://www.w3.org/TR/CSP/#base64_value</a>
	 */
	private static final Pattern PATTERN_BASE64 = Pattern.compile("^[a-z0-9+/]+={2}$", Pattern.CASE_INSENSITIVE);

	/**
	 * Pattern used to validate host name.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#host_part">https://www.w3.org/TR/CSP/#host_part</a>
	 */
	private static final Pattern PATTERN_HOST_NAME = Pattern.compile("^" + HOST_NAME_REGEX + "$", Pattern.CASE_INSENSITIVE);

	/**
	 * Pattern used to validate host port.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#port_part">https://www.w3.org/TR/CSP/#port_part</a>
	 */
	private static final Pattern PATTERN_PORT = Pattern.compile("^" + HOST_PORT_REGEX + "$", Pattern.CASE_INSENSITIVE);

	/**
	 * Pattern used to validate a path value.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">https://tools.ietf.org/html/rfc3986#section-3.3</a>
	 */
	private static final Pattern PATTERN_PATH = Pattern.compile(HOST_PATH_REGEX, Pattern.CASE_INSENSITIVE);

	/**
	 * Self keyword.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	private static final Source SELF = new SourceValue("'self'");

	/**
	 * None Source.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#source_list">https://www.w3.org/TR/CSP/#source_list</a>
	 */
	private static final Source NONE = new SourceValue("'none'");

	/**
	 * Unsafe-Eval keyword.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	private static final Source UNSAFE_EVAL = new SourceValue("'unsafe-eval'");

	/**
	 * Unsafe-Hashes keyword.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	private static final Source UNSAFE_HASHES = new SourceValue("'unsafe-hashes'");

	/**
	 * Strict-Dynamic keyword.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	private static final Source STRICT_DYNAMIC = new SourceValue("'strict-dynamic'");

	/**
	 * Report-Sample keyword.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	private static final Source REPORT_SAMPLE = new SourceValue("'report-sample'");

	/**
	 * Unsafe-Inline keyword.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	private static final Source UNSAFE_INLINE = new SourceValue("'unsafe-inline'");

	/**
	 * HTTP Scheme.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_source">https://www.w3.org/TR/CSP/#scheme_source</a>
	 */
	private static final Source HTTP = scheme("http");

	/**
	 * HTTPS Scheme.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_source">https://www.w3.org/TR/CSP/#scheme_source</a>
	 */
	private static final Source HTTPS = scheme("https");

	/**
	 * Data Scheme.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_source">https://www.w3.org/TR/CSP/#scheme_source</a>
	 */
	private static final Source DATA = scheme("data");

	/**
	 * All host value
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#host_part">https://www.w3.org/TR/CSP/#host_part</a>
	 */
	private static final Source ALL_HOST = new SourceValue("*");

	/**
	 * Get the self keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source self() {
		return SELF;
	}

	/**
	 * Get the none value.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#source_list">https://www.w3.org/TR/CSP/#source_list</a>
	 */
	public static Source none() {
		return NONE;
	}

	/**
	 * Get the unsafe-eval keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source unsafeEval() {
		return UNSAFE_EVAL;
	}

	/**
	 * Get the unsafe-hashes keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source unsafeHashes() {
		return UNSAFE_HASHES;
	}

	/**
	 * Get the unsafe-inline keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source unsafeInline() {
		return UNSAFE_INLINE;
	}

	/**
	 * Get the strict-dynamic keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source strictDynamic() {
		return STRICT_DYNAMIC;
	}

	/**
	 * Get the report-sample keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source reportSample() {
		return REPORT_SAMPLE;
	}

	/**
	 * Get the star matching all host.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#host_part">https://www.w3.org/TR/CSP/#host_part</a>
	 */
	public static Source allHosts() {
		return ALL_HOST;
	}

	/**
	 * Build a new scheme source.
	 * Note that the last {@code :} character will be appended.
	 *
	 * @param scheme Scheme value.
	 * @return The source item.
	 * @throws NullPointerException If {@code scheme} is null.
	 * @throws IllegalArgumentException If {@code scheme} does not match Scheme Pattern.
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_source">https://www.w3.org/TR/CSP/#scheme_source</a>
	 */
	public static Source scheme(String scheme) {
		PreConditions.notNull(scheme, "Scheme must not be null");
		PreConditions.match(scheme, PATTERN_SCHEME, String.format("Scheme %s is not a valid scheme", scheme));
		return new SourceValue(scheme + ":");
	}

	/**
	 * Build a new nonce source.
	 *
	 * @param base64 Base64 Value.
	 * @return The source item.
	 * @throws NullPointerException If {@code base64} is null.
	 * @throws IllegalArgumentException If {@code base64} does not match Base64	 Pattern.
	 * @see <a href="https://www.w3.org/TR/CSP/#nonce_source">https://www.w3.org/TR/CSP/#nonce_source</a>
	 */
	public static Source nonce(String base64) {
		PreConditions.notNull(base64, "Base64 value must not be null");
		PreConditions.match(base64, PATTERN_BASE64, String.format("%s is not a valid base64 value", base64));
		return new SourceValue("'nonce-" + base64 + "'");
	}

	/**
	 * Get HTTP scheme.
	 * This is a shortcut for {@link #scheme(String)} method.
	 *
	 * @return The source item.
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_part">https://www.w3.org/TR/CSP/#scheme_part</a>
	 */
	public static Source http() {
		return HTTP;
	}

	/**
	 * Get HTTPS scheme.
	 * This is a shortcut for {@link #scheme(String)} method.
	 *
	 * @return The source item.
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_part">https://www.w3.org/TR/CSP/#scheme_part</a>
	 */
	public static Source https() {
		return HTTPS;
	}

	/**
	 * Get Data scheme.
	 * This is a shortcut for {@link #scheme(String)} method.
	 *
	 * @return The source item.
	 * @see <a href="https://www.w3.org/TR/CSP/#scheme_part">https://www.w3.org/TR/CSP/#scheme_part</a>
	 */
	public static Source data() {
		return DATA;
	}

	private static Source algo(String algo, String base64) {
		PreConditions.notNull(base64, "Base64 value must not be null");
		PreConditions.match(base64, PATTERN_BASE64, String.format("%s is not a valid base64 value", base64));
		return new SourceValue("'" + algo + "-" + base64 + "'");
	}

	/**
	 * Build new SHA256 hash source.
	 *
	 * @param base64 Hash Value.
	 * @return Source item.
	 * @throws NullPointerException If {@code base64} is null.
	 * @throws IllegalArgumentException If {@code base64} is not a valid base64 value.
	 * @see <a href="https://www.w3.org/TR/CSP/#hash_value">https://www.w3.org/TR/CSP/#hash_value</a>
	 */
	public static Source sha256(String base64) {
		return algo("sha256", base64);
	}

	/**
	 * Build new SHA384 hash source.
	 *
	 * @param base64 Hash Value.
	 * @return Source item.
	 * @throws NullPointerException If {@code base64} is null.
	 * @throws IllegalArgumentException If {@code base64} is not a valid base64 value.
	 * @see <a href="https://www.w3.org/TR/CSP/#hash_value">https://www.w3.org/TR/CSP/#hash_value</a>
	 */
	public static Source sha384(String base64) {
		return algo("sha384", base64);
	}

	/**
	 * Build new SHA512 hash source.
	 *
	 * @param base64 Hash Value.
	 * @return Source item.
	 * @throws NullPointerException If {@code base64} is null.
	 * @throws IllegalArgumentException If {@code base64} is not a valid base64 value.
	 * @see <a href="https://www.w3.org/TR/CSP/#hash_value">https://www.w3.org/TR/CSP/#hash_value</a>
	 */
	public static Source sha512(String base64) {
		return algo("sha512", base64);
	}

	/**
	 * Build new host source.
	 *
	 * @param scheme Host scheme, may be {@code null}.
	 * @param host Host name.
	 * @param port Port value, may be {@code null} or {@code *}.
	 * @param path Path value, may be {@code null}.
	 * @return Source item.
	 * @throws NullPointerException If {@code host} is null.
	 * @throws IllegalArgumentException <ul>
	 * <li>If {@code scheme} is not a valid scheme value.</li>
	 * <li>If {@code host} is not a valid host name.</li>
	 * <li>If {@code port} is not a valid port value.</li>
	 * <li>If {@code path} is not a valid path value.</li>
	 * </ul>
	 * @see <a href="https://www.w3.org/TR/CSP/#host_source">https://www.w3.org/TR/CSP/#host_source</a>
	 */
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

	/**
	 * Build new host source.
	 *
	 * @param url Host url, must not be {@code null}.
	 * @return Source item.
	 * @throws NullPointerException If {@code url} is null.
	 * @see <a href="https://www.w3.org/TR/CSP/#host_source">https://www.w3.org/TR/CSP/#host_source</a>
	 */
	public static Source host(URL url) {
		PreConditions.notNull(url, "Host url must not be null");

		// If port is less than zero, then it means it is not set.
		int port = url.getPort();
		String portValue = port >= 0 ? String.valueOf(port) : null;

		return new Host(url.getProtocol(), url.getHost(), portValue, url.getPath());
	}

	/**
	 * Set of allowed sandbox value.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#sandbox-usage">https://www.w3.org/TR/CSP/#sandbox-usage</a>
	 */
	public enum Sandbox implements Source {
		ALLOW_SCRIPTS("allow-scripts"),
		ALLOW_SAME_ORIGIN("allow-same-origin"),
		ALLOW_FORMS("allow-forms"),
		ALLOW_POINTER_LOCK("allow-pointer-lock"),
		ALLOW_POPUPS("allow-popups"),
		ALLOW_TOP_NAVIGATION("allow-top-navigation");

		/**
		 * Label as specified by RFC.
		 */
		private final String value;

		Sandbox(String value) {
			this.value = value;
		}

		/**
		 * Label as specified by RFC.
		 *
		 * @return Sandbox value appearing in CSP value.
		 */
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

		/**
		 * Get sandbox item from value.
		 *
		 * @param value Value.
		 * @return Sandbox item, may be {@code null} if {@code value} does not exist.
		 */
		static Sandbox byValue(String value) {
			return map.get(value.toLowerCase());
		}
	}

	/**
	 * Set of allowed sandbox value.
	 *
	 * @see <a href="https://w3c.github.io/webappsec-subresource-integrity/">https://w3c.github.io/webappsec-subresource-integrity/</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/require-sri-for">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/require-sri-for</a>
	 */
	public enum RequireSriFor implements Source {
		SCRIPT("script"),
		STYLE("style");

		/**
		 * Label as specified by <a href="https://w3c.github.io/webappsec-subresource-integrity/#opt-in-require-sri-for">RFC</a>.
		 *
		 * @see <a href="https://w3c.github.io/webappsec-subresource-integrity/#opt-in-require-sri-for">https://w3c.github.io/webappsec-subresource-integrity/#opt-in-require-sri-for</a>
		 */
		private final String value;

		RequireSriFor(String value) {
			this.value = value;
		}

		/**
		 * Get {@link #value}
		 *
		 * @return {@link #value}
		 */
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

		/**
		 * Get {@link RequireSriFor} item from value.
		 *
		 * @param value Value.
		 * @return The item, may be {@code null} if {@code value} does not exist.
		 */
		static RequireSriFor byValue(String value) {
			return map.get(value.toLowerCase());
		}
	}
}
