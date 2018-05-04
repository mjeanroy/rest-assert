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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * The JavaDoc parser static utility.
 */
public final class JavaDocParser {

	/**
	 * The regex to match content in a JavaDoc tag.
	 */
	private static final String INSIDE_TAG_PATTERN = "[a-zA-Z0-9_#\" ]+";

	/**
	 * The list of JavaDoc tags that may appear as a new "section" in the
	 * JavaDoc comment.
	 */
	private static final Set<String> JAVADOC_TAGS = unmodifiableSet(new HashSet<String>() {{
		add("@author");
		add("@deprecated");
		add("@exception");
		add("@param");
		add("@return");
		add("@see");
		add("@serial");
		add("@serialData");
		add("@serialField");
		add("@since");
		add("@throws");
		add("@version");
	}});

	/**
	 * The list of JavaDoc tags that may appear "inline" (i.e inside {@code "{"} and {@code "}"}) in the
	 * JavaDoc comment.
	 */
	private static final Set<String> INLINE_JAVADOC_TAGS = unmodifiableSet(new HashSet<String>() {{
		add("@code");
		add("@docRoot");
		add("@inheritDoc");
		add("@link");
		add("@linkplain");
		add("@literal");
		add("@value");
	}});

	/**
	 * The line break character.
	 */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	// Ensure non instantiation.
	private JavaDocParser() {
	}

	/**
	 * Read JavaDoc text and produce structured {@link JavaDoc} instance.
	 *
	 * @param javadoc The JavaDoc text.
	 * @return The structured {@link JavaDoc} instance.
	 */
	public static JavaDoc parse(String javadoc) {
		List<String> blocks = extractBlocks(javadoc);
		JavaDoc.Builder builder = new JavaDoc.Builder();

		for (String block : blocks) {
			if (!isJavadocTag(block)) {
				builder.setDescription(toMarkdown(block));
			}
			else if (isParam(block)) {
				String[] parts = block.split(" ", 3);
				builder.addParam(parts[1], toMarkdown(parts[2]));
			}
			else if (isReturns(block)) {
				String[] parts = block.split(" ", 2);
				builder.setReturns(toMarkdown(parts[1]));
			}
			else if (isSee(block)) {
				String[] parts = block.split(" ", 2);
				builder.addSee(toMarkdown(parts[1]));
			}
		}

		return builder.build();
	}

	/**
	 * Check if line starts with {@code "@param"} text, which means that a new param
	 * is described.
	 *
	 * @param line The text line.
	 * @return {@code true} if line starts with {@code "@param"}, {@code false} otherwise.
	 */
	private static boolean isParam(String line) {
		return line.startsWith("@param");
	}

	/**
	 * Check if line starts with {@code "@see"} text, which means that a new external links
	 * is described.
	 *
	 * @param line The text line.
	 * @return {@code true} if line starts with {@code "@see"}, {@code false} otherwise.
	 */
	private static boolean isSee(String line) {
		return line.startsWith("@see");
	}

	/**
	 * Check if line starts with {@code "@return"} text, which means that method return
	 * value is described.
	 *
	 * @param line The text line.
	 * @return {@code true} if line starts with {@code "@return"}, {@code false} otherwise.
	 */
	private static boolean isReturns(String line) {
		return line.startsWith("@return");
	}

	/**
	 * Split JavaDoc text into "blocks".
	 * A block is a "section" in the JavaDoc, a section meaning a new description in the JavaDoc.
	 *
	 * For example, suppose this javadoc, it is composed of three sections:
	 * <ul>
	 *   <li>The method description.</li>
	 *   <li>The {@code "@param javadoc"} parameter description.</li>
	 *   <li>The {@code "@return"} description.</li>
	 * </ul>
	 *
	 * Note that there is only one parameter, so there is only one {@code "@param"} block, but we would have
	 * as many blocks as there is {@code "@param"} elements.
	 *
	 * @param javadoc The JavaDoc text.
	 * @return The JavaDoc sections.
	 */
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

	/**
	 * Check if the line describe a new "section" block: a new section block starts with a new
	 * javadoc tag.
	 *
	 * @param line The line.
	 * @return {@code true} if line is a new section block, {@code false} otherwise.
	 */
	private static boolean isStartingBlock(String line) {
		return isJavadocTag(line);
	}

	/**
	 * Check if the line starts with a javadoc tag (i.e {@code "@param"}, {@code "@return"}, etc.).
	 * To keep it simple, this method only checks if the line starts with {@code "@"} character.
	 *
	 * @param line The line.
	 * @return {@code true} if line starts with a javadoc tag, {@code false} otherwise.
	 */
	private static boolean isJavadocTag(String line) {
		if (line.length() == 0 || line.charAt(0) != '@') {
			return false;
		}

		String[] parts = line.split(" ", 2);
		return parts.length >= 1 && JAVADOC_TAGS.contains(parts[0]);
	}

	/**
	 * Translate JavaDoc text to markdown text.
	 *
	 * @param text JavaDoc text.
	 * @return Markdown text.
	 */
	private static String toMarkdown(String text) {
		String output = text;
		for (String inlineTag : INLINE_JAVADOC_TAGS) {
			output = output.replaceAll("\\{" + inlineTag + " (" + INSIDE_TAG_PATTERN + ")\\}", "`$1`");
		}

		return output;
	}
}
