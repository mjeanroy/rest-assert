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

package com.github.mjeanroy.restassert.generator.templates.modules;

import com.github.mjeanroy.restassert.generator.TemplateModel;
import com.github.mjeanroy.restassert.generator.templates.internal.Arg;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.restassert.generator.utils.ClassUtils.findPublicMethods;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

/**
 * Abstract representation of template model.
 *
 * Each template model must provide at least:
 * <ul>
 *   <li>Package name: package name of generated class.</li>
 *   <li>Class name: name of generated class.</li>
 *   <li>Core class name: class (FQN) that will execute assertion test.</li>
 *   <li>Actual class: class (FQN) of tested object.</li>
 *   <li>Methods: assertion methods.</li>
 * </ul>
 */
public abstract class AbstractTemplateModel implements TemplateModel {

	private static final List<String> CLASS_NAME_PREFIXES = asList("class ", "interface ");

	/**
	 * The paranamer instance that will be used to extract parameter name
	 * from the method instance.
	 */
	private final Paranamer paranamer;

	/**
	 * Create template model and instantiate paranamer
	 * value.
	 */
	protected AbstractTemplateModel() {
		this.paranamer = new BytecodeReadingParanamer();
	}

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
	protected abstract Class<?> coreClass();

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
		sort(methods, METHOD_NAME_COMPARATOR);

		// Just build model for each methods
		List<Map<String, Object>> models = new ArrayList<>(methods.size());
		for (Method method : methods) {
			models.add(getMethod(method));
		}

		return unmodifiableList(models);
	}

	private Map<String, Object> getMethod(Method method) {
		final String coreMethodName = method.getName();

		final List<Arg> args;
		final Class<?>[] classTypes = method.getParameterTypes();
		final Type[] paramTypes = method.getGenericParameterTypes();
		final int size = paramTypes == null ? 0 : paramTypes.length;
		final String[] parameterNames = paranamer.lookupParameterNames(method);

		if (size > 1) {
			args = new ArrayList<>(size);

			for (int i = 1; i < paramTypes.length; i++) {
				Class<?> paramType = classTypes[i];
				Type type = paramTypes[i];

				String genericType = null;
				if (type instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) type;
					genericType = parameterizedType.getActualTypeArguments()[0].toString();
				}

				final String name = paramType.isArray() ?
						paramType.getComponentType().getName() + (i == size - 1 ? "..." : "[]") :
						paramType.getName();

				final String typeName = name.replace("$", ".");

				if (genericType != null) {
					for (String prefix : CLASS_NAME_PREFIXES) {
						if (genericType.startsWith(prefix)) {
							genericType = genericType.substring(prefix.length());
							break;
						}
					}
				}

				final String parameterName = parameterNames[i];

				args.add(new Arg(typeName, genericType, parameterName, i));
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
	 *
	 * The core method is the name of the method that will internally
	 * execute assertion test.
	 *
	 * For example, in the `rest-assert-unit` context, the returned value
	 * for method `isOk` will be `assertIsOk`.
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

	/**
	 * A comparator that will execute a string comparison on the returned
	 * values of {@link Method#getName()}.
	 */
	private static final Comparator<Method> METHOD_NAME_COMPARATOR = new Comparator<Method>() {
		@Override
		public int compare(Method o1, Method o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
}
