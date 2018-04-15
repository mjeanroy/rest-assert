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

package com.github.mjeanroy.restassert.generator.templates.engine;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import com.github.mjeanroy.restassert.generator.Template;
import com.github.mjeanroy.restassert.generator.TemplateEngine;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Mustache {@link TemplateEngine} implementation.
 * This implementation mustache.java library as internal
 * engine.
 * <p/>
 * This class is thread safe.
 * This class is implemented as a singleton.
 */
public class MustacheTemplateEngine implements TemplateEngine {

	/**
	 * Singleton instance.
	 */
	private static final MustacheTemplateEngine INSTANCE = new MustacheTemplateEngine();

	/**
	 * Get singleton object.
	 *
	 * @return Singleton.
	 */
	public static TemplateEngine instance() {
		return INSTANCE;
	}

	private final MustacheFactory mf;

	// Ensure non instantiation
	private MustacheTemplateEngine() {
		mf = new DefaultMustacheFactory();
	}

	@Override
	public String execute(Template template, Map<String, Object> context) {
		Mustache mustache = compile(template);
		return execute(mustache, context);
	}

	private Mustache compile(Template template) {
		return mf.compile(new StringReader(template.read()), template.getPath());
	}

	private String execute(Mustache mustache, Map<String, Object> context) {
		StringWriter writer = new StringWriter();
		mustache.execute(writer, context);
		writer.flush();
		return writer.toString();
	}
}
