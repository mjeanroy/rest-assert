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

package com.github.mjeanroy.restassert.core.internal.loggers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

public class Slf4jLoggerTest {

	private org.slf4j.Logger slf4jLogger;
	private Logger logger;

	@Before
	public void setUp() throws Exception {
		slf4jLogger = mock(org.slf4j.Logger.class);
		logger = new Slf4jLogger(Slf4jLoggerTest.class);
		FieldUtils.writeDeclaredField(logger, "logger", slf4jLogger, true);
	}

	@Test
	public void it_should_have_debug() {
		String message = "foo bar";
		logger.debug(message);
		verify(slf4jLogger).debug(message);
	}

	@Test
	public void it_should_have_debug_with_one_parameter() {
		String message = "foo bar";
		String param = "test";

		logger.debug(message, param);

		verify(slf4jLogger).debug(message, param);
	}

	@Test
	public void it_should_have_debug_with_two_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";

		logger.debug(message, p1, p2);

		verify(slf4jLogger).debug(message, p1, p2);
	}

	@Test
	public void it_should_have_debug_with_three_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";
		String p3 = "test3";

		logger.debug(message, p1, p2, p3);

		verify(slf4jLogger).debug(message, p1, p2, p3);
	}

	@Test
	public void it_should_have_info() {
		String message = "foo bar";
		logger.info(message);
		verify(slf4jLogger).info(message);
	}

	@Test
	public void it_should_have_info_with_one_parameter() {
		String message = "foo bar";
		String param = "test";

		logger.info(message, param);

		verify(slf4jLogger).info(message, param);
	}

	@Test
	public void it_should_have_info_with_two_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";

		logger.info(message, p1, p2);

		verify(slf4jLogger).info(message, p1, p2);
	}

	@Test
	public void it_should_have_info_with_three_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";
		String p3 = "test3";

		logger.info(message, p1, p2, p3);

		verify(slf4jLogger).info(message, p1, p2, p3);
	}

	@Test
	public void it_should_have_warn() {
		String message = "foo bar";
		logger.warn(message);
		verify(slf4jLogger).warn(message);
	}

	@Test
	public void it_should_have_warn_with_one_parameter() {
		String message = "foo bar";
		String param = "test";

		logger.warn(message, param);

		verify(slf4jLogger).warn(message, param);
	}

	@Test
	public void it_should_have_warn_with_two_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";

		logger.warn(message, p1, p2);

		verify(slf4jLogger).warn(message, p1, p2);
	}

	@Test
	public void it_should_have_warn_with_three_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";
		String p3 = "test3";

		logger.warn(message, p1, p2, p3);

		verify(slf4jLogger).warn(message, p1, p2, p3);
	}

	@Test
	public void it_should_have_error() {
		String message = "foo bar";
		logger.error(message);
		verify(slf4jLogger).error(message);
	}

	@Test
	public void it_should_have_error_with_one_parameter() {
		String message = "foo bar";
		String param = "test";

		logger.error(message, param);

		verify(slf4jLogger).error(message, param);
	}

	@Test
	public void it_should_have_error_with_two_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";

		logger.error(message, p1, p2);

		verify(slf4jLogger).error(message, p1, p2);
	}

	@Test
	public void it_should_have_error_with_three_parameter() {
		String message = "foo bar";
		String p1 = "test1";
		String p2 = "test2";
		String p3 = "test3";

		logger.error(message, p1, p2, p3);

		verify(slf4jLogger).error(message, p1, p2, p3);
	}
}
