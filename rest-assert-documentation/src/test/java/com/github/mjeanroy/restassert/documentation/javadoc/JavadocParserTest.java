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

package com.github.mjeanroy.restassert.documentation.javadoc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.github.mjeanroy.restassert.documentation.javadoc.JavadocParser;
import com.github.mjeanroy.restassert.documentation.javadoc.Javadoc;
import com.github.mjeanroy.restassert.test.commons.StringTestUtils;
import org.junit.Test;

public class JavadocParserTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Test
	public void it_should_parse_javadoc() {
		String comment = joinWithBr(
				" Check that status code of http response is 'RESET_CONTENT' status.",
				" ",
				" @param httpResponse Http response.",
				" @return Assertion result.",
				" "
		);

		Javadoc javadoc = JavadocParser.parse(comment);

		assertThat(javadoc).isNotNull();

		assertThat(javadoc.getDescription()).isEqualTo(joinWithBr(
			"Check that status code of http response is 'RESET_CONTENT' status."
		));

		assertThat(javadoc.getParams())
			.hasSize(1)
			.extracting("name", "description")
			.contains(tuple("httpResponse", "Http response."));

		assertThat(javadoc.getReturns()).isNotNull();
		assertThat(javadoc.getReturns().getDescription()).isEqualTo("Assertion result.");
	}

	@Test
	public void it_should_parse_multiline_javadoc() {
			String comment = joinWithBr(
			" Check that http response contains cookie with given name (note that cookie name is",
			" case-sensitive).",
			" ",
			" @param name Cookie name.",
			" @return Assertion result.",
			" "
		);

		Javadoc javadoc = JavadocParser.parse(comment);

		assertThat(javadoc).isNotNull();

		assertThat(javadoc.getDescription()).isEqualTo(joinWithBr(
			"Check that http response contains cookie with given name (note that cookie name is" +
				"case-sensitive)."
		));

		assertThat(javadoc.getParams())
			.hasSize(1)
			.extracting("name", "description")
			.contains(tuple("name", "Cookie name."));

		assertThat(javadoc.getReturns()).isNotNull();
		assertThat(javadoc.getReturns().getDescription()).isEqualTo("Assertion result.");
	}

	private static String joinWithBr(String... lines) {
		return StringTestUtils.join(lines, LINE_SEPARATOR);
	}
}
