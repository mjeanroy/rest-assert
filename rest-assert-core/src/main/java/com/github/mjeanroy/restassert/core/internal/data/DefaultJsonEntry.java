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

package com.github.mjeanroy.restassert.core.internal.data;

import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * Default implement for {@link JsonEntry} interface.
 */
public final class DefaultJsonEntry implements JsonEntry {

	/**
	 * Entry key.
	 */
	private final String key;

	/**
	 * Entry value.
	 */
	private final Object value;

	/**
	 * Create JSON entry.
	 *
	 * @param key   JSON entry (must not be null).
	 * @param value JSON value.
	 */
	public DefaultJsonEntry(String key, Object value) {
		this.key = notNull(key, "JSON key cannot be null");
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", getKey(), getValue());
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof DefaultJsonEntry) {
			DefaultJsonEntry e = (DefaultJsonEntry) o;
			return Objects.equals(getKey(), e.getKey())
					&& Objects.equals(getValue(), e.getValue());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getKey(), getValue());
	}
}
