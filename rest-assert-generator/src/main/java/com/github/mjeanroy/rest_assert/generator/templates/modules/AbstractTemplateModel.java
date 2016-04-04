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

package com.github.mjeanroy.rest_assert.generator.templates.modules;

import com.github.mjeanroy.rest_assert.generator.TemplateModel;
import com.github.mjeanroy.rest_assert.generator.templates.internal.Arg;
import com.github.mjeanroy.rest_assert.reflect.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.rest_assert.generator.utils.ClassUtils.findPublicMethods;
import static java.util.Collections.emptyList;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

/**
 * Abstract representation of template model.
 * Each template model must provide at least:
 * - Package name: package name of generated class.
 * - Class name: name of generated class.
 * - Core class name: class (FQN) that will execute assertion test.
 * - Actual class: class (FQN) of tested object.
 * - Methods: assertion methods.
 */
public abstract class AbstractTemplateModel implements TemplateModel {

	@Override
	public Map<String, Object> data() {
		Map<String, Object> map = new HashMap<>();
		map.put("package", getPackageName());
		map.put("class_name", getClassName());
		map.put("core_class_name", getCoreClassName());
		map.put("actual_class", getActualClass());
		map.put("methods", getMethods());
		map.put("factory", getFactory());
		return unmodifiableMap(map);
	}

	@Override
	public String getFactory() {
		// Default implementation.
		return null;
	}

	/**
	 * Get Rest-Assert actual class that is tested
	 * in the generated class.
	 *
	 * @return Actual class.
	 */
	protected abstract String getActualClass();

	/**
	 * Get Rest-Assert core class that is proxified
	 * This assertion class will be used in generated test
	 * to execute assertion test..
	 *
	 * @return Core class.
	 */
	protected abstract String getCoreClassName();

	/**
	 * Get Rest-Assert core class that will be used
	 * to extract assertion methods.
	 *
	 * @return Core class.
	 */
	protected abstract Class coreClass();

	/**
	 * Get list of methods data to proxify.
	 * These methods will be publicly available for
	 * assertions.
	 *
	 * @return List of methods.
	 */
	protected List<Map<String, Object>> getMethods() {
		List<Method> methods = findPublicMethods(coreClass());

		// Sort methods by name
		// Not mandatory but useful for tests and to group
		// methods by name
		sort(methods, new Comparator<Method>() {
			@Override
			public int compare(Method o1, Method o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// Just build model for each methods
		List<Map<String, Object>> models = new ArrayList<>(methods.size());
		for (Method method : methods) {
			models.add(getMethod(method));
		}

		return unmodifiableList(models);
	}

	protected Map<String, Object> getMethod(Method method) {
		final String coreMethodName = method.getName();

		final List<Arg> args;
		final Class[] classTypes = method.getParameterTypes();
		final Type[] paramTypes = method.getGenericParameterTypes();
		final Annotation[][] annotations = method.getParameterAnnotations();
		final int size = paramTypes == null ? 0 : paramTypes.length;

		if (size > 1) {
			args = new ArrayList<>(size);
			for (int i = 1; i < paramTypes.length; i++) {
				Class genericType = null;
				Class paramType = classTypes[i];

				if (Collection.class.isAssignableFrom(paramType)) {
					ParameterizedType parameterizedType = (ParameterizedType) paramTypes[i];
					genericType = (Class) parameterizedType.getActualTypeArguments()[0];
				}

				Annotation[] parameterAnnotations = annotations[i];
				Param param = find(parameterAnnotations, Param.class);
				if (param == null || param.value() == null || param.value().trim().isEmpty()) {
					throw new IllegalArgumentException("Add @Param annotation to additional parameters of method " + coreMethodName);
				}

				args.add(new Arg(
						paramType.getName(),
						genericType == null ? null : genericType.getName(),
						param.value(),
						i
				));
			}
		} else {
			args = emptyList();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("core_method_name", buildCoreMethodName(coreMethodName));
		map.put("method_name", buildMethodName(coreMethodName));
		map.put("arguments", args);
		return unmodifiableMap(map);
	}

	/**
	 * Build core method name.
	 * The core method is the name of method that will internally
	 * execute assertion test.
	 *
	 * @param methodName Current method name that is parsed.
	 * @return Core method to use.
	 */
	protected abstract String buildCoreMethodName(String methodName);

	/**
	 * Build method name that is generated.
	 *
	 * @param methodName Current method name that is parsed.
	 * @return Method name that is generated.
	 */
	protected abstract String buildMethodName(String methodName);

	@SuppressWarnings("unchecked")
	private static <T extends Annotation> T find(Annotation[] annotations, Class<T> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationClass) {
				return (T) annotation;
			}
		}

		return null;
	}
}
