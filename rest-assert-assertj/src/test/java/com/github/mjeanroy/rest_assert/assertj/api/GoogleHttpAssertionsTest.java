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

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.data.bindings.googlehttp.GoogleHttpResponse;
import com.github.mjeanroy.rest_assert.tests.json.JsonObject;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static com.github.mjeanroy.rest_assert.tests.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.rest_assert.tests.json.JsonObject.jsonObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(com.google.api.client.http.HttpResponse.class)
public class GoogleHttpAssertionsTest {

	@Test
	public void it_should_create_new_assertion_object() throws Exception {
		com.google.api.client.http.HttpResponse response = mock(com.google.api.client.http.HttpResponse.class);
		HttpResponseAssert assertions = GoogleHttpAssertions.assertThat(response);

		assertThat(assertions).isNotNull();
		HttpResponse httpResponse = (HttpResponse) FieldUtils.readField(assertions, "actual", true);
		assertThat(httpResponse)
				.isNotNull()
				.isExactlyInstanceOf(GoogleHttpResponse.class);
	}

	@Test
	public void it_should_create_new_json_assertion_object() throws Exception {
		JsonObject object = jsonObject(
				jsonEntry("foo", "bar")
		);

		String body = object.toJson();

		com.google.api.client.http.HttpResponse response = mock(com.google.api.client.http.HttpResponse.class);
		when(response.getContent()).thenReturn(new ByteArrayInputStream(body.getBytes()));
		when(response.getContentCharset()).thenReturn(Charset.defaultCharset());

		JsonAssert assertions = GoogleHttpAssertions.assertJsonThat(response);

		assertThat(assertions).isNotNull();
		String actual = (String) FieldUtils.readField(assertions, "actual", true);
		assertThat(actual)
				.isNotNull()
				.isEqualTo(body);
	}
}
