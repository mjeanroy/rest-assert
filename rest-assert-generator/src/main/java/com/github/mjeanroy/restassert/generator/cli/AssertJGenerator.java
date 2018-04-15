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

package com.github.mjeanroy.restassert.generator.cli;

import com.github.mjeanroy.restassert.generator.ClassFile;
import com.github.mjeanroy.restassert.generator.processors.AssertJProcessor;
import com.github.mjeanroy.restassert.generator.TemplateEngine;
import com.github.mjeanroy.restassert.generator.templates.engine.MustacheTemplateEngine;

import java.util.List;

import static java.util.Arrays.asList;

public class AssertJGenerator {

	public static void main(String[] args) {
		String buildDirectory = args[args.length - 1];
		AssertJGenerator generator = new AssertJGenerator();
		generator.generate(buildDirectory);
	}

	/**
	 * Generate classes into target directory.
	 *
	 * @param buildDirectory Target directory.
	 */
	private void generate(String buildDirectory) {
		TemplateEngine templateEngine = MustacheTemplateEngine.instance();
		List<AssertJProcessor> templates = getProcessors();
		for (AssertJProcessor template : templates) {
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
	private List<AssertJProcessor> getProcessors() {
		return asList(AssertJProcessor.values());
	}
}
