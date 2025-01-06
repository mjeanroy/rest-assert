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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.addAll;
import static java.util.Collections.unmodifiableList;

/**
 * Implementations of (immutable) JSON array.
 */
public final class JSONArray {

	/**
	 * Array values.
	 */
	private final List<Object> values;

	public JSONArray() {
		this(new ArrayList<>());
	}

	private JSONArray(List<Object> values) {
		this.values = unmodifiableList(values);
	}

	/**
	 * Add values to given array.
	 *
	 * @param value First value.
	 * @param other Other values.
	 * @return The array.
	 */
	public JSONArray add(int value, int... other) {
		return push(value, Arrays.stream(other).boxed().toArray(Object[]::new));
	}

	/**
	 * Add values to given array.
	 *
	 * @param value First value.
	 * @param other Other values.
	 * @return The array.
	 */
	public JSONArray add(double value, double... other) {
		return push(value, Arrays.stream(other).boxed().toArray(Object[]::new));
	}

	/**
	 * Add values to given array.
	 *
	 * @param value First value.
	 * @param other Other values.
	 * @return The array.
	 */
	public JSONArray add(boolean value, boolean... other) {
		Object[] o = new Object[other.length];
		for (int i = 0; i < other.length; i++) {
			o[i] = other[i];
		}

		return push(value, o);
	}

	/**
	 * Add values to given array.
	 *
	 * @param value First value.
	 * @param other Other values.
	 * @return The array.
	 */
	public JSONArray add(String value, String... other) {
		return push(value, Arrays.stream(other).toArray(Object[]::new));
	}

	/**
	 * Add values to given array.
	 *
	 * @param value First value.
	 * @param other Other values.
	 * @return The array.
	 */
	public JSONArray add(JSONObject value, JSONObject... other) {
		return push(value.values(), Arrays.stream(other).map(JSONObject::values).toArray(Object[]::new));
	}

	/**
	 * Get array size.
	 *
	 * @return Size.
	 */
	public int size() {
		return values.size();
	}

	/**
	 * Get JSON representation of given array.
	 *
	 * @return JSON representation.
	 */
	public String toJSON() {
		return JSONTestUtils.toJSON(this);
	}

	List<Object> values() {
		return values;
	}

	private JSONArray push(Object value, Object... other) {
		List<Object> newValues = new ArrayList<>(values.size() + other.length + 1);
		newValues.addAll(values);
		newValues.add(value);
		addAll(newValues, other);
		return new JSONArray(newValues);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JSONArray) {
			JSONArray that = (JSONArray) o;
			return Objects.equals(values, that.values);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}
}
