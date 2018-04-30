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

package com.github.mjeanroy.restassert.documentation.templates;

import com.github.mjeanroy.restassert.documentation.DocumentedMethod;
import com.github.mjeanroy.restassert.documentation.javadoc.JavaDoc;
import org.junit.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.test.commons.IoTestUtils.readFile;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateProcessorTest {

	@Test
	public void it_should_generate_documentation_as_markdown() {
		DocumentedMethod method1 = new DocumentedMethod("isStatusEqualTo", new JavaDoc.Builder()
			.setDescription("Check that status code of http response has an expected status.")
			.addParam("httpResponse", "Http response.")
			.addParam("status", "Expected status.")
			.setReturns("Assertion result.")
			.build());

		DocumentedMethod method2 = new DocumentedMethod("isOk", new JavaDoc.Builder()
			.setDescription("Check that status code of http response is 'OK'")
			.addParam("httpResponse", "Http response.")
			.setReturns("Assertion result.")
			.build());

		List<DocumentedMethod> methods = asList(method1, method2);

		String output = TemplateProcessor.REST_ASSERT_UNIT.process(methods);

		assertThat(output.trim()).isEqualTo(readFile("test-rest-assert-unit-output.txt").trim());
	}
}
