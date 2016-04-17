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

public class JsonObject implements JsonValue {

	public static JsonObject jsonObject() {
		return new JsonObject();
	}

	public static JsonObject jsonObject(JsonEntry... entries) {
		JsonObject object = new JsonObject();
		for (JsonEntry entry : entries) {
			object.addEntry(entry);
		}
		return object;
	}

	private final List<JsonEntry> entries;

	private JsonObject() {
		this.entries = new ArrayList<>();
	}

	private void addEntry(JsonEntry entry) {
		entries.add(entry);
	}

	@Override
	public String toJson() {
		StringBuilder sub = new StringBuilder();
		for (JsonEntry entry : entries) {
			sub.append(entry.toJson()).append(", ");
		}

		return String.format("{%s}", sub.substring(0, sub.length() - 2));
	}
}
