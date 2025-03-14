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

package com.github.mjeanroy.restassert.generator.templates.modules.assertj.models.json;

import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModelTest;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

class JsonAssertTest extends AbstractTemplateModelTest {

	private JsonAssert jsonAssert;

	@BeforeEach
	void setUp() {
		jsonAssert = (JsonAssert) JsonAssert.jsonAssert();
	}

	@Override
	protected AbstractTemplateModel getTemplateModel() {
		return jsonAssert;
	}

	@Override
	protected String getExpectedPackageName() {
		return "com.github.mjeanroy.restassert.assertj.api";
	}

	@Override
	protected String getExpectedClassName() {
		return "AbstractJsonAssert";
	}

	@Override
	protected String getExpectedCoreClassName() {
		return "com.github.mjeanroy.restassert.assertj.internal.Jsons";
	}

	@Override
	protected Class<?> getExpectedCoreClass() {
		return JsonAssertions.class;
	}

	@Override
	protected String getExpectedActualClass() {
		return String.class.getName();
	}

	@Override
	protected Condition<Map<String, Object>> getMethodCondition() {
		return new Condition<Map<String, Object>>() {
			@Override
			public boolean matches(Map<String, Object> value) {
				return !value.get("method_name").toString().startsWith("assert");
			}
		};
	}

	@Override
	protected String getFactory() {
		return null;
	}
}
