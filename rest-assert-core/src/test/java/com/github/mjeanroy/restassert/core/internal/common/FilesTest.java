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

package com.github.mjeanroy.restassert.core.internal.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class FilesTest {

	private static final String BR = System.getProperty("line.separator");

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_read_file_to_string() throws Exception {
		URL resource = getClass().getResource("/test.txt");
		Path path = Paths.get(resource.toURI());

		String content = Files.readFileToString(path);

		String expectedContent =
			"Hello World" + BR +
			"Foo Bar" + BR +
			"Test";

		assertThat(content)
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(expectedContent);
	}

	@Test
	public void it_should_fail_to_read_file_to_string_with_custom_exception() throws Exception {
		URL resource = getClass().getResource("/test-not-utf-8.txt");
		Path path = Paths.get(resource.toURI());

		thrown.expect(Files.UnreadableFileException.class);

		Files.readFileToString(path);
	}
}
