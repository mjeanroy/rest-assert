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

package com.github.mjeanroy.restassert.documentation;

import com.github.mjeanroy.restassert.documentation.javadoc.JavaDoc;
import com.github.mjeanroy.restassert.documentation.javadoc.JavaDocParam;

import java.util.List;
import java.util.Objects;

/**
 * A documented method.
 */
public final class DocumentedMethod {

	/**
	 * The method name.
	 */
	private final String name;

	/**
	 * The method javadoc.
	 */
	private final JavaDoc javaDoc;

	/**
	 * Create the method.
	 *
	 * @param name The method name.
	 * @param javadoc The method javadoc.
	 */
	public DocumentedMethod(String name, JavaDoc javadoc) {
		this.name = name;
		this.javaDoc = javadoc;
	}

	/**
	 * Get {@link #name}
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get {@link #javaDoc}
	 *
	 * @return {@link #javaDoc}
	 */
	public JavaDoc getJavaDoc() {
		return javaDoc;
	}

	/**
	 * Get method arguments.
	 *
	 * @return Method arguments.
	 */
	public List<JavaDocParam> getArguments() {
		return javaDoc.getParams();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof DocumentedMethod) {
			DocumentedMethod m = (DocumentedMethod) o;
			return Objects.equals(name, m.name) && Objects.equals(javaDoc, m.javaDoc);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, javaDoc);
	}

	@Override
	public String toString() {
		return String.format("DocumentedMethod{name=%s, javaDoc=%s}", name, javaDoc);
	}
}
