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

package com.github.mjeanroy.restassert.hamcrest.api.json.isequalto;

import com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mjeanroy.restassert.test.commons.IoTestUtils.readFile;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

abstract class AbstractJsonEqualToTest<T> {

	@Test
	final void it_should_pass() {
		MatcherAssert.assertThat(input(), run(actual()));
	}

	@Test
	void it_should_fail() {
		String actual = input();
		T expected = expected();

		String message = HamcrestTestUtils.generateHamcrestErrorMessage(
			String.join(System.lineSeparator(), Arrays.asList(
				"          → Expecting json entry \"firstName\" to be equal to \"Jane\"",
				"          → Expecting json entry \"gender\" to be equal to \"F\""
			)),

			String.join(System.lineSeparator(), Arrays.asList(
				"          → was \"John\"",
				"          → was \"M\""
			))
		);

		assertFailure(message, () ->
			MatcherAssert.assertThat(actual, run(expected))
		);
	}

	@Test
	void it_should_fail_with_null() {
		String actual = null;
		T expected = expected();

		String message = HamcrestTestUtils.generateHamcrestErrorMessage(
			"Expecting json not to be null",
			"was null"
		);

		assertFailure(message, () ->
			MatcherAssert.assertThat(actual, run(expected))
		);
	}

	abstract TypeSafeMatcher<String> run(T expected);

	abstract T actual();

	abstract T expected();

	private static String input() {
		return readFile("/json/actual.json");
	}
}
