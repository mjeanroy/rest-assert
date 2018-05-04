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

package com.github.mjeanroy.restassert.assertj.internal.json.isequalto;

import com.github.mjeanroy.restassert.assertj.internal.Jsons;
import org.assertj.core.api.AssertionInfo;
import org.junit.Test;

import static com.github.mjeanroy.restassert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonSuccess;
import static com.github.mjeanroy.restassert.core.internal.common.Files.LINE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractJsonsIsEqualToTest<T> {

	final Jsons jsons = Jsons.instance();

	@Test
	public void should_pass() {
		invoke(someInfo(), success());
	}

	@Test
	public void should_fail() {
		final AssertionInfo info = someInfo();
		final T json = failure();

		try {
			invoke(info, json);
			failBecauseExpectedAssertionErrorWasNotThrown();
		} catch (AssertionError e) {
			String expectedMessage = "" +
					"Expecting json entry \"str\" to be equal to \"bar\" but was \"foo\"," + LINE_SEPARATOR +
					"Expecting json entry \"nb\" to be equal to 2.0 but was 1.0," + LINE_SEPARATOR +
					"Expecting json entry \"bool\" to be equal to false but was true," + LINE_SEPARATOR +
					"Expecting json entry \"array[0]\" to be equal to 1.1 but was 1.0," + LINE_SEPARATOR +
					"Expecting json entry \"array[1]\" to be equal to 2.1 but was 2.0," + LINE_SEPARATOR +
					"Expecting json entry \"array[2]\" to be equal to 3.1 but was 3.0";

			assertThat(e.getMessage())
					.isNotNull()
					.isNotEmpty()
					.isEqualTo(expectedMessage);
		}
	}

	String actual() {
		return jsonSuccess();
	}

	protected abstract T success();

	protected abstract T failure();

	protected abstract void invoke(AssertionInfo info, T json);
}
