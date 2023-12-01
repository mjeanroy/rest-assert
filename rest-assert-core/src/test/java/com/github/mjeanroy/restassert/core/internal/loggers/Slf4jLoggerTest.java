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

package com.github.mjeanroy.restassert.core.internal.loggers;

import ch.qos.logback.classic.Level;
import com.github.mjeanroy.restassert.tests.junit.CaptureSystemOut;
import com.github.mjeanroy.restassert.tests.junit.CaptureSystemOutTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.ReflectionTestUtils.readField;
import static org.assertj.core.api.Assertions.assertThat;

@CaptureSystemOutTest
class Slf4jLoggerTest {

	private Logger log;

	@BeforeEach
	void setUp() {
		log = createLogger();
	}

	@Test
	void it_should_log_debug_message_with_one_argument(CaptureSystemOut sysOut) {
		log.debug("Message with placeholder: {}", "arg1");
		verifyOutput(sysOut, "DEBUG", "Message with placeholder: arg1");
	}

	@Test
	void it_should_log_debug_message_with_two_arguments(CaptureSystemOut sysOut) {
		log.debug("Message with placeholder: {} {}", "arg1", "arg2");
		verifyOutput(sysOut, "DEBUG", "Message with placeholder: arg1 arg2");
	}

	@Test
	void it_should_log_debug_message_without_argument(CaptureSystemOut sysOut) {
		log.debug("Message with placeholder");
		verifyOutput(sysOut, "DEBUG", "Message with placeholder");
	}

	@Test
	void it_should_log_debug_message_with_list_of_arguments(CaptureSystemOut sysOut) {
		log.debug("Message with placeholder: {} {} {}", "arg1", "arg2", "arg3");
		verifyOutput(sysOut, "DEBUG", "Message with placeholder: arg1 arg2 arg3");
	}

	@Test
	void it_should_log_info_message_with_one_argument(CaptureSystemOut sysOut) {
		log.info("Message with placeholder: {}", "arg1");
		verifyOutput(sysOut, "INFO", "Message with placeholder: arg1");
	}

	@Test
	void it_should_log_info_message_with_two_arguments(CaptureSystemOut sysOut) {
		log.info("Message with placeholder: {} {}", "arg1", "arg2");
		verifyOutput(sysOut, "INFO", "Message with placeholder: arg1 arg2");
	}

	@Test
	void it_should_log_info_message_without_argument(CaptureSystemOut sysOut) {
		log.info("Message with placeholder");
		verifyOutput(sysOut, "INFO", "Message with placeholder");
	}

	@Test
	void it_should_log_info_message_with_list_of_arguments(CaptureSystemOut sysOut) {
		log.info("Message with placeholder: {} {} {}", "arg1", "arg2", "arg3");
		verifyOutput(sysOut, "INFO", "Message with placeholder: arg1 arg2 arg3");
	}

	@Test
	void it_should_log_warn_message_with_one_argument(CaptureSystemOut sysOut) {
		log.warn("Message with placeholder: {}", "arg1");
		verifyOutput(sysOut, "WARN", "Message with placeholder: arg1");
	}

	@Test
	void it_should_log_warn_message_with_two_arguments(CaptureSystemOut sysOut) {
		log.warn("Message with placeholder: {} {}", "arg1", "arg2");
		verifyOutput(sysOut, "WARN", "Message with placeholder: arg1 arg2");
	}

	@Test
	void it_should_log_warn_message_without_argument(CaptureSystemOut sysOut) {
		log.warn("Message with placeholder");
		verifyOutput(sysOut, "WARN", "Message with placeholder");
	}

	@Test
	void it_should_log_warn_message_with_list_of_arguments(CaptureSystemOut sysOut) {
		log.warn("Message with placeholder: {} {} {}", "arg1", "arg2", "arg3");
		verifyOutput(sysOut, "WARN", "Message with placeholder: arg1 arg2 arg3");
	}

	@Test
	void it_should_log_error_message_with_one_argument(CaptureSystemOut sysOut) {
		log.error("Message with placeholder: {}", "arg1");
		verifyOutput(sysOut, "ERROR", "Message with placeholder: arg1");
	}

	@Test
	void it_should_log_error_message_with_two_arguments(CaptureSystemOut sysOut) {
		log.error("Message with placeholder: {} {}", "arg1", "arg2");
		verifyOutput(sysOut, "ERROR", "Message with placeholder: arg1 arg2");
	}

	@Test
	void it_should_log_error_message_without_argument(CaptureSystemOut sysOut) {
		log.error("Message with placeholder");
		verifyOutput(sysOut, "ERROR", "Message with placeholder");
	}

	@Test
	void it_should_log_error_message_with_list_of_arguments(CaptureSystemOut sysOut) {
		log.error("Message with placeholder: {} {} {}", "arg1", "arg2", "arg3");
		verifyOutput(sysOut, "ERROR", "Message with placeholder: arg1 arg2 arg3");
	}

	@Test
	void it_should_log_throwable(CaptureSystemOut sysOut) {
		Exception ex = new RuntimeException("A runtime exception");
		String message = "error message";
		log.error(message, ex);

		verifyOutput(sysOut, "ERROR", ex.getMessage());
	}

	private void verifyOutput(CaptureSystemOut sysOut, String logLevel, String message) {
		String out = sysOut.getOut();
		assertThat(out).contains(logLevel);
		assertThat(out).contains(message);
	}

	private Logger createLogger() {
		Slf4jLogger log = new Slf4jLogger(getClass());

		ch.qos.logback.classic.Logger logback = readField(log, "logger");
		logback.setLevel(Level.TRACE);
		logback.setAdditive(true);

		return log;
	}
}
