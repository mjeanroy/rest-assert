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

package com.github.mjeanroy.restassert.test.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.unmodifiableMap;

/**
 * Implementation of (immutable) JSON Object.
 */
public final class JSONObject {

	/**
	 * Object values.
	 */
	private final Map<String, Object> values;

	public JSONObject() {
		this(new HashMap<>());
	}

	private JSONObject(Map<String, Object> values) {
		this.values = unmodifiableMap(new HashMap<>(values));
	}

	/**
	 * Put given entry.
	 *
	 * @param entry Entry.
	 * @param other Other entries.
	 * @return The JSON object.
	 */
	public JSONObject add(JSONEntry entry, JSONEntry... other) {
		return put(entry, other);
	}

	/**
	 * Get value at given path.
	 *
	 * @param path The path.
	 * @return The value.
	 */
	public Object getValue(String path) {
		String name = path.startsWith("$.") ? path.substring(2) : path;
		return values.get(name);
	}

	/**
	 * Get JSON representation of given array.
	 *
	 * @return JSON representation.
	 */
	public String toJSON() {
		return JSONTestUtils.toJSON(this);
	}

	/**
	 * Get object size.
	 *
	 * @return Size.
	 */
	public int size() {
		return values.size();
	}

	Map<String, Object> values() {
		return this.values;
	}

	private JSONObject put(JSONEntry entry, JSONEntry... others) {
		Map<String, Object> newValues = new HashMap<>(this.values);
		newValues.put(entry.getName(), entry.getValue());

		for (JSONEntry other : others) {
			newValues.put(other.getName(), other.getValue());
		}

		return new JSONObject(newValues);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JSONObject) {
			JSONObject that = (JSONObject) o;
			return Objects.equals(values, that.values);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}
}
