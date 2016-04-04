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

package com.github.mjeanroy.rest_assert.internal.assertions.json.is_equal_to_ignoring;

import com.github.mjeanroy.rest_assert.error.CompositeError;
import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.internal.assertions.JsonAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertFailureResult;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.assertSuccessResult;
import static com.github.mjeanroy.rest_assert.tests.fixtures.JsonFixtures.jsonSuccess;
import static com.github.mjeanroy.rest_assert.utils.Utils.LINE_SEPARATOR;
import static java.util.Arrays.asList;

public abstract class AbstractJsonAssertion_isEqualToIgnoring_Test<T> {

	protected JsonAssertions assertions;

	@Before
	public void setUp() {
		assertions = JsonAssertions.instance();
	}

	@Test
	public void it_should_pass() throws Exception {
		AssertionResult result = invoke(actual(), successObject());
		assertSuccessResult(result);
	}

	@Test
	public void it_should_fail() throws Exception {
		AssertionResult result = invoke(actual(), failureObject());

		String expectedPattern = "" +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s," + LINE_SEPARATOR +
				"Expecting json entry %s to be equal to %s but was %s";

		Object[] args = new Object[] {
				"array[0]", 1.1, 1.0,
				"array[1]", 2.1, 2.0,
				"array[2]", 3.1, 3.0
		};

		assertFailureResult(result, CompositeError.class, expectedPattern, args);
	}

	protected abstract AssertionResult invoke(String actual, T expected);

	protected String actual() {
		return jsonSuccess();
	}

	protected List<String> ignoringKeys() {
		return asList("str", "nb", "bool");
	}

	protected abstract T successObject() throws Exception;

	protected abstract T failureObject() throws Exception;
}
