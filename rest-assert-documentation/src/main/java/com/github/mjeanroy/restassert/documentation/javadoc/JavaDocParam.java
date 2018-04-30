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

package com.github.mjeanroy.restassert.documentation.javadoc;

import java.util.Objects;

/**
 * The Javadoc {@code @param} element, defined by a name (the parameter name)
 * and the parameter description.
 */
public final class JavaDocParam {

	/**
	 * Parameter Name.
	 */
	private final String name;

	/**
	 * Parameter Description.
	 */
	private final String description;

	/**
	 * Create Javadoc Param.
	 *
	 * @param name The parameter name.
	 * @param description The parameter description.
	 */
	JavaDocParam(String name, String description) {
		this.name = name;
		this.description = description;
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
	 * Get {@link #description}
	 *
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return String.format("JavaDocParam{name=%s, description=%s}", name, description);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JavaDocParam) {
			JavaDocParam p = (JavaDocParam) o;
			return Objects.equals(name, p.name) && Objects.equals(description, p.description);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description);
	}
}
