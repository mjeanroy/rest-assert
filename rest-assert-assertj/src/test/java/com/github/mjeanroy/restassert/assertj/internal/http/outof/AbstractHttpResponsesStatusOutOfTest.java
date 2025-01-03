/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.assertj.internal.http.outof;

import com.github.mjeanroy.restassert.assertj.internal.HttpResponses;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Range;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.assertj.core.api.AssertionInfo;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractHttpResponsesStatusOutOfTest {

	final HttpResponses httpResponses = HttpResponses.instance();

	@Test
	void should_pass() {
		Range range = getRange();
		for (int i = 0; i <= 999; i++) {
			if (i < range.getStart() || i > range.getEnd()) {
				HttpResponse httpResponse = new HttpResponseBuilderImpl().setStatus(i).build();
				run(someInfo(), httpResponse);
			}
		}
	}

	@Test
	void should_fail() {
		AssertionInfo info = someInfo();
		Range range = getRange();
		int start = range.getStart();
		int end = range.getEnd();
		for (int status = start; status <= end; status++) {
			HttpResponse httpResponse = new HttpResponseBuilderImpl().setStatus(status).build();

			try {
				run(info, httpResponse);
				failBecauseExpectedAssertionErrorWasNotThrown();
			}
			catch (AssertionError e) {
				assertThat(e.getMessage()).isEqualTo(String.format("Expecting status code to be out of %s and %s but was %s", start, end, status));
			}
		}
	}

	abstract Range getRange();

	abstract void run(AssertionInfo info, HttpResponse httpResponse);
}
