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
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class DocumentedMethodTest {

	@Test
	public void it_should_create_method() {
		String name = "isStatusEqual";
		JavaDoc javaDoc = new JavaDoc.Builder()
			.setDescription("Check that status is equal to expected")
			.addParam("status", "Expected Status")
			.setReturns("The assertion result")
			.build();

		DocumentedMethod method = new DocumentedMethod(name, javaDoc);

		assertThat(method.getName()).isEqualTo(name);
		assertThat(method.getJavaDoc()).isEqualTo(javaDoc);
		assertThat(method.getArguments()).hasSize(1)
			.extracting("name", "description")
			.contains(tuple("status", "Expected Status"));
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(DocumentedMethod.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		String name = "isStatusEqual";
		JavaDoc javaDoc = new JavaDoc.Builder()
				.setDescription("Check that status is equal to expected")
				.addParam("status", "Expected Status")
				.setReturns("The assertion result")
				.build();

		DocumentedMethod method = new DocumentedMethod(name, javaDoc);

		assertThat(method.toString()).isEqualTo(
			"DocumentedMethod{" +
				"name=isStatusEqual, " +
				"javaDoc=JavaDoc{" +
					"description=Check that status is equal to expected, " +
					"params=[JavaDocParam{name=status, description=Expected Status}], " +
					"returns=JavaDocReturn{description=The assertion result}, " +
					"see=[]" +
				"}" +
			"}"
		);
	}
}
