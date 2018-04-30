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

package com.github.mjeanroy.restassert.documentation.javadoc;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class JavadocParser {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final Set<String> STARTING_BLOCKS = new HashSet<>(asList(
		"@param",
		"@return",
		"@throws"
	));

	private JavadocParser() {
	}

	public static Javadoc parse(String javadoc) {
		List<String> blocks = extractBlocks(javadoc);
		Javadoc.Builder builder = new Javadoc.Builder();

		for (String block : blocks) {
			if (!isJavadocTag(block)) {
				builder.setDescription(block);
			}
			else if (isParam(block)) {
				String[] parts = block.split(" ", 3);
				builder.addParam(parts[1], parts[2]);
			}
			else if (isReturns(block)) {
				String[] parts = block.split(" ", 2);
				builder.setReturns(parts[1]);
			}
			else {
				throw new UnsupportedOperationException("Cannot parse line: '" + block + "'");
			}
		}

		return builder.build();
	}

	private static boolean isParam(String line) {
		return line.startsWith("@param");
	}

	private static boolean isReturns(String line) {
		return line.startsWith("@return");
	}

	private static List<String> extractBlocks(String javadoc) {
		StringBuilder currentBlock = new StringBuilder();
		List<String> blocks = new ArrayList<>();

		for (String line : javadoc.split(LINE_SEPARATOR)) {
			String trimmedLine = line.trim();

			if (isStartingBlock(trimmedLine)) {
				if (currentBlock.length() > 0) {
					blocks.add(currentBlock.toString());
					currentBlock = new StringBuilder();
				}
			}

			currentBlock.append(trimmedLine);
		}

		if (currentBlock.length() > 0) {
			blocks.add(currentBlock.toString());
		}

		return blocks;
	}

	private static boolean isStartingBlock(String line) {
		return isJavadocTag(line);
	}

	private static boolean isJavadocTag(String line) {
		return line.length() > 0 && line.charAt(0) == '@';
	}
}
