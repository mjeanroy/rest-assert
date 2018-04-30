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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Documentation processor.
 */
public enum TemplateProcessor {

	REST_ASSERT_UNIT("rest-assert-unit.template.txt") {
		@Override
		public String process(List<DocumentedMethod> methods) {
			List<RestAssertUnitMethod> restAssertUnitMethods = new ArrayList<>(methods.size());
			for (DocumentedMethod method : methods) {
				restAssertUnitMethods.add(new RestAssertUnitMethod(method));
			}

			InputStream template = getClass().getResourceAsStream("/templates/" + this.template);
			Map<String, Object> model = new HashMap<>();
			model.put("methods", restAssertUnitMethods);

			return MustacheProcessor.getInstance().render(template, model);
		}
	};

	/**
	 * The template name, located in {@code "/templates/"}.
	 */
	final String template;

	/**
	 * Create processor.
	 *
	 * @param template Template name.
	 */
	TemplateProcessor(String template) {
		this.template = template;
	}

	/**
	 * Process documented methods and generate documentation output.
	 *
	 * @param methods List of documented methods.
	 * @return The documentation output.
	 */
	public abstract String process(List<DocumentedMethod> methods);

}
