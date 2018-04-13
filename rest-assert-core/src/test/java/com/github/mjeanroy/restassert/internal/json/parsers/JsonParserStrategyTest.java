/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.internal.json.parsers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class JsonParserStrategyTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_get_gson_parser() {
		JsonParser parser = JsonParserStrategy.GSON.build();
		assertThat(parser)
			.isNotNull()
			.isExactlyInstanceOf(GsonJsonParser.class);
	}

	@Test
	public void it_should_get_jackson2_parser() {
		JsonParser parser = JsonParserStrategy.JACKSON2.build();
		assertThat(parser)
			.isNotNull()
			.isExactlyInstanceOf(Jackson2JsonParser.class);
	}

	@Test
	public void it_should_get_jackson1_parser() {
		JsonParser parser = JsonParserStrategy.JACKSON1.build();
		assertThat(parser)
			.isNotNull()
			.isExactlyInstanceOf(Jackson1JsonParser.class);
	}
}
