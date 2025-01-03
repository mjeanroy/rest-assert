/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.generator.processors;

import com.github.mjeanroy.restassert.generator.ClassFile;
import com.github.mjeanroy.restassert.generator.Template;
import com.github.mjeanroy.restassert.generator.TemplateEngine;
import com.github.mjeanroy.restassert.generator.TemplateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.ApacheHttpResponseMatchers.apacheHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.AsyncHttpResponseMatchers.asyncHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.GoogleHttpResponseMatchers.googleHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.HttpResponseMatchers.coreHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.JunitServersHttpResponseMatchers.junitServersHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.NingHttpResponseMatchers.ningHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.OkHttpResponseMatchers.okHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.http.SpringMockMvcHttpResponseMatchers.springMockMvcHttpResponseMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.models.json.JsonMatchers.jsonMatchers;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.tmpls.HttpResponseMatchersTemplate.httpResponseMatchersTemplate;
import static com.github.mjeanroy.restassert.generator.templates.modules.hamcrest.tmpls.JsonMatchersTemplate.jsonMatchersTemplate;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Set of processors that will be used to generate assertions
 * classes for rest-assert-unit module.
 */
public enum HamcrestProcessor implements Processor {

	/**
	 * Processor that will generate matchers class for http response
	 * matchers static factories.
	 */
	HTTP_RESPONSE_MATCHERS(
		httpResponseMatchersTemplate(),
		asList(
			apacheHttpResponseMatchers(),
			asyncHttpResponseMatchers(),
			coreHttpResponseMatchers(),
			googleHttpResponseMatchers(),
			junitServersHttpResponseMatchers(),
			ningHttpResponseMatchers(),
			okHttpResponseMatchers(),
			springMockMvcHttpResponseMatchers()
		)
	),

	/**
	 * Processor that will generate matchers class for JSON
	 * matchers static factories.
	 */
	JSON_MATCHERS(
		jsonMatchersTemplate(),
		singletonList(
			jsonMatchers()
		)
	);

	/**
	 * Template that will be processed.
	 */
	private final Template template;

	/**
	 * Data that will be merged into template.
	 */
	private final List<TemplateModel> models;

	// Create new processor
	HamcrestProcessor(Template template, List<TemplateModel> models) {
		this.template = template;
		this.models = models;
	}

	@Override
	public Collection<ClassFile> process(TemplateEngine engine) {
		List<ClassFile> results = new ArrayList<>(models.size());

		for (TemplateModel model : models) {
			String content = engine.execute(template, model.data());
			results.add(new ClassFile(model.getPackageName(), model.getClassName(), content));
		}

		return results;
	}
}
