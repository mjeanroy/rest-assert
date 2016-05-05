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

package com.github.mjeanroy.rest_assert.generator.templates.modules.unit.models.http;

import com.github.mjeanroy.rest_assert.generator.TemplateModel;
import com.github.mjeanroy.rest_assert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.bindings.GoogleHttpResponse;
import com.google.api.client.http.HttpResponse;

import static com.github.mjeanroy.rest_assert.generator.utils.GeneratorUtils.generateAssertMethodName;

/**
 * Template model for rest-assert-unit HttpAssert class.
 */
public class GoogleHttpAssert extends AbstractTemplateModel implements TemplateModel {

	/**
	 * Singleton Instance.
	 */
	private static final GoogleHttpAssert INSTANCE = new GoogleHttpAssert();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel googleHttpAssert() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private GoogleHttpAssert() {
		super();
	}

	@Override
	public String getActualClass() {
		return HttpResponse.class.getName();
	}

	@Override
	public String getCoreClassName() {
		return coreClass().getName();
	}

	@Override
	public Class<?> coreClass() {
		return HttpResponseAssertions.class;
	}

	@Override
	public String getPackageName() {
		return "com.github.mjeanroy.rest_assert.api.http";
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
		return GoogleHttpResponse.class.getName();
	}
}
