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

package com.github.mjeanroy.restassert.generator.utils;

import org.assertj.core.api.Condition;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static java.io.File.separator;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassUtilsTest {

	@Test
	public void it_should_get_public_methods() {
		List<Method> methods = ClassUtils.findPublicMethods(Foo.class);
		assertThat(methods)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1)
				.are(new Condition<Method>() {
					@Override
					public boolean matches(Method method) {
						return Modifier.isPublic(method.getModifiers());
					}
				});

		Method method = methods.get(0);
		assertThat(method.getName()).isEqualTo("publicMethod");
	}

	@Test
	public void it_should_turn_a_package_name_to_a_path() {
		String path = ClassUtils.packageNameToDirectory("com.github.mjeanroy.restassert.generator.utils");
		assertThat(path)
				.isNotNull()
				.isNotEmpty()
				.isEqualTo(format(
						"com%sgithub%smjeanroy%srestassert%sgenerator%sutils",
						separator, separator, separator, separator, separator
				));
	}

	public static final class Foo {

		public static void publicStaticMethod() {
		}

		public void publicMethod() {
		}

		@SuppressWarnings("unused")
		private void methodPrivate() {

		}

		protected void methodProtected() {

		}

		void methodPackageProtected() {
		}
	}
}
