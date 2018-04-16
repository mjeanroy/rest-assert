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

package com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie;

import static com.github.mjeanroy.restassert.generator.utils.GeneratorUtils.generateAssertMethodName;

import javax.servlet.http.Cookie;

import com.github.mjeanroy.restassert.core.internal.assertions.CookieAssertions;
import com.github.mjeanroy.restassert.core.internal.data.bindings.javax.JavaxCookie;
import com.github.mjeanroy.restassert.generator.TemplateModel;
import com.github.mjeanroy.restassert.generator.templates.modules.unit.models.AbstractUnitTemplateModel;

/**
 * Template model for rest-assert-unit CookieAssert class.
 */
public class JavaxCookieAssert extends AbstractUnitTemplateModel implements TemplateModel {

	/**
	 * Singleton instance.
	 */
	private static final JavaxCookieAssert INSTANCE = new JavaxCookieAssert();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel javaxCookieAssert() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private JavaxCookieAssert() {
		super();
	}

	@Override
	public String getActualClass() {
		return Cookie.class.getName();
	}

	@Override
	public String getCoreClassName() {
		return coreClass().getName();
	}

	@Override
	protected Class<?> coreClass() {
		return CookieAssertions.class;
	}

	@Override
	protected String getSubPackage() {
		return "cookie";
	}

	@Override
	public String getClassName() {
		return getClass().getSimpleName();
	}

	@Override
	public String buildCoreMethodName(String methodName) {
		return methodName;
	}

	@Override
	public String buildMethodName(String methodName) {
		return generateAssertMethodName(methodName);
	}

	@Override
	public String getFactory() {
		return JavaxCookie.class.getName();
	}
}
