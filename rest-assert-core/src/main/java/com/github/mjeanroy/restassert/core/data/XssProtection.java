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

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderValue;

import java.net.URI;
import java.util.Objects;

import static com.github.mjeanroy.restassert.core.data.Parameter.parameter;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * Values of valid XSS protection value.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
 */
public final class XssProtection implements HttpHeaderValue {

	/**
	 * Create {@code "X-XSS-Protection"} header with {@code "DISABLE"} parameter.
	 *
	 * @return The header.
	 */
	public static XssProtection disable() {
		return new XssProtection(Directive.DISABLE, null);
	}

	/**
	 * Create {@code "X-XSS-Protection"} header with {@code "ENABLE"} parameter.
	 *
	 * @return The header.
	 */
	public static XssProtection enable() {
		return new XssProtection(Directive.ENABLE, null);
	}

	/**
	 * Create {@code "X-XSS-Protection"} header with {@code "ENABLE MODE=block"} parameter.
	 *
	 * @return The header.
	 */
	public static XssProtection enableModeBlock() {
		return new XssProtection(Directive.ENABLE, parameter("mode", "block"));
	}

	/**
	 * Create {@code "X-XSS-Protection"} header with {@code "ENABLE REPORT=<uri>"} parameter.
	 *
	 * @param uri Report URI.
	 * @return The header.
	 */
	public static XssProtection enableModeReport(String uri) {
		return new XssProtection(Directive.ENABLE, parameter("report", uri));
	}

	/**
	 * Create {@code "X-XSS-Protection"} header with {@code "ENABLE REPORT=<uri>"} parameter.
	 *
	 * @param uri Report URI.
	 * @return The header.
	 */
	public static XssProtection enableModeReport(URI uri) {
		return new XssProtection(Directive.ENABLE, parameter("report", uri.toString()));
	}

	/**
	 * The parser instance.
	 */
	private static final XssProtectionParser PARSER = new XssProtectionParser();

	/**
	 * Get parser for {@link XssProtection} instances.
	 *
	 * @return The parser.
	 */
	public static HttpHeaderParser<XssProtection> parser() {
		return PARSER;
	}

	/**
	 * The {@code "X-XSS-Protection"} directives.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
	 */
	enum Directive {
		/**
		 * Disables the XSS Protections offered by the user-agent.
		 */
		DISABLE("0") {
			@Override
			boolean match(String value) {
				return value.equals(getPrefix());
			}
		},

		/**
		 * Enables the XSS Protections.
		 */
		ENABLE("1") {
			@Override
			boolean match(String value) {
				return value.equals(getPrefix());
			}
		};

		/**
		 * The directive value.
		 */
		private final String prefix;

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
		 * Check if value raw value match specified value.
		 *
		 * @param value Raw value.
		 * @return {@code true} if {@code value} match specified value, {@code false} otherwise.
		 */
		abstract boolean match(String value);
	}

	/**
	 * The header directive.
	 */
	private final Directive directive;

	/**
	 * The directive parameter.
	 */
	private final Parameter parameter;

	/**
	 * Create {@code "X-XSS-Protection"} header.
	 *
	 * @param directive The header directive.
	 * @param parameter Directive parameter, may be {@code null}.
	 */
	XssProtection(Directive directive, Parameter parameter) {
		this.directive = notNull(directive, "X-XSS-Protection directive must be defined");
		this.parameter = parameter;
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
	 * Get {@link #parameter}
	 *
	 * @return {@link #parameter}
	 */
	public Parameter getParameter() {
		return parameter;
	}

	@Override
	public String serializeValue() {
		String output = directive.getPrefix();

		if (parameter != null) {
			output += "; " + parameter.serializeValue();
		}

		return output;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof XssProtection) {
			XssProtection x = (XssProtection) o;
			return Objects.equals(directive, x.directive) && Objects.equals(parameter, x.parameter);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(directive, parameter);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("directive", directive)
			.append("parameter", parameter)
			.build();
	}
}
