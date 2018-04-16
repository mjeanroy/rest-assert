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

package com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.generator.TemplateModel;

/**
 * Template model for rest-assert-unit HttpAssert class.
 */
public class HttpAssert extends AbstractUnitHttpAssertTemplateModel implements TemplateModel {

	/**
	 * Singleton Instance.
	 */
	private static final HttpAssert INSTANCE = new HttpAssert(null);

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel httpAssert() {
		return INSTANCE;
	}

	/**
	 * Get new instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel httpAssert(Class<?> factory) {
		return new HttpAssert(factory);
	}

	private final Class<?> factory;

	// Ensure non instantiation
	private HttpAssert(Class<?> factory) {
		super();
		this.factory = factory;
	}

	@Override
	public String getActualClass() {
		return HttpResponse.class.getName();
	}

	@Override
	public String getFactory() {
		return factory != null ? String.format("%s.create", factory.getName()) : null;
	}
}
