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

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.join;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A JSON Array which is only an ordered list of values (a value can be a {@link String}, a {@link Number},
 * a {@link Boolean}, any {@link JsonValue} implementation, or anything that can be serialized as JSON
 * using the {@code toString} method.
 *
 * This is a very simple implementation and should be used in unit test only.
 */
public final class JsonArray implements JsonValue {

	/**
	 * Create JSON array.
	 *
	 * @param values Array values.
	 * @return The JSON array.
	 */
	public static JsonArray jsonArray(Object... values) {
		return new JsonArray(values);
	}

	/**
	 * The ordered list of values in array.
	 */
	private final List<Object> values;

	// Use static factory.
	private JsonArray(Object... values) {
		this.values = asList(values);
	}

	/**
	 * Get {@link #values}
	 *
	 * @return {@link #values}
	 */
	public List<Object> getValues() {
		return unmodifiableList(values);
	}

	@Override
	public String toJson() {
		List<String> formattedValues = new ArrayList<>(values.size());
		for (Object value : values) {
			String val = JsonUtil.formatValue(value);
			formattedValues.add(val);
		}

		return "[" + join(formattedValues, ", ") + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JsonArray) {
			JsonArray array = (JsonArray) o;
			return Objects.equals(values, array.values);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}

	@Override
	public String toString() {
		return String.format("JsonArray{values=%s}", values);
	}
}
