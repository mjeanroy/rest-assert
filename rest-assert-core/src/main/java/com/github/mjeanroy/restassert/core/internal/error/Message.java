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

package com.github.mjeanroy.restassert.core.internal.error;

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;
import static java.util.Collections.addAll;

/**
 * Templated message with arguments.
 */
public final class Message {

	private static final String CONCAT_SEPARATOR = "," + System.lineSeparator();

	/**
	 * Concat two messages into a single one.
	 * @param first First message.
	 * @param second Second message.
	 * @return Concatenated message.
	 */
	public static Message concat(Message first, Message second) {
		if (first == null) {
			return second;
		}

		if (second == null) {
			return first;
		}

		List<String> newMessages = new ArrayList<>(2);
		List<Object> newArgs = new ArrayList<>(first.getNbArgs() + second.getNbArgs());

		newMessages.add(first.message);
		addAll(newArgs, first.args);

		newMessages.add(second.message);
		addAll(newArgs, second.args);

		String finalMessage = String.join(CONCAT_SEPARATOR, newMessages);
		Object[] finalArgs = newArgs.toArray();
		return new Message(finalMessage, finalArgs);
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
	private final String message;

	/**
	 * The message parameters.
	 */
	private final Object[] args;

	/**
	 * Create message with given arguments.
	 *
	 * @param message The message.
	 * @param args Message arguments.
	 */
	private Message(String message, Object[] args) {
		this.message = notBlank(message, "Message must be defined");
		this.args = args;
	}

	/**
	 * Get {@link #message}
	 *
	 * @return {@link #message}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Get {@link #args}
	 *
	 * @return {@link #args}
	 */
	public Object[] getArgs() {
		return Arrays.copyOf(args, args.length);
	}

	public String formatMessage() {
		if (args.length == 0) {
			return message;
		}

		return String.format(message, args);
	}

	/**
	 * Get number of arguments.
	 *
	 * @return The number of arguments.
	 */
	public int getNbArgs() {
		return args.length;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Message) {
			Message m = (Message) o;
			return Objects.equals(message, m.message) && Arrays.equals(args, m.args);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, Arrays.hashCode(args));
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("message", message)
			.append("args", args)
			.build();
	}
}
