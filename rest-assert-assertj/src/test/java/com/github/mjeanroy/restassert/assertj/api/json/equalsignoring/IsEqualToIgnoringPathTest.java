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

package com.github.mjeanroy.restassert.assertj.api.json.equalsignoring;

import com.github.mjeanroy.restassert.assertj.api.JsonAssert;
import org.assertj.core.api.AssertionInfo;

import java.nio.file.Path;

import static com.github.mjeanroy.restassert.tests.fixtures.JsonFixtures.jsonPathSuccess;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class IsEqualToIgnoringPathTest extends AbstractJsonIsEqualIgnoringToTest {

	@Override
	protected JsonAssert invoke() {
		return api.isEqualToIgnoring(fixture(), keys());
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertIsEqualToIgnoring(any(AssertionInfo.class), any(String.class), eq(fixture()), eq(keys()));
	}

	private Iterable<String> keys() {
		return asList("foo", "bar");
	}

	private Path fixture() {
		return jsonPathSuccess();
	}
}
