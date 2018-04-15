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

package com.github.mjeanroy.restassert.generator.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {

	@Test
	public void it_should_get_input_stream_from_classpath() {
		InputStream inputStream = IOUtils.fromClasspath("/license.txt");
		assertThat(inputStream)
				.isNotNull();
	}

	@Test
	public void it_should_read_from_classpath() {
		String content = IOUtils.read("/license.txt");
		assertThat(content)
				.isNotNull()
				.isNotEmpty();
	}

	@Test
	public void it_should_read_from_classpath_and_prepend_string() {
		final String tab = "\t";
		String content = IOUtils.read("/license.txt", tab);
		assertThat(content)
				.isNotNull()
				.isNotEmpty();

		String[] lines = content.split(IOUtils.LINE_SEPARATOR);
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
	public void it_should_read_input_stream() {
		InputStream inputStream = getClass().getResourceAsStream("/license.txt");
		String content = IOUtils.read(inputStream);
		assertThat(content)
				.isNotNull()
				.isNotEmpty();
	}

	@Test
	public void it_should_read_input_stream_and_prepend_string() {
		final String tab = "\t";
		InputStream inputStream = getClass().getResourceAsStream("/license.txt");
		String content = IOUtils.read(inputStream, tab);
		assertThat(content)
				.isNotNull()
				.isNotEmpty();

		String[] lines = content.split(IOUtils.LINE_SEPARATOR);
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
	public void it_should_write_to_a_file() throws Exception {
		String path = FilenameUtils.normalize(FileUtils.getTempDirectoryPath() + "/foo.txt");
		File file = new File(path);
		String content = "Hello World";

		IOUtils.write(file, content);

		assertThat(file).exists();
		assertThat(FileUtils.readFileToString(file).trim()).isEqualTo(content.trim());

		file.delete();
		assertThat(file).doesNotExist();
	}
}
