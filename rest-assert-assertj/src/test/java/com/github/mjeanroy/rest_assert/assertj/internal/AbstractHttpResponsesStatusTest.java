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

package com.github.mjeanroy.rest_assert.assertj.internal;

import static com.github.mjeanroy.rest_assert.assertj.tests.Failures.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.rest_assert.assertj.tests.TestData.newHttpResponseWithStatus;
import static com.github.mjeanroy.rest_assert.assertj.tests.TestData.someInfo;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AssertionInfo;
import org.junit.Test;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

public abstract class AbstractHttpResponsesStatusTest {

	protected HttpResponses httpResponses = HttpResponses.instance();

	@Test
	public void should_pass_if_status_code_is_ok() {
		HttpResponse httpResponse = newHttpResponseWithStatus(status());
		test(someInfo(), httpResponse);
	}

	@Test
	public void should_fail_if_status_code_are_not_equal() {
		final AssertionInfo info = someInfo();
		final int status = status();
		final int expectedStatus = status + 1;
		final HttpResponse httpResponse = newHttpResponseWithStatus(expectedStatus);

		try {
			test(info, httpResponse);
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError e) {
			assertThat(e.getMessage())
					.isNotNull()
					.isNotEmpty()
					.isEqualTo(format("Expecting status code to be %s but was %s", status(), expectedStatus));
		}
	}

	protected abstract int status();

	protected abstract void test(AssertionInfo info, HttpResponse httpResponse);
}