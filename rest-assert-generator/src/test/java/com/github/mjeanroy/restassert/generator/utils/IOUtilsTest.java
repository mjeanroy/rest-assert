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

package com.github.mjeanroy.restassert.generator.utils;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;

class IOUtilsTest {

	@Test
	void it_should_get_input_stream_from_classpath() {
		InputStream inputStream = IOUtils.fromClasspath("/license.txt");
		assertThat(inputStream).isNotNull();
	}

	@Test
	void it_should_read_from_classpath() {
		String content = IOUtils.read("/license.txt");
		assertThat(content).isNotNull().isNotEmpty();
	}

	@Test
	void it_should_read_from_classpath_and_prepend_string() {
		final String tab = "\t";
		final String content = IOUtils.read("/license.txt", tab);
		assertThat(content).isNotNull().isNotEmpty();

		String[] lines = content.split(System.lineSeparator());
		assertThat(lines)
			.isNotNull()
			.isNotEmpty()
			.are(new Condition<String>() {
				@Override
				public boolean matches(String line) {
					return line.startsWith(tab);
				}
			});
	}

	@Test
	void it_should_read_input_stream() {
		InputStream inputStream = getClass().getResourceAsStream("/license.txt");
		String content = IOUtils.read(inputStream);
		assertThat(content).isNotNull().isNotEmpty();
	}

	@Test
	void it_should_read_input_stream_and_prepend_string() {
		final String tab = "\t";
		final InputStream inputStream = getClass().getResourceAsStream("/license.txt");
		final String content = IOUtils.read(inputStream, tab);
		assertThat(content).isNotNull().isNotEmpty();

		String[] lines = content.split(System.lineSeparator());
		assertThat(lines)
			.isNotNull()
			.isNotEmpty()
			.are(new Condition<String>() {
				@Override
				public boolean matches(String line) {
					return line.startsWith(tab);
				}
			});
	}

	@Test
	void it_should_write_to_a_file(@TempDir File tempDir) throws Exception {
		String path = new File(tempDir.getAbsolutePath(), "foo.txt").getAbsolutePath();
		File file = new File(path);
		String content = "Hello World";

		IOUtils.write(file, content);

		assertThat(file).exists();
		assertThat(readFileToString(file, StandardCharsets.UTF_8).trim()).isEqualTo(content.trim());

		file.delete();
		assertThat(file).doesNotExist();
	}
}
