/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.generator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;

class ClassFileTest {

	@Test
	void it_should_build_class_file() {
		String packageName = "com.github.mjeanroy";
		String className = "Foo";
		String content = "bar";

		ClassFile classFile = new ClassFile(packageName, className, content);

		assertThat(classFile.getPackageName()).isEqualTo(packageName);
		assertThat(classFile.getClassName()).isEqualTo(className);
		assertThat(classFile.getContent()).isEqualTo(content);
	}

	@Test
	void it_should_write_class_on_disk(@TempDir File directory) throws Exception {
		String packageName = "com.github.mjeanroy";
		String className = "Foo";
		String content = "bar";

		ClassFile classFile = new ClassFile(packageName, className, content);
		classFile.writeTo(directory.getAbsolutePath());

		File klass = new File(directory + "/com/github/mjeanroy/Foo.java");
		assertThat(klass).exists().isFile();
		assertThat(readFileToString(klass, StandardCharsets.UTF_8).trim()).isEqualTo(content.trim());
	}
}
