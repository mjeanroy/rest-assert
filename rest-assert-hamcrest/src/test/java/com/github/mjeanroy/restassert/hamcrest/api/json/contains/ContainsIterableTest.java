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

package com.github.mjeanroy.restassert.hamcrest.api.json.contains;

import com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils;
import org.hamcrest.MatcherAssert;

import static com.github.mjeanroy.restassert.hamcrest.api.json.JsonMatchers.contains;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static java.util.Arrays.asList;

class ContainsIterableTest extends AbstractJsonContainsTest {

	@Override
	void test_pass() {
		MatcherAssert.assertThat(
			input.toJson(),
			contains(
				asList("id", "name")
			)
		);
	}

	@Override
	void test_fail() {
		String entryName1 = "foo";
		String entryName2 = "bar";
		String entryName3 = "id";
		String message = HamcrestTestUtils.generateHamcrestErrorMessage(
			buildExpectationMessage(entryName1, entryName2, entryName3),
			buildMismatchMessage()
		);

		assertFailure(message, () ->
			MatcherAssert.assertThat(
				input.toString(),
				contains(
					asList(entryName1, entryName2, entryName3)
				)
			)
		);
	}
}
