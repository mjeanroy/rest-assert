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

package com.github.mjeanroy.rest_assert.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Static utilities.
 */
public final class Utils {

	// Private constructor to ensure non instantiation.
	private Utils() {
	}

	/**
	 * Get first non null parameter.
	 * If both parameters are null, null will be returned.
	 *
	 * @param obj1 First parameter.
	 * @param obj2 Second parameter.
	 * @param <T>  Type of parameters.
	 * @return First non null parameters.
	 */
	public static <T> T firstNonNull(T obj1, T obj2) {
		return obj1 != null ? obj1 : obj2;
	}

	/**
	 * Check that a given value is not null.
	 * If value is null, a {@link NullPointerException} will be thrown
	 * with given message.
	 *
	 * @param obj     Value to check.
	 * @param message Message given in {@link NullPointerException}.
	 * @param <T>     Type of object.
	 * @return Original object if it is not null.
	 */
	public static <T> T notNull(T obj, String message) {
		if (obj == null) {
			throw new NullPointerException(message);
		}
		return obj;
	}

	/**
	 * Map each element of input list to an output list.
	 *
	 * @param inputs Input list.
	 * @param mapper Mapper function.
	 * @param <T>    Input type.
	 * @param <U>    Output type.
	 * @return Outputs.
	 */
	public static <T, U> List<U> map(List<T> inputs, Mapper<T, U> mapper) {
		List<U> outputs = new ArrayList<>(inputs.size());
		for (T input : inputs) {
			outputs.add(mapper.apply(input));
		}
		return outputs;
	}
}
