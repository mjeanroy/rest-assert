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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of header.
 * A header is defined by a name and a value.
 */
public final class Header {

	/**
	 * Create new header.
	 *
	 * @param name  Header name.
	 * @param value Header value.
	 * @return Header.
	 */
	public static Header header(String name, String value) {
		return new Header(name, value);
	}

	/**
	 * Header name.
	 */
	private final String name;

	/**
	 * Header value.
	 */
	private final String value;

	// Use static factory instead
	private Header(String name, String value) {
		this.name = name;
		this.value = value;
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

	/**
	 * Get all header values.
	 *
	 * @return Header values.
	 */
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		for (String current : value.split(",")) {
			values.add(current.trim());
		}

		return unmodifiableList(values);
	}

	@Override
	public String toString() {
		return String.format("Header{name=%s, value=%s}", name, value);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Header) {
			Header h = (Header) o;
			return Objects.equals(name, h.name) && Objects.equals(value, h.value);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}
}
