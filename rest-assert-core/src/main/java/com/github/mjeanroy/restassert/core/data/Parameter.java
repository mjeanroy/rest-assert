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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notEmpty;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * A parameter that can be displayed in header raw values formatted as {@code "<name>=<value>"}.
 */
public final class Parameter implements HeaderValue {

	/**
	 * Create parameter.
	 *
	 * @param name Parameter name.
	 * @param value Parameter value.
	 * @return The parameter.
	 * @throws NullPointerException If {@code name} or {@code value} are {@code null}.
	 * @throws IllegalArgumentException If {@code name} is empty.
	 */
	public static Parameter parameter(String name, String value) {
		return new Parameter(name, value);
	}

	/**
	 * Parameter name.
	 */
	private final String name;

	/**
	 * Parameter value.
	 */
	private final String value;

	/**
	 * Create parameter.
	 *
	 * @param name The parameter name.
	 * @param value The parameter value.
	 * @throws NullPointerException If {@code name} or {@code value} are {@code null}.
	 * @throws IllegalArgumentException If {@code name} is empty.
	 */
	private Parameter(String name, String value) {
		this.name = notEmpty(name, "Parameter name must be defined");
		this.value = notNull(value, "Parameter value must be defined");
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
	 * Get {@link #value}
	 *
	 * @return {@link #value}
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String serializeValue() {
		return name + "=" + value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Parameter) {
			Parameter p = (Parameter) o;
			return Objects.equals(name, p.name) && Objects.equals(value, p.value);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("name", name)
			.append("value", value)
			.build();
	}
}
