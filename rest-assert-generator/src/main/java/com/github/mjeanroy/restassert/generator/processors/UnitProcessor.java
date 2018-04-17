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

package com.github.mjeanroy.restassert.generator.processors;

import com.github.mjeanroy.restassert.generator.ClassFile;
import com.github.mjeanroy.restassert.generator.Template;
import com.github.mjeanroy.restassert.generator.TemplateEngine;
import com.github.mjeanroy.restassert.generator.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie.ApacheHttpCookieAssert.apacheHttpCookieAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie.CookieAssert.cookieAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie.JavaxCookieAssert.javaxCookieAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.cookie.NingHttpCookieAssert.asyncHttpCookieAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.ApacheHttpAssert.apacheHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.AsyncHttpAssert.asyncHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.GoogleHttpAssert.googleHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.HttpAssert.httpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.JunitServersHttpAssert.junitServersHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.NingHttpAssert.ningHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.OkHttpAssert.okHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.http.SpringMockMvcHttpAssert.springMockMvcHttpAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.models.json.JsonAssert.jsonAssert;
import static com.github.mjeanroy.restassert.generator.templates.modules.unit.tmpls.AssertTemplate.assertTemplate;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Set of processors that will be used to generate assertions
 * classes for rest-assert-unit module.
 */
public enum UnitProcessor {

	/**
	 * Processor that will generate assert class for http response
	 * objects.
	 */
	HTTP_RESPONSE(
			assertTemplate(),
			asList(
				httpAssert(),
				ningHttpAssert(),
				asyncHttpAssert(),
				apacheHttpAssert(),
				googleHttpAssert(),
				okHttpAssert(),
				springMockMvcHttpAssert(),
				junitServersHttpAssert()
			)
	),

	/**
	 * Processor that will generate assert class cookies
	 * objects.
	 */
	COOKIES(
			assertTemplate(),
			asList(
				cookieAssert(),
				asyncHttpCookieAssert(),
				apacheHttpCookieAssert(),
				javaxCookieAssert()
			)
	),

	/**
	 * Processor that will generate assert class json
	 * objects.
	 */
	JSON(
			assertTemplate(),
			singletonList(jsonAssert())
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
	UnitProcessor(Template template, List<TemplateModel> models) {
		this.template = template;
		this.models = models;
	}

	/**
	 * Generate class file from template and model.
	 *
	 * @param engine Template engine to use.
	 * @return Class file.
	 */
	public List<ClassFile> process(TemplateEngine engine) {
		List<ClassFile> results = new ArrayList<>(models.size());
		for (TemplateModel model : models) {
			String content = engine.execute(template, model.data());
			results.add(new ClassFile(model.getPackageName(), model.getClassName(), content));
		}

		return results;
	}
}
