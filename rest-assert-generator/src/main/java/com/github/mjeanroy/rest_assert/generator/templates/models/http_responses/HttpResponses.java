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

package com.github.mjeanroy.rest_assert.generator.templates.models.http_responses;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mjeanroy.rest_assert.generator.TemplateModel;
import com.github.mjeanroy.rest_assert.generator.templates.models.internal.Arg;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;

/**
 * Data model to use to produce valid {@link HttpResponseAssertions} class for
 * assertj framework.
 * <p/>
 * This class is implemented as singleton.
 * This class is thread safe.
 */
public class HttpResponses extends AbstractHttpResponseModel implements TemplateModel {

	/**
	 * Singleton object.
	 */
	private static final HttpResponses INSTANCE = new HttpResponses();

	/**
	 * Get singleton instance.
	 *
	 * @return Singleton instance.
	 */
	public static HttpResponses httpResponsesModel() {
		return INSTANCE;
	}

	// Ensure non instantiation
	private HttpResponses() {
	}

	@Override
	public String getPackageName() {
		return "com.github.mjeanroy.rest_assert.assertj.internal";
	}

	@Override
	protected String getCoreClass() {
		return coreClass().getName();
	}

	private Class coreClass() {
		return HttpResponseAssertions.class;
	}

	@Override
	protected Map<String, Object> getMethod(Method method) {
		String coreMethodName = method.getName();

		List<Arg> args;
		Class[] paramTypes = method.getParameterTypes();
		int size = paramTypes == null ? 0 : paramTypes.length;
		if (size > 1) {
			args = new ArrayList<>(size);
			for (int i = 1; i < paramTypes.length; i++) {
				args.add(new Arg(paramTypes[i].getName(), "arg" + i));
			}
		} else {
			args = emptyList();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("core_method_name", coreMethodName);
		map.put("method_name", getTargetMethodName(coreMethodName));
		map.put("arguments", args);

		return unmodifiableMap(map);
	}

	private String getTargetMethodName(String coreMethodName) {
		String targetMethodName = Character.toUpperCase(coreMethodName.charAt(0)) + coreMethodName.substring(1);
		return "assert" + targetMethodName;
	}
}
