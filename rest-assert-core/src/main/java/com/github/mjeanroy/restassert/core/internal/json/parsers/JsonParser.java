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

package com.github.mjeanroy.restassert.core.internal.json.parsers;

import java.util.List;
import java.util.Map;

/**
 * Json parser interface.
 * A Json parser turn a json string into a map
 * object.
 */
public interface JsonParser {

	/**
	 * Parse JSON representation and return object result.
	 * @param json Json string.
	 * @return Object result.
	 */
	Object parse(String json);

	/**
	 * Parse JSON object and deserialize result into a map object.
	 *
	 * @param json Json string.
	 * @return Map object.
	 */
	Map<String, Object> parseObject(String json);

	/**
	 * Parse JSON array and deserialize result into a list of objects.
	 *
	 * @param json Json string.
	 * @return List of objects.
	 */
	List<Object> parseArray(String json);
}
