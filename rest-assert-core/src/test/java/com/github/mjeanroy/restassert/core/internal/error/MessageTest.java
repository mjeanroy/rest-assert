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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageTest {

	@Test
	public void it_should_create_simple_message() {
		String str = "Simple Message";

		Message message = Message.message(str);

		assertThat(message).isNotNull();
		assertThat(message.getMessage()).isEqualTo(str);
		assertThat(message.getArgs()).isNotNull().isEmpty();
		assertThat(message.formatMessage()).isEqualTo(str);
	}

	@Test
	public void it_should_create_message_with_one_argument() {
		String str = "Simple Message: %s";
		Object arg = "test";

		Message message = Message.message(str, arg);

		assertThat(message).isNotNull();
		assertThat(message.getMessage()).isEqualTo(str);
		assertThat(message.getArgs()).hasSize(1).containsOnly(arg);
		assertThat(message.formatMessage()).isEqualTo("Simple Message: test");
	}

	@Test
	public void it_should_create_message_with_arguments() {
		String str = "Simple Message: %s %s";
		Object arg1 = "test1";
		Object arg2 = "test2";

		Message message = Message.message(str, arg1, arg2);

		assertThat(message).isNotNull();
		assertThat(message.getMessage()).isEqualTo(str);
		assertThat(message.getArgs()).hasSize(2).containsExactly(arg1, arg2);
		assertThat(message.formatMessage()).isEqualTo("Simple Message: test1 test2");
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Message.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		String str = "Simple Message: %s %s";
		Object arg1 = "test1";
		Object arg2 = "test2";
		Message message = Message.message(str, arg1, arg2);

		assertThat(message).hasToString(
			"Message{" +
				"message=Simple Message: %s %s, " +
				"args=[test1, test2]" +
			"}"
		);
	}
}
