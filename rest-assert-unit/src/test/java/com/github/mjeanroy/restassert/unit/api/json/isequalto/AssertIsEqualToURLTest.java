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

package com.github.mjeanroy.restassert.unit.api.json.isequalto;

import java.net.URL;

import static com.github.mjeanroy.restassert.unit.api.json.JsonAssert.assertIsEqualTo;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonUrlFailure;
import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonUrlSuccess;

public class AssertIsEqualToURLTest extends AbstractJsonIsEqualToTest<URL> {

	@Override
	protected void invoke(URL actual) {
		assertIsEqualTo(actual(), actual);
	}

	@Override
	protected void invoke(String message, URL actual) {
		assertIsEqualTo(message, actual(), actual);
	}

	@Override
	protected URL success() {
		return jsonUrlSuccess();
	}

	@Override
	protected URL failure() {
		return jsonUrlFailure();
	}
}
