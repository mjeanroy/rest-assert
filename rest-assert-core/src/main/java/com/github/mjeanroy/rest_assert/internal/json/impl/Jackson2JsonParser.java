/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.json.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mjeanroy.rest_assert.internal.json.JsonException;
import com.github.mjeanroy.rest_assert.internal.json.JsonParser;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link JsonParser}
 * using Jackson2 as internal implementation.
 */
public class Jackson2JsonParser implements JsonParser {

	private final ObjectMapper mapper;

	public Jackson2JsonParser() {
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