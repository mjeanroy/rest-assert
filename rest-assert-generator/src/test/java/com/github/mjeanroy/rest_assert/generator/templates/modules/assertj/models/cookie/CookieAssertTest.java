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

package com.github.mjeanroy.rest_assert.generator.templates.modules.assertj.models.cookie;

import java.util.Map;

import org.assertj.core.api.Condition;
import org.junit.Before;

import com.github.mjeanroy.rest_assert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.rest_assert.generator.templates.modules.AbstractTemplateModelTest;
import com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;

public class CookieAssertTest extends AbstractTemplateModelTest {

	private CookieAssert cookieAssert;

	@Before
	public void setUp() {
		cookieAssert = CookieAssert.cookieAssert();
	}

	@Override
	protected AbstractTemplateModel getTemplateModel() {
		return cookieAssert;
	}

	@Override
	protected String getExpectedPackageName() {
		return "com.github.mjeanroy.rest_assert.assertj.api";
	}

	@Override
	protected String getExpectedClassName() {
		return "AbstractCookieAssert";
	}

	@Override
	protected String getExpectedCoreClassName() {
		return "com.github.mjeanroy.rest_assert.assertj.internal.Cookies";
	}

	@Override
	protected Class getExpectedCoreClass() {
		return CookieAssertions.class;
	}

	@Override
	protected String getExpectedActualClass() {
		return Cookie.class.getName();
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
}