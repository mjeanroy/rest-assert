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

package com.github.mjeanroy.rest_assert.internal.json.comparators;

import java.util.LinkedList;
import java.util.List;

/**
 * Json context (a.k.a current fully qualified entry
 * name).
 */
public class JsonContext {

	public static JsonContext rootContext() {
		return new JsonContext();
	}

	private final List<String> contexts;

	private JsonContext() {
		contexts = new LinkedList<>();
	}

	public void append(String entryName) {
		contexts.add(entryName);
	}

	public void remove() {
		contexts.remove(contexts.size() - 1);
	}

	public String toPath(String last) {
		if (contexts.isEmpty()) {
			return last;
		}

		String separator = ".";
		StringBuilder sb = new StringBuilder();
		for (String context : contexts) {
			sb.append(context).append(separator);
		}

		sb.append(last);
		return sb
				.toString()
				.replace(".[", "["); // Replace dot symbol before array bracket
	}

	@Override
	public String toString() {
		return toPath("");
	}
}