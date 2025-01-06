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

package com.github.mjeanroy.restassert.assertj.api.json.is;

import com.github.mjeanroy.restassert.assertj.api.AbstractApiTest;
import com.github.mjeanroy.restassert.assertj.api.JsonAssert;
import com.github.mjeanroy.restassert.assertj.internal.Jsons;
import org.assertj.core.api.AssertionInfo;

import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonObject;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class IsArrayEntryTest extends AbstractApiTest<Jsons, JsonAssert> {

	@Override
	protected Jsons createAssertions() {
		return mock(Jsons.class);
	}

	@Override
	protected JsonAssert createApi() {
		return new JsonAssert(actual());
	}

	@Override
	protected JsonAssert run() {
		return api.isArrayEntry("roles");
	}

	@Override
	protected void verifyApiCall() {
		verify(assertions).assertIsArrayEntry(any(AssertionInfo.class), eq(actual()), eq("roles"));
	}

	private static String actual() {
		return toJSON(
			jsonEntry("id", 1),
			jsonEntry("name", "John Doe"),
			jsonEntry("active", true),
			jsonEntry("gender"),
			jsonEntry("roles", jsonArray("ADMIN")),
			jsonEntry("permissions", jsonObject())
		);
	}
}
