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

package com.github.mjeanroy.restassert.core.internal.assertions.json.isequalto;

import com.github.mjeanroy.restassert.core.error.CompositeError;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertSuccessResult;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonSuccess;
import static com.github.mjeanroy.restassert.core.utils.Utils.LINE_SEPARATOR;

public abstract class AbstractJsonAssertion_isEqualTo_Test<T> {

	JsonAssertions assertions;

	@Before
	public void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	public void it_should_pass_with_object() {
		AssertionResult result = invoke(actual(), successObject());
		assertSuccessResult(result);
	}

	@Test
	public void it_should_fail() {
		AssertionResult result = invoke(actual(), failureObject());

		String expectedPattern = "" +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s";

		Object[] args = new Object[] {
				"str", "bar", "foo",
				"nb", 2.0, 1.0,
				"bool", false, true,
				"array[0]", 1.1, 1.0,
				"array[1]", 2.1, 2.0,
				"array[2]", 3.1, 3.0
		};

		assertFailureResult(result, CompositeError.class, expectedPattern, args);
	}

	protected abstract AssertionResult invoke(String actual, T expected);

	private String actual() {
		return jsonSuccess();
	}

	protected abstract T successObject();

	protected abstract T failureObject();
}