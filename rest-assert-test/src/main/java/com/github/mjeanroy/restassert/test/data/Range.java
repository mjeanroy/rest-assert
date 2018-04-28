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

package com.github.mjeanroy.restassert.test.data;

import java.util.Objects;

/**
 * A status range, with a lower bound and an upper bound.
 */
public final class Range {

	/**
	 * Create new range.
	 *
	 * @param start The lower bound.
	 * @param end The upper bound.
	 * @return The new range.
	 */
	public static Range range(int start, int end) {
		return new Range(start, end);
	}

	/**
	 * The lower bound.
	 */
	private final int start;

	/**
	 * The upper bound.
	 */
	private final int end;

	/**
	 * Create the range.
	 *
	 * @param start The lower bound.
	 * @param end The upper bound.
	 */
	private Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Get {@link #start}
	 *
	 * @return {@link #start}
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Get {@link #end}
	 *
	 * @return {@link #end}
	 */
	public int getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return String.format("Range{start=%d, end=%d}", start, end);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Range) {
			Range r = (Range) o;
			return r.start == start && r.end == end;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}
}
