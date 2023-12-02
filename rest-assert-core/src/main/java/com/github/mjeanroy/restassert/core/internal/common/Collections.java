/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.addAll;

/**
 * Static Collection Utilities.
 */
public final class Collections {

	// Ensure non instantiation.
	private Collections() {
	}

	/**
	 * Create a list from all parameters.
	 *
	 * @param v1 First value.
	 * @param other Other values.
	 * @param <T> Type of inputs.
	 * @return Output list.
	 */
	@SafeVarargs
	public static <T> List<T> toList(T v1, T... other) {
		final int initialCapacity = other.length + 1;
		final List<T> outputs = new ArrayList<>(initialCapacity);
		outputs.add(v1);
		addAll(outputs, other);
		return outputs;
	}

	/**
	 * Get size of given iterable:
	 * - If size can be known in 0(1), then it is returned.
	 * - Otherwise, default size is returned.
	 *
	 * @param iterable Iterable collection.
	 * @param defaultSize Default size.
	 * @param <T> Type of elements in iterable.
	 * @return The size.
	 */
	public static <T> int sizeOf(Iterable<T> iterable, int defaultSize) {
		if (iterable instanceof Collection) {
			return ((Collection<T>) iterable).size();
		}

		if (!iterable.iterator().hasNext()) {
			return 0;
		}

		return defaultSize;
	}
}
