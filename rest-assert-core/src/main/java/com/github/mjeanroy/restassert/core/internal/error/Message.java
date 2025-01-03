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

package com.github.mjeanroy.restassert.core.internal.error;

import com.github.mjeanroy.restassert.core.internal.common.Strings;
import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

/**
 * Templated message with arguments.
 */
public final class Message {

	private static final String LINE_SEPARATOR = System.lineSeparator();
	private static final String BULLET_PREFIX = "→ ";

	/**
	 * Concat two messages into a single one.
	 *
	 * @param first First message.
	 * @param second Second message.
	 * @return Concatenated message.
	 */
	static Message concat(Message first, Message second) {
		if (first == null) {
			return second;
		}

		if (second == null) {
			return first;
		}

		List<String> newTemplates = new ArrayList<>(first.templates.size() + second.templates.size());
		newTemplates.addAll(first.templates);
		newTemplates.addAll(second.templates);

		List<Object[]> newArgs = new ArrayList<>(first.args.size() + second.args.size());
		newArgs.addAll(first.args);
		newArgs.addAll(second.args);

		return new Message(
			newTemplates,
			newArgs,
			first.nbArgs + second.nbArgs
		);
	}

	/**
	 * Create message without any arguments.
	 *
	 * @param message The string message.
	 * @return The message.
	 */
	public static Message message(String message) {
		return new Message(message, new Object[0]);
	}

	/**
	 * Create message with given arguments.
	 *
	 * @param message The string message template.
	 * @param arg First argument.
	 * @param others Other, optional, arguments.
	 * @return The message.
	 */
	public static Message message(String message, Object arg, Object... others) {
		Object[] args = Stream.concat(Stream.of(arg), Arrays.stream(others)).toArray(Object[]::new);
		return new Message(message, args);
	}

	/**
	 * Create message with given arguments.
	 *
	 * @param message The string message template.
	 * @param args Arguments.
	 * @return The message.
	 */
	public static Message message(String message, Object[] args) {
		return new Message(message, Arrays.copyOf(args, args.length));
	}

	/**
	 * The message template.
	 */
	private final List<String> templates;

	/**
	 * The message parameters.
	 */
	private final List<Object[]> args;

	/**
	 * Total number of arguments.
	 */
	private final int nbArgs;

	/**
	 * Create message with given arguments.
	 *
	 * @param template The message.
	 * @param args Message arguments.
	 */
	private Message(String template, Object[] args) {
		this.templates = singletonList(
			notBlank(template, "Message must be defined")
		);

		this.args = singletonList(args);
		this.nbArgs = args.length;
	}

	/**
	 * Create message with given arguments.
	 *
	 * @param templates All templates.
	 * @param args Message arguments.
	 * @param nbArgs Total number of arguments.
	 */
	private Message(List<String> templates, List<Object[]> args, int nbArgs) {
		this.templates = unmodifiableList(templates);
		this.args = unmodifiableList(args);
		this.nbArgs = nbArgs;
	}

	/**
	 * Get {@link #message}
	 *
	 * @return {@link #message}
	 */
	public String getMessage() {
		if (templates.isEmpty()) {
			return "";
		}

		if (templates.size() == 1) {
			return templates.get(0);
		}

		return templates.stream().map((tmpl) -> BULLET_PREFIX + tmpl).collect(
			Collectors.joining(LINE_SEPARATOR)
		);
	}

	/**
	 * Check if message contains more than one error.
	 *
	 * @return {@code true} if message contains more than one error, {@code false} otherwise.
	 */
	public boolean isMulti() {
		return templates.size() > 1;
	}

	/**
	 * Get {@link #args}
	 *
	 * @return {@link #args}
	 */
	public Object[] getArgs() {
		Object[] allArgs = new Object[nbArgs];
		int i = 0;

		for (Object[] args : this.args) {
			for (Object o : args) {
				allArgs[i++] = o;
			}
		}

		return allArgs;
	}

	/**
	 * Format output message.
	 *
	 * @return Output message.
	 */
	public String formatMessage() {
		return String.join(LINE_SEPARATOR, formatMessages());
	}

	/**
	 * Format output message.
	 *
	 * @return Output message.
	 */
	public List<String> formatMessages() {
		if (templates.isEmpty()) {
			return emptyList();
		}

		if (templates.size() == 1) {
			return singletonList(formatTemplateAt(0));
		}

		List<String> formattedMessages = new ArrayList<>(templates.size());
		for (int i = 0; i < templates.size(); i++) {
			formattedMessages.add(
				BULLET_PREFIX + formatTemplateAt(i)
			);
		}

		return unmodifiableList(formattedMessages);
	}

	private String formatTemplateAt(int i) {
		Object[] formattedArgs = Arrays.stream(args.get(i)).map(Strings::serialize).toArray(Object[]::new);
		return String.format(templates.get(i), formattedArgs);
	}

	/**
	 * Get number of arguments.
	 *
	 * @return The number of arguments.
	 */
	public int getNbArgs() {
		return nbArgs;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Message) {
			Message m = (Message) o;
			return Objects.equals(templates, m.templates) && Objects.equals(args, m.args) && Objects.equals(nbArgs, m.nbArgs);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(templates, args, nbArgs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("templates", templates)
			.append("args", args.stream().map(Arrays::toString).collect(Collectors.toList()))
			.append("nbArgs", nbArgs)
			.build();
	}
}
