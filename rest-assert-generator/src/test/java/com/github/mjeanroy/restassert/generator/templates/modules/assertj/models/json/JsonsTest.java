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

package com.github.mjeanroy.restassert.generator.templates.modules.assertj.models.json;

import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModelTest;
import com.github.mjeanroy.restassert.core.internal.assertions.JsonAssertions;
import org.assertj.core.api.Condition;
import org.junit.Before;

import java.util.Map;

public class JsonsTest extends AbstractTemplateModelTest {

	private Jsons jsons;

	@Before
	public void setUp() {
		jsons = (Jsons) Jsons.jsonsModel();
	}

	@Override
	protected AbstractTemplateModel getTemplateModel() {
		return jsons;
	}

	@Override
	protected String getExpectedPackageName() {
		return "com.github.mjeanroy.restassert.assertj.internal";
	}

	@Override
	protected String getExpectedClassName() {
		return "Jsons";
	}

	@Override
	protected String getExpectedCoreClassName() {
		return JsonAssertions.class.getName();
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
	protected String getFactory() {
		return null;
	}

	@Override
	protected Condition<Map<String, Object>> getMethodCondition() {
		return new Condition<Map<String, Object>>() {
			@Override
			public boolean matches(Map<String, Object> value) {
				String expectedMethodName = value.get("method_name").toString().substring("assert".length());
				expectedMethodName = Character.toLowerCase(expectedMethodName.charAt(0)) + expectedMethodName.substring(1);
				return expectedMethodName.equals(value.get("core_method_name"));
			}
		};
	}
}
