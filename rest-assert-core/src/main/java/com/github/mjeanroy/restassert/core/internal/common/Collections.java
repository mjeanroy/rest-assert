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

package com.github.mjeanroy.restassert.core.internal.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Static Collection Utilities.
 */
public final class Collections {

	// Ensure non instantiation.
	private Collections() {
	}

	/**
	 * Map each element of input list to an output list.
	 *
	 * @param inputs Input list.
	 * @param mapper Mapper function.
	 * @param <T> Input type.
	 * @param <U> Output type.
	 * @return Outputs.
	 */
	public static <T, U> List<U> map(List<T> inputs, Mapper<T, U> mapper) {
		List<U> outputs = new ArrayList<>(inputs.size());
		for (T input : inputs) {
			outputs.add(mapper.apply(input));
		}
		return outputs;
	}

	/**
	 * Map each element of input list to an output list.
	 *
	 * @param inputs Input list.
	 * @param mapper Mapper function.
	 * @param <T> Input type.
	 * @param <U> Output type.
	 * @return Outputs.
	 */
	public static <T, U> List<U> map(Iterable<T> inputs, Mapper<T, U> mapper) {
		int size = inputs instanceof Collection ? ((Collection) inputs).size() : 10;
		List<U> outputs = new ArrayList<>(size);
		for (T input : inputs) {
			outputs.add(mapper.apply(input));
		}

		return outputs;
	}

	/**
	 * Map each element of input array to an output list.
	 *
	 * @param inputs Input list.
	 * @param mapper Mapper function.
	 * @param <T> Input type.
	 * @param <U> Output type.
	 * @return Outputs.
	 */
	public static <T, U> List<U> map(T[] inputs, Mapper<T, U> mapper) {
		List<U> outputs = new ArrayList<>(inputs.length);
		for (T input : inputs) {
			outputs.add(mapper.apply(input));
		}
		return outputs;
	}

	/**
	 * Filter a list using a given predicate.
	 *
	 * @param inputs List input.
	 * @param predicate Predicate function.
	 * @param <T> Type of inputs.
	 * @return Output (i.e filtered inputs).
	 */
	public static <T> List<T> filter(List<T> inputs, Predicate<T> predicate) {
		List<T> outputs = new ArrayList<>(inputs.size());
		for (T t : inputs) {
			if (predicate.apply(t)) {
				outputs.add(t);
			}
		}

		return outputs;
	}

	/**
	 * Returns {@code true} if at least one element in the collection verify
	 * a predicate.
	 *
	 * @param inputs Collection of inputs.
	 * @param predicate Predicate to check.
	 * @param <T> Type of elements in the collection.
	 * @return {@code true} if at least one element in the collection verify the predicate, {@code false} otherwise.
	 */
	public static <T> boolean some(Iterable<T> inputs, Predicate<T> predicate) {
		for (T input : inputs) {
			if (predicate.apply(input)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Predicate function.
	 */
	public interface Predicate<T> {

		/**
		 * Apply predicate.
		 *
		 * @param input Input.
		 * @return Predicate output.
		 */
		boolean apply(T input);
	}

	/**
	 * Mapper function.
	 */
	public interface Mapper<T, U> {

		/**
		 * Transform input to a new output.
		 *
		 * @param input Input.
		 * @return Output.
		 */
		U apply(T input);
	}
}
