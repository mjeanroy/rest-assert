/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2021 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
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
import com.github.mjeanroy.restassert.core.internal.common.Strings;
import com.github.mjeanroy.restassert.core.internal.error.Message;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHamcrestMatcher<T> extends TypeSafeMatcher<T> {

	private static final String EXPECTATION_HAMCREST_PREFIX = "Expected: ";
	private static final String EXPECTATION_MESSAGE_INDENT = Strings.repeat(' ', EXPECTATION_HAMCREST_PREFIX.length());

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
		Message mismatch = error().getMismatch();

		if (mismatch == null) {
			String expectation = error().getExpectation().getMessage();
			boolean isNegation = expectation.contains(" not ");
			mismatch = Message.message("was" + (isNegation ? "" : " not"));
		}

		mismatchDescription.appendText(
			prettifyHamcrestMessage(mismatch)
		);
	}

	@Override
	public void describeTo(Description description) {
		Message expectationMessage = error().getExpectation();
		description.appendText(
			prettifyHamcrestMessage(expectationMessage)
		);
	}

	protected abstract AssertionResult verify(T actual);

	private RestAssertError error() {
		if (assertionResult == null) {
			// If we are there, it means it likely has been called with null.
			assertionResult = verify(null);
		}

		return assertionResult.getError();
	}

	private static String prettifyHamcrestMessage(Message expectationMessage) {
		if (!expectationMessage.isMulti()) {
			return expectationMessage.formatMessage();
		}

		List<String> messages = expectationMessage.formatMessages();
		String indent = Strings.repeat(' ', "Expected: ".length());
		String output = messages.stream()
			.map(m -> indent + m)
			.collect(Collectors.joining(System.lineSeparator()));

		return output.trim();
	}
}
