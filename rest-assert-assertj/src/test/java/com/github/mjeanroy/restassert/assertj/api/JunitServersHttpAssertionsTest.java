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

import com.github.mjeanroy.restassert.tests.builders.junitservers.JunitServersHttpResponseBuilder;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.ReflectionTestUtils.readField;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JSONTestUtils.toJSON;
import static org.assertj.core.api.Assertions.assertThat;

class JunitServersHttpAssertionsTest {

	@Test
	void it_should_create_new_assertion_object() {
		com.github.mjeanroy.junit.servers.client.HttpResponse response = new JunitServersHttpResponseBuilder().build();
		JunitServersHttpAssertions.assertThat(response).isNotNull();
	}

	@Test
	void it_should_create_new_assertion_object_from_null() {
		com.github.mjeanroy.junit.servers.client.HttpResponse response = null;
		JunitServersHttpAssertions.assertThat(response).isNull();
	}

	@Test
	void it_should_create_new_json_assertion_object() {
		String body = toJSON(jsonEntry("foo", "bar"));
		com.github.mjeanroy.junit.servers.client.HttpResponse response = new JunitServersHttpResponseBuilder().setContent(body).build();
		JsonAssert assertions = JunitServersHttpAssertions.assertThatJson(response);

		assertThat(assertions).isNotNull();
		assertThat((Object) readField(assertions, "actual")).isEqualTo(body);
	}
}
