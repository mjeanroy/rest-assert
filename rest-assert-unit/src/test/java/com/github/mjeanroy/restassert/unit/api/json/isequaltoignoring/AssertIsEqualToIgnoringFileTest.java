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

package com.github.mjeanroy.restassert.unit.api.json.isequaltoignoring;

import java.io.File;

import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertIsEqualToIgnoring;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonFileFailure;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonFileSuccess;

public class AssertIsEqualToIgnoringFileTest extends AbstractJsonIsEqualToIgnoringTest<File> {

	@Override
	protected void invoke(File actual) {
		assertIsEqualToIgnoring(actual(), actual, ignoringKeys());
	}

	@Override
	protected void invoke(String message, File actual) {
		assertIsEqualToIgnoring(message, actual(), actual, ignoringKeys());
	}

	@Override
	protected File success() {
		return jsonFileSuccess();
	}

	@Override
	protected File failure() {
		return jsonFileFailure();
	}
}
