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

package com.github.mjeanroy.restassert.generator;

import static com.github.mjeanroy.restassert.generator.utils.ClassUtils.packageNameToDirectory;

import java.io.File;

import com.github.mjeanroy.restassert.generator.utils.IOUtils;

/**
 * Class file that will generated and written to disk.
 * Generated class file will be a valid Java file.
 */
public class ClassFile {

	/**
	 * Target class name.
	 * This package name will be translated to a path on disk.
	 */
	private final String packageName;

	/**
	 * Target class name.
	 * This class name will be translated to java file name.
	 */
	private final String className;

	/**
	 * Class content (i.e java code written to generated file).
	 */
	private final String content;

	public ClassFile(String packageName, String className, String content) {
		this.packageName = packageName;
		this.className = className;
		this.content = content;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getContent() {
		return content;
	}

	/**
	 * Write class file to disk into
	 * target directory.
	 *
	 * @param directory Target directory.
	 */
	public void writeTo(String directory) {
		String separator = File.separator;
		String dir = directory;
		if (!dir.endsWith(separator)) {
			dir += separator;
		}

		String subDirectory = packageNameToDirectory(packageName);
		String classDirectory = dir + subDirectory;

		File fileDirectory = new File(classDirectory);
		mkdir(fileDirectory);

		String filename = subDirectory + separator + className + ".java";
		File file = new File(dir + filename);
		delete(file);

		IOUtils.write(file, content);
	}

	private void mkdir(File file) {
		if (!file.exists() && !file.mkdirs()) {
			throw new RuntimeException("Unable to make directories: " + file.getAbsolutePath());
		}
	}

	private void delete(File file) {
		if (file.exists() && !file.delete()) {
			throw new RuntimeException("Unable to delete file: " + file.getAbsolutePath());
		}
	}
}
