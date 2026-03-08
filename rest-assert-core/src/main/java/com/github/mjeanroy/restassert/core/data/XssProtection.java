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

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderValue;

import java.net.URI;
import java.util.Objects;

import static com.github.mjeanroy.restassert.core.data.Parameter.parameter;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/// Values of valid XSS protection value ([MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection)).
public final class XssProtection implements HttpHeaderValue {

	/// Create `"X-XSS-Protection"` header with `"DISABLE"` parameter.
	///
	/// @return The header.
	public static XssProtection disable() {
		return new XssProtection(Directive.DISABLE, null);
	}

	/// Create `"X-XSS-Protection"` header with `"ENABLE"` parameter.
	///
	/// @return The header.
	public static XssProtection enable() {
		return new XssProtection(Directive.ENABLE, null);
	}

	/// Create `"X-XSS-Protection"` header with `"ENABLE MODE=block"` parameter.
	///
	/// @return The header.
	public static XssProtection enableModeBlock() {
		return new XssProtection(Directive.ENABLE, parameter("mode", "block"));
	}

	/// Create `"X-XSS-Protection"` header with `"ENABLE REPORT=<uri>"` parameter.
	///
	/// @param uri Report URI.
	/// @return The header.
	public static XssProtection enableModeReport(String uri) {
		return new XssProtection(Directive.ENABLE, parameter("report", uri));
	}

	/// Create `"X-XSS-Protection"` header with `"ENABLE REPORT=<uri>"` parameter.
	///
	/// @param uri Report URI.
	/// @return The header.
	public static XssProtection enableModeReport(URI uri) {
		return new XssProtection(Directive.ENABLE, parameter("report", uri.toString()));
	}

	/// The parser instance.
	private static final XssProtectionParser PARSER = new XssProtectionParser();

	/// Get parser for [XssProtection] instances.
	///
	/// @return The parser.
	public static HttpHeaderParser<XssProtection> parser() {
		return PARSER;
	}

	/// The `"X-XSS-Protection"` directives ([MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection)).
	public enum Directive {
		/// Disables the XSS Protections offered by the user-agent.
		DISABLE("0") {
			@Override
			boolean match(String value) {
				return value.equals(getPrefix());
			}
		},

		/// Enables the XSS Protections.
		ENABLE("1") {
			@Override
			boolean match(String value) {
				return value.equals(getPrefix());
			}
		};

		/// The directive value.
		private final String prefix;

		Directive(String prefix) {
			this.prefix = prefix;
		}

		/// Get [#prefix]
		///
		/// @return Returns [#prefix]
		String getPrefix() {
			return prefix;
		}

		/// Check if value raw value match specified value.
		///
		/// @param value Raw value.
		/// @return `true` if `value` match specified value, `false` otherwise.
		abstract boolean match(String value);
	}

	/// The header directive.
	private final Directive directive;

	/// The directive parameter.
	private final Parameter parameter;

	/// Create `"X-XSS-Protection"` header.
	///
	/// @param directive The header directive.
	/// @param parameter Directive parameter, may be `null`.
	XssProtection(Directive directive, Parameter parameter) {
		this.directive = notNull(directive, "X-XSS-Protection directive must be defined");
		this.parameter = parameter;
	}

	/// Get [#directive]
	///
	/// @return Returns [#directive]
	public Directive getDirective() {
		return directive;
	}

	/// Get [#parameter]
	///
	/// @return Returns [#parameter]
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
