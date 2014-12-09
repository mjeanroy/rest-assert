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

package com.github.mjeanroy.rest_assert.api.http.out_of;

import com.github.mjeanroy.rest_assert.api.AbstractAssertTest;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailure;
import static java.lang.String.format;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractHttpStatusOutOfTest extends AbstractAssertTest<HttpResponse> {

	@Test
	public void it_should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() && i > end()) {
				invoke(newResponse(i));
				invoke("foo", newResponse(i));
			}
		}
	}

	@Test
	public void it_should_fail() {
		final int start = start();
		final int end = end();

		for (int i = start; i <= end; i++) {
			final int status = i;
			final String message = format("Expecting status code to be out of %s and %s but was %s", start, end, status);

			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invoke(newResponse(status));
				}
			});
		}
	}

	@Test
	public void it_should_fail_with_custom_message() {
		final String message = "foo";
		final int start = start();
		final int end = end();

		for (int i = start; i <= end; i++) {
			final int status = i;
			assertFailure(message, new Function() {
				@Override
				public void apply() {
					invoke(message, newResponse(status));
				}
			});
		}
	}

	protected HttpResponse newResponse(int status) {
		HttpResponse httpResponse = mock(HttpResponse.class);
		when(httpResponse.getStatus()).thenReturn(status);
		return httpResponse;
	}

	protected abstract int start();

	protected abstract int end();
}
