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

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.mjeanroy.restassert.core.data.Parameter.parameter;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.map;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.join;
import static java.util.Collections.singletonMap;

/**
 * A model for Content-Type values.
 *
 * @see <a href="http://tools.ietf.org/html/rfc2045">http://tools.ietf.org/html/rfc2045</a>
 */
public final class ContentType implements HeaderValue {

	private static final String CHARSET_PARAMETER_NAME = "charset";

	/**
	 * Create content-type with given media type and charset.
	 *
	 * @param mediaType The media type.
	 * @param charset The charset.
	 * @return The associated {@link ContentType}.
	 */
	public static ContentType contentType(MediaType mediaType, Charset charset) {
		Parameter parameter = parameter(CHARSET_PARAMETER_NAME, charset.displayName().toLowerCase());
		return new ContentType(mediaType, singletonMap(CHARSET_PARAMETER_NAME, parameter));
	}

	/**
	 * Create content-type with given media type without charset information.
	 *
	 * @param mediaType The media type.
	 * @return The associated {@link ContentType}.
	 */
	public static ContentType contentType(MediaType mediaType) {
		return new ContentType(mediaType, java.util.Collections.<String, Parameter>emptyMap());
	}

	/**
	 * The parser instance.
	 */
	private static final ContentTypeParser PARSER = new ContentTypeParser();

	/**
	 * A parser for {@link ContentType} values.
	 *
	 * @return The parser instance.
	 */
	public static ContentTypeParser parser() {
		return PARSER;
	}

	/**
	 * The media type.
	 */
	private final MediaType mediaType;

	/**
	 * The charset, may be {@code null}.
	 */
	private final Map<String, Parameter> parameters;

	/**
	 * Create content-type value.
	 *
	 * @param mediaType The media type, must not be {@code null}.
	 * @param parameters The header parameters, such as charset.
	 * @throws NullPointerException If {@code mediaType} is {@code null}.
	 */
	ContentType(MediaType mediaType, Map<String, Parameter> parameters) {
		this.mediaType = notNull(mediaType, "Media Type must not be null");
		this.parameters = parameters;
	}

	/**
	 * Get {@link #mediaType}
	 *
	 * @return {@link #mediaType}
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * Get {@link #parameters}
	 *
	 * @return {@link #parameters}
	 */
	public Collection<Parameter> getParameters() {
		return parameters.values();
	}

	/**
	 * Get parameter by its name.
	 *
	 * @param name Parameter name.
	 * @return The parameter, {@code null} if parameter was not defined.
	 */
	public Parameter getParameter(String name) {
		return parameters.get(name.toLowerCase());
	}

	/**
	 * Get the {@code charset} parameter, this is a shortcut for {@link #getParameter(String)}
	 * called with {@code "charset"}.
	 *
	 * @return The charset parameter, {@code null} if it was not defined.
	 */
	public String getCharset() {
		Parameter parameter = parameters.get(CHARSET_PARAMETER_NAME);
		return parameter == null ? null : parameter.getValue();
	}

	@Override
	public String serializeValue() {
		String output = mediaType.serializeValue();

		if (!parameters.isEmpty()) {
			List<String> options = map(parameters.values(), new Mapper<Parameter, String>() {
				@Override
				public String apply(Parameter input) {
					return input.serializeValue();
				}
			});

			output += "; " + join(options, "; ");
		}

		return output;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof ContentType) {
			ContentType ct = (ContentType) o;
			return Objects.equals(mediaType, ct.mediaType) && Objects.equals(parameters, ct.parameters);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mediaType, parameters);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("mediaType", mediaType)
			.append("parameters", parameters)
			.build();
	}

}
