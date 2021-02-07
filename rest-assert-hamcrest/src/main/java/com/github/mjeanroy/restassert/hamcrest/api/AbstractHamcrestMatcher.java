/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.hamcrest.api;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class AbstractHamcrestMatcher<T> extends TypeSafeMatcher<T> {

	private AssertionResult assertionResult;

	protected AbstractHamcrestMatcher() {
		this.assertionResult = null;
	}

	@Override
	protected final boolean matchesSafely(T actual) {
		assertionResult = verify(actual);
		return assertionResult.isSuccess();
	}

	@Override
	protected final void describeMismatchSafely(T item, Description mismatchDescription) {
		mismatchDescription.appendText(error().getMismatch());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(error().getExpectation());
	}

	protected abstract AssertionResult verify(T actual);

	private RestAssertError error() {
		if (assertionResult == null) {
			throw new IllegalStateException(
				"Cannot read assertion result, did you call `matchesSafely`?"
			);
		}

		return assertionResult.getError();
	}
}
