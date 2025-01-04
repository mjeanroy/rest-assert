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

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.json.JsonArray;
import com.github.mjeanroy.restassert.test.json.JsonObject;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.ReflectionTestUtils.readField;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class JsonAssertionsTest {

	@Test
	void it_should_create_new_assertion_object() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("foo", "bar")
		);

		String json = jsonObject.toJson();
		JsonAssert assertions = JsonAssertions.assertThatJson(json);

		assertThat(assertions).isNotNull();
		assertThat((String) readField(assertions, "actual")).isEqualTo(json);
	}

	@Test
	void it_should_create_new_assertion_object_from_http_response() {
		JsonObject jsonObject = jsonObject(
			jsonEntry("foo", "bar")
		);

		String json = jsonObject.toJson();
		HttpResponse httpResponse = new HttpResponseBuilderImpl().setContent(json).build();

		JsonAssert assertions = JsonAssertions.assertThatJson(httpResponse);

		assertThat(assertions).isNotNull();
		assertThat((String) readField(assertions, "actual")).isEqualTo(json);
	}

	@Test
	void it_should_translate_to_map_assert() {
		String actual = JsonObject.toJson(
			jsonEntry("foo", "bar")
		);

		JsonAssertions.assertThatJson(actual).asObject().hasSize(1).contains(
			entry("foo", "bar")
		);
	}

	@Test
	void it_should_translate_to_list_assert() {
		String actual = JsonArray.toJson(
			0, 1, 2
		);

		JsonAssertions.assertThatJson(actual).asArray().hasSize(3).containsExactly(
			0, 1, 2
		);
	}

	@Test
	void it_should_translate_to_boolean_assert() {
		JsonAssertions.assertThatJson("false").asBoolean().isFalse();
		JsonAssertions.assertThatJson("true").asBoolean().isTrue();
	}

	@Test
	void it_should_translate_to_double_assert() {
		JsonAssertions.assertThatJson("0.5").asNumber().isEqualTo(0.5);
	}

	@Test
	void it_should_translate_to_string_assert() {
		JsonAssertions.assertThatJson("\"Hello World\"").asString().isEqualTo(
			"Hello World"
		);
	}
}
