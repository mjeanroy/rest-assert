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

/**
 * A logger.
 */
public interface Logger {

	/**
	 * Log message with DEBUG level.
	 *
	 * @param message The message to log.
	 */
	void debug(String message);

	/**
	 * Log message with DEBUG level with a dynamic parameter.
	 *
	 * @param message The message to log.
	 * @param param The message parameter.
	 */
	void debug(String message, Object param);

	/**
	 * Log message with DEBUG level with two dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 */
	void debug(String message, Object param1, Object param2);

	/**
	 * Log message with DEBUG level with three dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 * @param param3 The third message parameter.
	 */
	void debug(String message, Object param1, Object param2, Object param3);

	/**
	 * Log message with INFO level.
	 *
	 * @param message The message to log.
	 */
	void info(String message);

	/**
	 * Log message with INFO level with a dynamic parameter.
	 *
	 * @param message The message to log.
	 * @param param The message parameter.
	 */
	void info(String message, Object param);

	/**
	 * Log message with INFO level with two dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 */
	void info(String message, Object param1, Object param2);

	/**
	 * Log message with INFO level with three dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 * @param param3 The third message parameter.
	 */
	void info(String message, Object param1, Object param2, Object param3);

	/**
	 * Log message with WARN level.
	 *
	 * @param message The message to log.
	 */
	void warn(String message);

	/**
	 * Log message with WARN level with a dynamic parameter.
	 *
	 * @param message The message to log.
	 * @param param The message parameter.
	 */
	void warn(String message, Object param);

	/**
	 * Log message with WARN level with two dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 */
	void warn(String message, Object param1, Object param2);

	/**
	 * Log message with WARN level with three dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 * @param param3 The third message parameter.
	 */
	void warn(String message, Object param1, Object param2, Object param3);

	/**
	 * Log message with ERROR level.
	 *
	 * @param message The message to log.
	 */
	void error(String message);

	/**
	 * Log message with ERROR level with a dynamic parameter.
	 *
	 * @param message The message to log.
	 * @param param The message parameter.
	 */
	void error(String message, Object param);

	/**
	 * Log message with ERROR level with two dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 */
	void error(String message, Object param1, Object param2);

	/**
	 * Log message with ERROR level with three dynamic parameters.
	 *
	 * @param message The message to log.
	 * @param param1 The first message parameter.
	 * @param param2 The second message parameter.
	 * @param param3 The third message parameter.
	 */
	void error(String message, Object param1, Object param2, Object param3);
}
