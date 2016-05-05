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

package com.github.mjeanroy.rest_assert.assertj.internal.http.out_of;

import com.github.mjeanroy.rest_assert.assertj.internal.HttpResponses;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilderImpl;
import org.assertj.core.api.AssertionInfo;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHttpResponsesStatusOutOfTest {

	protected HttpResponses httpResponses = HttpResponses.instance();

	@Test
	public void should_pass() {
		for (int i = 0; i <= 999; i++) {
			if (i < start() || i > end()) {
				HttpResponse httpResponse = new HttpResponseMockBuilderImpl()
					.setStatus(i)
					.build();

				invoke(someInfo(), httpResponse);
			}
		}
	}

	@Test
	public void should_fail() {
		final AssertionInfo info = someInfo();
		final int start = start();
		final int end = end();
		for (int status = start; status <= end; status++) {
			final HttpResponse httpResponse = new HttpResponseMockBuilderImpl()
				.setStatus(status)
				.build();

			try {
				invoke(info, httpResponse);
				failBecauseExpectedAssertionErrorWasNotThrown();
			} catch (AssertionError e) {
				assertThat(e.getMessage())
						.isNotNull()
						.isNotEmpty()
						.isEqualTo(format("Expecting status code to be out of %s and %s but was %s", start, end, status));
			}
		}
	}

	protected abstract int start();

	protected abstract int end();

	protected abstract void invoke(AssertionInfo info, HttpResponse httpResponse);
}
