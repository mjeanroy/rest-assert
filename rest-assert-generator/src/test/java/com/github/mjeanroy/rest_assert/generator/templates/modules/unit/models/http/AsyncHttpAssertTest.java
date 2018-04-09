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

import java.util.Map;

import com.github.mjeanroy.junit4.runif.RunIf;
import com.github.mjeanroy.junit4.runif.RunIfRunner;
import com.github.mjeanroy.junit4.runif.conditions.AtLeastJava8Condition;
import com.github.mjeanroy.rest_assert.generator.templates.modules.AbstractTemplateModel;
import com.github.mjeanroy.rest_assert.generator.templates.modules.AbstractTemplateModelTest;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.bindings.AsyncHttpResponse;
import org.assertj.core.api.Condition;
import org.asynchttpclient.Response;
import org.junit.Before;
import org.junit.runner.RunWith;

import static com.github.mjeanroy.rest_assert.generator.templates.modules.unit.models.http.AsyncHttpAssert.asyncHttpAssert;

@RunWith(RunIfRunner.class)
@RunIf(AtLeastJava8Condition.class)
public class AsyncHttpAssertTest extends AbstractTemplateModelTest {

	private AsyncHttpAssert httpAssert;

	@Before
	public void setUp() {
		httpAssert = (AsyncHttpAssert) asyncHttpAssert();
	}

	@Override
	protected AbstractTemplateModel getTemplateModel() {
		return httpAssert;
	}

	@Override
	protected String getExpectedPackageName() {
		return "com.github.mjeanroy.rest_assert.api.http";
	}

	@Override
	protected String getExpectedClassName() {
		return "AsyncHttpAssert";
	}

	@Override
	protected String getExpectedCoreClassName() {
		return HttpResponseAssertions.class.getName();
	}

	@Override
	protected Class<?> getExpectedCoreClass() {
		return HttpResponseAssertions.class;
	}

	@Override
	protected String getExpectedActualClass() {
		return Response.class.getName();
	}

	@Override
	protected String getFactory() {
		return AsyncHttpResponse.class.getName();
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
