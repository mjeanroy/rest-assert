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

import com.github.mjeanroy.restassert.generator.TemplateModel;
import com.github.mjeanroy.restassert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.restassert.internal.assertions.CookieAssertions;
import com.github.mjeanroy.restassert.internal.data.bindings.NingHttpCookie;
import com.ning.http.client.cookie.Cookie;

import static com.github.mjeanroy.restassert.generator.utils.GeneratorUtils.generateAssertMethodName;

/**
 * Template model for rest-assert-unit CookieAssert class.
 */
public class NingHttpCookieAssert extends AbstractTemplateModel implements TemplateModel {

	/**
	 * Singleton instance.
	 */
	private static final NingHttpCookieAssert INSTANCE = new NingHttpCookieAssert();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel asyncHttpCookieAssert() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private NingHttpCookieAssert() {
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
	public String getPackageName() {
		return "com.github.mjeanroy.restassert.api.cookie";
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
		return NingHttpCookie.class.getName();
	}
}