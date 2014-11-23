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

package com.github.mjeanroy.rest_assert.generator.templates.models.internal;

/**
 * Argument representation.
 * An argument is defined by:
 * - its type (int, java.lang.String etc.).
 * - its name (arg1, arg2 etc).
 * <p/>
 * This class is immutable and thread safe.
 */
public class Arg {

	/**
	 * Argument type.
	 */
	private final String type;

	/**
	 * Argument name.
	 */
	private final String name;

	public Arg(String type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * Get {@link #type}
	 *
	 * @return {@link #type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get {@link #name}
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Check if argument is the first of the method.
	 * This method is used to know if argument must be prepended
	 * with a comma during rendering.
	 *
	 * @return True if argument is the first, false otherwise.
	 */
	public boolean isFirst() {
		return name.equals("arg1");
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Arg) {
			Arg a = (Arg) o;
			return getType().equals(a.getType()) && getName().equals(a.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode() + getType().hashCode();
	}

	@Override
	public String toString() {
		return String.format("Arg{type=%s, name=%s}", getType(), getName());
	}
}
