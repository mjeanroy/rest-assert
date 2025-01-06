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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Static JSON helpers, to use in tests.
 */
public final class JSONTestUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private JSONTestUtils() {
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with null value.
	 *
	 * @param name JSON key.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name) {
		return new JSONEntry(name, null);
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with given key and {@code int} value.
	 *
	 * @param name JSON key.
	 * @param value JSON value.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name, int value) {
		return new JSONEntry(name, value);
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with given key and {@code double} value.
	 *
	 * @param name JSON key.
	 * @param value JSON value.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name, double value) {
		return new JSONEntry(name, value);
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with given key and {@code boolean} value.
	 *
	 * @param name JSON key.
	 * @param value JSON value.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name, boolean value) {
		return new JSONEntry(name, value);
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with given key and {@code String} value.
	 *
	 * @param name JSON key.
	 * @param value JSON value.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name, String value) {
		return new JSONEntry(name, value);
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with null value.
	 *
	 * @param name JSON key.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name, JSONObject object) {
		return new JSONEntry(name, object.values());
	}

	/**
	 * Create JSON entry (i.e JSON object entry) with given key and {@code String} value.
	 *
	 * @param name JSON key.
	 * @param value JSON value.
	 * @return The JSON entry.
	 */
	public static JSONEntry jsonEntry(String name, JSONArray value) {
		return new JSONEntry(name, value.values());
	}

	/**
	 * Create empty JSON object.
	 *
	 * @return JSON Object.
	 */
	public static JSONObject jsonObject() {
		return new JSONObject();
	}

	/**
	 * Create JSON object.
	 *
	 * @return JSON Object.
	 */
	public static JSONObject jsonObject(JSONEntry entry, JSONEntry... others) {
		return new JSONObject().add(entry, others);
	}

	/**
	 * Create empty JSON array.
	 * @return JSON Array.
	 */
	public static JSONArray jsonArray() {
		return new JSONArray();
	}

	/**
	 * Create JSON array.
	 *
	 * @param value First value.
	 * @param others Other values.
	 * @return JSON Array.
	 */
	public static JSONArray jsonArray(JSONObject value, JSONObject... others) {
		return new JSONArray().add(value, others);
	}

	/**
	 * Create JSON array.
	 * @param value First value.
	 * @param others Other values.
	 * @return JSON Array.
	 */
	public static JSONArray jsonArray(int value, int... others) {
		return new JSONArray().add(value, others);
	}

	/**
	 * Create JSON array.
	 * @param value First value.
	 * @param others Other values.
	 * @return JSON Array.
	 */
	public static JSONArray jsonArray(double value, double... others) {
		return new JSONArray().add(value, others);
	}

	/**
	 * Create JSON array.
	 * @param value First value.
	 * @param others Other values.
	 * @return JSON Array.
	 */
	public static JSONArray jsonArray(String value, String... others) {
		return new JSONArray().add(value, others);
	}

	/**
	 * Serialize entries to a valid JSON object, for example:
	 *
	 * <pre><code>
	 *   String json = toJson(
	 *     entry("id", 1),
	 *     entry("name", "John Doe")
	 *   );
	 *
	 *   // Will produce: {"id":1,"name":"John Doe"}
	 * </code></pre>
	 *
	 * @param entry JSON entry.
	 * @param entries Other JSON entries.
	 * @return Entries.
	 */
	public static String toJSON(JSONEntry entry, JSONEntry... entries) {
		Map<String, Object> map = new HashMap<>();
		map.put(entry.getName(), entry.getValue());
		for (JSONEntry e : entries) {
			map.put(e.getName(), e.getValue());
		}

		return serializeToJson(map);
	}

	/**
	 * Serialize given array to JSON.
	 *
	 * @param array Array to serialize.
	 * @return The serialized JSON.
	 */
	public static String toJSON(JSONArray array) {
		return serializeToJson(array.values());
	}

	/**
	 * Serialize given object to JSON.
	 *
	 * @param object Object to serialize.
	 * @return The serialized JSON.
	 */
	public static String toJSON(JSONObject object) {
		return serializeToJson(object.values());
	}

	private static String serializeToJson(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
