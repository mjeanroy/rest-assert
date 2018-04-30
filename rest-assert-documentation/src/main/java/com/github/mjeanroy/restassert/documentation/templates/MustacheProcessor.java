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

import com.samskivert.mustache.Mustache;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

/**
 * Render templates using Mustache rendering.
 */
final class MustacheProcessor {

	/**
	 * The Mustache Processor.
	 */
	private static final MustacheProcessor INSTANCE = new MustacheProcessor();

	/**
	 * Get the mustache processor instance.
	 *
	 * @return The mustache processor.
	 */
	static MustacheProcessor getInstance() {
		return INSTANCE;
	}

	/**
	 * The mustache compiler.
	 */
	private final Mustache.Compiler mustache;

	/**
	 * Create the processor with default compiler options.
	 */
	private MustacheProcessor() {
		this.mustache = Mustache.compiler().escapeHTML(false);
	}

	/**
	 * Render template with data model.
	 *
	 * @param template Mustache template.
	 * @param model Data model.
	 * @return The rendered template.
	 */
	public String render(InputStream template, Map<String, Object> model) {
		Reader source = new InputStreamReader(template);
		return mustache.compile(source).execute(model);
	}
}
