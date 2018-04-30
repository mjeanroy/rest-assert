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

package com.github.mjeanroy.restassert.test.commons;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Static IO Test Utilities.
 */
public final class IoTestUtils {

	// Ensure non instantiation.
	private IoTestUtils() {
	}

	/**
	 * Get resource URL from classpath.
	 *
	 * @param path URL path.
	 * @return URL.
	 */
	public static URL urlFromClasspath(String path) {
		return IoTestUtils.class.getResource(path);
	}

	/**
	 * Get resource URI from classpath.
	 *
	 * @param path The URI path.
	 * @return URI.
	 */
	public static URI uriFromClasspath(String path) {
		try {
			return urlFromClasspath(path).toURI();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Get a file from classpath.
	 *
	 * @param path File path.
	 * @return The file.
	 */
	public static File fileFromClasspath(String path) {
		return new File(uriFromClasspath(path));
	}

	/**
	 * Get path from classpath.
	 *
	 * @param path The resource path.
	 * @return The path.
	 */
	public static Path pathFromClasspath(String path) {
		return Paths.get(uriFromClasspath(path));
	}

	/**
	 * Read a file from classpath and returns content.
	 *
	 * @param resource Resource path.
	 * @return File content.
	 */
	public static String readFile(String resource) {
		try {
			ClassLoader classLoader = IoTestUtils.class.getClassLoader();
			Path path = Paths.get(classLoader.getResource(resource).toURI());
			byte[] fileBytes = Files.readAllBytes(path);
			return new String(fileBytes);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
