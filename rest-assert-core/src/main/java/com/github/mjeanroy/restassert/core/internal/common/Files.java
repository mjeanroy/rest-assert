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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

/**
 * Static utilities.
 */
public final class Files {

	/**
	 * Line separator (system dependent).
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	// Private constructor to ensure non instantiation.
	private Files() {
	}

	/**
	 * Read file and return text content.
	 *
	 * @param file File.
	 * @return File content.
	 */
	public static String readFileToString(Path file) {
		try {
			List<String> lines = java.nio.file.Files.readAllLines(file, Charset.defaultCharset());
			return Strings.join(lines, LINE_SEPARATOR);
		} catch (IOException ex) {
			throw new UnreadableFileException(file, ex);
		}
	}

	/**
	 * Exception to throw when a file cannot be read and
	 * fail with {@link IOException}.
	 */
	public static class UnreadableFileException extends RuntimeException {

		/**
		 * Original Path.
		 */
		private final Path path;

		/**
		 * Create exception.
		 * @param ex Original exception.
		 */
		private UnreadableFileException(Path path, IOException ex) {
			super(ex);
			this.path = path;
		}

		/**
		 * Get {@link #path}
		 *
		 * @return {@link #path}
		 */
		public Path getPath() {
			return path;
		}
	}
}
