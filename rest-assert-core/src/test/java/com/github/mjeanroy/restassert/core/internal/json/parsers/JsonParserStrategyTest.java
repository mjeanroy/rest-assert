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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

import com.github.mjeanroy.junit4.customclassloader.BlackListClassLoader;
import com.github.mjeanroy.junit4.customclassloader.BlackListClassLoaderHolder;
import com.github.mjeanroy.junit4.customclassloader.CustomClassLoaderRunner;
import com.github.mjeanroy.junit4.customclassloader.RunWithClassLoader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(CustomClassLoaderRunner.class)
@RunWithClassLoader(BlackListClassLoaderHolder.class)
public class JsonParserStrategyTest {

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void GSON_should_get_gson_parser() {
		JsonParser parser = JsonParserStrategy.GSON.build();
		assertThat(parser).isExactlyInstanceOf(GsonJsonParser.class);
	}

	@Test
	public void JACKSON2_should_get_jackson2_parser() {
		JsonParser parser = JsonParserStrategy.JACKSON2.build();
		assertThat(parser).isExactlyInstanceOf(Jackson2JsonParser.class);
	}

	@Test
	public void JACKSON1_should_get_jackson1_parser() {
		JsonParser parser = JsonParserStrategy.JACKSON1.build();
		assertThat(parser).isExactlyInstanceOf(Jackson1JsonParser.class);
	}

	@Test
	public void AUTO_should_get_jackson2_parser_by_default() {
		// com.fasterxml.jackson.databind.ObjectMapper
		JsonParser parser = JsonParserStrategy.AUTO.build();
		assertThat(parser).isExactlyInstanceOf(Jackson2JsonParser.class);
	}

	@Test
	public void AUTO_should_get_jackson_1_parser_if_jackson_2_is_not_available() {
		BlackListClassLoader classLoader = getClassLoader();
		classLoader.add("com.fasterxml.jackson.databind.ObjectMapper");

		JsonParser parser = JsonParserStrategy.AUTO.build();
		assertThat(parser).isExactlyInstanceOf(Jackson1JsonParser.class);
	}

	@Test
	public void AUTO_should_get_gson_parser_if_jackson_2_and_jackson_1_are_not_available() {
		BlackListClassLoader classLoader = getClassLoader();
		classLoader.add("com.fasterxml.jackson.databind.ObjectMapper");
		classLoader.add("org.codehaus.jackson.map.ObjectMapper");

		JsonParser parser = JsonParserStrategy.AUTO.build();
		assertThat(parser).isExactlyInstanceOf(GsonJsonParser.class);
	}

	@Test
	public void AUTO_should_fail_if_no_implementation_is_available() {
		BlackListClassLoader classLoader = getClassLoader();
		classLoader.add("com.fasterxml.jackson.databind.ObjectMapper");
		classLoader.add("org.codehaus.jackson.map.ObjectMapper");
		classLoader.add("com.google.gson.Gson");

		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("Please add a json parser to your classpath (Jackson2, Jackson1 or Gson)");
		JsonParserStrategy.AUTO.build();
	}

	private BlackListClassLoader getClassLoader() {
		return (BlackListClassLoader) Thread.currentThread().getContextClassLoader();
	}
}
