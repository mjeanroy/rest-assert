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

package com.github.mjeanroy.restassert.generator.templates.modules.assertj.models.json;

import com.github.mjeanroy.restassert.generator.TemplateModel;
import com.github.mjeanroy.restassert.internal.assertions.JsonAssertions;

import static com.github.mjeanroy.restassert.generator.utils.GeneratorUtils.generateAssertMethodName;

/**
 * Data model to use to produce valid internal assertions for {@link JsonAssertions} class for
 * assertj framework.
 *
 * This class is implemented as singleton.
 * This class is thread safe.
 */
public class Jsons extends AbstractJsonsModel implements TemplateModel {

	/**
	 * Singleton object.
	 */
	private static final Jsons INSTANCE = new Jsons();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel jsonsModel() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private Jsons() {
	}

	@Override
	public String getPackageName() {
		return "com.github.mjeanroy.restassert.assertj.internal";
	}

	@Override
	public String getCoreClassName() {
		return coreClass().getName();
	}

	@Override
	protected Class<?> coreClass() {
		return JsonAssertions.class;
	}

	@Override
	public String buildCoreMethodName(String methodName) {
		return methodName;
	}

	@Override
	public String buildMethodName(String methodName) {
		return generateAssertMethodName(methodName);
	}
}