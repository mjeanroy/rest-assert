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

import static com.github.mjeanroy.rest_assert.generator.utils.GeneratorUtils.generateAssertMethodName;

import com.github.mjeanroy.rest_assert.generator.TemplateModel;
import com.github.mjeanroy.rest_assert.internal.assertions.CookieAssertions;

/**
 * Data model to use to produce valid {@link CookieAssertions} class for
 * assertj framework.
 *
 * This class is implemented as singleton.
 * This class is thread safe.
 */
public class CookieAssert extends AbstractCookieModel implements TemplateModel {

	/**
	 * Singleton object.
	 */
	private static final CookieAssert INSTANCE = new CookieAssert();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static CookieAssert cookieAssert() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private CookieAssert() {
	}

	@Override
	public String getPackageName() {
		return "com.github.mjeanroy.rest_assert.assertj.api";
	}

	@Override
	public String getClassName() {
		return "Abstract" + super.getClassName();
	}

	@Override
	public String getCoreClassName() {
		return "com.github.mjeanroy.rest_assert.assertj.internal.Cookies";
	}

	@Override
	public Class coreClass() {
		return CookieAssertions.class;
	}

	@Override
	public String buildCoreMethodName(String methodName) {
		return generateAssertMethodName(methodName);
	}

	@Override
	public String buildMethodName(String methodName) {
		return methodName;
	}
}