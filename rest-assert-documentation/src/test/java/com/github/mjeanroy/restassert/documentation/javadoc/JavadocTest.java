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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class JavadocTest {

	@Test
	public void it_should_create_javadoc() {
		String description = "The Description";

		List<JavadocParam> params = asList(
			new JavadocParam("p1", "Description for p1"),
			new JavadocParam("p2", "Description for p2")
		);

		JavadocReturn returns = new JavadocReturn("The return value");

		Javadoc javadoc = new Javadoc(description, params, returns);

		assertThat(javadoc.getDescription()).isEqualTo(description);
		assertThat(javadoc.getParams()).isEqualTo(params);
		assertThat(javadoc.getReturns()).isEqualTo(returns);
	}

	@Test
	public void it_should_create_javadoc_with_builder() {
		String description = "The Description";
		JavadocParam p1 = new JavadocParam("p1", "Description for p1");
		JavadocParam p2 = new JavadocParam("p2", "Description for p2");
		JavadocReturn returns = new JavadocReturn("The return value");

		Javadoc javadoc = new Javadoc.Builder()
			.setDescription(description)
			.addParam(p1.getName(), p1.getDescription())
			.addParam(p2.getName(), p2.getDescription())
			.setReturns(returns.getDescription())
			.build();

		assertThat(javadoc.getDescription()).isEqualTo(description);
		assertThat(javadoc.getParams()).isEqualTo(asList(p1, p2));
		assertThat(javadoc.getReturns()).isEqualTo(returns);
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Javadoc.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		String description = "The Description";

		List<JavadocParam> params = asList(
			new JavadocParam("p1", "Description for p1"),
			new JavadocParam("p2", "Description for p2")
		);

		JavadocReturn returns = new JavadocReturn("The return value");

		Javadoc javadoc = new Javadoc(description, params, returns);

		assertThat(javadoc.toString()).isEqualTo(
			"Javadoc{" +
				"description=The Description, " +
				"params=[" +
					"JavadocParam{name=p1, description=Description for p1}, " +
					"JavadocParam{name=p2, description=Description for p2}" +
				"], " +
				"returns=JavadocReturn{description=The return value}" +
			"}"
		);
	}
}
