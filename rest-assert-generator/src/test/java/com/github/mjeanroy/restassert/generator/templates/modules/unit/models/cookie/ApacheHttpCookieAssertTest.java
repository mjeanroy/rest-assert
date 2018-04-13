/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie;

import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModelTest;
import com.github.mjeanroy.restassert.internal.assertions.CookieAssertions;
import com.github.mjeanroy.restassert.internal.data.bindings.ApacheHttpCookie;
import org.apache.http.cookie.Cookie;
import org.assertj.core.api.Condition;
import org.junit.Before;

import java.util.Map;

import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie.ApacheHttpCookieAssert.apacheHttpCookieAssert;

public class ApacheHttpCookieAssertTest extends AbstractTemplateModelTest {

	private ApacheHttpCookieAssert cookieAssert;

	@Before
	public void setUp() {
		cookieAssert = (ApacheHttpCookieAssert) apacheHttpCookieAssert();
	}

	@Override
	protected AbstractTemplateModel getTemplateModel() {
		return cookieAssert;
	}

	@Override
	protected String getExpectedPackageName() {
		return "com.github.mjeanroy.restassert.api.cookie";
	}

	@Override
	protected String getExpectedClassName() {
		return "ApacheHttpCookieAssert";
	}

	@Override
	protected String getExpectedCoreClassName() {
		return CookieAssertions.class.getName();
	}

	@Override
	protected Class<?> getExpectedCoreClass() {
		return CookieAssertions.class;
	}

	@Override
	protected String getExpectedActualClass() {
		return Cookie.class.getName();
	}

	@Override
	protected String getFactory() {
		return ApacheHttpCookie.class.getName();
	}

	@Override
	protected Condition<Map<String, Object>> getMethodCondition() {
		return new Condition<Map<String, Object>>() {
			@Override
			public boolean matches(Map<String, Object> value) {
				String methodName = (String) value.get("method_name");
				return methodName.startsWith("assert");
			}
		};
	}
}
