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
 * furnished to do so, subject to the following httpResponses:
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

package com.github.mjeanroy.rest_assert.assertj.api.json.contains;

import com.github.mjeanroy.rest_assert.assertj.api.AbstractApiTest;
import com.github.mjeanroy.rest_assert.assertj.api.JsonAssert;
import com.github.mjeanroy.rest_assert.assertj.internal.Jsons;
import org.assertj.core.api.AssertionInfo;

import static com.github.mjeanroy.rest_assert.tests.fixtures.JsonFixtures.jsonSuccess;
import static java.util.Collections.singleton;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JsonAssert_contains_iterable_Test extends AbstractApiTest<Jsons, JsonAssert> {

	@Override
	protected Jsons createAssertions() {
		return mock(Jsons.class);
	}

	@Override
	protected JsonAssert createApi() {
		return new JsonAssert(actual());
	}

	@Override
	protected JsonAssert invoke() {
		return api.contains(singleton("foo"));
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertContains(any(AssertionInfo.class), eq(actual()), eq(singleton("foo")));
	}

	protected String actual() {
		return jsonSuccess();
	}
}