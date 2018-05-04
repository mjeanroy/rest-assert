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

package com.github.mjeanroy.restassert.core.internal.error;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractErrorTest {

	@Test
	public void it_should_build_error_object() {
		String message = "foo %s";
		String arg = "bar";

		FooError error = new FooError(message, arg);
		assertThat(error.message()).isEqualTo(message);
		assertThat(error.args()).isEqualTo(new Object[]{arg});
		assertThat(error.buildMessage()).isEqualTo("foo bar");
		assertThat(error.toString()).isEqualTo("foo bar");
	}

	@Test
	public void it_build_error_with_empty_array() {
		String message = "foo";
		FooError error = new FooError(message);
		assertThat(error.args()).isNotNull().isEmpty();
		assertThat(error.message()).isEqualTo(message);
		assertThat(error.buildMessage()).isEqualTo(message);
		assertThat(error.toString()).isEqualTo(message);
	}

	@Test
	public void it_build_error_with_a_copy_of_array() {
		Object[] args = new Object[]{"bar", "foo"};
		FooError error = new FooError("foo", args);
		args[1] = "bar";
		assertThat(error.args()).isEqualTo(new Object[]{"bar", "foo"});
	}

	@Test
	public void it_should_return_a_copy_of_arguments() {
		String message = "foo %s";
		String arg = "bar";

		FooError error = new FooError(message, arg);

		Object[] args1 = error.args();
		Object[] args2 = error.args();
		assertThat(args1).isNotSameAs(args2).isEqualTo(args2);
	}

	private static final class FooError extends AbstractError {
		FooError(String message, Object... args) {
			super(message, args);
		}
	}
}
