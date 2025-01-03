/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

import static com.github.mjeanroy.restassert.core.internal.common.ClassUtils.isPresent;
import static com.github.mjeanroy.restassert.core.internal.json.parsers.GsonJsonParser.gsonParser;
import static com.github.mjeanroy.restassert.core.internal.json.parsers.Jackson1JsonParser.jackson1Parser;
import static com.github.mjeanroy.restassert.core.internal.json.parsers.Jackson2JsonParser.jackson2Parser;

/**
 * Access json parser implementation.
 */
public enum JsonParserStrategy {
	/**
	 * Json parser using Jackson2 as internal implementation.
	 */
	JACKSON2("com.fasterxml.jackson.databind.ObjectMapper") {
		@Override
		public JsonParser build() {
			return jackson2Parser();
		}
	},

	/**
	 * Json parser using Google Gson as internal implementation.
	 */
	GSON("com.google.gson.Gson") {
		@Override
		public JsonParser build() {
			return gsonParser();
		}
	},

	/**
	 * Json parser using Jackson1 as internal implementation.
	 */
	JACKSON1("org.codehaus.jackson.map.ObjectMapper") {
		@Override
		public JsonParser build() {
			return jackson1Parser();
		}
	},

	/**
	 * Strategy that try to detect available implementation on classpath
	 * and return associated parser.
	 */
	AUTO(null) {
		@Override
		public JsonParser build() {
			for (JsonParserStrategy strategy : JsonParserStrategy.values()) {
				String className = strategy.className;
				if (className == null) {
					continue;
				}

				if (isPresent(className)) {
					return strategy.build();
				}
			}

			// Fail if no available implementation found
			throw new UnsupportedOperationException("Please add a json parser to your classpath (Jackson2, Jackson1 or Gson)");
		}
	};

	private final String className;

	JsonParserStrategy(String className) {
		this.className = className;
	}

	/**
	 * Get parser instance according to strategy.
	 *
	 * @return Parser.
	 */
	public abstract JsonParser build();
}
