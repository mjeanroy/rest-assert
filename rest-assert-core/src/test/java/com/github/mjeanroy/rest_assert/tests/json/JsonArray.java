/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.tests.json;

import java.util.ArrayList;
import java.util.List;

import static com.github.mjeanroy.rest_assert.tests.TestUtils.join;
import static java.util.Arrays.asList;

public class JsonArray implements JsonValue {
	public static JsonArray jsonArray(Object... values) {
		return new JsonArray(values);
	}

	private final List<Object> values;

	private JsonArray(Object... values) {
		this.values = asList(values);
	}

	@Override
	public String toJson() {
		return "[" + join(formatValues(), ", ") + "]";
	}

	private List<String> formatValues() {
		List<String> vals = new ArrayList<>();
		for (Object value : values) {
			String val = JsonUtil.formatValue(value);
			vals.add(val);
		}
		return vals;
	}
}
