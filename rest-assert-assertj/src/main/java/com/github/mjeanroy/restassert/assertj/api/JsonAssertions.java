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

package com.github.mjeanroy.restassert.assertj.api;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.JsonEntry;

public class JsonAssertions {

	private JsonAssertions() {
	}

	/**
	 * Create JSON entry.
	 * <p/>
	 * Basically, a shortcut to {@link com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions#jsonEntry(String, Object)}, but
	 * this method may be used to avoid to import the same class name.
	 *
	 * @param key   JSON key.
	 * @param value JSON value.
	 * @return JSON entry.
	 */
	public static JsonEntry jsonEntry(String key, Object value) {
		return com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions.jsonEntry(key, value);
	}

	/**
	 * Creates a new instance of {@link JsonAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static JsonAssert assertJsonThat(String actual) {
		return new JsonAssert(actual);
	}

	/**
	 * Creates a new instance of {@link JsonAssert}.
	 *
	 * @param actual the response http that will be used to extract content body.
	 * @return the created assertion object.
	 */
	public static JsonAssert assertJsonThat(HttpResponse actual) {
		return new JsonAssert(actual.getContent());
	}
}
