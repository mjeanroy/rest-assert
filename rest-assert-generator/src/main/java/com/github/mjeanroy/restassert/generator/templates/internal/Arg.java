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

package com.github.mjeanroy.restassert.generator.templates.internal;

import java.util.Objects;

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
	 * Argument index.
	 */
	private final int index;

	/**
	 * Argument type.
	 */
	private final String type;

	/**
	 * Argument generic type.
	 */
	private final String genericType;

	/**
	 * Argument name.
	 */
	private final String name;

	public Arg(String type, String genericType, String name, int index) {
		this.type = type;
		this.genericType = genericType;
		this.name = name;
		this.index = index;
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
	 * Get {@link #genericType}
	 *
	 * @return {@link #genericType}
	 */
	public String getGenericType() {
		return genericType;
	}

	/**
	 * Check if argument is the first of the method.
	 * This method is used to know if argument must be prepended
	 * with a comma during rendering.
	 *
	 * @return True if argument is the first, false otherwise.
	 */
	public boolean isFirst() {
		return index == 1;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Arg) {
			Arg a = (Arg) o;
			return Objects.equals(getType(), a.getType()) &&
					Objects.equals(getName(), a.getName()) &&
					Objects.equals(getGenericType(), a.getGenericType());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getType(), getGenericType());
	}

	@Override
	public String toString() {
		return String.format("Arg{type=%s, genericType=%s, name=%s}", getType(), getGenericType(), getName());
	}
}
