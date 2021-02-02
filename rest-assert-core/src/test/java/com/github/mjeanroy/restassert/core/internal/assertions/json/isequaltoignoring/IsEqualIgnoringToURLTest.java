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

package com.github.mjeanroy.restassert.core.internal.assertions.json.isequaltoignoring;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonUrlFailure;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonUrlSuccess;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IsEqualIgnoringToURLTest extends AbstractJsonAssertion_isEqualToIgnoring_Test<URL> {

	@Override
	protected AssertionResult run(String actual, URL expected, Iterable<String> ignoringKeys) {
		return assertions.isEqualToIgnoring(actual, expected, ignoringKeys);
	}

	@Override
	protected URL successObject() {
		return jsonUrlSuccess();
	}

	@Override
	protected URL failureObject() {
		return jsonUrlFailure();
	}

	@Test
	public void it_should_fail_if_uri_syntax_exception() throws Exception {
		final String actual = "{}";
		final URL url = new URL("http://fgoogle.com/q/h?s=^IXIC");
		final List<String> objects = emptyList();

		assertThatThrownBy(isEqualToIgnoring(actual, url, objects))
				.isExactlyInstanceOf(AssertionError.class)
				.hasCauseExactlyInstanceOf(URISyntaxException.class);
	}

	private static ThrowingCallable isEqualToIgnoring(final String actual, final URL url, final List<String> objects) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				assertions.isEqualToIgnoring(actual, url, objects);
			}
		};
	}
}
