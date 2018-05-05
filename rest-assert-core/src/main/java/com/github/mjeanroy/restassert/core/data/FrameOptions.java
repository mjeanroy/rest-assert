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

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.internal.common.Strings.join;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableSet;

/**
 * Values of valid X-Frame-Options directive.
 * Specification: https://tools.ietf.org/html/rfc7034
 *
 * Important: values are case-insensitive!
 *
 * @see <a href="https://tools.ietf.org/html/rfc7034">https://tools.ietf.org/html/rfc7034</a>
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options</a>
 */
public final class FrameOptions implements HeaderValue {

	private static final FrameOptions DENY = new FrameOptions(Directive.DENY);
	private static final FrameOptions SAME_ORIGIN = new FrameOptions(Directive.SAME_ORIGIN);

	/**
	 * Get the {@code "DENY"} {@code "X-Frame-Options"} header.
	 *
	 * @return The {@code "X-Frame-Options"} header.
	 */
	public static FrameOptions deny() {
		return DENY;
	}

	/**
	 * Get the {@code "SAMEORIGIN"} {@code "X-Frame-Options"} header.
	 *
	 * @return The {@code "X-Frame-Options"} header.
	 */
	public static FrameOptions sameOrigin() {
		return SAME_ORIGIN;
	}

	/**
	 * Get the {@code "ALLOW-FROM"} {@code "X-Frame-Options"} header with given URI.
	 *
	 * @param uri The given URI.
	 * @return The {@code "X-Frame-Options"} header.
	 */
	public static FrameOptions allowFrom(String uri) {
		return allowFrom(URI.create(uri));
	}

	/**
	 * Get the {@code "ALLOW-FROM"} {@code "X-Frame-Options"} header with given URI.
	 *
	 * @param uri The given URI.
	 * @return The {@code "X-Frame-Options"} header.
	 */
	public static FrameOptions allowFrom(URI uri) {
		return new FrameOptions(Directive.ALLOW_FROM, singleton(uri.toString()));
	}

	/**
	 * The {@code X-Frame-Options} directives.
	 */
	enum Directive {
		/**
		 * A browser receiving content with this directive MUST NOT display
		 * this content in any frame.
		 */
		DENY("DENY") {
			@Override
			FrameOptions parse(String value) {
				return FrameOptions.DENY;
			}
		},

		/**
		 * A browser receiving content with this directive MUST NOT display
		 * this content in any frame from a page of different origin than
		 * the content itself.
		 *
		 * If a browser or plugin can not reliably determine whether the
		 * origin of the content and the frame have the same origin, this
		 * MUST be treated as "DENY".
		 */
		SAME_ORIGIN("SAMEORIGIN") {
			@Override
			FrameOptions parse(String value) {
				return FrameOptions.SAME_ORIGIN;
			}
		},

		/**
		 * A browser receiving content with this directive MUST NOT display
		 * this content in any frame from a page of different origin than
		 * the listed origin.  While this can expose the page to risks by
		 * the trusted origin, in some cases it may be necessary to use
		 * content from other domains.
		 */
		ALLOW_FROM("ALLOW-FROM") {
			@Override
			boolean match(String value) {
				return value.startsWith(getPrefix());
			}

			@Override
			FrameOptions parse(String value) {
				String[] parts = value.split(" ", 2);
				if (parts.length != 2) {
					return null;
				}

				String[] options = parts[1].split(" ");
				Set<String> uris = new LinkedHashSet<>();
				for (String opt : options) {
					String trimmedOpt = opt.trim();
					if (!trimmedOpt.isEmpty()) {
						uris.add(trimmedOpt);
					}
				}

				return new FrameOptions(Directive.ALLOW_FROM, unmodifiableSet(uris));
			}
		};

		/**
		 * The directive directive.
		 */
		private final String prefix;

		/**
		 * Create the directive using the directive prefix.
		 *
		 * @param prefix Directive prefix.
		 */
		Directive(String prefix) {
			this.prefix = prefix;
		}

		/**
		 * Get {@link #prefix}
		 *
		 * @return {@link #prefix}
		 */
		String getPrefix() {
			return prefix;
		}

		/**
		 * Check if value match given directive name.
		 *
		 * @param value The header raw value.
		 * @return The
		 */
		boolean match(String value) {
			return getPrefix().equals(value);
		}

		/**
		 * Parse header and return structured {@link FrameOptions} header.
		 *
		 * @param value The header raw value.
		 * @return The header.
		 */
		abstract FrameOptions parse(String value);
	}

	/**
	 * The parser instance.
	 */
	private static final FrameOptionsParser PARSER = new FrameOptionsParser();

	/**
	 * Get parser for {@link FrameOptions} instances.
	 *
	 * @return The parser.
	 */
	public static FrameOptionsParser parser() {
		return PARSER;
	}

	/**
	 * The header directive value.
	 */
	private final Directive directive;

	/**
	 * The header directive options.
	 */
	private final Set<String> options;

	/**
	 * Create FrameOptions directive.
	 *
	 * @param value The directive mode.
	 * @param options Directive options.
	 */
	private FrameOptions(Directive value, Set<String> options) {
		this.directive = value;
		this.options = options;
	}

	/**
	 * Create FrameOptions directive without any options.
	 *
	 * @param directive The directive mode.
	 */
	private FrameOptions(Directive directive) {
		this(directive, Collections.<String>emptySet());
	}

	/**
	 * Get {@link #directive}
	 *
	 * @return {@link #directive}
	 */
	public Directive getDirective() {
		return directive;
	}

	/**
	 * Get {@link #options}
	 *
	 * @return {@link #options}
	 */
	public Set<String> getOptions() {
		return options;
	}

	@Override
	public String serializeValue() {
		String opts = options.isEmpty() ? "" : " " + join(options, " ");
		return directive.getPrefix() + opts;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof FrameOptions) {
			FrameOptions f = (FrameOptions) o;
			return Objects.equals(directive, f.directive) && Objects.equals(options, f.options);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(directive, options);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("directive", directive)
			.append("options", options)
			.build();
	}
}
