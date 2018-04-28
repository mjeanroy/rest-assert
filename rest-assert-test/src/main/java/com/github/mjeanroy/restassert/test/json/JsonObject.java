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

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A JSON Object that can be used to serialize data to a JSON representation
 * using the {@link #toJson()} method.
 *
 * This is a very simple implementation and should be used in unit test only.
 */
public final class JsonObject implements JsonValue {

	/**
	 * Create empty JSON Object.
	 *
	 * @return The empty JSON object.
	 */
	public static JsonObject jsonObject() {
		return new JsonObject();
	}

	/**
	 * Create JSON object with given entries.
	 *
	 * @param entries JSON Object entries.
	 * @return JSON Object.
	 */
	public static JsonObject jsonObject(JsonEntry... entries) {
		JsonObject object = new JsonObject();
		for (JsonEntry entry : entries) {
			object.addEntry(entry);
		}

		return object;
	}

	/**
	 * The JSON object entries: basically a list of key-value pairs.
	 */
	private final Map<String, JsonEntry> entries;

	// Use static factory.
	private JsonObject() {
		this.entries = new LinkedHashMap<>();
	}

	/**
	 * Get {@link #entries}
	 *
	 * @return {@link #entries}
	 */
	public Collection<JsonEntry> getEntries() {
		return unmodifiableCollection(entries.values());
	}

	/**
	 * Add new entry to object, and fails if an entry with the same name already exist (fail fast).
	 *
	 * @param entry The JSON entry.
	 * @throws IllegalArgumentException If an entry with same name already exists.
	 */
	private void addEntry(JsonEntry entry) {
		if (entries.containsKey(entry.getKey())) {
			throw new IllegalArgumentException("The JSON object already contains '" + entry.getKey() + "'");
		}

		entries.put(entry.getKey(), entry);
	}

	@Override
	public String toJson() {
		if (entries.isEmpty()) {
			return "{}";
		}

		StringBuilder sub = new StringBuilder();
		boolean first = true;
		for (JsonEntry entry : entries.values()) {
			if (!first) {
				sub.append(", ");
			}

			sub.append(entry.toJson());
			first = false;
		}

		return "{" + sub.toString() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JsonObject) {
			JsonObject obj = (JsonObject) o;
			return Objects.equals(entries, obj.entries);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(entries);
	}

	@Override
	public String toString() {
		return String.format("JsonObject{entries=%s}", entries);
	}
}
