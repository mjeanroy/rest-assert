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

package com.github.mjeanroy.restassert.assertj.api;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.tests.builders.MockCookieBuilder;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.ReflectionTestUtils.readField;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseAssertionsTest {

	@Test
	void it_should_create_new_assertion_object() {
		HttpResponse response = new HttpResponseBuilderImpl().build();
		HttpResponseAssert assertions = HttpResponseAssertions.assertThat(response);

		assertThat(assertions).isNotNull();
		assertThat((Object) readField(assertions, "actual")).isSameAs(response);
	}

	@Test
	void it_should_create_new_cookie_assertion_object() {
		Cookie cookie = new MockCookieBuilder().build();
		CookieAssert assertions = HttpResponseAssertions.assertThat(cookie);

		assertThat(assertions).isNotNull();
		assertThat((Object) readField(assertions, "actual")).isSameAs(cookie);
	}

	@Test
	void it_should_create_new_json_assertion_object() {
		JsonObject object = jsonObject(
			jsonEntry("foo", "bar")
		);

		String body = object.toJson();
		HttpResponse response = new HttpResponseBuilderImpl().setContent(body).build();

		JsonAssert assertions = HttpResponseAssertions.assertThatJson(response);

		assertThat(assertions).isNotNull();
		assertThat((Object) readField(assertions, "actual")).isEqualTo(body);
	}

	@Test
	void it_should_fluently_create_assertion_object() {
		JsonObject object = jsonObject(
			jsonEntry("foo", "bar")
		);

		String body = object.toJson();
		HttpResponse response = new HttpResponseBuilderImpl().setContent(body).build();
		JsonAssert assertions = HttpResponseAssertions.assertThat(response).isOk().extractingJsonBody();

		assertThat(assertions).isNotNull();
		assertThat((Object) readField(assertions, "actual")).isEqualTo(body);
	}
}
