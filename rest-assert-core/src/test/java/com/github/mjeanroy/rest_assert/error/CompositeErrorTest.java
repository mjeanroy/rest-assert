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

package com.github.mjeanroy.rest_assert.error;

import org.junit.Test;

import static com.github.mjeanroy.rest_assert.error.CompositeError.composeErrors;
import static com.github.mjeanroy.rest_assert.utils.Utils.LINE_SEPARATOR;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeErrorTest {

	@Test
	public void it_should_compose_errors() {
		RestAssertError error1 = createError("foo");
		RestAssertError error2 = createError("bar %s %s", 1, 2);
		RestAssertError error3 = createError("foobar %s", "hello");

		CompositeError error = composeErrors(asList(error1, error2, error3));

		assertThat(error).isNotNull();

		assertThat(error.message())
				.isNotNull()
				.isNotEmpty()
				.isEqualTo("" +
								"foo," + LINE_SEPARATOR +
								"bar %s %s," + LINE_SEPARATOR +
								"foobar %s"
				);

		assertThat(error.args())
				.isNotNull()
				.isNotEmpty()
				.hasSize(3)
				.contains(1, 2, "hello");

		assertThat(error.buildMessage())
				.isNotNull()
				.isNotEmpty()
				.isEqualTo("" +
								"foo," + LINE_SEPARATOR +
								"bar 1 2," + LINE_SEPARATOR +
								"foobar hello"
				);
	}

	private RestAssertError createError(String message, Object... args) {
		RestAssertError error = mock(RestAssertError.class);
		when(error.message()).thenReturn(message);
		when(error.args()).thenReturn(args);
		return error;
	}
}
