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

package com.github.mjeanroy.rest_assert.assertj.api;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.json.JsonObject;
import com.github.mjeanroy.rest_assert.tests.mocks.CookieMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilderImpl;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.rest_assert.tests.json.JsonObject.jsonObject;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseAssertionsTest {

	@Test
	public void it_should_create_new_assertion_object() throws Exception {
		HttpResponse response = new HttpResponseMockBuilderImpl().build();
		HttpResponseAssert assertions = HttpResponseAssertions.assertThat(response);

		assertThat(assertions).isNotNull();
		HttpResponse httpResponse = (HttpResponse) FieldUtils.readField(assertions, "actual", true);
		assertThat(httpResponse)
				.isNotNull()
				.isSameAs(response);
	}

	@Test
	public void it_should_create_new_cookie_assertion_object() throws Exception {
		Cookie cookie = new CookieMockBuilder().build();
		CookieAssert assertions = HttpResponseAssertions.assertThat(cookie);

		assertThat(assertions).isNotNull();
		Cookie actual = (Cookie) FieldUtils.readField(assertions, "actual", true);
		assertThat(actual)
				.isNotNull()
				.isSameAs(cookie);
	}

	@Test
	public void it_should_create_new_json_assertion_object() throws Exception {
		JsonObject object = jsonObject(
				jsonEntry("foo", "bar")
		);

		String body = object.toJson();

		HttpResponse response = new HttpResponseMockBuilderImpl()
			.setContent(body)
			.build();

		JsonAssert assertions = HttpResponseAssertions.assertJsonThat(response);

		assertThat(assertions).isNotNull();
		String actual = (String) FieldUtils.readField(assertions, "actual", true);
		assertThat(actual)
				.isNotNull()
				.isEqualTo(body);
	}
}
