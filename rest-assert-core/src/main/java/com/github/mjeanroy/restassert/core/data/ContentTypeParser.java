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

import com.github.mjeanroy.restassert.core.internal.data.AbstractHeaderParser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.mjeanroy.restassert.core.data.Parameter.parameter;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.isQuoted;
import static java.util.Collections.unmodifiableMap;

/**
 * Parser that translate {@link String} to {@link ContentType} values.
 *
 * @see <a href="http://tools.ietf.org/html/rfc2045">http://tools.ietf.org/html/rfc2045</a>
 */
public final class ContentTypeParser extends AbstractHeaderParser<ContentType> {

	// Ensure no public instantiation.
	ContentTypeParser() {
	}

	@Override
	protected ContentType doParse(String value) {
		String[] parts = value.split(";");
		MediaType mediaType = MediaType.parser().parse(parts[0].trim().toLowerCase());

		int nbParts = parts.length;
		if (nbParts == 1) {
			return new ContentType(mediaType, Collections.<String, Parameter>emptyMap());
		}

		Map<String, Parameter> parameters = new LinkedHashMap<>();

		for (int i = 1; i < nbParts; ++i) {
			String part = parts[i].toLowerCase();
			String[] parameter = part.split("=", 2);
			String parameterName = parameter[0].trim();
			String parameterValue = parameter.length == 2 ? parameter[1].trim() : "";

			if (isQuoted(parameterValue)) {
				parameterValue = parameterValue.substring(1, parameterValue.length() - 1);
			}

			if (!parameters.containsKey(parameterName)) {
				parameters.put(parameterName, parameter(parameterName, parameterValue));
			}
		}

		return new ContentType(mediaType, unmodifiableMap(parameters));
	}
}
