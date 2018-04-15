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

package com.github.mjeanroy.restassert.core.internal.json.comparators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonContextTest {

	@Test
	public void it_should_create_context() {
		JsonContext context = JsonContext.rootContext();
		assertThat(context).isNotNull();
		assertThat(context.toString()).isEqualTo("");
	}

	@Test
	public void it_should_append_key_to_context() {
		JsonContext context = JsonContext.rootContext();
		assertThat(context.toString()).isEqualTo("");

		context.append("foo");
		assertThat(context.toString()).isEqualTo("foo");

		context.append("bar");
		assertThat(context.toString()).isEqualTo("foo.bar");
	}

	@Test
	public void it_should_append_and_remove_key_to_context() {
		JsonContext context = JsonContext.rootContext();
		assertThat(context.toString()).isEqualTo("");

		context.append("foo");
		assertThat(context.toString()).isEqualTo("foo");

		context.remove();
		assertThat(context.toString()).isEqualTo("");
	}

	@Test
	public void it_should_get_path_with_array() {
		JsonContext context = JsonContext.rootContext();
		assertThat(context.toPath("foo")).isEqualTo("foo");

		context.append("[0]");
		assertThat(context.toPath("foo")).isEqualTo("[0].foo");
	}

	@Test
	public void it_should_get_path_with_array_as_last_value() {
		JsonContext context = JsonContext.rootContext();
		assertThat(context.toPath("[0]")).isEqualTo("[0]");

		context.append("foo");
		assertThat(context.toPath("[0]")).isEqualTo("foo[0]");
	}
}
