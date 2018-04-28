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

import static com.github.mjeanroy.restassert.test.json.JsonUtil.jsonEscape;

import java.util.Objects;

/**
 * A JSON entry: contains a key name (as a {@link String} and a value (basically anything
 * that can be translated to JSON).
 *
 * This is a very simple implementation and should be used in unit test only.
 */
public final class JsonEntry implements JsonValue {

	/**
	 * Create new JSON entry.
	 *
	 * @param name The JSON entry name.
	 * @param value The JSON entry value.
	 * @return The JSON pair.
	 */
	public static JsonEntry jsonEntry(String name, Object value) {
		return new JsonEntry(name, value);
	}

	/**
	 * The entry key.
	 */
	private final String key;

	/**
	 * The entry value.
	 */
	private final Object value;

	// Use static factory.
	private JsonEntry(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Get {@link #key}
	 *
	 * @return {@link #key}
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get {@link #value}
	 *
	 * @return {@link #value}
	 */
	public Object getValue() {
		return value;
	}

	@Override
	public String toJson() {
		return "\"" + jsonEscape(key) + "\" : " + JsonUtil.formatValue(value);
	}

	@Override
	public String toString() {
		return String.format("JsonEntry{key=%s, value=%s}", key, value);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JsonEntry) {
			JsonEntry e = (JsonEntry) o;
			return Objects.equals(key, e.key) && Objects.equals(value, e.value);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}
}
