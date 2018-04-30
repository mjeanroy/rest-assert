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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class JavaDocTest {

	@Test
	public void it_should_create_javadoc() {
		String description = "The Description";

		List<JavaDocParam> params = asList(
			new JavaDocParam("p1", "Description for p1"),
			new JavaDocParam("p2", "Description for p2")
		);

		JavaDocReturn returns = new JavaDocReturn("The return value");

		List<JavaDocSee> see = singletonList(
				new JavaDocSee("<a href=\"https://httpstatuses.com/200\">https://httpstatuses.com/200</a>")
		);

		JavaDoc javaDoc = new JavaDoc(description, params, returns, see);

		assertThat(javaDoc.getDescription()).isEqualTo(description);
		assertThat(javaDoc.getParams()).isEqualTo(params);
		assertThat(javaDoc.getReturns()).isEqualTo(returns);
		assertThat(javaDoc.getSee()).isEqualTo(see);
	}

	@Test
	public void it_should_create_javadoc_with_builder() {
		String description = "The Description";
		JavaDocParam p1 = new JavaDocParam("p1", "Description for p1");
		JavaDocParam p2 = new JavaDocParam("p2", "Description for p2");
		JavaDocReturn returns = new JavaDocReturn("The return value");
		JavaDocSee see = new JavaDocSee("<a href=\"https://httpstatuses.com/200\">https://httpstatuses.com/200</a>");

		JavaDoc javaDoc = new JavaDoc.Builder()
			.setDescription(description)
			.addParam(p1.getName(), p1.getDescription())
			.addParam(p2.getName(), p2.getDescription())
			.setReturns(returns.getDescription())
			.addSee(see.getDescription())
			.build();

		assertThat(javaDoc.getDescription()).isEqualTo(description);
		assertThat(javaDoc.getParams()).isEqualTo(asList(p1, p2));
		assertThat(javaDoc.getReturns()).isEqualTo(returns);
		assertThat(javaDoc.getSee()).isEqualTo(singletonList(see));
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(JavaDoc.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		String description = "The Description";

		List<JavaDocParam> params = asList(
			new JavaDocParam("p1", "Description for p1"),
			new JavaDocParam("p2", "Description for p2")
		);

		JavaDocReturn returns = new JavaDocReturn("The return value");

		List<JavaDocSee> see = singletonList(
				new JavaDocSee("<a href=\"https://httpstatuses.com/200\">https://httpstatuses.com/200</a>")
		);

		JavaDoc javaDoc = new JavaDoc(description, params, returns, see);

		assertThat(javaDoc.toString()).isEqualTo(
			"JavaDoc{" +
				"description=The Description, " +
				"params=[" +
					"JavaDocParam{name=p1, description=Description for p1}, " +
					"JavaDocParam{name=p2, description=Description for p2}" +
				"], " +
				"returns=JavaDocReturn{description=The return value}, " +
				"see=[" +
					"JavaDocSee{description=<a href=\"https://httpstatuses.com/200\">https://httpstatuses.com/200</a>}" +
				"]" +
			"}"
		);
	}
}
