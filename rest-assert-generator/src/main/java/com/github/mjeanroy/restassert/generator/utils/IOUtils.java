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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Static IO Utilities.
 */
public final class IOUtils {

	// Ensure non instantiation
	private IOUtils() {
	}

	/**
	 * Line separator, system dependent !
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Get input stream object from a file relative to classpath.
	 *
	 * @param path Path, relative to classpath.
	 * @return Input stream.
	 */
	public static InputStream fromClasspath(String path) {
		return IOUtils.class.getResourceAsStream(path);
	}

	/**
	 * Read content of a file, available in classpath.
	 *
	 * @param path Path, relative to classpath.
	 * @return File content.
	 */
	public static String read(String path) {
		return read(path, "");
	}

	/**
	 * Read content of a file, available in classpath.
	 *
	 * @param path Path, relative to classpath.
	 * @param prepend String prepended to each lines.
	 * @return File content.
	 */
	public static String read(String path, String prepend) {
		return read(fromClasspath(path), prepend);
	}

	/**
	 * Read content of an input stream.
	 *
	 * @param in Input stream.
	 * @return File content.
	 */
	public static String read(InputStream in) {
		return read(in, "");
	}

	/**
	 * Read content of an input stream.
	 *
	 * @param in Input stream.
	 * @param prepend String prepended to each lines.
	 * @return File content.
	 */
	public static String read(InputStream in, String prepend) {
		try (
				InputStreamReader is = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(is)
		) {

			String prependString = prepend == null ? "" : prepend;
			StringBuilder sb = new StringBuilder();
			String read = br.readLine();
			while (read != null) {
				sb.append(prependString).append(read).append(LINE_SEPARATOR);
				read = br.readLine();
			}

			return sb.toString();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Write content to a file.
	 *
	 * @param file Target file.
	 * @param content File content.
	 */
	public static void write(File file, String content) {
		try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			writer.println(content);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
