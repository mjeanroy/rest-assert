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

package com.github.mjeanroy.rest_assert.generator.templates.models.http_responses;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

public class HttpResponseAssertTest {

	private HttpResponseAssert httpResponseAssert;

	@Before
	public void setUp() {
		httpResponseAssert = HttpResponseAssert.httpResponseAssert();
	}

	@Test
	public void it_should_have_package_name() {
		assertThat(httpResponseAssert.getPackageName())
				.isNotNull()
				.isNotEmpty()
				.isEqualTo("com.github.mjeanroy.rest_assert.assertj.api");
	}

	@Test
	public void it_should_have_class_name() {
		assertThat(httpResponseAssert.getClassName())
				.isNotNull()
				.isNotEmpty()
				.isEqualTo("AbstractHttpResponseAssert");
	}

	@Test
	public void it_should_define_core_class() {
		assertThat(httpResponseAssert.getCoreClass())
				.isNotNull()
				.isEqualTo("com.github.mjeanroy.rest_assert.assertj.internal.HttpResponses");
	}

	@Test
	public void it_should_define_actual_class() {
		assertThat(httpResponseAssert.getActualClass())
				.isNotNull()
				.isEqualTo(HttpResponse.class.getName());
	}

	@Test
	public void it_should_build_methods_list() {
		List<Map<String,Object>> methods = httpResponseAssert.getMethods();

		assertThat(methods)
				.isNotNull()
				.isNotEmpty()
				.are(new Condition<Map<String, Object>>() {
					@Override
					public boolean matches(Map<String, Object> value) {
						return value.containsKey("arguments") &&
								value.containsKey("method_name") &&
								value.containsKey("core_method_name");
					}
				})
				.are(new Condition<Map<String, Object>>() {
					@Override
					public boolean matches(Map<String, Object> value) {
						return !value.get("method_name").toString().startsWith("assert");
					}
				})
				.areAtLeast(1, new Condition<Map<String, Object>>() {
					@Override
					public boolean matches(Map<String, Object> value) {
						return ((Collection) value.get("arguments")).size() > 0;
					}
				});
	}

	@Test
	public void it_should_build_data() {
		Map<String, Object> data = httpResponseAssert.data();

		assertThat(data)
				.isNotNull()
				.isNotEmpty()
				.hasSize(5)
				.containsKey("methods")
				.contains(
						entry("core_class_name", "com.github.mjeanroy.rest_assert.assertj.internal.HttpResponses"),
						entry("actual_class", "com.github.mjeanroy.rest_assert.internal.data.HttpResponse"),
						entry("class_name", "AbstractHttpResponseAssert"),
						entry("package", "com.github.mjeanroy.rest_assert.assertj.api")
				);
	}
}