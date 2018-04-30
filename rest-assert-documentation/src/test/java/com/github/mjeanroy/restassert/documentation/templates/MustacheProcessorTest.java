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

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MustacheProcessorTest {

	@Test
	public void it_should_render_mustache_template() {
		List<Person> persons = asList(
				new Person("John Doe", 20),
				new Person("Jane Doe", 30)
		);

		InputStream template = new ByteArrayInputStream("{{#persons}}{{name}}: {{age}}{{/persons}}".getBytes());
		Map<String, Object> model = new HashMap<>();
		model.put("persons", persons);

		String output = MustacheProcessor.getInstance().render(template, model);

		assertThat(output).isEqualTo("John Doe: 20Jane Doe: 30");
	}

	private static class Person {
		private final String name;
		private final int age;

		private Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		private String getName() {
			return name;
		}

		private int getAge() {
			return age;
		}
	}
}
