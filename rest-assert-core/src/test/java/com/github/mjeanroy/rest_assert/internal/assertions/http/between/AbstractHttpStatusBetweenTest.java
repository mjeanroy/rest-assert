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

package com.github.mjeanroy.rest_assert.internal.assertions.http.between;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatusBetween;
import com.github.mjeanroy.rest_assert.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

public abstract class AbstractHttpStatusBetweenTest extends AbstractAssertionsTest<HttpResponse> {

	protected HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_status_in_bounds() {
		for (int i = start(); i <= end(); i++) {
			AssertionResult result = invoke(newResponse(i));
			checkSuccess(result);
		}
	}

	@Test
	public void it_should_fail_with_response_not_in_bounds() {
		final int start = start();
		final int end = end();

		for (int status = 100; status <= 599; status++) {
			if (status >= start && status <= end) {
				continue;
			}

			AssertionResult result = invoke(newResponse(status));

			checkError(result,
					ShouldHaveStatusBetween.class,
					"Expecting status code to be between %s and %s but was %s",
					start, end, status
			);
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