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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mjeanroy.restassert.core.internal.json.JsonException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.restassert.core.internal.json.parsers.Jackson2JsonParser.jackson2Parser;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Jackson2JsonParserTest extends AbstractJsonParserTest {

	private JsonParser parser;
	private ObjectMapper mapper;

	@Before
	public void setUp() throws Exception {
		parser = jackson2Parser();
		mapper = (ObjectMapper) readField(parser, "mapper", true);
	}

	@After
	public void tearDown() throws Exception {
		writeField(parser, "mapper", mapper, true);
	}

	@Override
	protected JsonParser parser() {
		return parser;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_wrap_checked_exception() throws Exception {
		ObjectMapper mapper = mock(ObjectMapper.class);
		when(mapper.readValue(anyString(), any(Class.class))).thenThrow(RuntimeException.class);
		writeField(parser, "mapper", mapper, true);

		thrown.expect(JsonException.class);

		parser.parse("{}");
	}
}
