/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public abstract class AbstractTemplateModelTest {

	@Test
	void it_should_have_package_name() {
		assertThat(getTemplateModel().getPackageName()).isEqualTo(getExpectedPackageName());
	}

	@Test
	void it_should_have_class_name() {
		assertThat(getTemplateModel().getClassName()).isEqualTo(getExpectedClassName());
	}

	@Test
	void it_should_define_core_class_name() {
		assertThat(getTemplateModel().getCoreClassName()).isEqualTo(getExpectedCoreClassName());
	}

	@Test
	void it_should_define_core_class() {
		assertThat(getTemplateModel().coreClass()).isEqualTo(getExpectedCoreClass());
	}

	@Test
	void it_should_define_actual_class() {
		assertThat(getTemplateModel().getActualClass()).isEqualTo(getExpectedActualClass());
	}

	@Test
	void it_should_build_methods_list() {
		List<Map<String, Object>> methods = getTemplateModel().getMethods();
		assertThat(methods)
			.are(getMethodCondition())
			.are(IS_VALID_METHOD_CONDITION)
			.areAtLeast(1, HAS_ARGUMENTS_CONDITION);
	}

	@Test
	void it_should_build_data() {
		Map<String, Object> data = getTemplateModel().data();

		assertThat(data)
			.hasSize(7)
			.containsKey("methods")
			.containsKey("current_tt")
			.contains(
				entry("core_class_name", getExpectedCoreClassName()),
				entry("actual_class", getExpectedActualClass()),
				entry("class_name", getExpectedClassName()),
				entry("package", getExpectedPackageName()),
				entry("factory", getFactory())
			);
	}

	protected abstract AbstractTemplateModel getTemplateModel();

	protected abstract String getExpectedPackageName();

	protected abstract String getExpectedClassName();

	protected abstract String getExpectedCoreClassName();

	protected abstract Class<?> getExpectedCoreClass();

	protected abstract String getExpectedActualClass();

	protected abstract Condition<Map<String, Object>> getMethodCondition();

	protected abstract String getFactory();

	private static final Condition<Map<String, Object>> HAS_ARGUMENTS_CONDITION = new Condition<Map<String, Object>>() {
		@Override
		public boolean matches(Map<String, Object> value) {
			return !((Collection<?>) value.get("arguments")).isEmpty();
		}
	};

	private static final Condition<Map<String, Object>> IS_VALID_METHOD_CONDITION = new Condition<Map<String, Object>>() {
		@Override
		public boolean matches(Map<String, Object> value) {
			return value.containsKey("arguments") && value.containsKey("method_name") && value.containsKey("core_method_name");
		}
	};
}
