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

package com.github.mjeanroy.restassert.unit.api.json.isequaltoignoring;

import com.github.mjeanroy.restassert.unit.api.AbstractAssertTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonSuccess;
import static java.util.Arrays.asList;

public abstract class AbstractJsonIsEqualToIgnoringTest<T> extends AbstractAssertTest<T> {

	@Test
	void it_should_pass() {
		run(success());
		run("foo", success());
	}

	@Test
	void it_should_fail() {
		T failure = failure();
		String message = String.join(System.lineSeparator(), asList(
			"Expecting json entry array[0] to be equal to 1.1 but was 1.0,",
			"Expecting json entry array[1] to be equal to 2.1 but was 2.0,",
			"Expecting json entry array[2] to be equal to 3.1 but was 3.0"
		));

		assertFailure(message, () -> run(failure));
	}

	@Test
	void it_should_fail_with_custom_message() {
		T failure = failure();
		String message = "foo";
		assertFailure(message, () -> run(message, failure));
	}

	String actual() {
		return jsonSuccess();
	}

	List<String> ignoringKeys() {
		return asList("str", "nb", "bool");
	}

	protected abstract T success();

	protected abstract T failure();
}
