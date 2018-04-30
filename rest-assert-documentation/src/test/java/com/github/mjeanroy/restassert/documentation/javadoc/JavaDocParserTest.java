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

import com.github.mjeanroy.restassert.test.commons.StringTestUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class JavaDocParserTest {

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

		JavaDoc javaDoc = JavaDocParser.parse(comment);

		assertThat(javaDoc).isNotNull();

		assertThat(javaDoc.getDescription()).isEqualTo(joinWithBr(
			"Check that status code of http response is 'RESET_CONTENT' status."
		));

		assertThat(javaDoc.getParams())
			.hasSize(1)
			.extracting("name", "description")
			.contains(tuple("httpResponse", "Http response."));

		assertThat(javaDoc.getReturns()).isNotNull();
		assertThat(javaDoc.getReturns().getDescription()).isEqualTo("Assertion result.");
	}

	@Test
	public void it_should_parse_javadoc_see_tag() {
		String comment = joinWithBr(
				" Check that status code of http response is 'RESET_CONTENT' status.",
				" ",
				" @param httpResponse Http response.",
				" @return Assertion result.",
				" @see <a href=\"https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200>https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200</a>",
				" @see <a href=\"https://httpstatuses.com/200\">https://httpstatuses.com/200</a>",
				" "
		);

		JavaDoc javaDoc = JavaDocParser.parse(comment);

		assertThat(javaDoc).isNotNull();

		assertThat(javaDoc.getDescription()).isEqualTo(joinWithBr(
				"Check that status code of http response is 'RESET_CONTENT' status."
		));

		assertThat(javaDoc.getParams())
				.hasSize(1)
				.extracting("name", "description")
				.contains(tuple("httpResponse", "Http response."));

		assertThat(javaDoc.getReturns()).isNotNull();
		assertThat(javaDoc.getReturns().getDescription()).isEqualTo("Assertion result.");

		assertThat(javaDoc.getSee())
				.hasSize(2)
				.extracting("description")
				.contains(
						"<a href=\"https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200>https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200</a>",
						"<a href=\"https://httpstatuses.com/200\">https://httpstatuses.com/200</a>"
				);
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

		JavaDoc javaDoc = JavaDocParser.parse(comment);

		assertThat(javaDoc).isNotNull();

		assertThat(javaDoc.getDescription()).isEqualTo(joinWithBr(
			"Check that http response contains cookie with given name (note that cookie name is" +
				"case-sensitive)."
		));

		assertThat(javaDoc.getParams())
			.hasSize(1)
			.extracting("name", "description")
			.contains(tuple("name", "Cookie name."));

		assertThat(javaDoc.getReturns()).isNotNull();
		assertThat(javaDoc.getReturns().getDescription()).isEqualTo("Assertion result.");
	}

	@Test
	public void it_should_parse_javadoc_and_replace_code_tag() {
		String comment = joinWithBr(
				" Check that status code of http response is {@code HttpStatus#RESET_CONTENT} status.",
				" ",
				" @param httpResponse Http response.",
				" @return Assertion result.",
				" "
		);

		JavaDoc javaDoc = JavaDocParser.parse(comment);

		assertThat(javaDoc.getDescription()).isEqualTo(
				"Check that status code of http response is `HttpStatus#RESET_CONTENT` status."
		);
	}

	@Test
	public void it_should_parse_javadoc_and_replace_link_tag() {
		String comment = joinWithBr(
				" Check that status code of http response is {@link HttpStatus#RESET_CONTENT} status.",
				" ",
				" @param httpResponse Http response.",
				" @return Assertion result.",
				" "
		);

		JavaDoc javaDoc = JavaDocParser.parse(comment);

		assertThat(javaDoc.getDescription()).isEqualTo(
				"Check that status code of http response is `HttpStatus#RESET_CONTENT` status."
		);
	}

	private static String joinWithBr(String... lines) {
		return StringTestUtils.join(lines, LINE_SEPARATOR);
	}
}
