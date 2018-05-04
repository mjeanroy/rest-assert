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
import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.common.Strings.join;

/**
 * An helper to write {@code toString()} methods easily.
 */
public final class ToStringBuilder {

	private static final char OPEN_CHAR = '{';
	private static final char CLOSE_CHAR = '}';
	private static final String PARAMETER_SEPARATOR = ", ";

	/**
	 * Create the builder using the class simple name as first
	 * prefix.
	 *
	 * @param klass The class.
	 * @return The builder.
	 */
	public static ToStringBuilder toStringBuilder(Class<?> klass) {
		return new ToStringBuilder(klass.getSimpleName());
	}

	/**
	 * The output prefix.
	 */
	private final String prefix;

	/**
	 * The list of parameters to append to the final output.
	 */
	private final List<String> parameters;

	/**
	 * The current size of the output.
	 * This value will be used to initialize the final {@link StringBuilder} with the correct
	 * size.
	 */
	private int size;

	private ToStringBuilder(String prefix) {
		this.prefix = prefix;
		this.parameters = new ArrayList<>();
		this.size = prefix.length();
	}

	/**
	 * Append new field to the final output.
	 *
	 * @param name Parameter name.
	 * @param value Parameter value.
	 * @return The current builder.
	 */
	public ToStringBuilder append(String name, Object value) {
		String parameter = name + "=" + value;
		parameters.add(parameter);
		size += parameter.length();
		return this;
	}

	/**
	 * Generate the {@code toString} output.
	 *
	 * @return The final output.
	 */
	public String build() {
		int finalSize = size + (PARAMETER_SEPARATOR.length() * (parameters.size() - 1)) + 2;
		return new StringBuilder(finalSize)
			.append(prefix)
			.append(OPEN_CHAR)
			.append(join(parameters, PARAMETER_SEPARATOR))
			.append(CLOSE_CHAR)
			.toString();
	}
}
