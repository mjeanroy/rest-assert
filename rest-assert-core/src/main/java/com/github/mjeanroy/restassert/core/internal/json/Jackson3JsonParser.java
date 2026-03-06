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

package com.github.mjeanroy.restassert.core.internal.json;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * Implementation of {@link JsonParser}
 * using Jackson 3 as internal implementation.
 *
 * This class is implemented as a singleton.
 * This class is thread safe.
 */
final class Jackson3JsonParser extends AbstractJsonParser {

	/**
	 * Get parser.
	 *
	 * @return Parser.
	 */
	static Jackson3JsonParser getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Jackson 3 parser.
	 */
	private final ObjectMapper mapper;

	private Jackson3JsonParser(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	<T> T doParse(String json, Class<T> klass) {
		return mapper.readValue(json, klass);
	}

	private static final class Holder {
		private static final Jackson3JsonParser INSTANCE = new Jackson3JsonParser(
			JsonMapper.builder()
				.findAndAddModules()
				.build()
		);
	}
}
