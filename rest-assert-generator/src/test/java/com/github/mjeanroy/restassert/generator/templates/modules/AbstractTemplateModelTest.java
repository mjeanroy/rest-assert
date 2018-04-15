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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Condition;
import org.junit.Test;

public abstract class AbstractTemplateModelTest {

	/**
	 * Get the template model to be tested.
	 *
	 * @return Template model to be tested.
	 */
	protected abstract AbstractTemplateModel getTemplateModel();

	/**
	 * Get the expected package name of the generated class.
	 *
	 * @return Expected package name.
	 */
	protected abstract String getExpectedPackageName();

	/**
	 * Get the expected class name of the generated class.
	 *
	 * @return Expected class name.
	 */
	protected abstract String getExpectedClassName();

	/**
	 * Get the expected class name of the core class that will be used as delegated assertions.
	 *
	 * @return Expected core class name.
	 */
	protected abstract String getExpectedCoreClassName();

	/**
	 * Get the expected core class that will be used as delegated assertions.
	 *
	 * @return Expected core class.
	 */
	protected abstract Class<?> getExpectedCoreClass();

	/**
	 * Get the expected class name that will be tested.
	 *
	 * @return Expected tested class name.
	 */
	protected abstract String getExpectedActualClass();

	/**
	 * Get the expected class assertj condition, used to validate the method name generation.
	 *
	 * @return AssertJ condition.
	 */
	protected abstract Condition<Map<String, Object>> getMethodCondition();

	/**
	 * Get expected factory name.
	 *
	 * @return Expected factory.
	 */
	protected abstract String getFactory();

	@Test
	public void it_should_have_package_name() {
		assertThat(getTemplateModel().getPackageName()).isEqualTo(getExpectedPackageName());
	}

	@Test
	public void it_should_have_class_name() {
		assertThat(getTemplateModel().getClassName()).isEqualTo(getExpectedClassName());
	}

	@Test
	public void it_should_define_core_class_name() {
		assertThat(getTemplateModel().getCoreClassName()).isEqualTo(getExpectedCoreClassName());
	}

	@Test
	public void it_should_define_core_class() {
		assertThat(getTemplateModel().coreClass()).isEqualTo(getExpectedCoreClass());
	}

	@Test
	public void it_should_define_actual_class() {
		assertThat(getTemplateModel().getActualClass()).isEqualTo(getExpectedActualClass());
	}

	@Test
	public void it_should_build_methods_list() {
		List<Map<String, Object>> methods = getTemplateModel().getMethods();
		assertThat(methods)
			.are(getMethodCondition())
			.are(IS_VALID_METHOD_CONDITION)
			.areAtLeast(1, HAS_ARGUMENTS_CONDITION);
	}

	@Test
	public void it_should_build_data() {
		Map<String, Object> data = getTemplateModel().data();

		assertThat(data)
			.hasSize(6)
			.containsKey("methods")
			.contains(
				entry("core_class_name", getExpectedCoreClassName()),
				entry("actual_class", getExpectedActualClass()),
				entry("class_name", getExpectedClassName()),
				entry("package", getExpectedPackageName()),
				entry("factory", getFactory())
			);
	}

	private static final Condition<Map<String, Object>> HAS_ARGUMENTS_CONDITION = new Condition<Map<String, Object>>() {
		@Override
		public boolean matches(Map<String, Object> value) {
			return ((Collection<?>) value.get("arguments")).size() > 0;
		}
	};

	private static final Condition<Map<String, Object>> IS_VALID_METHOD_CONDITION = new Condition<Map<String, Object>>() {
		@Override
		public boolean matches(Map<String, Object> value) {
			return value.containsKey("arguments") && value.containsKey("method_name") && value.containsKey("core_method_name");
		}
	};
}
