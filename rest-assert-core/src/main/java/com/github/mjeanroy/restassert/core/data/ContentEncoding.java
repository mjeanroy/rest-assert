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

import com.github.mjeanroy.restassert.core.internal.common.Collections.Mapper;
import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.Collections.map;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.join;
import static java.util.Collections.singletonList;

/**
 * The list of available content-encoding values.
 */
public final class ContentEncoding implements HeaderValue {

	/**
	 * Create {@code "GZIP"} {@code "Content-Encoding"} header.
	 *
	 * @return The {@code "Content-Encoding"} header.
	 */
	public static ContentEncoding gzip() {
		return new ContentEncoding(singletonList(Directive.GZIP));
	}

	/**
	 * Create {@code "DEFLATE"} {@code "Content-Encoding"} header.
	 *
	 * @return The {@code "Content-Encoding"} header.
	 */
	public static ContentEncoding deflate() {
		return new ContentEncoding(singletonList(Directive.DEFLATE));
	}

	/**
	 * Create {@code "BR"} {@code "Content-Encoding"} header.
	 *
	 * @return The {@code "Content-Encoding"} header.
	 */
	public static ContentEncoding br() {
		return new ContentEncoding(singletonList(Directive.BR));
	}

	/**
	 * The parser instance.
	 */
	private static final ContentEncodingParser PARSER = new ContentEncodingParser();

	/**
	 * Get {@link ContentEncoding} parser.
	 *
	 * @return The parser.
	 */
	public static ContentEncodingParser parser() {
		return PARSER;
	}

	/**
	 * Content-Encoding directive.
	 *
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5</a>
	 */
	enum Directive {
		GZIP("gzip"),
		COMPRESS("compress"),
		DEFLATE("deflate"),
		IDENTITY("identity"),
		BR("br");

		/**
		 * The directive value, as it should appear in
		 * header raw value.
		 */
		private final String value;

		/**
		 * Create the directive.
		 *
		 * @param value The value.
		 */
		Directive (String value) {
			this.value = value;
		}

		/**
		 * Get {@link #value}
		 *
		 * @return {@link #value}
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Index of directive, each one being identitied by its value.
		 */
		private static final Map<String, Directive> map;

		static {
			map = new HashMap<>();
			for (Directive directive : Directive.values()) {
				map.put(directive.getValue(), directive);
			}
		}

		/**
		 * Get the directive element by its value.
		 *
		 * @param value Directive value.
		 * @return The directive.
		 */
		static Directive byValue(String value) {
			return map.get(value);
		}
	}

	/**
	 * The {@code "Content-Encoding"} directives, order is important here since encoding
	 * order defines transformation order.
	 */
	private final List<Directive> directives;

	/**
	 * Create the {@code "Content-Encoding"} header.
	 *
	 * @param directives List of directives.
	 */
	ContentEncoding(List<Directive> directives) {
		this.directives = directives;
	}

	/**
	 * Get {@link #directives}
	 *
	 * @return {@link #directives}
	 */
	public List<Directive> getDirectives() {
		return directives;
	}

	@Override
	public String serializeValue() {
		List<String> directiveValues = map(directives, new Mapper<Directive, String>() {
			@Override
			public String apply(Directive input) {
				return input.getValue();
			}
		});

		return join(directiveValues, ", ");
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof ContentEncoding) {
			ContentEncoding c = (ContentEncoding) o;
			return Objects.equals(directives, c.directives);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(directives);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("directives", directives)
			.build();
	}
}
