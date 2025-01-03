/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.json.parsers;

import com.github.mjeanroy.restassert.core.internal.json.JsonException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.trimToNull;

/**
 * Abstract json parser.
 */
abstract class AbstractJsonParser implements JsonParser {

	@Override
	public final Object parse(String json) {
		String trimmedJson = notNull(trimToNull(json), "JSON");

		try {
			return translateValue(
				doParse(trimmedJson)
			);
		}
		catch (Exception ex) {
			throw new JsonException(ex);
		}
	}

	abstract Object doParse(String json);

	@SuppressWarnings("unchecked")
	private static Object translateValue(Object value) {
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}

		if (value instanceof Map) {
			return Collections.unmodifiableMap((Map<String, Object>) value);
		}

		if (value instanceof List) {
			return Collections.unmodifiableList((List<Object>) value);
		}

		return value;
	}
}
