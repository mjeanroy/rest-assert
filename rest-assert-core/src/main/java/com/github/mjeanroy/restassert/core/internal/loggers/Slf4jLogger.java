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

import org.slf4j.LoggerFactory;

/**
 * A {@link Logger} implementation delegating everything to an SLF4J Logger.
 */
class Slf4jLogger implements Logger {
	/**
	 * The SLF4J Logger.
	 */
	private final org.slf4j.Logger logger;

	/**
	 * Create the logger.
	 *
	 * @param klass Logger name.
	 */
	Slf4jLogger(Class<?> klass) {
		this.logger = LoggerFactory.getLogger(klass);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void debug(String message, Object param) {
		logger.debug(message, param);
	}

	@Override
	public void debug(String message, Object param1, Object param2) {
		logger.debug(message, param1, param2);
	}

	@Override
	public void debug(String message, Object param1, Object param2, Object param3) {
		logger.debug(message, param1, param2, param3);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void info(String message, Object param) {
		logger.info(message, param);
	}

	@Override
	public void info(String message, Object param1, Object param2) {
		logger.info(message, param1, param2);
	}

	@Override
	public void info(String message, Object param1, Object param2, Object param3) {
		logger.info(message, param1, param2, param3);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void warn(String message, Object param) {
		logger.warn(message, param);
	}

	@Override
	public void warn(String message, Object param1, Object param2) {
		logger.warn(message, param1, param2);
	}

	@Override
	public void warn(String message, Object param1, Object param2, Object param3) {
		logger.warn(message, param1, param2, param3);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void error(String message, Object param) {
		logger.error(message, param);
	}

	@Override
	public void error(String message, Object param1, Object param2) {
		logger.error(message, param1, param2);
	}

	@Override
	public void error(String message, Object param1, Object param2, Object param3) {
		logger.error(message, param1, param2, param3);
	}
}
