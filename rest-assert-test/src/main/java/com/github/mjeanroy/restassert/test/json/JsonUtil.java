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

package com.github.mjeanroy.restassert.test.json;

/**
 * Static JSON Utilities.
 */
final class JsonUtil {

	// Ensure non instantiation.
	private JsonUtil() {
	}

	/**
	 * Try to format value to a JSON string value:
	 *
	 * <ul>
	 *   <li>If {@code value} is {@code null}, {@code null} is returned.</li>
	 *   <li>If {@code value} is a {@link String}, then the string prefixed/suffixed with double quote is returned.</li>
	 *   <li>If {@code value} is a {@link JsonValue} instance, the result of {@link JsonValue#toJson()} is returned.</li>
	 *   <li>Otherwise, the result of {@link #toString()} is returned.</li>
	 * </ul>
	 *
	 * @param value The value to format.
	 * @return The JSON string value.
	 */
	static String formatValue(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof String) {
			return "\"" + jsonEscape(value.toString()) + "\"";
		}

		if (value instanceof JsonValue) {
			return ((JsonValue) value).toJson();
		}

		return jsonEscape(value.toString());
	}

	/**
	 * Escape value to be json compliant (i.e all double quotes are escaped).
	 *
	 * @param value Input value.
	 * @return Escaped output value.
	 */
	static String jsonEscape(String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}

		return value.replace("\"", "\\\"");
	}
}
