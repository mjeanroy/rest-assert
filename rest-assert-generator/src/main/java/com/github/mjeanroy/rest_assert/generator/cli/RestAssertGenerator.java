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

package com.github.mjeanroy.rest_assert.generator.cli;

import static java.util.Arrays.asList;

import java.util.List;

import com.github.mjeanroy.rest_assert.generator.ClassFile;
import com.github.mjeanroy.rest_assert.generator.TemplateEngine;
import com.github.mjeanroy.rest_assert.generator.Processor;
import com.github.mjeanroy.rest_assert.generator.templates.engine.MustacheTemplateEngine;

public class RestAssertGenerator {

	public static void main(String[] args) {
		String buildDirectory = args[args.length - 1];
		RestAssertGenerator generator = new RestAssertGenerator();
		generator.generate(buildDirectory);
	}

	/**
	 * Generate classes into target directory.
	 *
	 * @param buildDirectory Target directory.
	 */
	protected void generate(String buildDirectory) {
		TemplateEngine templateEngine = MustacheTemplateEngine.instance();
		List<Processor> templates = getProcessors();
		for (Processor template : templates) {
			ClassFile classFile = template.process(templateEngine);
			classFile.writeTo(buildDirectory);
		}
	}

	/**
	 * Get list of processors.
	 * Each processor will build a class file that can be
	 * written to disk.
	 *
	 * @return Processors.
	 */
	protected List<Processor> getProcessors() {
		return asList(Processor.values());
	}
}
