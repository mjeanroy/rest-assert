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

package com.github.mjeanroy.restassert.test.commons;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class IoTestUtilsTest {

	@Test
	void it_should_get_file_from_classpath() {
		File file = IoTestUtils.fileFromClasspath("/test.txt");
		assertThat(file).isNotNull().exists().hasName("test.txt");
	}

	@Test
	void it_should_get_path_from_classpath() {
		Path path = IoTestUtils.pathFromClasspath("/test.txt");
		assertThat(path).isNotNull().exists().hasFileName("test.txt");
	}

	@Test
	void it_should_get_URL_from_classpath() {
		URL url = IoTestUtils.urlFromClasspath("/test.txt");
		assertThat(url).isNotNull().hasProtocol("file");
	}

	@Test
	void it_should_get_URI_from_classpath() {
		URI url = IoTestUtils.uriFromClasspath("/test.txt");
		assertThat(url).isNotNull().hasScheme("file");
	}

	@Test
	void it_should_read_file_content() {
		String content = IoTestUtils.readFile("test.txt");
		assertThat(content).isEqualTo(
			String.join(System.lineSeparator(), asList(
				"Hello World",
				"Foo Bar",
				"Test"
			))
		);
	}
}
