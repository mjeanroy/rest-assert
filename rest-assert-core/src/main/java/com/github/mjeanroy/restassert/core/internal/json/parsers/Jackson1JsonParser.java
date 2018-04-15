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

import com.github.mjeanroy.restassert.core.internal.json.JsonException;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParser}
 * using Jackson1 as internal implementation.
 *
 * This class is implemented as a singleton.
 * This class is thread safe.
 */
public class Jackson1JsonParser extends AbstractJsonParser {

	/**
	 * Singleton instance.
	 */
	private static final Jackson1JsonParser INSTANCE = new Jackson1JsonParser();

	/**
	 * Get parser.
	 *
	 * @return Parser.
	 */
	public static Jackson1JsonParser jackson1Parser() {
		return INSTANCE;
	}

	/**
	 * Jackson mapper.
	 */
	private final ObjectMapper mapper;

	private Jackson1JsonParser() {
		super();
		this.mapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> parseObject(String json) {
		return parse(json, Map.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> parseArray(String json) {
		return parse(json, List.class);
	}

	private <T> T parse(String json, Class<T> klass) {
		try {
			return mapper.readValue(json, klass);
		}
		catch (Exception ex) {
			throw new JsonException(ex);
		}
	}
}
