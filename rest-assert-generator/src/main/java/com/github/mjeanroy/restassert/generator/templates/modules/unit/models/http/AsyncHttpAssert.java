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

import com.github.mjeanroy.restassert.core.internal.data.bindings.async.AsyncHttpResponse;
import com.github.mjeanroy.restassert.generator.TemplateModel;

/**
 * Template model for rest-assert-unit HttpAssert class.
 */
public class AsyncHttpAssert extends AbstractUnitHttpAssertTemplateModel implements TemplateModel {

	/**
	 * Singleton Instance.
	 */
	private static final AsyncHttpAssert INSTANCE = new AsyncHttpAssert();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static TemplateModel asyncHttpAssert() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private AsyncHttpAssert() {
		super();
	}

	@Override
	public String getActualClass() {
		// Be careful, do not import org.asynchttpclient, as it will not compile on JDK 7
		// and the unit test suite run on JDK 7.
		return "org.asynchttpclient.Response";
	}

	@Override
	public String getFactory() {
		return AsyncHttpResponse.class.getName();
	}
}
