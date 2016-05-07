/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.data;

import com.github.mjeanroy.rest_assert.utils.Mapper;
import com.github.mjeanroy.rest_assert.utils.PreConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

import static com.github.mjeanroy.rest_assert.utils.Utils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;

/**
 * Content-Security-Policy Header.
 *
 * @see <a href="https://www.w3.org/TR/CSP/">https://www.w3.org/TR/CSP/</a>
 */
public class ContentSecurityPolicy implements HeaderValue {

	/**
	 * Class logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ContentSecurityPolicy.class);

	/**
	 * List of header directives.
	 */
	private final Map<SourceDirective, Set<Source>> directives;

	/**
	 * Create CSP header object.
	 *
	 * @param sources Header directives.
	 */
	private ContentSecurityPolicy(Map<SourceDirective, Set<Source>> sources) {
		this.directives = new LinkedHashMap<>();

		// Make a deep copy.
		for (Map.Entry<SourceDirective, Set<Source>> entry : sources.entrySet()) {
			this.directives.put(entry.getKey(), new LinkedHashSet<>(entry.getValue()));
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof ContentSecurityPolicy) {
			ContentSecurityPolicy csp = (ContentSecurityPolicy) o;
			return directives.equals(csp.directives);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return directives.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (SourceDirective directive : SourceDirective.values()) {
			if (directives.containsKey(directive)) {
				if (sb.length() != 0) {
					sb.append(" ");
				}

				StringBuilder allValues = new StringBuilder();
				for (Source src : directives.get(directive)) {
					if (allValues.length() != 0) {
						allValues.append(" ");
					}

					allValues.append(src.getValue());
				}

				sb.append(new StringBuilder()
						.append(directive.getName())
						.append(" ")
						.append(allValues)
						.toString()
						.trim()
					).append(";");
			}
		}

		return sb.toString();
	}

	@Override
	public String value() {
		return toString();
	}

	@Override
	public boolean match(String actualValue) {
		log.debug("Parsing Content-Security-Policy header: '{}'", actualValue);

		String[] directives = actualValue.split(";");
		Builder builder = new Builder();

		for (String directive : directives) {
			StringBuilder nameBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();

			StringBuilder current = nameBuilder;
			for (char c : directive.trim().toCharArray()) {
				if (Character.isWhitespace(c)) {
					current = valueBuilder;
				}

				current.append(c);
			}

			String name = nameBuilder.toString().trim();
			String value = valueBuilder.toString().trim();

			log.debug("-> Found directive: '{} {}'", name, value);

			// Check if directive has name.
			if (name.isEmpty()) {
				log.error("Directive name is empty, fail");
				throw new IllegalArgumentException(String.format("Header %s is not a valid Content-Security-Policy header", actualValue));
			}

			SourceDirective dir = SourceDirective.byName(name);

			// Check if name is valid.
			if (dir == null) {
				log.error("Cannot find a matching for directive '{}', fail", name);
				throw new IllegalArgumentException(String.format("Cannot parse Content-Security-Policy header since directive %s seems not valid", name));
			}

			log.debug("  - Found directive: {}", dir);
			log.debug("  - Parse directive value: '{}'", value);
			dir.parse(value, builder);
		}

		ContentSecurityPolicy csp = builder.build();
		return this.equals(csp);
	}

	/**
	 * List of CSP directive.
	 */
	private static enum SourceDirective {
		/**
		 * Handle {@code base-uri} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-base-uri">https://www.w3.org/TR/CSP/#directive-base-uri</a>
		 */
		BASE_URI("base-uri") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addBaseUri(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code default-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-default-src">https://www.w3.org/TR/CSP/#directive-default-src</a>
		 */
		DEFAULT_SRC("default-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addDefaultSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code script-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-script-src">https://www.w3.org/TR/CSP/#directive-script-src</a>
		 */
		SCRIPT_SRC("script-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addScriptSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code style-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-style-src">https://www.w3.org/TR/CSP/#directive-style-src</a>
		 */
		STYLE_SRC("style-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addStyleSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code object-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-object-src">https://www.w3.org/TR/CSP/#directive-object-src</a>
		 */
		OBJECT_SRC("object-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addObjectSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code media-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-media-src">https://www.w3.org/TR/CSP/#directive-media-src</a>
		 */
		MEDIA_SRC("media-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addMediaSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code img-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-img-src">https://www.w3.org/TR/CSP/#directive-img-src</a>
		 */
		IMG_SRC("img-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addImgSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code font-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-font-src">https://www.w3.org/TR/CSP/#directive-font-src</a>
		 */
		FONT_SRC("font-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addFontSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code connect-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-connect-src">https://www.w3.org/TR/CSP/#directive-connect-src</a>
		 */
		CONNECT_SRC("connect-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addConnectSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code child-src} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-child-src">https://www.w3.org/TR/CSP/#directive-child-src</a>
		 */
		CHILD_SRC("child-src") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addChildSrc(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code form-action} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-form-action">https://www.w3.org/TR/CSP/#directive-form-action</a>
		 */
		FORM_ACTION("form-action") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addFormAction(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code frame-ancestors} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-frame-ancestors">https://www.w3.org/TR/CSP/#directive-frame-ancestors</a>
		 */
		FRAME_ANCESTORS("frame-ancestors") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addFrameAncestors(new SourceValue(value));
			}
		},

		/**
		 * Handle {@code plugin-types} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-plugin-types">https://www.w3.org/TR/CSP/#directive-plugin-types</a>
		 */
		PLUGIN_TYPES("plugin-types") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addPluginTypes(value);
			}
		},

		/**
		 * Handle {@code report-uri} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-report-uri">https://www.w3.org/TR/CSP/#directive-report-uri</a>
		 */
		REPORT_URI("report-uri") {
			@Override
			void doParse(String value, Builder builder) {
				builder.addReportUri(value);
			}
		},

		/**
		 * Handle {@code sandbox} directive.
		 *
		 * @see <a href="https://www.w3.org/TR/CSP/#directive-sandbox">https://www.w3.org/TR/CSP/#directive-sandbox</a>
		 */
		SANDBOX("sandbox") {
			@Override
			void doParse(String value, Builder builder) {
				Sandbox sandbox = Sandbox.byValue(value);
				builder.addSandbox(sandbox);
			}
		},

		BLOCK_ALL_MIXED_CONTENT("block-all-mixed-content") {
			@Override
			void parse(String headerValue, Builder builder) {
				builder.blockAllMixedContent();
			}

			@Override
			void doParse(String value, Builder builder) {

			}
		};

		/**
		 * Name of directive.
		 * This name is the directive label in CSP header.
		 */
		private final String name;

		private SourceDirective(String name) {
			this.name = name;
		}

		/**
		 * Get {@link #name}.
		 *
		 * @return {@link #name}.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Parse header directive value.
		 *
		 * @param headerValue Directive value.
		 * @param builder Current builder.
		 */
		void parse(String headerValue, Builder builder) {
			String[] values = headerValue.split(" ");
			for (String value : values) {
				doParse(value, builder);
			}
		}

		/**
		 * Append {@code value} to appropriate section.
		 *
		 * @param value Value.
		 * @param builder Builder.
		 */
		abstract void doParse(String value, Builder builder);

		/**
		 * Map of directives indexed by name.
		 */
		private static final Map<String, SourceDirective> map;

		static {
			Map<String, SourceDirective> index = new HashMap<>();
			for (SourceDirective directive : SourceDirective.values()) {
				index.put(directive.getName().toLowerCase(), directive);
			}

			map = unmodifiableMap(index);
		}

		/**
		 * Get {@link SourceDirective} by name (search is case-insensitive).
		 *
		 * @param name Name.
		 * @return Directive, may be {@code null} if name does not exist.
		 */
		public static SourceDirective byName(String name) {
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
	private abstract static class AbstractSourceValue implements Source {
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
	private static class SourceValue extends AbstractSourceValue implements Source {
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
		private SourceValue(String value) {
			this.value = PreConditions.notBlank(value, "Source value must be defined");
		}

		@Override
		public String getValue() {
			return value;
		}
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

	// Empty source, always return empty string.
	private static final Source EMPTY_SOURCE = new AbstractSourceValue() {
		@Override
		public String getValue() {
			return "";
		}
	};

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

	private static final String SCHEME_REGEX = "[a-z][a-z0-9\\+\\-\\.]*";
	private static final String HOST_NAME_REGEX = "(\\*\\.)?([a-z0-9\\-])+(\\.[a-z0-9\\-]+)*";
	private static final String HOST_PORT_REGEX = "([0-9]+)|\\*";
	private static final String HOST_PATH_REGEX = "([^?#]*)";

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
	private static final Pattern PATTERN_BASE64 = Pattern.compile("^[a-z0-9\\+/]+={2}$", Pattern.CASE_INSENSITIVE);

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
	 * Get the unsafe-inline keyword.
	 *
	 * @return Self Source.
	 * @see <a href="https://www.w3.org/TR/CSP/#keyword_source">https://www.w3.org/TR/CSP/#keyword_source</a>
	 */
	public static Source unsafeInline() {
		return UNSAFE_INLINE;
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
	 * Validator interface.
	 */
	private interface SourceValidator {
		void validate(Source src);
	}

	/**
	 * Validator used as a fallback.
	 */
	private static SourceValidator noOpValidator = new SourceValidator() {
		@Override
		public void validate(Source src) {
		}
	};

	/**
	 * Set of allowed sandbox value.
	 *
	 * @see <a href="https://www.w3.org/TR/CSP/#sandbox-usage">https://www.w3.org/TR/CSP/#sandbox-usage</a>
	 */
	public static enum Sandbox implements Source {
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

		private Sandbox(String value) {
			this.value = value;
		}

		/**
		 * Label as specified by RFC.
		 *
		 * @return Sandbox value appearing in CSP header.
		 */
		@Override
		public String getValue() {
			return value;
		}

		private static final Map<String, Sandbox> map;

		static {
			Map<String, Sandbox> index = new HashMap<>();
			for (Sandbox sandbox : Sandbox.values()) {
				index.put(sandbox.getValue().toLowerCase(), sandbox);
			}

			map = unmodifiableMap(index);
		}

		/**
		 * Get sandbox item from value.
		 *
		 * @param value Value.
		 * @return Sandbox item, may be {@code null} if {@code value} does not exist.
		 */
		public static Sandbox byValue(String value) {
			return map.get(value.toLowerCase());
		}
	}

	/**
	 * Builder used to create {@link ContentSecurityPolicy} instances.
	 */
	public static class Builder {
		/**
		 * List of header directives.
		 */
		private final Map<SourceDirective, Set<Source>> sources;

		/**
		 * Create builder.
		 */
		public Builder() {
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
		public Builder addBaseUri(Source src, Source... other) {
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
		public Builder addDefaultSrc(Source src, Source... other) {
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
		public Builder addScriptSrc(Source src, Source... other) {
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
		public Builder addStyleSrc(Source src, Source... other) {
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
		public Builder addFontSrc(Source src, Source... other) {
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
		public Builder addObjectSrc(Source src, Source... other) {
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
		public Builder addMediaSrc(Source src, Source... other) {
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
		public Builder addImgSrc(Source src, Source... other) {
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
		public Builder addConnectSrc(Source src, Source... other) {
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
		public Builder addChildSrc(Source src, Source... other) {
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
		public Builder addFormAction(Source src, Source... other) {
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
		public Builder addFrameAncestors(Source src, Source... other) {
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
		public Builder addPluginTypes(String mediaType, String... other) {
			Source src = new SourceValue(mediaType);
			List<Source> otherSources = map(other, new Mapper<String, Source>() {
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
		public Builder addReportUri(String uri, String... other) {
			Source src = new UriSource(uri);
			List<Source> otherSources = map(other, new Mapper<String, Source>() {
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
		public Builder addReportUri(URI uri, URI... other) {
			Source src = new UriSource(uri);
			List<Source> otherSources = map(other, new Mapper<URI, Source>() {
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
		public Builder addSandbox(Sandbox sandbox, Sandbox... other) {
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
		public Builder blockAllMixedContent() {
			return add(SourceDirective.BLOCK_ALL_MIXED_CONTENT, EMPTY_SOURCE, Collections.<Source>emptyList());
		}

		private Builder add(SourceDirective directive, Source src, List<Source> other) {
			return add(directive, src, other, noOpValidator);
		}

		private Builder add(SourceDirective directive, Source src, List<Source> other, SourceValidator validator) {
			Set<Source> list = sources.get(directive);
			if (list == null) {
				list = new LinkedHashSet<>();
				sources.put(directive, list);
			}

			// First check validity
			PreConditions.notNull(src, "Source value must not be null");
			validator.validate(src);

			for (Source o : other) {
				PreConditions.notNull(o, "Source value must not be null");
				validator.validate(o);
			}

			list.add(src);
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
	}
}
