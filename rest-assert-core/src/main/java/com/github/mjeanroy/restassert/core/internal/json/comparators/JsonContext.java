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

package com.github.mjeanroy.restassert.core.internal.json.comparators;

import java.util.LinkedList;
import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.common.Strings.isSurroundedBy;

/**
 * Json context (a.k.a current fully qualified entry
 * name).
 */
class JsonContext {

	static JsonContext rootContext(String actual, String expected) {
		return new JsonContext(actual, expected);
	}

	private final String actual;
	private final String expected;
	private final List<String> contexts;

	private JsonContext(String actual, String expected) {
		this.actual = actual;
		this.expected = expected;
		this.contexts = new LinkedList<>();
	}

	String actual() {
		return actual;
	}

	String expected() {
		return expected;
	}

	void append(String entryName) {
		contexts.add(entryName);
	}

	void remove() {
		contexts.remove(contexts.size() - 1);
	}

	String toPath(String last) {
		if (contexts.isEmpty()) {
			return last;
		}

		String separator = ".";
		StringBuilder sb = new StringBuilder();

		int i = 0;
		for (String context : contexts) {
			if (shouldAddSeparator(i, context)) {
				sb.append(separator);
			}

			sb.append(context);
			i++;
		}

		if (!last.isEmpty() && shouldAddSeparator(i, last)) {
			sb.append(separator);
		}

		// Do not forget to append last value
		sb.append(last);

		return sb.toString();
	}

	@Override
	public String toString() {
		return toPath("");
	}

	private static boolean shouldAddSeparator(int index, String context) {
		return index != 0 && !isArrayNotation(context);
	}

	private static boolean isArrayNotation(String context) {
		return isSurroundedBy(context, '[', ']');
	}
}
