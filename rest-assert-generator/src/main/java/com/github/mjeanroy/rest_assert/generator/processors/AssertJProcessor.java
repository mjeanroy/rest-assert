/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.generator.processors;

import com.github.mjeanroy.rest_assert.generator.ClassFile;
import com.github.mjeanroy.rest_assert.generator.Template;
import com.github.mjeanroy.rest_assert.generator.TemplateEngine;
import com.github.mjeanroy.rest_assert.generator.TemplateModel;

import static com.github.mjeanroy.rest_assert.generator.templates.modules.assertj.models.http_responses.HttpResponseAssert.httpResponseAssert;
import static com.github.mjeanroy.rest_assert.generator.templates.modules.assertj.models.http_responses.HttpResponses.httpResponsesModel;
import static com.github.mjeanroy.rest_assert.generator.templates.modules.assertj.tmpls.ClassAssertTemplate.classAssertTemplate;
import static com.github.mjeanroy.rest_assert.generator.templates.modules.assertj.tmpls.ClassAssertionsTemplate.classAssertionsTemplate;

public enum AssertJProcessor {

	HTTP_RESPONSES(
			classAssertionsTemplate(),
			httpResponsesModel()
	),

	HTTP_RESPONSES_ASSERT(
			classAssertTemplate(),
			httpResponseAssert()
	);

	/**
	 * Template that will be processed.
	 */
	private final Template template;

	/**
	 * Data that will be merged into template.
	 */
	private final TemplateModel model;

	// Create new processor
	private AssertJProcessor(Template template, TemplateModel model) {
		this.template = template;
		this.model = model;
	}

	public ClassFile process(TemplateEngine engine) {
		String content = engine.execute(template, model.data());
		return new ClassFile(model.getPackageName(), model.getClassName(), content);
	}
}