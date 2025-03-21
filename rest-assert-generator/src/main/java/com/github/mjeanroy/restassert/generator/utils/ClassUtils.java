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

package com.github.mjeanroy.restassert.generator.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Static Class Utilities.
 */
public final class ClassUtils {

	// Ensure non instantiation
	private ClassUtils() {
	}

	/**
	 * Get all public methods available on a java class.
	 *
	 * @param klass Class.
	 * @return List of public methods.
	 */
	public static List<Method> findPublicMethods(Class<?> klass) {
		Method[] methods = klass.getDeclaredMethods();
		List<Method> results = new ArrayList<>(methods.length);
		for (Method method : methods) {
			int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
				results.add(method);
			}
		}
		return results;
	}

	/**
	 * Turn a package name to file system path.
	 *
	 * @param packageName Package name.
	 * @return Path.
	 */
	public static String packageNameToDirectory(String packageName) {
		return packageName.replaceAll("\\.", separator());
	}

	/**
	 * Get the file separator value, safe to be used on windows or unix systems.
	 *
	 * @return File separator.
	 */
	private static String separator() {
		char separator = File.separatorChar;
		if (separator == '/') {
			return String.valueOf(separator);
		}

		// Escape on windows
		return "\\" + separator;
	}
}
